package com.main.sellit.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.DialogCompat;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.google.android.material.snackbar.Snackbar;
import com.main.sellit.R;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import lombok.SneakyThrows;

public class FlagErrors {

    Context context;
    Activity activity;
    TextView tvGeneralError;
    TextView errorTitle;
    AppCompatButton btnError;

    public FlagErrors(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @SneakyThrows
    public void flagApiError(VolleyError apiError){
        AlertDialog.Builder searchDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.api_customer_dialog_error, null);
        searchDialog.setView(dialogView);
        tvGeneralError = (TextView)dialogView.findViewById(R.id.tv_general_arror_message);
        errorTitle = (TextView)dialogView.findViewById(R.id.tv_error_title);
        btnError = (AppCompatButton)dialogView.findViewById(R.id.btn_dismiss_error_dialog);
        AlertDialog r = searchDialog.create();
        r.getWindow().setLayout(200, 100);

        if(apiError instanceof NoConnectionError) {
            errorTitle.setText("No Connection Error");
            tvGeneralError.setText("There is No Internet\nConnection Available.");
            r.show();
        }
        else {
            int errorCode = apiError.networkResponse.statusCode;
            String body;
            body = new String(apiError.networkResponse.data, StandardCharsets.UTF_8);
            JSONObject errorOb = new JSONObject(body);
            String message = errorOb.getString("message");
            switch (errorCode){
                case 400:
                    errorTitle.setText("Bad Request Error");
                    tvGeneralError.setText("Bad Request. Server Could not\nprocess This Request.");
                    r.show();
                    break;
                case 401:
                    errorTitle.setText("Authentication Error");
                    tvGeneralError.setText("Server could not authenticate\n your identity. Re-supply your\ncredentials.");
                    r.show();
                    break;
                case 403:
                    errorTitle.setText("Unauthorized Error");
                    tvGeneralError.setText("Your are not allowed to\ndo this operation.");
                    r.show();
                    break;
                case 422:
                    errorTitle.setText("Processing Error");
                    tvGeneralError.setText("The server could not process\n your entity.");
                    r.show();
                    break;
                case 409:
                    errorTitle.setText("Conflict Error");
                    tvGeneralError.setText(message);
                    r.show();
                    break;
                case 404:
                    errorTitle.setText("Entity Not Found Error");
                    tvGeneralError.setText(message);
                    r.show();
                    break;
                case 500:
                    errorTitle.setText("Server error");
                    tvGeneralError.setText("The sever could not\nprocess your request.");
                    r.show();
                    break;
                default:
                    errorTitle.setText("Error");
                    tvGeneralError.setText("Server could not process\nyour request.");
                    r.show();
            }

        }
        btnError.setOnClickListener(v->{
            r.dismiss();
        });
    }

    public void flagValidationError(int baseViewId){
        Snackbar s = Snackbar.make(activity.findViewById(baseViewId), "There are errors in your inputs",
                Snackbar.LENGTH_SHORT)
                .setBackgroundTint(activity.getResources().getColor(R.color.white))
                .setTextColor(activity.getResources().getColor(R.color.color_secondary_blend));
        View v = s.getView();
        TextView tv = v.findViewById(com.google.android.material.R.id.snackbar_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        s.show();
    }
}


