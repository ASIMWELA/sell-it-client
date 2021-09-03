package com.main.sellit.presenter.provider;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.AddProductCategoryContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddProductCategoryPresenter implements AddProductCategoryContract.Presenter {

    final AddProductCategoryContract.View view;
    final Context ctx;

    public AddProductCategoryPresenter(AddProductCategoryContract.View view, Context ctx) {
        this.view = view;
        this.ctx = ctx;
    }

    @Override
    public void submitData(JSONObject data, String token) {
        if(view.validateInput()){
            view.startLoadingButton();
            JsonObjectRequest addCategoryRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    ApiUrls.BASE_API_URL + "/services/categories", data,
                    response -> {
                          view.stopLoadingButton();
                          view.onSubmitSuccess(response);
                  }, error -> {
                          view.stopLoadingButton();
                         view.onSubmitError(error);
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("Authorization", "Bearer "+token);
                    return params;
                }
            };

            VolleyController.getInstance(ctx).addToRequestQueue(addCategoryRequest);
        }else {
            view.onFailedValidation();
        }

    }
}
