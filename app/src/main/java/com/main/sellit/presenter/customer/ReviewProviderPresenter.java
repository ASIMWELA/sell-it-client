package com.main.sellit.presenter.customer;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.ReviewProviderContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReviewProviderPresenter implements ReviewProviderContract.Presenter{
    Context context;
    ReviewProviderContract.View view;


    @Override
    public void sendProviderReview(String token, String serviceAppointmentUuid, JSONObject data) {
        if(view.validateData()){
            view.showLoadingButton();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    ApiUrls.BASE_API_URL+"/providers/"+serviceAppointmentUuid+"/reviews",
                    data,
                    response -> {
                        view.hideLoadingButton();
                        view.onSendReviewSuccess(response);
                    },
                    error -> {
                        view.hideLoadingButton();
                        view.onSendReviewError(error);
                    }){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Bearer "+token);
                    return headers;
                }
            };
            VolleyController.getInstance(context).addToRequestQueue(jsonObjectRequest);
        }else {
            view.onFailedValidation();
        }
    }
}
