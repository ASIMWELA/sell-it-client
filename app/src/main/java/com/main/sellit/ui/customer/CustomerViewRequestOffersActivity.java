package com.main.sellit.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.main.sellit.R;
import com.main.sellit.helper.AppConstants;

public class CustomerViewRequestOffersActivity extends AppCompatActivity {

    FrameLayout progressBar;
    TextView tvNoOffersMessage, tvOfferId;
    ImageView ivBackArrow;
    RecyclerView rvOffers;



    String requestUuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_request_offers);
        initViews();

        requestUuid = getIntent().getStringExtra(AppConstants.UUID_TO_VIEW_OFFERS_FOR);

    }

    private void initViews(){
        ivBackArrow = findViewById(R.id.iv_customer_view_offers_back_arrow);
        tvNoOffersMessage = findViewById(R.id.tv_customer_view_request_no_offers);
        tvOfferId = findViewById(R.id.tv_customer_viiew_request_offers_request_name);
        rvOffers = findViewById(R.id.rv_customer_view_offers_recycler_view);
        progressBar = findViewById(R.id.progress_overlay_holder_customer_get_offers);
    }

}