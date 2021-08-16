package com.main.sellit.presenter;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.main.sellit.contract.CustomerViewRequestOffersContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerViewRequestOffersPresenter implements CustomerViewRequestOffersContract.Presenter {
    Context context;
    CustomerViewRequestOffersContract.View view;

    public CustomerViewRequestOffersPresenter(Context context, CustomerViewRequestOffersContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getOffers(String token, String requestUuid) {
        view.showGetOffersProgressBar();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                ApiUrls.BASE_API_URL+"/services/requests/"+requestUuid+"/offers",
                null,
                response -> {
                    view.hideGetOffersProgressBar();
                    view.onGetOffersSuccess(response);
                },
                error -> {
                    view.hideGetOffersProgressBar();
                    view.onGetOffersError(error);
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+token);
                return params;
            }
        };
        VolleyController.getInstance(context).addToRequestQueue(request);
    }
}
