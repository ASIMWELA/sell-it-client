package com.main.sellit.presenter;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.CustomerServicesContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerServicesPresenter implements CustomerServicesContract.Presenter {
    CustomerServicesContract.View view;
    Context ctx;

    public CustomerServicesPresenter(CustomerServicesContract.View view, Context ctx) {
        this.view = view;
        this.ctx = ctx;
    }

    @Override
    public void getServices() {
        view.showGetServicesProgressBar();

        JsonObjectRequest serviceCategoriesRequest = new JsonObjectRequest(
                Request.Method.GET, ApiUrls.BASE_API_URL + "/services?pageNo=0&pageSize=150",
                null,
                response -> {
                   view.hideGetServicesProgressBar();
                   view.onGetServicesSuccess(response);
              }, error -> {
                    view.hideGetServicesProgressBar();
                    view.onGetServicesError(error);
        });
        VolleyController.getInstance(ctx).addToRequestQueue(serviceCategoriesRequest);
    }
}
