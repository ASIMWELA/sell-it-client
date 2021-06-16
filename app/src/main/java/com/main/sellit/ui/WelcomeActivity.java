 package com.main.sellit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.main.sellit.R;
import com.main.sellit.contract.WelcomeContract;
import com.main.sellit.presenter.WelcomePresenter;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

 @FieldDefaults(level = AccessLevel.PRIVATE)
 public class WelcomeActivity extends AppCompatActivity implements WelcomeContract.View {
    WelcomePresenter presenter;
    Button btnLogin, btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        presenter = new WelcomePresenter(this, this);
        btnSignup = (Button)findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);

        //navigate to login and signup activities
        btnSignup.setOnClickListener(v -> navigateToSignUp());
        btnLogin.setOnClickListener(v->navigateToLogin());
    }

     @Override
     public void navigateToSignUp() {
        presenter.signUpButtonClicked();
     }

     @Override
     public void navigateToLogin() {
        presenter.loginButtonClicked();
     }
 }