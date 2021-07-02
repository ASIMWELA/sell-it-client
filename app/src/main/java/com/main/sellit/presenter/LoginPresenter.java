package com.main.sellit.presenter;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.LoginContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.SneakyThrows;

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
        JSONObject loginObject = new JSONObject();
        loginObject.put("userName", userName);
        loginObject.put("password", password);
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, ApiUrls.BASE_API_URL + "/auth/login", loginObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                    view.onLoginSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.onLoginFailure();
            }
        });

        VolleyController.getInstance(context).addToRequestQueue(loginRequest);
    }
}
