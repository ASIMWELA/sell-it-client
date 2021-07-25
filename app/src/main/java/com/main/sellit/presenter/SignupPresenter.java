package com.main.sellit.presenter;

import com.main.sellit.contract.SignupContract;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupPresenter implements SignupContract.Presenter {

    private final SignupContract.View view;

    public SignupPresenter(SignupContract.View view) {
        this.view = view;
    }

    @Override
    public void captureProviderPersonaDetails() {
        if(view.validateInput()){
            view.onValidateSuccess();
        }else{
            view.onFailedValidation();
        }
    }
}
