package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface ProviderServiceContract {
    interface View {
        void openAddCategoryActivity();
        void showGetServicesProgressBar();
        void hideGetServicesProgressBar();
        void onGetServicesResponse(JSONObject servicesResponse);
        void onGetServicesError(VolleyError volleyError);
    }

    interface Presenter{
        void openCategoryBtnClicked();
        void getProviderServices(String providerUuid, String token);
    }
}
