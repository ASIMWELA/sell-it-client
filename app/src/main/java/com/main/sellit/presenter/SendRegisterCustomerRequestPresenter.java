package com.main.sellit.presenter;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.SendCustomerSignUpRequestContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendRegisterCustomerRequestPresenter implements SendCustomerSignUpRequestContract.Presenter{
    final SendCustomerSignUpRequestContract.View view;
    final Context ctx;

    public SendRegisterCustomerRequestPresenter(SendCustomerSignUpRequestContract.View view, Context ctx) {
        this.view = view;
        this.ctx = ctx;
    }

    @Override
    public void sendSignUpRequest(JSONObject data) {
        if(view.validateData()){
            view.showLoadingButton();
            JsonObjectRequest signUpCustomerRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    ApiUrls.BASE_API_URL + "/customers", data,
                    response -> {
                        view.hideLoadingButton();
                        view.onRegistrationRequestSuccess(response);
                   }, error -> {
                       view.onRegistrationRequestError(error);
                       view.hideLoadingButton();
            });

            VolleyController.getInstance(ctx).addToRequestQueue(signUpCustomerRequest);

        }else {
            view.onFailedValidation();
        }

    }
}
