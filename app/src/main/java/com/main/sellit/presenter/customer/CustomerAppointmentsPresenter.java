package com.main.sellit.presenter.customer;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.CustomerAppointmentsContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerAppointmentsPresenter implements CustomerAppointmentsContract.Presenter {
    Context context;
    CustomerAppointmentsContract.View view;

    public CustomerAppointmentsPresenter(Context context, CustomerAppointmentsContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getAppointments(String token, String customerUuid) {
        view.showGetAppointmentProgressBar();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                ApiUrls.BASE_API_URL+"/services/appointments/"+customerUuid,
                null,
                response -> {
                    view.onGetAppointmentSuccess(response);
                    view.hideGetAppointmentProgressBar();
                },
                error -> {
                    view.hideGetAppointmentProgressBar();
                    view.onGetAppointmentError(error);
                }){
                @Override
                public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+token);
                return params;
                }
        };

        VolleyController.getInstance(context).addToRequestQueue(request);

    }
}
