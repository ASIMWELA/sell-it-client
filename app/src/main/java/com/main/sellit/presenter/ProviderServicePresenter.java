package com.main.sellit.presenter;

import com.main.sellit.contract.ProviderServiceContract;

public class ProviderServicePresenter implements ProviderServiceContract.Presenter{

    private final ProviderServiceContract.View view;

    public ProviderServicePresenter(ProviderServiceContract.View view) {
        this.view = view;
    }

    @Override
    public void openCategoryBtnClicked() {
        view.openAddCategoryActivity();
    }
}
