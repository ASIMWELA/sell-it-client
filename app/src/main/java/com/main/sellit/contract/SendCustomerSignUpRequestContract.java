package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface SendCustomerSignUpRequestContract {
    interface View{
        boolean validateData();
        void onFailedValidation();
        void onRegistrationRequestError(VolleyError volleyError);
        void onRegistrationRequestSuccess(JSONObject apiResponse);
        void showLoadingButton();
        void hideLoadingButton();

    }
    interface Presenter{
        void sendSignUpRequest(JSONObject data);
    }
}
