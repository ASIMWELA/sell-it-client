package com.main.sellit.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.AddServiceContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    public void saveService(JSONObject serviceData, String token, String categoryUuid) {
        if(view.validateInput()){
            view.showLoadingButton();
            JsonObjectRequest addService= new JsonObjectRequest(Request.Method.POST, ApiUrls.BASE_API_URL + "/services/"+categoryUuid+"/save-service", serviceData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    view.hideLoadingButton();
                    view.onSubmitServiceSuccess(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error == null || error.networkResponse == null) {
                        Log.e("ERRORHERE", error.toString());
                        view.hideLoadingButton();
                        return;
                    }
                    String body;
                    body = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    view.hideLoadingButton();
                    view.onSubmitServiceError(body);
                    Log.e("ERRORHERE", error.toString());
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

            VolleyController.getInstance(ctx).addToRequestQueue(addService);

        }else {
            view.onFailedValidation();
        }
    }
}
