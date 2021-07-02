package com.main.sellit.helper;

import android.widget.EditText;

import com.main.sellit.R;

public class Validator{
    String userValue;

    public void validateUserInput(EditText editText, String userInput){
        String cool = userInput;
        editText.addTextChangedListener(new TextValidator(editText) {
            @Override
            public void validate() {
                if(editText.getText().toString().trim().length()<3){
                   // value = null;
                    editText.setBackgroundResource(R.drawable.rounded_boaders_error);
                    editText.setError("Input too short");

                }else {
                    editText.setBackgroundResource(R.drawable.rounded_boaders);
                     editText.getText().toString().trim();
                }
            }
        });

    }

}
