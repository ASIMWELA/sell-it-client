package com.main.sellit.presenter;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.ProviderServiceContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProviderServicePresenter implements ProviderServiceContract.Presenter{

    final ProviderServiceContract.View view;
    final Context ctx;

    public ProviderServicePresenter(ProviderServiceContract.View view, Context ctx) {
        this.view = view;
        this.ctx = ctx;
    }

    @Override
    public void openCategoryBtnClicked() {
        view.openAddCategoryActivity();
    }

    @Override
    public void getProviderServices(String providerUuid, String token) {
        view.showGetServicesProgressBar();
        JsonObjectRequest getServicesRequest = new JsonObjectRequest(Request.Method.GET, ApiUrls.BASE_API_URL + "/services/"+providerUuid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                view.hideGetServicesProgressBar();
                view.onGetServicesResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideGetServicesProgressBar();
                view.onGetServicesError(error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+token);
                return params;
            }
        };

        VolleyController.getInstance(ctx).addToRequestQueue(getServicesRequest);
    }
}
