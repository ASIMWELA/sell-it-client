package com.main.sellit.presenter;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.AddServiceContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONObject;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddServicePresenter implements AddServiceContract.Presenter{
    final AddServiceContract.View view;
    final Context ctx;

    public AddServicePresenter(AddServiceContract.View view, Context ctx) {
        this.view = view;
        this.ctx = ctx;
    }

    @Override
    public void getServiceCategories() {
        view.showProgressBar();
        JsonObjectRequest serviceCategoriesRequest = new JsonObjectRequest(Request.Method.GET, ApiUrls.BASE_API_URL + "/services/categories", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                view.hideProgressBar();
                view.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideProgressBar();
                view.onError(error.toString());
            }
        });
        VolleyController.getInstance(ctx).addToRequestQueue(serviceCategoriesRequest);
    }
}
