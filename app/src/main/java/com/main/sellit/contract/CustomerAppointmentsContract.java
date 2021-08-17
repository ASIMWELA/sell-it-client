package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface CustomerAppointmentsContract {
    interface View{
        void showGetAppointmentProgressBar();
        void hideGetAppointmentProgressBar();
        void onGetAppointmentSuccess(JSONObject apiResponse);
        void onGetAppointmentError(VolleyError apiError);
    }

    interface Presenter{
        void getAppointments(String token, String customerUuid);
    }
}
