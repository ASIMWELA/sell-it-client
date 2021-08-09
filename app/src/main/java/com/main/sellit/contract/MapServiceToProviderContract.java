package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface MapServiceToProviderContract {
    interface View{
        void showGetServicesProgressBar();
        void hideGetServicesProgressBar();
        void onGetServicesResponse(JSONObject response);
        void onGetServicesError(VolleyError error);
        boolean validateInput();
        void onFailedValidation();
        void showLoadingButton();
        void hideLoadingButton();
        void onSubmitServiceSuccess(JSONObject apiResponse);
        void onSubmitServiceError(VolleyError volleyError);
    }
    interface Presenter{
        void getServices();
        void mapServiceToProvider(JSONObject serviceProviderDetails, String serviceUuid, String providerUuid, String token);
    }
}
