package com.main.sellit.ui.provider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;
import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.model.UserDetailsModel;
import com.main.sellit.network.VolleyController;
import com.main.sellit.ui.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import lombok.SneakyThrows;

public class RegisterProvider extends AppCompatActivity{
    private static final String USER_DETAILS = "userDetails";
    Spinner spnProviderIsAnIndividual;
    EditText editTxtBusinessDesc, editTxtOfficeAddress;
    boolean  boolProviderIsAnIndividual;
    String officeAddress, providerDescription;
    LoadingButton btnSubmitProviderData;
    UserDetailsModel userDetailsModel;
    TextView txVErrorMessage;
    JSONObject providerSignupRequest = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_provider);
        initViews();

        //get associated data
        Intent intent = getIntent();
        userDetailsModel= intent.getParcelableExtra(USER_DETAILS);

        //populate the spinners

        validateInputs();

    }

    private void initViews(){
        editTxtBusinessDesc = (EditText)findViewById(R.id.edtx_firt_name);
        editTxtOfficeAddress = (EditText)findViewById(R.id.edtx_office_address);
        spnProviderIsAnIndividual = (Spinner)findViewById(R.id.spn_is_individual);
        btnSubmitProviderData = (LoadingButton)findViewById(R.id.btn_submit_provider_details);
        txVErrorMessage = (TextView)findViewById(R.id.edtx_signup_error_message);

        //initialize spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.boolean_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

    private void validateInputs(){
        editTxtBusinessDesc.addTextChangedListener(new TextValidator(editTxtBusinessDesc) {
            @Override
            public void validate() {
                if(editTxtBusinessDesc.getText().toString().trim().length()<20){
                    providerDescription = null;
                    editTxtBusinessDesc.setBackgroundResource(R.drawable.rounded_boaders_error);
                    editTxtBusinessDesc.setError("Business description too short");
                }else {
                    providerDescription = editTxtBusinessDesc.getText().toString().trim();
                    editTxtBusinessDesc.setBackgroundResource(R.drawable.rounded_boaders);
                }
            }
        });
        editTxtOfficeAddress.addTextChangedListener(new TextValidator(editTxtOfficeAddress) {
                @Override
                public void validate() {
                    if(editTxtOfficeAddress.getText().toString().trim().length()<15){
                        officeAddress = null;
                        editTxtOfficeAddress.setBackgroundResource(R.drawable.rounded_boaders_error);
                        editTxtOfficeAddress.setError("Office address too short");
                    }else {
                        officeAddress = editTxtOfficeAddress.getText().toString().trim();
                        editTxtOfficeAddress.setBackgroundResource(R.drawable.rounded_boaders);
                    }
                }
            });

            btnSubmitProviderData.setOnClickListener(new View.OnClickListener() {
                @SneakyThrows
                @Override
                public void onClick(View v) {
                    txVErrorMessage.setText(null);
                    txVErrorMessage.setVisibility(View.GONE);
                    if(officeAddress==null || providerDescription==null){
                        Snackbar.make(findViewById(R.id.cnst_send_provider_details_container), "There are errors in your inputs", Snackbar.LENGTH_LONG).show();
                    }else {
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
                }
            });

    }
    private void signUpProvider(JSONObject jsonObject){
        btnSubmitProviderData.showLoading();
        btnSubmitProviderData.setEnabled(false);
        JsonObjectRequest jsonObjectRequest =  new JsonObjectRequest(Request.Method.POST, ApiUrls.BASE_API_URL + "/providers", jsonObject, new Response.Listener<JSONObject>() {
            @SneakyThrows
            @Override
            public void onResponse(JSONObject response) {
                boolean success = response.getBoolean("success");
                if(success){
                    Toast.makeText(RegisterProvider.this, "Sign up success", Toast.LENGTH_SHORT).show();
                    btnSubmitProviderData.hideLoading();
                    btnSubmitProviderData.setEnabled(true);
                    Intent intent = new Intent(RegisterProvider.this, LoginActivity.class);
                    //TODO: clear the signup  tasks
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null || error.networkResponse == null) {
                    return;
                }
                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    JSONObject errorObject = new JSONObject(body);
                    String message = errorObject.getString("message");
                    txVErrorMessage.setText(message);
                    txVErrorMessage.setVisibility(View.VISIBLE);
                } catch (UnsupportedEncodingException | JSONException e) {
                    // exception
                }
                btnSubmitProviderData.hideLoading();
                btnSubmitProviderData.setEnabled(true);
            }
        });
        VolleyController.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

}