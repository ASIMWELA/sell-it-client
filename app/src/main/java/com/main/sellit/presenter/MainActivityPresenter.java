package com.main.sellit.presenter;

import android.content.Context;
import android.content.Intent;

import com.main.sellit.contract.MainActivityContract;
import com.main.sellit.ui.LoginActivity;
import com.main.sellit.ui.SignupActivity;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainActivityPresenter implements MainActivityContract.Presenter {
    MainActivityContract.View view;
    Context ctx;

    public MainActivityPresenter(MainActivityContract.View view, Context ctx) {
        this.view = view;
        this.ctx = ctx;
    }

    @Override
    public void loginButtonClicked() {
        Intent intent = new Intent(ctx, LoginActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    public void signUpButtonClicked() {
        Intent intent = new Intent(ctx, SignupActivity.class);
        ctx.startActivity(intent);
    }
}
