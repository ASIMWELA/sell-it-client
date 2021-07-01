package com.main.sellit.presenter;


import android.content.Context;
import android.widget.EditText;

import com.main.sellit.contract.ProviderContract;
import com.main.sellit.helper.Validator;

public class ProviderPresenter implements ProviderContract.Presenter {
    ProviderContract.View view;
    Context ctx;

    public ProviderPresenter(ProviderContract.View view, Context ctx) {
        this.view = view;
        this.ctx = ctx;

    }
    @Override
    public boolean validateUserInput(EditText editText, int minLength,boolean isEmail) {
        return Validator.inputHasErrors(editText, minLength, isEmail);
    }
}
