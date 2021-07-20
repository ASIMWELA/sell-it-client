package com.main.sellit.contract;

import org.json.JSONObject;

public interface ProviderServiceContract {
    interface View {
        void openAddCategoryActivity();
        void showGetServicesProgressBar();
        void hideGetServicesProgressBar();
        void onGetServicesResponse(JSONObject servicesResponse);
        void onGetServicesError(String volleyError);
    }

    interface Presenter{
        void openCategoryBtnClicked();
        void getProviderServices(String providerUuid, String token);
    }
}
