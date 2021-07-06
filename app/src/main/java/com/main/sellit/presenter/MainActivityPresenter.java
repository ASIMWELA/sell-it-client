package com.main.sellit.presenter;

import com.main.sellit.contract.MainContract;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainActivityPresenter implements MainContract.Presenter {
    final MainContract.View view;

    public MainActivityPresenter(MainContract.View view) {
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
