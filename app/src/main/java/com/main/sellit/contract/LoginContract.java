package com.main.sellit.contract;

public interface LoginContract {
    interface LoginView{
        void onLoginSuccess();
        void onLoginFailure();
    }
    interface Presenter{
        void login(String userName, String password);

    }
}
