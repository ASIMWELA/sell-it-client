package com.main.sellit.ui.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.main.sellit.R;
import com.main.sellit.helper.AppConstants;

public class CustomerViewRequestOffersActivity extends AppCompatActivity {

    String requestUuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_request_offers);
        requestUuid = getIntent().getStringExtra(AppConstants.UUID_TO_VIEW_OFFERS_FOR);
    }
}