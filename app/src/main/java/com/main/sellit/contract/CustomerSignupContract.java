package com.main.sellit.contract;

public interface CustomerSignupContract {
    interface View{
        boolean validateData();
        void onFailedValidation();
        void onValidationSuccess();
    }

    interface  Presenter{
        void captureInformation();
    }
}
