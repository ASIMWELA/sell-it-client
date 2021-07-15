package com.main.sellit.contract;

import org.json.JSONObject;

public interface AddServiceContract {
    interface View{
        void showProgressBar();
        void hideProgressBar();
        void onResponse(JSONObject response);
        void onError(String error);
        boolean validateInput();
        void onFailedValidation();
        void showLoadingButton();
        void hideLoadingButton();
        void onSubmitServiceSuccess(JSONObject apiResponse);
        void onSubmitServiceError(String volleyError);
    }

    interface Presenter{
        void getServiceCategories();
        void saveService(JSONObject serviceData, String token, String serviceCategoryUuid);
    }
}
