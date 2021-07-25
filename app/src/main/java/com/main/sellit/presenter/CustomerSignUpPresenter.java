package com.main.sellit.presenter;

import com.main.sellit.contract.CustomerSignupContract;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerSignUpPresenter implements CustomerSignupContract.Presenter {

    final CustomerSignupContract.View view;

    public CustomerSignUpPresenter(CustomerSignupContract.View view) {
        this.view = view;
    }
    @Override
    public void captureInformation() {
        if(view.validateData()){
            view.onValidationSuccess();
        }else {
            view.onFailedValidation();
        }
    }
}
