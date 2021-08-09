package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface AddProductCategoryContract {
    interface View{
        boolean validateInput();
        void onFailedValidation();
        void onSubmitSuccess(JSONObject apiResponse);
        void onSubmitError(VolleyError apiError);
        void startLoadingButton();
        void stopLoadingButton();

    }
    interface Presenter{
        void submitData(JSONObject data, String token);
    }
}
