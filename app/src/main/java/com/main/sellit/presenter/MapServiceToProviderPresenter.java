package com.main.sellit.presenter;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.MapServiceToProviderContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONObject;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MapServiceToProviderPresenter implements MapServiceToProviderContract.Presenter{

    final Context ctx;
    final MapServiceToProviderContract.View view;

    public MapServiceToProviderPresenter(Context ctx, MapServiceToProviderContract.View view) {
        this.ctx = ctx;
        this.view = view;
    }

    @Override
    public void getServices() {
        view.showGetServicesProgressBar();
        JsonObjectRequest getServicesRequest = new JsonObjectRequest(Request.Method.GET, ApiUrls.BASE_API_URL + "/services?pageNo=0&pageSize=20", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                view.hideGetServicesProgressBar();
                view.onGetServicesResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.showGetServicesProgressBar();
                view.onGetServicesError(error.toString());
            }
        });
        VolleyController.getInstance(ctx).addToRequestQueue(getServicesRequest);
    }
}
