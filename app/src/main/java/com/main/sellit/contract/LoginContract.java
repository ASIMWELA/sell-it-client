package com.main.sellit.contract;

import org.json.JSONObject;

public interface LoginContract {
    interface LoginView{
        void onLoginSuccess(JSONObject jsonObject);
        void onLoginFailure(JSONObject errorObject);
        boolean validateInput();
        void onFailedValidation();
    }
    interface Presenter{
        void login(String userName, String password);

    }
}
