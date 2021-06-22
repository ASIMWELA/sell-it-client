package com.main.sellit.presenter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.main.sellit.R;
import com.main.sellit.contract.ProviderContract;
import com.main.sellit.helper.TextValidator;

import org.json.JSONObject;

public class ProviderPresenter implements ProviderContract.Presenter {
    ProviderContract.View view;
    Context ctx;

    public ProviderPresenter(ProviderContract.View view, Context ctx) {
        this.view = view;
        this.ctx = ctx;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"ResourceAsColor", "UseCompatTextViewDrawableApis"})
    @Override
    public JSONObject createPersonalDetailsObject(View view) {
        String userName,
                firstName,
                lastName,
                email,
                phoneNumber,
                password;

        EditText editTextUserName= (EditText)view.findViewById(R.id.edtx_user_name);
        EditText editTextFirstName= (EditText)view.findViewById(R.id.edtx_first_name);
        EditText editTextLastName= (EditText)view.findViewById(R.id.edtx_last_name);
        EditText editTextEmail= (EditText)view.findViewById(R.id.edtx_email);
        EditText editTextPhoneNumber= (EditText)view.findViewById(R.id.edtx_phone_number);
        EditText editTextPassword= (EditText)view.findViewById(R.id.edtx_password);

        editTextEmail.addTextChangedListener(new TextValidator(editTextEmail) {
            @Override
            public void validate(int stringLength, boolean isEmail) {

            }
        });
        //TODO:validate inputs
        userName = editTextUserName.getText().toString().trim();
        firstName = editTextFirstName.getText().toString().trim();
        lastName = editTextLastName.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        phoneNumber = editTextPhoneNumber.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();




        return null;

    }
}
