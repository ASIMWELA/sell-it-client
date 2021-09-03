package com.main.sellit.presenter.provider;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.ProviderRequestsContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProviderRequestsPresenter implements ProviderRequestsContract.Presenter{
    ProviderRequestsContract.View view;
    Context context;

    public ProviderRequestsPresenter(ProviderRequestsContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void getRequests(String token) {
        view.showGetRequestProgressBar();
        JsonObjectRequest getRequestsRequest = new JsonObjectRequest(Request.Method.GET, ApiUrls.BASE_API_URL + "/services/requests?pageNo=0&pageSize=100", null, response -> {
            view.hideGetRequestProgressBar();
            view.onGetRequestSuccess(response);
        }, error -> {
            view.hideGetRequestProgressBar();
            view.onGetRequestError(error);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+token);
                return params;
            }
        };

        VolleyController.getInstance(context).addToRequestQueue(getRequestsRequest);

    }
}
