package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface ProviderSendOfferContract {
    interface View{
        boolean validateInput();
        void onFailedValidation();
        void showLoadingButton();
        void hideLoadingButton();
        void onSendOfferResponse(JSONObject apiResponse);
        void onSendOfferError(VolleyError apiError);
    }
    interface Presenter{
        void sendOfferRequest(String token, String requestUuid, String providerUuid, JSONObject data);
    }
}
