package com.main.sellit.contract;

public interface RegisterProviderContract {
    interface View{
        boolean validateInput();
        void onValidationSuccess();
        void onFailedValidation();

    }
    interface Presenter{
        void signUp();

    }
}
