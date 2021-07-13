package com.main.sellit.contract;

import org.json.JSONObject;

public interface AddProductCategoryContract {
    interface View{
        boolean validateInput();
        void onFailedValidation();
        void onSubmitSuccess(JSONObject apiResponse);
        void onSubmitError(String apiError);
        void startLoadingButton();
        void stopLoadingButton();
    }
    interface Presenter{
        void submitData(JSONObject data, String token);
    }
}
