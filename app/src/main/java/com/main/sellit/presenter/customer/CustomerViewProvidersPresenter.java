package com.main.sellit.presenter.customer;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.CustomerViewProvidersContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomerViewProvidersPresenter implements CustomerViewProvidersContract.Presenter{
    Context context;
    CustomerViewProvidersContract.View view;

    @Override
    public void getProviders(String token, String serviceUuid) {
        view.showGetProvidersProgressBar();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                ApiUrls.BASE_API_URL+"/services/"+serviceUuid+"/providers",
                null,
                response -> {
                    view.hideGetProvidersProgressBar();
                    view.onGetProvidersSuccess(response);
                },
                error -> {
                    view.onGetProvidersError(error);
                    view.hideGetProvidersProgressBar();
                }
        ){@Override
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
