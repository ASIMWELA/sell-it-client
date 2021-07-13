package com.main.sellit.contract;

import org.json.JSONObject;

public interface AddServiceContract {
    interface View{
        void showProgressBar();
        void hideProgressBar();
        void onResponse(JSONObject response);
        void onError(String error);
    }

    interface Presenter{
        void getServiceCategories();
    }
}
