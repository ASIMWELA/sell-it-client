package com.main.sellit.presenter.provider;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.MapServiceToProviderContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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
        JsonObjectRequest getServicesRequest = new JsonObjectRequest(Request.Method.GET, ApiUrls.BASE_API_URL + "/services?pageNo=0&pageSize=20", null, response -> {
            view.hideGetServicesProgressBar();
            view.onGetServicesResponse(response);
        }, error -> {
            view.hideGetServicesProgressBar();
            view.onGetServicesError(error);
        });
        VolleyController.getInstance(ctx).addToRequestQueue(getServicesRequest);
    }

    @Override
    public void mapServiceToProvider(JSONObject serviceProviderDetails, String serviceUuid, String providerUuid, String token) {
        if(view.validateInput()){
            view.showLoadingButton();
            JsonObjectRequest mapServiceRequest = new JsonObjectRequest(Request.Method.POST, ApiUrls.BASE_API_URL + "/providers/"+serviceUuid+"/"+providerUuid+"/map-service-to-provider", serviceProviderDetails, response -> {
                view.hideLoadingButton();
                view.onSubmitServiceSuccess(response);
            }, error -> {
                view.hideLoadingButton();
                view.onSubmitServiceError(error);
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("Authorization", "Bearer "+token);
                    return params;
                }
            };

            VolleyController.getInstance(ctx).addToRequestQueue(mapServiceRequest);

        }else {
            view.onFailedValidation();
        }

    }
}
