package com.main.sellit.presenter.customer;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.RequestServiceContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.helper.AppConstants;
import com.main.sellit.network.VolleyController;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestServicePresenter implements RequestServiceContract.Presenter{
    final RequestServiceContract.View view;
    final Context context;

    public RequestServicePresenter(RequestServiceContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void createRequest(JSONObject data, String customerUuid, String serviceUuid, String token) {
        if(view.validateInput()){
            view.showLoadingButton();
            JsonObjectRequest createRequest = new JsonObjectRequest(
                    Request.Method.POST, ApiUrls.BASE_API_URL+"/services/"+customerUuid+"/"+serviceUuid+"/request-service",
                    data, response -> {
                        view.hideLoadingButton();
                        view.onCreateRequestSuccess(response);
                    }, error -> {
                        view.hideLoadingButton();
                        view.onCreateRequestError(error);
                    }){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("Authorization", "Bearer "+token);
                    return params;
                }
            };

            VolleyController.getInstance(context).addToRequestQueue(createRequest);
        }else {
            view.onFailedValidation();
        }
    }
}
