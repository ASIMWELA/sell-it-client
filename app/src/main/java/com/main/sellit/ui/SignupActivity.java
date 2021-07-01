package com.main.sellit.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.main.sellit.R;
import com.main.sellit.contract.ProviderContract;
import com.main.sellit.presenter.ProviderPresenter;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SignupActivity extends AppCompatActivity implements ProviderContract.View {

    Context ctx;
    TextView activityTitle;
    ProviderPresenter providerPresenter;
    Button captureProviderInfo;

    EditText editTxtUserName, editTxtEmail, editTxtPassword, editTxtPhoneNumber, editTxtFirstName, editTxtLastName;
    public SignupActivity(Context cts){
        this.ctx=cts;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_capture_provider_personal_details);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //initialize views
        initViews();







    }

    private void initViews(){
        captureProviderInfo = (Button)findViewById(R.id.btn_capture_user_info);
        activityTitle = (TextView)findViewById((R.id.txt_title_text));
        activityTitle.setText(R.string.sign_provider);

        //user input views
        editTxtEmail = (EditText)findViewById(R.id.edtx_email);
        editTxtFirstName=(EditText)findViewById(R.id.edtx_first_name);
        editTxtLastName = (EditText)findViewById(R.id.edtx_last_name);
        editTxtPassword = (EditText)findViewById(R.id.edtx_password);
        editTxtPhoneNumber = (EditText)findViewById(R.id.edtx_phone_number);
        editTxtUserName = (EditText)findViewById(R.id.edtx_user_name);
    }
    //load fragment into view  by swapping the views

}