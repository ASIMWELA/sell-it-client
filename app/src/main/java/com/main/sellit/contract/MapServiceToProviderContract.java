package com.main.sellit.contract;

import org.json.JSONObject;

public interface MapServiceToProviderContract {
    interface View{
        void showGetServicesProgressBar();
        void hideGetServicesProgressBar();
        void onGetServicesResponse(JSONObject response);
        void onGetServicesError(String error);
        boolean validateInput();
        void onFailedValidation();
        void showLoadingButton();
        void hideLoadingButton();
        void onSubmitServiceSuccess(JSONObject apiResponse);
        void onSubmitServiceError(String volleyError);
    }
    interface Presenter{
        void getServices();
        void mapServiceToProvider(JSONObject serviceProviderDetails, String serviceUuid, String providerUuid, String token);
    }
}
