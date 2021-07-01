package com.main.sellit.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.main.sellit.R;

public class Validator {
    static boolean hasErrors;
    public static boolean inputHasErrors(EditText editText, int minLength, boolean isEmail){

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(editText.getText().toString().length()<minLength){
                    Validator.hasErrors = true;
                    editText.setError("Short length");
                    editText.setBackgroundResource(R.drawable.rounded_boaders_error);

                }else{
                    Validator.hasErrors = false;
                    editText.setBackgroundResource(R.drawable.rounded_boaders);
                }
                if(isEmail){
                    String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if(!editText.getText().toString().matches(emailRegex)){
                        Validator.hasErrors = true;
                        editText.setError("Invalid email");
                        editText.setBackgroundResource(R.drawable.rounded_boaders_error);
                    }else{
                        Validator.hasErrors = false;
                        editText.setBackgroundResource(R.drawable.rounded_boaders);
                    }

                }
            }
        });
        return Validator.hasErrors;
    }
}
