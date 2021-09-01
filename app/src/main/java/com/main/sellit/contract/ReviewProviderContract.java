package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface ReviewProviderContract {
    interface View{
        boolean validateData();
        void showLoadingButton();
        void hideLoadingButton();
        void onFailedValidation();
        void onSendReviewSuccess(JSONObject response);
        void onSendReviewError(VolleyError error);
    }
    interface Presenter{
        void sendProviderReview(String token, String serviceAppointmentUuid, JSONObject data);
    }
}
