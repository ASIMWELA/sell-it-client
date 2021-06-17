package com.main.sellit.presenter;


import com.main.sellit.contract.ProviderContract;

public class ProviderPresenter implements ProviderContract.Presenter {
    ProviderContract.View view;

    public ProviderPresenter(ProviderContract.View view) {
        this.view = view;
    }

    @Override
    public void getPersonalDetailsObject() {

    }
}
