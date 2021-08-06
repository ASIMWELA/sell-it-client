package com.main.sellit.ui.provider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.main.sellit.R;
import com.main.sellit.helper.AppConstants;

public class ProviderSendOfferActivity extends AppCompatActivity {

    String requestUuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_send_offer);


        Intent i = getIntent();
        requestUuid = i.getStringExtra(AppConstants.UUID_FOR_REQUEST_TO_SEND_OFFER_FOR);
    }
}