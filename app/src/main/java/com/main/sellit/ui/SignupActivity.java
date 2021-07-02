package com.main.sellit.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.snackbar.Snackbar;
import com.main.sellit.R;
import com.main.sellit.contract.ProviderContract;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.model.UserDetailsModel;
import com.main.sellit.presenter.ProviderPresenter;
import com.main.sellit.ui.provider.RegisterProvider;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SignupActivity extends AppCompatActivity implements ProviderContract.View {

    Context ctx;
    TextView activityTitle;
    ProviderPresenter providerPresenter;
    Button captureProviderInfo;
    private AwesomeValidation validator;

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

    private static final String USER_DETAILS = "userDetails";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_capture_provider_personal_details);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);

        //initialize objects
        providerPresenter = new ProviderPresenter(this, ctx);
        validator = new AwesomeValidation(ValidationStyle.BASIC);

      //  validator = new Validator();

        //initialize views
        initViews();

        validateInputs();








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

    }
    private void validateInputs() {

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
        captureProviderInfo.setOnClickListener(v->{
            if((userName==null)
                    || (phoneNumber==null)
                    || (password == null)
                    || (firstName == null)
                    || (lastName == null)
                    || (email==null) ){
                Snackbar.make(findViewById(R.id.capture_provider_details_container), "There are errors in your inputs", Snackbar.LENGTH_LONG).show();
            }
            else{
                     UserDetailsModel userDetailsModel = UserDetailsModel.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .userName(userName)
                        .email(email)
                        .phoneNumber(phoneNumber)
                        .password(password)
                        .build();
                     Intent intent = new Intent(this, RegisterProvider.class);
                     intent.putExtra(USER_DETAILS, userDetailsModel);
                     startActivity(intent);
            }

        });

    }

}