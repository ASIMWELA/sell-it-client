package com.main.sellit.ui.provider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.contract.RegisterProviderContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.helper.AppConstants;
import com.main.sellit.helper.FlagErrors;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.model.UserDetailsModel;
import com.main.sellit.network.VolleyController;
import com.main.sellit.presenter.provider.RegisterProviderPresenter;
import com.main.sellit.ui.LoginActivity;

import org.json.JSONObject;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterProviderActivity extends AppCompatActivity implements RegisterProviderContract.View {
    Spinner spnProviderIsAnIndividual;
    EditText editTxtBusinessDesc, editTxtOfficeAddress;
    boolean  boolProviderIsAnIndividual;
    String officeAddress, providerDescription;
    LoadingButton btnSubmitProviderData;
    UserDetailsModel userDetailsModel;
    RegisterProviderPresenter registerProviderPresenter;
    TextView txVErrorMessage;
    JSONObject providerSignupRequest = new JSONObject();
    ImageView ivBackArrow;
    FlagErrors flagErrors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_provider);
        registerProviderPresenter = new RegisterProviderPresenter(this);

        //initialize views
        initViews();

        //activate validations
        validateInput();
        //get associated data
        Intent intent = getIntent();
        userDetailsModel= intent.getParcelableExtra(AppConstants.USER_DETAILS);
        flagErrors = new FlagErrors(this, this);

        btnSubmitProviderData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerProviderPresenter.signUp();
            }
        });

        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });

    }

    private void initViews(){
        editTxtBusinessDesc = findViewById(R.id.et_register_provider_bussiness_desc);
        editTxtOfficeAddress = findViewById(R.id.edtx_office_address);
        spnProviderIsAnIndividual = findViewById(R.id.spn_is_individual);
        btnSubmitProviderData = findViewById(R.id.btn_submit_provider_details);
        txVErrorMessage = findViewById(R.id.edtx_signup_error_message);
        ivBackArrow = findViewById(R.id.iv_back_arrow);

        //initialize spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.boolean_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //populate spinner
        spnProviderIsAnIndividual.setAdapter(adapter);
        spnProviderIsAnIndividual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SneakyThrows
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolProviderIsAnIndividual = Boolean.parseBoolean(String.valueOf(parent.getSelectedItem()));
                providerSignupRequest.put("individual", boolProviderIsAnIndividual);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean validateInput() {
        editTxtBusinessDesc.addTextChangedListener(new TextValidator(editTxtBusinessDesc) {
            @Override
            public void validate() {
                if(!editTxtBusinessDesc.getText().toString().isEmpty()){
                    if(editTxtBusinessDesc.getText().toString().trim().length()<20){
                        providerDescription = null;
                        editTxtBusinessDesc.setBackgroundResource(R.drawable.input_text_area_border_error);
                        editTxtBusinessDesc.setError("Business description too short");
                    }else {
                        providerDescription = editTxtBusinessDesc.getText().toString().trim();
                        editTxtBusinessDesc.setBackgroundResource(R.drawable.rounded_boaders);
                    }
                }
            }
        });
        editTxtOfficeAddress.addTextChangedListener(new TextValidator(editTxtOfficeAddress) {
            @Override
            public void validate() {
                if(!editTxtOfficeAddress.getText().toString().isEmpty()){
                    if(editTxtOfficeAddress.getText().toString().trim().length()<15){
                        officeAddress = null;
                        editTxtOfficeAddress.setBackgroundResource(R.drawable.rounded_boaders_error);
                        editTxtOfficeAddress.setError("Office address too short");
                    }else {
                        officeAddress = editTxtOfficeAddress.getText().toString().trim();
                        editTxtOfficeAddress.setBackgroundResource(R.drawable.rounded_boaders);
                    }
                }
            }
        });

        return officeAddress != null && providerDescription != null;
    }

    @Override
    @SneakyThrows
    public void onValidationSuccess() {
        providerSignupRequest.put("firstName", userDetailsModel.getFirstName());
        providerSignupRequest.put("lastName", userDetailsModel.getLastName());
        providerSignupRequest.put("email", userDetailsModel.getEmail());
        providerSignupRequest.put("password", userDetailsModel.getPassword());
        providerSignupRequest.put("mobileNumber", userDetailsModel.getPhoneNumber());
        providerSignupRequest.put("userName", userDetailsModel.getUserName());
        providerSignupRequest.put("providerDescription", providerDescription);
        providerSignupRequest.put("registeredOffice", true);
        providerSignupRequest.put("officeAddress", officeAddress);
        signUpProvider(providerSignupRequest);
    }

    @Override
    public void onFailedValidation() {
        flagErrors.flagValidationError(R.id.cnst_send_provider_details_container);
    }

    private void signUpProvider(JSONObject jsonObject){
        btnSubmitProviderData.showLoading();
        btnSubmitProviderData.setEnabled(false);
        JsonObjectRequest jsonObjectRequest =  new JsonObjectRequest(Request.Method.POST, ApiUrls.BASE_API_URL + "/providers", jsonObject, new Response.Listener<JSONObject>() {
            @SneakyThrows
            @Override
            public void onResponse(JSONObject response) {
               // boolean success = response.getBoolean("success");
                    Toast.makeText(RegisterProviderActivity.this, "Sign up success", Toast.LENGTH_SHORT).show();
                    btnSubmitProviderData.hideLoading();
                    btnSubmitProviderData.setEnabled(true);
                    Intent intent = new Intent(RegisterProviderActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterProviderActivity.this, "Sign up Success", Toast.LENGTH_SHORT).show();
                    finish();
            }
        }, error -> {
            btnSubmitProviderData.hideLoading();
            btnSubmitProviderData.setEnabled(true);
            flagErrors.flagApiError(error);
        });
        VolleyController.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

}

