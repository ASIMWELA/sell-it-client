package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface ProviderRequestsContract {
    interface View{
        void onGetRequestSuccess(JSONObject apiResponse);
        void onGetRequestError(VolleyError apiError);
        void showGetRequestProgressBar();
        void hideGetRequestProgressBar();
    }
    interface Presenter{
        void getRequests(String token);
    }
}
