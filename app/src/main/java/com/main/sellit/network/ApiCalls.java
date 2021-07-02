package com.main.sellit.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.helper.ApiUrls;

import org.json.JSONObject;

public class ApiCalls {
    Context ctx;

    public ApiCalls(Context ctx) {
        this.ctx = ctx;
    }

    public void signUpProvider(JSONObject signUpRequest){

    }
}
