package com.main.sellit.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;
import com.main.sellit.contract.LoginContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginPresenter implements LoginContract.Presenter {

    LoginContract.LoginView view;
    Context context;

    public LoginPresenter(LoginContract.LoginView view, Context context) {
        this.view = view;
        this.context = context;
    }
    @SneakyThrows
    @Override
    public void login(String userName, String password){
            view.startLoadingButton();
            JSONObject loginObject = new JSONObject();
            loginObject.put("userName", userName);
            loginObject.put("password", password);
            JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, ApiUrls.BASE_API_URL + "/auth/login", loginObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if(response == null){
                        Toast.makeText(context, "We could not authenticate. Try again", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    view.stopLoadingButton();
                    view.onLoginSuccess(response);
                }
            }, error -> {
                if (error == null || error.networkResponse == null) {
                    view.stopLoadingButton();
                    return;
                }
                String body;
                body = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                view.onLoginFailure(body);
                view.stopLoadingButton();

            });

            VolleyController.getInstance(context).addToRequestQueue(loginRequest);

    }

}
