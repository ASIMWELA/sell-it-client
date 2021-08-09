package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface CustomerServicesContract {
    interface View{
        void onGetServicesSuccess(JSONObject response);
        void onGetServicesError(VolleyError error);
        void showGetServicesProgressBar();
        void hideGetServicesProgressBar();
    }
    interface Presenter{
            void getServices();
    }
}
