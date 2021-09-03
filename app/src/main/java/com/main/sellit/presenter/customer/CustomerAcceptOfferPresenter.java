package com.main.sellit.presenter.customer;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.CustomerAcceptOfferContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerAcceptOfferPresenter implements  CustomerAcceptOfferContract.Presenter {
    Context context;
    CustomerAcceptOfferContract.View view;

    public CustomerAcceptOfferPresenter(Context context, CustomerAcceptOfferContract.View view) {
        this.context = context;
        this.view = view;
    }
    @Override
    public void sendAppointmentDetails(String token, String offerUuid, JSONObject appointmentData) {
        if(view.validateInput()){
            view.showLoadingButton();
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    ApiUrls.BASE_API_URL+"/services/"+offerUuid+"/complete-offer",
                    appointmentData,
                    response -> {
                        view.hideLoadingButton();
                        view.onSendAppointmentSuccess(response);
                    },
                    error -> {
                        view.hideLoadingButton();
                        view.onSendAppointmentError(error);
                    }){
                  @Override
                   public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("Authorization", "Bearer "+token);
                    return params;
                }

            };
            VolleyController.getInstance(context).addToRequestQueue(request);
        }else {
            view.onFailedValidation();
        }
    }
}
