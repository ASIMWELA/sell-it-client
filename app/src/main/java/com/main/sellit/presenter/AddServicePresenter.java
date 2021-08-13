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
        JsonObjectRequest serviceCategoriesRequest = new JsonObjectRequest(
                Request.Method.GET,
                ApiUrls.BASE_API_URL + "/services/categories?pageNo=0&pageSize=150",
                null,
                response -> {
                     view.hideProgressBar();
                     view.onResponse(response);
              }, error -> {
                     view.hideProgressBar();
                     view.onError(error);
        });
        VolleyController.getInstance(ctx).addToRequestQueue(serviceCategoriesRequest);
    }

    @Override
    public void saveService(JSONObject serviceData, String token, String categoryUuid) {
        if(view.validateInput()){
            view.showLoadingButton();
            JsonObjectRequest addService= new JsonObjectRequest(
                    Request.Method.POST, ApiUrls.BASE_API_URL + "/services/"+categoryUuid+"/save-service",
                    serviceData,
                    response -> {
                         view.hideLoadingButton();
                         view.onSubmitServiceSuccess(response);
                  }, error -> {
                         view.hideLoadingButton();
                         view.onSubmitServiceError(error);
            }){
                @Override
                public Map<String, String> getHeaders() {
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
