package com.main.sellit.presenter;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.AddCategoryContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class AddCategoryPresenter implements AddCategoryContract.Presenter {

    private final AddCategoryContract.View view;
    private final Context ctx;

    public AddCategoryPresenter(AddCategoryContract.View view, Context ctx) {
        this.view = view;
        this.ctx = ctx;
    }

    @Override
    public void submitData(JSONObject data) {
        if(view.validateInput()){
            view.startLoadingButton();
            JsonObjectRequest addCategoryRequest = new JsonObjectRequest(Request.Method.POST, ApiUrls.BASE_API_URL + "/auth/login", data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    view.stopLoadingButton();
                    view.onSubmitSuccess(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error == null || error.networkResponse == null) {
                        view.stopLoadingButton();
                        return;
                    }
                    String body;
                    body = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    view.stopLoadingButton();
                    view.onSubmitError(body);
                }
            });

            VolleyController.getInstance(ctx).addToRequestQueue(addCategoryRequest);
        }else {
            view.onFailedValidation();
        }

    }
}
