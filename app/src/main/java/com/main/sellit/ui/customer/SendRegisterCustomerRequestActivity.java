package com.main.sellit.ui.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.contract.SendCustomerSignUpRequestContract;
import com.main.sellit.helper.AppConstants;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.model.UserDetailsModel;
import com.main.sellit.presenter.SendRegisterCustomerRequestPresenter;

import org.json.JSONObject;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendRegisterCustomerRequestActivity extends AppCompatActivity implements SendCustomerSignUpRequestContract.View {

    UserDetailsModel userDetailsModel;
    JSONObject customerDetails, customerAddress;
    SendRegisterCustomerRequestPresenter sendRegisterCustomerRequestPresenter;

    EditText etCity, etCountry, etRegion, etLocationDescription, etStreet;
    String city, country, locationDescription, region, street;
    LoadingButton btnSendRequest;
    ImageView ivBackArrow;
    JSONObject data;


    @Override
    @SneakyThrows
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_register_customer_request);
        initViews();

        customerAddress = new JSONObject();
        customerDetails = new JSONObject();
        sendRegisterCustomerRequestPresenter = new SendRegisterCustomerRequestPresenter(this, this);


        Intent intent = getIntent();
        userDetailsModel= intent.getParcelableExtra(AppConstants.USER_DETAILS);
        validateData();



        customerDetails.put("userName", userDetailsModel.getUserName());
        customerDetails.put("firstName", userDetailsModel.getFirstName());
        customerDetails.put("lastName", userDetailsModel.getLastName());
        customerDetails.put("email", userDetailsModel.getEmail());
        customerDetails.put("mobileNumber", userDetailsModel.getPhoneNumber());
        customerDetails.put("password", userDetailsModel.getPassword());

        data = new JSONObject();

        data.put("customer",customerDetails);
        data.put("address", customerAddress);

        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });

        btnSendRequest.setOnClickListener(v->{
            sendRegisterCustomerRequestPresenter.sendSignUpRequest(data);
        });

    }
    private void initViews(){
        ivBackArrow = findViewById(R.id.iv_send_register_customer_request_back_arrow);
        etCity = findViewById(R.id.et_send_register_customer_request_city);
        etCountry = findViewById(R.id.et_send_register_customer_request_country);
        etLocationDescription = findViewById(R.id.et_send_register_customer_request_location_description);
        etRegion = findViewById(R.id.et_send_register_customer_request_region);
        btnSendRequest = findViewById(R.id.btn_send_register_customer_request_send_request);
        etStreet = findViewById(R.id.et_send_register_customer_request_street);
    }
    @Override
    public boolean validateData() {
        etStreet.addTextChangedListener(new TextValidator(etStreet) {
            @Override
            @SneakyThrows
            public void validate() {
                if(etStreet.getText().toString().trim().length() < 2){
                    street = null;
                    etStreet.setBackgroundResource(R.drawable.rounded_boaders_error);
                    etStreet.setError("Street name short");
                }else {
                    etStreet.setBackgroundResource(R.drawable.rounded_boaders);
                    street = etStreet.getText().toString().trim();
                    customerAddress.put("street", street);
                }
            }
        });
        etCountry.addTextChangedListener(new TextValidator(etCountry) {
            @Override
            @SneakyThrows
            public void validate() {
                if(etCountry.getText().toString().trim().length() < 2){
                    country = null;
                    etCountry.setBackgroundResource(R.drawable.rounded_boaders_error);
                    etCountry.setError("country name short");
                }else {
                    etCountry.setBackgroundResource(R.drawable.rounded_boaders);
                    country = etCountry.getText().toString().trim();
                    customerAddress.put("country", country);
                }
            }
        });
        etLocationDescription.addTextChangedListener(new TextValidator(etLocationDescription) {
            @Override
            @SneakyThrows
            public void validate() {
                if(etLocationDescription.getText().toString().trim().length() < 20){
                    locationDescription = null;
                    etLocationDescription.setBackgroundResource(R.drawable.rounded_boaders_error);
                    etLocationDescription.setError("country name short");
                }else {
                    etLocationDescription.setBackgroundResource(R.drawable.rounded_boaders);
                    locationDescription = etLocationDescription.getText().toString().trim();
                    customerAddress.put("locationDescription", locationDescription);
                }
            }
        });
        etRegion.addTextChangedListener(new TextValidator(etRegion) {
            @Override
            @SneakyThrows
            public void validate() {
                if(etRegion.getText().toString().trim().length() < 3){
                    region = null;
                    etRegion.setBackgroundResource(R.drawable.rounded_boaders_error);
                    etRegion.setError("Region name short");
                }else {
                    etRegion.setBackgroundResource(R.drawable.rounded_boaders);
                    region = etRegion.getText().toString().trim();
                    customerAddress.put("region", region);
                }
            }
        });
        etCity.addTextChangedListener(new TextValidator(etCity) {
            @Override
            @SneakyThrows
            public void validate() {
                if(etCity.getText().toString().trim().length() < 3){
                    city = null;
                    etCity.setBackgroundResource(R.drawable.rounded_boaders_error);
                    etCity.setError("Region name short");
                }else {
                    etCity.setBackgroundResource(R.drawable.rounded_boaders);
                    city = etCity.getText().toString().trim();
                    customerAddress.put("city", city);
                }
            }
        });
        return (city != null)
                &&
                (country != null)
                &&
                (locationDescription!=null)
                &&
                (region != null)
                &&
                (street != null);
    }
    @Override
    public void onFailedValidation() {
        Snackbar.make(findViewById(R.id.sende_register_customer_request_base_view),"There are errors in your inputs" ,Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onRegistrationRequestError(String volleyError) {
        Toast.makeText(this, volleyError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegistrationRequestSuccess(JSONObject apiResponse) {
        Toast.makeText(this, apiResponse.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showLoadingButton() {
        btnSendRequest.showLoading();
        btnSendRequest.setEnabled(false);
    }

    @Override
    public void hideLoadingButton() {
        btnSendRequest.hideLoading();
        btnSendRequest.setEnabled(true);
    }
}