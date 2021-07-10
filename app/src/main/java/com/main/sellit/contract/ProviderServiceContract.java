package com.main.sellit.contract;

public interface ProviderServiceContract {
    interface View {
        void openAddCategoryActivity();
    }

    interface Presenter{
        void openCategoryBtnClicked();
    }
}
