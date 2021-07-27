package com.main.sellit.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.main.sellit.R;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerHomeActivity extends AppCompatActivity {

    RelativeLayout btnLogout;
    CardView cvOpenAppointmentsActivity, cvOpenServicesActivity, cvOpenProfileActivity, cvOpenRequestsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        initViews();


    }



    private void initViews() {
        cvOpenAppointmentsActivity = findViewById(R.id.cv_customer_home_open_appointments_activity);
        cvOpenProfileActivity = findViewById(R.id.cv_customer_home_open_profile_activity);
        cvOpenRequestsActivity = findViewById(R.id.cv_customer_home_open_service_request_activity);
        cvOpenServicesActivity = findViewById(R.id.cv_customer_home_open_services_activity);
        btnLogout = findViewById(R.id.rl_customer_home_logout_btn);
    }

}