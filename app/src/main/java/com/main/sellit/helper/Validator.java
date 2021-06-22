package com.main.sellit.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.main.sellit.R;

public class Validator {
    public static void editTextValidator(EditText editText, int minLength, boolean isEmail){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()<minLength){
                    editText.setError("Short length");
                    editText.setBackgroundResource(R.drawable.rounded_boaders_error);
                    return;
                }else{
                    editText.setBackgroundResource(R.drawable.rounded_boaders);
                }

                if(isEmail){
                    String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if(!s.toString().matches(emailRegex)){
                        editText.setError("Invalid email");
                        editText.setBackgroundResource(R.drawable.rounded_boaders_error);
                        return;

                    }

                }
            }
        });
    }
}
