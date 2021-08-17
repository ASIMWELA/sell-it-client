package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface CustomerAcceptOfferContract {
    interface View{
        boolean validateInput();
        void onFailedValidation();
        void showLoadingButton();
        void hideLoadingButton();
        void onSendAppointmentSuccess(JSONObject apiResponse);
        void onSendAppointmentError(VolleyError apiError);
    }
    interface  Presenter{
        void sendAppointmentDetails(String token, String offerUuid, JSONObject appointmentData);
    }
}
