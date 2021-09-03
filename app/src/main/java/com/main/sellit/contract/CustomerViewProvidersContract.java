package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface CustomerViewProvidersContract {
    interface View{
        void showGetProvidersProgressBar();
        void hideGetProvidersProgressBar();
        void onGetProvidersSuccess(JSONObject apiResponse);
        void onGetProvidersError(VolleyError apiError);

    }
    interface Presenter{
        void getProviders(String token, String serviceUuid);
    }
}
