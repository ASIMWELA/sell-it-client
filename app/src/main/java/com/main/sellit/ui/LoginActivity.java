package com.main.sellit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.contract.LoginContract;
import com.main.sellit.helper.AppConstants;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.helper.UserRoles;
import com.main.sellit.model.CustomerLoginModel;
import com.main.sellit.model.ProviderLoginModel;
import com.main.sellit.presenter.LoginPresenter;
import com.main.sellit.ui.admin.AdminHomeActivity;
import com.main.sellit.ui.customer.CustomerHomeActivity;
import com.main.sellit.ui.provider.ProviderHomeActivity;

import org.json.JSONObject;

import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {


    LoginPresenter loginPresenter;
    LoadingButton btnLogin;
    EditText edtTxtUserName, edtTextPassword;
    String userName, password;
    TextView txvErrorMessage, tvOpenSignupActivity;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        sessionManager = new SessionManager(this);
        loginPresenter = new LoginPresenter(this, this);

        //validate initial values
        validateInput();

        tvOpenSignupActivity.setOnClickListener(v->{
            startActivity(new Intent(this, SignupActivity.class));
            finish();
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()){
                    txvErrorMessage.setText(null);
                    txvErrorMessage.setVisibility(View.INVISIBLE);
                    loginPresenter.login(userName, password);
                }else {
                    onFailedValidation();
                }
            }
        });

    }

    private void initViews(){
        edtTextPassword = (EditText)findViewById(R.id.edtx_login_password);
        edtTxtUserName = (EditText)findViewById(R.id.edt_txt_login_user_name);
        txvErrorMessage = (TextView)findViewById(R.id.txv_login_error_message);
        tvOpenSignupActivity = (TextView)findViewById(R.id.tv_login_open_signup_activity);
        btnLogin = findViewById(R.id.btn_login_send_request);
    }

    @Override
    @SneakyThrows
    public void onLoginSuccess(JSONObject jsonObject) {
        //TODO:
        String role = jsonObject.getJSONObject("userData").getJSONArray("roles").getJSONObject(0).getString("name");
       // JSONArray ar = jsonObject.getJSONArray("roles");
        if(role.equals(UserRoles.ROLE_PROVIDER.name())){
            //get token and set it to session
            String token = jsonObject.getJSONObject("tokenPayload").getString("accessToken");
            Intent providerIntent = new Intent(this, ProviderHomeActivity.class);
            JSONObject userDetails = jsonObject.getJSONObject("userData");
            JSONObject providerDetails = jsonObject.getJSONObject("userData").getJSONObject("providerDetails");

            ProviderLoginModel providerLoginModel = ProviderLoginModel.builder()
                    .providerUuid(providerDetails.getString("uuid"))
                    .providerDescription(providerDetails.getString("providerDescription"))
                    .officeAddress(providerDetails.getString("officeAddress"))
                    .email(userDetails.getString("email"))
                    .firstName(userDetails.getString("firstName"))
                    .lastName(userDetails.getString("lastName"))
                    .userName(userDetails.getString("userName"))
                    .uuid(userDetails.getString("uuid"))
                    .phoneNumber(userDetails.getString("mobileNumber"))
                    .build();
            providerIntent.putExtra(AppConstants.PROVIDER_LOGIN_DETAILS, providerLoginModel);
            sessionManager.setAccessToken(token);
            sessionManager.setLoggedInUser(providerLoginModel.toString());
            sessionManager.setIsUserLoggedIn(role);
            sessionManager.setProviderUuid(providerLoginModel.getProviderUuid());
            startActivity(providerIntent);
            finish();
        }
        if(role.equals(UserRoles.ROLE_ADMIN.name())){
            Intent adminIntent = new Intent(this, AdminHomeActivity.class);
            startActivity(adminIntent);
        }
        if(role.equals(UserRoles.ROLE_CUSTOMER.name())){
            Intent customerIntent = new Intent(this, CustomerHomeActivity.class);
            String token = jsonObject.getJSONObject("tokenPayload").getString("accessToken");
            JSONObject userDetails = jsonObject.getJSONObject("userData");
            JSONObject address = jsonObject.getJSONObject("userData").getJSONObject("address");
            CustomerLoginModel customerLoginModel = CustomerLoginModel.builder()
                    .email(userDetails.getString("email"))
                    .firstName(userDetails.getString("firstName"))
                    .lastName(userDetails.getString("lastName"))
                    .userName(userDetails.getString("userName"))
                    .uuid(userDetails.getString("uuid"))
                    .phoneNumber(userDetails.getString("mobileNumber"))
                    .city(address.getString("city"))
                    .country(address.getString("country"))
                    .region(address.getString("region"))
                    .street(address.getString("street"))
                    .locationDescription(address.getString("locationDescription"))
                    .build();
            customerIntent.putExtra(AppConstants.CUSTOMER_LOGIN_DETAILS, customerLoginModel);
            sessionManager.setAccessToken(token);
            sessionManager.setLoggedInUser(customerLoginModel.toString());
            sessionManager.setLoggedInCustomerUuid(customerLoginModel.getUuid());
            sessionManager.setIsUserLoggedIn(role);
            startActivity(customerIntent);
            finish();
        }

    }

    @Override
    @SneakyThrows
    public void onLoginFailure(String message) {
        //TODO: resolve btn error after second click
        JSONObject errorObject = new JSONObject(message);
        String errorMessage = errorObject.getString("message");
        txvErrorMessage.setText(errorMessage);
        txvErrorMessage.setVisibility(View.VISIBLE);
        // startActivity(getIntent());
    }

    @Override
    public void startLoadingButton() {
         btnLogin.setEnabled(false);
         btnLogin.showLoading();
    }

    @Override
    public void stopLoadingButton() {
        btnLogin.setEnabled(true);
        btnLogin.hideLoading();
    }

    @Override
    public boolean validateInput() {
        edtTxtUserName.addTextChangedListener(new TextValidator(edtTxtUserName) {
            @Override
            public void validate() {
                if(!edtTxtUserName.getText().toString().isEmpty()){
                    if(edtTxtUserName.getText().toString().trim().length()<2){
                        edtTxtUserName.setBackgroundResource(R.drawable.rounded_boaders_error);
                        edtTxtUserName.setError("User name is required");
                        userName=null;
                    }else {
                        userName = edtTxtUserName.getText().toString().trim();
                        edtTxtUserName.setBackgroundResource(R.drawable.rounded_boaders);
                    }
                }

            }
        });
        edtTextPassword.addTextChangedListener(new TextValidator(edtTextPassword) {
            @Override
            public void validate() {
                if(!edtTextPassword.getText().toString().isEmpty()){
                    if(edtTextPassword.getText().toString().trim().length()<2){
                        edtTextPassword.setError("Password is required");
                        edtTextPassword.setBackgroundResource(R.drawable.rounded_boaders_error);
                        password = null;
                    }else {
                        password = edtTextPassword.getText().toString().trim();
                        edtTextPassword.setBackgroundResource(R.drawable.rounded_boaders);
                    }
                }
            }
        });
        return userName != null && password != null;
    }

    @Override
    public void onFailedValidation() {
        Snackbar.make(findViewById(R.id.scrl_login_conatiner), "There are errors on your inputs", Snackbar.LENGTH_SHORT).show();
    }
}