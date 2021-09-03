package com.main.sellit.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.main.sellit.R;
import com.main.sellit.contract.CustomerSignupContract;
import com.main.sellit.helper.AppConstants;
import com.main.sellit.helper.FlagErrors;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.model.UserDetailsModel;
import com.main.sellit.presenter.customer.CustomerSignUpPresenter;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerSignUpActivity extends AppCompatActivity implements CustomerSignupContract.View {

    EditText etUserName, etLastName, etFirstName, etEmail, etPassword,etPhoneNumber;
    AppCompatButton btnCaptureInfo;
    ImageView ivBackBackArrow;
    CustomerSignUpPresenter customerSignUpPresenter;
    String userName, firstName, lastName, password, email, phoneNumber;
    FlagErrors flagErrors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);
        initViews();
        validateData();
        customerSignUpPresenter = new CustomerSignUpPresenter(this);

        btnCaptureInfo.setOnClickListener(v->{
            customerSignUpPresenter.captureInformation();
        });
        ivBackBackArrow.setOnClickListener(v->{
            onBackPressed();
        });
        flagErrors = new FlagErrors(this, this);


    }

    private void initViews(){
        etUserName = findViewById(R.id.et_customer_sign_up_user_name);
        etEmail = findViewById(R.id.et_customer_sign_up_email);
        etFirstName = findViewById(R.id.et_customer_sign_up_first_name);
        etPhoneNumber = findViewById(R.id.et_customer_sign_up_phone_number);
        etPassword = findViewById(R.id.et_customer_sign_up_password);
        etLastName = findViewById(R.id.et_customer_sign_up_last_name);
        btnCaptureInfo = findViewById(R.id.btn_customer_sign_up_capture_info);
        ivBackBackArrow = findViewById(R.id.btn_customer_sign_up_back_arrow);

    }

    @Override
    public boolean validateData() {
        etUserName.addTextChangedListener(new TextValidator(etUserName) {
            @Override
            public void validate() {
                if(!etUserName.getText().toString().isEmpty()){
                    if(etUserName.getText().toString().trim().length() < 3){
                        userName = null;
                        etUserName.setBackgroundResource(R.drawable.rounded_boaders_error);
                        etUserName.setError("Username too short");
                    }else {
                        etUserName.setBackgroundResource(R.drawable.rounded_boaders);
                        userName = etUserName.getText().toString().trim();
                    }
                }
            }
        });
        etPhoneNumber.addTextChangedListener(new TextValidator(etPhoneNumber) {
            @Override
            public void validate() {
                if(!etPhoneNumber.getText().toString().isEmpty()){
                    if(etPhoneNumber.getText().toString().trim().length() < 9){
                        phoneNumber = null;
                        etPhoneNumber.setBackgroundResource(R.drawable.rounded_boaders_error);
                        etPhoneNumber.setError("Phone number too short");
                    }else {
                        etPhoneNumber.setBackgroundResource(R.drawable.rounded_boaders);
                        phoneNumber = etPhoneNumber.getText().toString().trim();
                    }
                }
            }
        });
        etPassword.addTextChangedListener(new TextValidator(etPassword) {
            @Override
            public void validate() {
                if(!etPassword.getText().toString().isEmpty()) {
                    if (etPassword.getText().toString().trim().length() < 5) {
                        password = null;
                        etPassword.setBackgroundResource(R.drawable.rounded_boaders_error);
                        etPassword.setError("Password too short");
                    } else {
                        etPassword.setBackgroundResource(R.drawable.rounded_boaders);
                        password = etPassword.getText().toString().trim();
                    }
                }
            }
        });
        etFirstName.addTextChangedListener(new TextValidator(etFirstName) {
            @Override
            public void validate() {

                if(!etFirstName.getText().toString().isEmpty()){
                    if(etFirstName.getText().toString().trim().length() < 3){
                        firstName = null;
                        etFirstName.setBackgroundResource(R.drawable.rounded_boaders_error);
                        etFirstName.setError("First name too short");
                    }else {
                        etFirstName.setBackgroundResource(R.drawable.rounded_boaders);
                        firstName = etFirstName.getText().toString().trim();
                    }
                }

            }
        });

        etLastName.addTextChangedListener(new TextValidator(etLastName) {
            @Override
            public void validate() {
                if(!etLastName.getText().toString().isEmpty()){
                    if(etLastName.getText().toString().trim().length() < 3){
                        lastName = null;
                        etLastName.setBackgroundResource(R.drawable.rounded_boaders_error);
                        etLastName.setError("Last name too short");
                    }else {
                        etLastName.setBackgroundResource(R.drawable.rounded_boaders);
                        lastName = etLastName.getText().toString().trim();
                    }
                }

            }
        });

        etEmail.addTextChangedListener(new TextValidator(etEmail) {
            @Override
            public void validate() {
                if(!etEmail.getText().toString().isEmpty()){
                    String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if(!etEmail.getText().toString().matches(emailRegex)){
                        email = null;
                        etEmail.setError("Invalid email");
                        etEmail.setBackgroundResource(R.drawable.rounded_boaders_error);
                    }else{
                        email = etEmail.getText().toString().trim();
                        etEmail.setBackgroundResource(R.drawable.rounded_boaders);
                    }
                }
            }
        });

        return userName != null
                && lastName != null
                && firstName != null
                && phoneNumber != null
                && email != null
                && password !=  null;
    }

    @Override
    public void onFailedValidation() {
       flagErrors.flagValidationError(R.id.customer_signup_base_view);
    }
    @Override
    public void onValidationSuccess() {
        UserDetailsModel userDetailsModel = UserDetailsModel.builder()
                .firstName(firstName)
                .lastName(lastName)
                .userName(userName)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .build();
        Intent intent = new Intent(this, SendRegisterCustomerRequestActivity.class);
        intent.putExtra(AppConstants.USER_DETAILS, userDetailsModel);
        startActivity(intent);
    }
}