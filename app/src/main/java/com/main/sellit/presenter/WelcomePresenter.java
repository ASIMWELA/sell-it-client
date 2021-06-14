package com.main.sellit.presenter;

import com.main.sellit.contract.WelcomeContract;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class WelcomePresenter implements WelcomeContract.Presenter {
    WelcomeContract.View view;

    public WelcomePresenter(WelcomeContract.View view) {
        this.view = view;
    }
    @Override
    public void loginButtonClicked() {

    }

    @Override
    public void signUpButtonClicked() {

    }
}
