package com.main.sellit.presenter;

import android.content.Context;
import android.content.Intent;

import com.main.sellit.contract.MainActivityContract;
import com.main.sellit.ui.LoginActivity;
import com.main.sellit.ui.SignupActivity;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainActivityPresenter implements MainActivityContract.Presenter {
    final MainActivityContract.View view;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void loginButtonClicked() {
        view.navigateToLogin();
    }

    @Override
    public void signUpButtonClicked() {
        view.navigateToSignUp();
    }
}
