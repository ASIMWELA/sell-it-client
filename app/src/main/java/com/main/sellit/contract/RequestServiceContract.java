package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface RequestServiceContract {
    interface View{
        boolean validateInput();
        void onFailedValidation();
        void showLoadingButton();
        void hideLoadingButton();
        void onCreateRequestSuccess(JSONObject apiResponse);
        void onCreateRequestError(VolleyError apiError);
    }
    interface Presenter{
        void createRequest(JSONObject data, String customerUuid, String serviceUuid, String token);
    }
}
