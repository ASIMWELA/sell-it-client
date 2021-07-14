package com.main.sellit.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.main.sellit.R;
import com.main.sellit.contract.SignupContract;
import com.main.sellit.helper.AppConstants;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.model.UserDetailsModel;
import com.main.sellit.presenter.SignupPresenter;
import com.main.sellit.ui.provider.RegisterProviderActivity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupActivity extends AppCompatActivity implements SignupContract.View {

    Context ctx;
    TextView activityTitle;
    SignupPresenter signupPresenter;
    Button captureProviderInfo;
    EditText editTxtUserName,
            editTxtEmail,
            editTxtPassword,
            editTxtPhoneNumber,
            editTxtFirstName,
            editTxtLastName;

    String userName,
            email,
            password,
            phoneNumber,
            firstName,
            lastName;

    ImageView ivBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_capture_provider_personal_details);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        signupPresenter = new SignupPresenter(this);

        //initialize views
        initViews();

        //activate validations
        validateInput();
        captureProviderInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupPresenter.captureProviderPersonaDetails();
            }
        });

        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });
    }

    private void initViews(){
        captureProviderInfo = (Button)findViewById(R.id.btn_submit_provider_details);
        activityTitle = (TextView)findViewById((R.id.txt_title_text));
        activityTitle.setText(R.string.sign_provider);

        //user input views
        editTxtEmail = (EditText)findViewById(R.id.edtx_email);
        editTxtFirstName=(EditText)findViewById(R.id.edtx_firt_name);
        editTxtLastName = (EditText)findViewById(R.id.edtx_last_name);
        editTxtPassword = (EditText)findViewById(R.id.edtx_password);
        editTxtPhoneNumber = (EditText)findViewById(R.id.edtx_phone_number);
        editTxtUserName = (EditText)findViewById(R.id.edt_txt_user_name);
        ivBackArrow = (ImageView)findViewById(R.id.imv_back_arrow_provider_info);

    }
    @Override
    public boolean validateInput() {
        editTxtUserName.addTextChangedListener(new TextValidator(editTxtUserName) {
            @Override
            public void validate() {
                if(editTxtUserName.getText().toString().trim().length() < 3){
                    userName = null;
                    editTxtUserName.setBackgroundResource(R.drawable.rounded_boaders_error);
                    editTxtUserName.setError("Username too short");
                }else {
                    editTxtUserName.setBackgroundResource(R.drawable.rounded_boaders);
                    userName = editTxtUserName.getText().toString().trim();
                }
            }
        });
        editTxtPhoneNumber.addTextChangedListener(new TextValidator(editTxtPhoneNumber) {
            @Override
            public void validate() {
                if(editTxtPhoneNumber.getText().toString().trim().length() < 9){
                    phoneNumber = null;
                    editTxtPhoneNumber.setBackgroundResource(R.drawable.rounded_boaders_error);
                    editTxtPhoneNumber.setError("Phone number too short");
                }else {
                    editTxtPhoneNumber.setBackgroundResource(R.drawable.rounded_boaders);
                    phoneNumber = editTxtPhoneNumber.getText().toString().trim();
                }
            }
        });
        editTxtPassword.addTextChangedListener(new TextValidator(editTxtPassword) {
            @Override
            public void validate() {
                if(editTxtPassword.getText().toString().trim().length() < 5){
                    password = null;
                    editTxtPassword.setBackgroundResource(R.drawable.rounded_boaders_error);
                    editTxtPassword.setError("Password too short");
                    ContextCompat.getDrawable(getApplicationContext(), R.drawable.lock_icon).setTint(Color.RED);
                }else {
                    editTxtPassword.setBackgroundResource(R.drawable.rounded_boaders);
                    password = editTxtPassword.getText().toString().trim();
                }
            }
        });
        editTxtFirstName.addTextChangedListener(new TextValidator(editTxtFirstName) {
            @Override
            public void validate() {
                if(editTxtFirstName.getText().toString().trim().length() < 3){
                    firstName = null;
                    editTxtFirstName.setBackgroundResource(R.drawable.rounded_boaders_error);
                    editTxtFirstName.setError("First name too short");
                }else {
                    editTxtFirstName.setBackgroundResource(R.drawable.rounded_boaders);
                    firstName = editTxtFirstName.getText().toString().trim();
                }
            }
        });

        editTxtLastName.addTextChangedListener(new TextValidator(editTxtLastName) {
            @Override
            public void validate() {
                if(editTxtLastName.getText().toString().trim().length() < 3){
                    lastName = null;
                    editTxtLastName.setBackgroundResource(R.drawable.rounded_boaders_error);
                    editTxtLastName.setError("Last name too short");
                }else {
                    editTxtLastName.setBackgroundResource(R.drawable.rounded_boaders);
                    lastName = editTxtLastName.getText().toString().trim();
                }
            }
        });

        editTxtEmail.addTextChangedListener(new TextValidator(editTxtEmail) {
            @Override
            public void validate() {
                String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(!editTxtEmail.getText().toString().matches(emailRegex)){
                    email = null;
                    editTxtEmail.setError("Invalid email");
                    editTxtEmail.setBackgroundResource(R.drawable.rounded_boaders_error);
                }else{
                    email = editTxtEmail.getText().toString().trim();
                    editTxtEmail.setBackgroundResource(R.drawable.rounded_boaders);
                }

            }
        });
        return (userName != null)
                && (phoneNumber != null)
                && (password != null)
                && (firstName != null)
                && (lastName != null)
                && (email != null);
    }

    @Override
    public void onValidateSuccess() {
        UserDetailsModel userDetailsModel = UserDetailsModel.builder()
                .firstName(firstName)
                .lastName(lastName)
                .userName(userName)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .build();
        Intent intent = new Intent(this, RegisterProviderActivity.class);
        intent.putExtra(AppConstants.USER_DETAILS, userDetailsModel);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onFailedValidation() {
        Snackbar.make(findViewById(R.id.capture_provider_details_container), "There are errors in your inputs", Snackbar.LENGTH_LONG).show();
    }
}