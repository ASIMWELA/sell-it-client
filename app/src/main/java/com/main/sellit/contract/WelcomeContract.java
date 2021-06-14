package com.main.sellit.contract;

public interface WelcomeContract {
    interface View{
       void navigateToSignUp();
       void navigateToLogin();
    }
    interface Presenter{
        void loginButtonClicked();
        void signUpButtonClicked();
    }
}
