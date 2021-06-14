 package com.main.sellit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.main.sellit.R;
import com.main.sellit.contract.WelcomeContract;
import com.main.sellit.presenter.WelcomePresenter;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

 @FieldDefaults(level = AccessLevel.PRIVATE)
 public class Welcome extends AppCompatActivity implements WelcomeContract.View {
    WelcomePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        presenter = new WelcomePresenter(this);
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