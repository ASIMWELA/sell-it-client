package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface LoginContract {
    interface LoginView{
        void onLoginSuccess(JSONObject jsonObject);
        void onLoginFailure(VolleyError errorMessage);
        void startLoadingButton();
        void stopLoadingButton();
        boolean validateInput();
        void onFailedValidation();
    }
    interface Presenter{
        void login(String userName, String password);

    }
}
