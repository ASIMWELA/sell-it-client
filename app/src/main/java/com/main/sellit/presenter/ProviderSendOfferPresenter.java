package com.main.sellit.presenter;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.ProviderSendOfferContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProviderSendOfferPresenter implements ProviderSendOfferContract.Presenter {
    ProviderSendOfferContract.View view;
    Context context;

    public ProviderSendOfferPresenter(ProviderSendOfferContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void sendOfferRequest(String token, String requestUuid,
                                 String providerUuid,
                                 JSONObject data) {
       if(view.validateInput()){
           view.showLoadingButton();
           JsonObjectRequest jsonObjectRequest =  new JsonObjectRequest(
                   Request.Method.POST,
                   ApiUrls.BASE_API_URL + "/services/" + requestUuid + "/" + providerUuid + "/make-offer",
                      data,
                   response -> {
                          view.hideLoadingButton();
                          view.onSendOfferResponse(response);
                      },
                   error -> {
                          view.hideLoadingButton();
                          view.onSendOfferError(error);
                      }){
               @Override
               public Map<String, String> getHeaders() throws AuthFailureError {
                   Map<String, String> params = new HashMap<String, String>();
                   params.put("Content-Type", "application/json; charset=UTF-8");
                   params.put("Authorization", "Bearer "+token);
                   return params;
               }

           };
           VolleyController.getInstance(context).addToRequestQueue(jsonObjectRequest);
       }else {
           view.onFailedValidation();
       }
    }
}
