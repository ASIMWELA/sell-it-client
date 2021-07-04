package com.main.sellit.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.contract.LoginContract;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.helper.UserRoles;
import com.main.sellit.presenter.LoginPresenter;
import com.main.sellit.ui.admin.AdminHomeActivity;
import com.main.sellit.ui.customer.CustomerHomeActivity;
import com.main.sellit.ui.provider.ProviderHomeActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
@NoArgsConstructor
public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {

    LoginPresenter loginPresenter;
    LoadingButton btnLogin;
    EditText edtTxtUserName, edtTextPassword;
    String userName, password;
    TextView txvErrorMessage;
    Context context;

    public LoginActivity(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginPresenter(this, this);
        initViews();

        //validate initial values
        validateInput();

    }

    @Override
    protected void onStart() {
        super.onStart();
        loginUser();
    }

    private void loginUser() {
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
        btnLogin = (LoadingButton)findViewById(R.id.btn_login_send_request);
        edtTextPassword = (EditText)findViewById(R.id.edtx_login_password);
        edtTxtUserName = (EditText)findViewById(R.id.edt_txt_login_user_name);
        txvErrorMessage = (TextView)findViewById(R.id.txv_login_error_message);
    }

    @Override
    @SneakyThrows
    public void onLoginSuccess(JSONObject jsonObject) {

        //TODO:
        String role = jsonObject.getJSONObject("userData").getJSONArray("roles").getJSONObject(0).getString("name");
       // JSONArray ar = jsonObject.getJSONArray("roles");
        if(role==null){
            Toast.makeText(this, "Error: No role assigned login failed", Toast.LENGTH_SHORT).show();
        }else {
            if(role.equals(UserRoles.ROLE_PROVIDER.name())){
                Intent providerIntent = new Intent(this, ProviderHomeActivity.class);
                startActivity(providerIntent);
            }
            if(role.equals(UserRoles.ROLE_ADMIN.name())){
                Intent adminIntent = new Intent(this, AdminHomeActivity.class);
                startActivity(adminIntent);
            }
            if(role.equals(UserRoles.ROLE_CUSTOMER)){
                Intent customerIntent = new Intent(this, CustomerHomeActivity.class);
                startActivity(customerIntent);
            }


        }

    }

    @Override
    @SneakyThrows
    public void onLoginFailure(String message) {
        //TODO: resolve btn error after second click
        JSONObject errorObject = new JSONObject(message);
        String errorMessage = errorObject.getString("message");
        if(errorMessage != null){
            txvErrorMessage.setText(errorMessage);
            txvErrorMessage.setVisibility(View.VISIBLE);
        }else {
            txvErrorMessage.setText(R.string.auth_error);
            txvErrorMessage.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Login Failed :"+message, Toast.LENGTH_SHORT).show();
        }

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
                if(edtTxtUserName.getText().toString().trim().length()<1){
                    edtTxtUserName.setBackgroundResource(R.drawable.rounded_boaders_error);
                    edtTxtUserName.setError("User name is required");
                    userName=null;
                }else {
                    userName = edtTxtUserName.getText().toString().trim();
                    edtTxtUserName.setBackgroundResource(R.drawable.rounded_boaders);
                }
            }
        });
        edtTextPassword.addTextChangedListener(new TextValidator(edtTextPassword) {
            @Override
            public void validate() {
                    if(edtTextPassword.getText().toString().trim().length()<1){
                        edtTextPassword.setError("Password is required");
                        edtTextPassword.setBackgroundResource(R.drawable.rounded_boaders_error);
                        password = null;
                    }else {
                        password = edtTextPassword.getText().toString().trim();
                        edtTextPassword.setBackgroundResource(R.drawable.rounded_boaders);
                    }
            }
        });
        return userName != null && password != null;
    }

    @Override
    public void onFailedValidation() {
        Snackbar.make(findViewById(R.id.scrl_login_conatiner), "There are errors on your inputs", Snackbar.LENGTH_LONG).show();
    }
}