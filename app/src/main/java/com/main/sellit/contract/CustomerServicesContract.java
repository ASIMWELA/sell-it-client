package com.main.sellit.contract;

import org.json.JSONObject;

public interface CustomerServicesContract {
    interface View{
        void onGetServicesSuccess(JSONObject response);
        void onGetServicesError(String error);
        void showGetServicesProgressBar();
        void hideGetServicesProgressBar();
    }
    interface Presenter{
            void getServices();
    }
}
