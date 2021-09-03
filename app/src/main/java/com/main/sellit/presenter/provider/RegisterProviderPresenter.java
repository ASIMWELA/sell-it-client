package com.main.sellit.presenter.provider;

import com.main.sellit.contract.RegisterProviderContract;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterProviderPresenter implements RegisterProviderContract.Presenter {
    final RegisterProviderContract.View view;

    public RegisterProviderPresenter(RegisterProviderContract.View view) {
        this.view = view;
    }

    @Override
    public void signUp() {
        if(view.validateInput()){
            view.onValidationSuccess();
        }else {
            view.onFailedValidation();
        }

    }
}
