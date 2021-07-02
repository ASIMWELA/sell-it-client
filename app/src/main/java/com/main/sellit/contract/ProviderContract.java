package com.main.sellit.contract;


import android.widget.EditText;

public interface ProviderContract {
    interface View{

    }
    interface Presenter{
        boolean validateUserInput(EditText editText, int minLength,boolean isEmail);
        void flagError(EditText editText, String message);
    }
}
