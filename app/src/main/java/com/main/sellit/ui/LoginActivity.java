package com.main.sellit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.contract.LoginContract;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.presenter.LoginPresenter;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {

    LoginPresenter loginPresenter;
    LoadingButton btnLogin;
    EditText edtTxtUserName, edtTextPassword;
    String userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this, this);


        initViews();

        //validate initial values
        validateInput();
        LoginUser();

    }

    private void LoginUser() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()){
                    btnLogin.showLoading();
                    btnLogin.setEnabled(false);
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
    }

    @Override
    public void onLoginSuccess(JSONObject jsonObject) {
        btnLogin.hideLoading();
        btnLogin.setEnabled(true);
        Toast.makeText(this, "Login success :"+jsonObject, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailure(JSONObject errorObject) {
        //TODO: resolve class after second click
        btnLogin.setEnabled(true);
        btnLogin.hideLoading();
        Toast.makeText(this, "Login Failed : "+errorObject, Toast.LENGTH_SHORT).show();

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
        btnLogin.hideLoading();
        Snackbar.make(findViewById(R.id.scrl_login_conatiner), "There are errors on your inputs", Snackbar.LENGTH_LONG).show();
    }
}