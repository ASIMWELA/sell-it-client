package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface CustomerViewRequestOffersContract {
    interface View{
        void showGetOffersProgressBar();
        void hideGetOffersProgressBar();
        void onGetOffersSuccess(JSONObject apiResponse);
        void onGetOffersError(VolleyError apiError);
    }
    interface Presenter{
        void getOffers(String token, String requestUuid);
    }
}
