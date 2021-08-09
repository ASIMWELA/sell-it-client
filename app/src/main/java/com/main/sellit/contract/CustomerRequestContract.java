package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface CustomerRequestContract {
    interface View{
        void onGetRequestSuccess(JSONObject apiResponse);
        void onGetRequestsError(VolleyError apiError);
        void showProgressBar();
        void hideProgressBar();
    }
    interface Presenter{
        void getCustomerRequests(String token, String customerUuid);
    }
}
