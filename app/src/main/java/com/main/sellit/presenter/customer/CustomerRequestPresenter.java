package com.main.sellit.presenter.customer;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.CustomerRequestContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerRequestPresenter implements CustomerRequestContract.Presenter {
    CustomerRequestContract.View view;
    Context  context;

    public CustomerRequestPresenter(CustomerRequestContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void getCustomerRequests(String token, String customerUuid) {
        view.showProgressBar();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiUrls.BASE_API_URL + "/services/requests/" + customerUuid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                view.hideProgressBar();
                view.onGetRequestSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideProgressBar();
                view.onGetRequestsError(error);
            }
        }){ @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+token);
                return params;
            }
        };
        VolleyController.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
