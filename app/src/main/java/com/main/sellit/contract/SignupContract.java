package com.main.sellit.contract;

public interface SignupContract {

    interface View{
        boolean validateInput();
        void onValidateSuccess();
        void onFailedValidation();
    }
    interface Presenter{
        void captureProviderPersonaDetails();

    }
}
