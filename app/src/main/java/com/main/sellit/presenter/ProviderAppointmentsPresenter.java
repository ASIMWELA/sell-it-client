package com.main.sellit.presenter;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.ProviderAppointmentsContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProviderAppointmentsPresenter  implements ProviderAppointmentsContract.Presenter{
    Context context;
    ProviderAppointmentsContract.View view;

    public ProviderAppointmentsPresenter(Context context, ProviderAppointmentsContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getProviderAppointments(String token, String providerUuid) {
        view.showGetAppointmentsProgressBar();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                ApiUrls.BASE_API_URL+"/services/"+providerUuid+"/appointments",
                null,
                response -> {
                    view.hideGetAppointmentsProgressBar();
                    view.onGetAppointmentSuccess(response);
                },
                error -> {
                    view.hideGetAppointmentsProgressBar();
                    view.onGetAppointmentsError(error);
                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+token);
                return params;
            }
        };
        VolleyController.getInstance(context).addToRequestQueue(request);
    }
}
