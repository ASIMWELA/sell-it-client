package com.main.sellit.contract;

import org.json.JSONObject;

public interface ProviderRequestsContract {
    interface View{
        void onGetRequestSuccess(JSONObject apiResponse);
        void onGetRequestError(String apiError);
        void showGetRequestProgressBar();
        void hideGetRequestProgressBar();
    }
    interface Presenter{
        void getRequests(String token);
    }
}
