package com.main.sellit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.contract.LoginContract;
import com.main.sellit.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {

    LoginPresenter loginPresenter;
    LoadingButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this, this);

        initViews();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.login("udu", "djdj");
            }
        });

    }

    private void initViews(){
        btnLogin = (LoadingButton)findViewById(R.id.btn_login_send_request);
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "Login sucess", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailure() {
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
    }
}