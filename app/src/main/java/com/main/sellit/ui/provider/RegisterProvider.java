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

import org.json.JSONObject;

import lombok.SneakyThrows;

public class RegisterProvider extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String USER_DETAILS = "userDetails";
    Spinner spnProviderHasOffice, spnProviderIsAnIndividual;
    EditText editTxtBusinessDesc, editTxtOfficeAddress;
    boolean boolProviderHasOffice, boolProviderIsAnIndividual;
    String officeAddress, providerDescription;
    LoadingButton btnSubmitProviderData;
    UserDetailsModel userDetailsModel;

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
        editTxtOfficeAddress = (EditText)findViewById(R.id.edt_txt_user_name);
        spnProviderHasOffice = (Spinner)findViewById(R.id.spn_has_office);
        spnProviderIsAnIndividual = (Spinner)findViewById(R.id.spn_is_individual);
        btnSubmitProviderData = (LoadingButton)findViewById(R.id.btn_submit_provider_details);

        //initialize spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.boolean_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProviderHasOffice.setAdapter(adapter);
        spnProviderIsAnIndividual.setAdapter(adapter);
        spnProviderHasOffice.setOnItemSelectedListener(this);
        spnProviderIsAnIndividual.setOnItemSelectedListener(this);


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


        JSONObject providerSignupRequest = new JSONObject();

        if(boolProviderHasOffice){
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
                        providerSignupRequest.put("registeredOffice", boolProviderHasOffice);
                        providerSignupRequest.put("officeAddress", officeAddress);
                        providerSignupRequest.put("individual", boolProviderIsAnIndividual);
                        signUpProvider(providerSignupRequest);
                    }
                }
            });

        }else {
            btnSubmitProviderData.setOnClickListener(new View.OnClickListener() {
                @SneakyThrows
                @Override
                public void onClick(View v) {
                    if(providerDescription==null){
                        Snackbar.make(findViewById(R.id.cnst_send_provider_details_container), "There are errors in your inputs", Snackbar.LENGTH_LONG).show();
                    }else {
                        providerSignupRequest.put("firstName", userDetailsModel.getFirstName());
                        providerSignupRequest.put("lastName", userDetailsModel.getLastName());
                        providerSignupRequest.put("email", userDetailsModel.getEmail());
                        providerSignupRequest.put("password", userDetailsModel.getPassword());
                        providerSignupRequest.put("mobileNumber", userDetailsModel.getPhoneNumber());
                        providerSignupRequest.put("userName", userDetailsModel.getUserName());
                        providerSignupRequest.put("providerDescription", providerDescription);
                        providerSignupRequest.put("registeredOffice", boolProviderHasOffice);
                        providerSignupRequest.put("individual", boolProviderIsAnIndividual);
                        signUpProvider(providerSignupRequest);
                    }
                }
            });


        }

    }
    private void signUpProvider(JSONObject jsonObject){
        btnSubmitProviderData.showLoading();
        btnSubmitProviderData.setEnabled(false);
        JsonObjectRequest jsonObjectRequest =  new JsonObjectRequest(Request.Method.POST, ApiUrls.BASE_API_URL + "/providers", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("APICALLERROR", response.toString());
                btnSubmitProviderData.hideLoading();
                btnSubmitProviderData.setEnabled(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("APICALLERROR", error.toString());
                btnSubmitProviderData.hideLoading();
                btnSubmitProviderData.setEnabled(true);
            }
        });
        VolleyController.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(view == spnProviderHasOffice){
            boolean providerHasOffice = Boolean.parseBoolean(String.valueOf(parent.getItemAtPosition(position)));

            if(providerHasOffice){
                editTxtOfficeAddress.setVisibility(View.VISIBLE);
            }else {
                editTxtOfficeAddress.setVisibility(View.GONE);
            }
        }

        if(view == spnProviderIsAnIndividual){
            boolProviderIsAnIndividual = Boolean.parseBoolean(String.valueOf(parent.getSelectedItem()));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}