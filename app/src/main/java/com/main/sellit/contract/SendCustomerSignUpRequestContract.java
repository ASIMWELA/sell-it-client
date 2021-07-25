package com.main.sellit.contract;

import org.json.JSONObject;

public interface SendCustomerSignUpRequestContract {
    interface View{
        boolean validateData();
        void onFailedValidation();
        void onRegistrationRequestError(String volleyError);
        void onRegistrationRequestSuccess(JSONObject apiResponse);
        void showLoadingButton();
        void hideLoadingButton();

    }
    interface Presenter{
        void sendSignUpRequest(JSONObject data);
    }
}
