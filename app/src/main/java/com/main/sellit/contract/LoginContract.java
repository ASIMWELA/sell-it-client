package com.main.sellit.contract;

import org.json.JSONObject;

public interface LoginContract {
    interface LoginView{
        void onLoginSuccess(JSONObject jsonObject);
        void onLoginFailure(String errorMessage);
        void startLoadingButton();
        void stopLoadingButton();
        boolean validateInput();
        void onFailedValidation();
    }
    interface Presenter{
        void login(String userName, String password);

    }
}
