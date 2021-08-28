package com.main.sellit.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface ProviderAppointmentsContract {
    interface View{
        void showGetAppointmentsProgressBar();
        void hideGetAppointmentsProgressBar();
        void onGetAppointmentSuccess(JSONObject apiResponse);
        void onGetAppointmentsError(VolleyError apiError);
    }
    interface Presenter{
        void getProviderAppointments(String token , String providerUuid);
    }
}
