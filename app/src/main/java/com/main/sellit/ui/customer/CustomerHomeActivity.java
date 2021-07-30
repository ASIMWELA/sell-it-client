package com.main.sellit.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.main.sellit.R;
import com.main.sellit.contract.CustomerHomeContract;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.presenter.CustomerHomePresenter;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerHomeActivity extends AppCompatActivity implements CustomerHomeContract.View {

    RelativeLayout btnLogout;
    CustomerHomePresenter customerHomePresenter;
    SessionManager sessionManager;
    CardView cvOpenAppointmentsActivity, cvOpenServicesActivity, cvOpenProfileActivity, cvOpenRequestsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        initViews();

        sessionManager = new SessionManager(this);
        customerHomePresenter = new CustomerHomePresenter(this);

        cvOpenServicesActivity.setOnClickListener(v->{
            customerHomePresenter.onCustomerServicesCardClicked();
        });

        cvOpenRequestsActivity.setOnClickListener(v->{
            customerHomePresenter.onCustomerRequestCardClicked();
        });

        cvOpenProfileActivity.setOnClickListener(v->{
            customerHomePresenter.onCustomerProfileCardClicked();
        });

        cvOpenAppointmentsActivity.setOnClickListener(v->{
            customerHomePresenter.onCustomerAppointmentsCardClicked();
        });
        btnLogout.setOnClickListener(v->{
            customerHomePresenter.onLogoutBtnClicked();
        });

    }

    private void initViews() {
        cvOpenAppointmentsActivity = findViewById(R.id.cv_customer_home_open_appointments_activity);
        cvOpenProfileActivity = findViewById(R.id.cv_customer_home_open_profile_activity);
        cvOpenRequestsActivity = findViewById(R.id.cv_customer_home_open_service_request_activity);
        cvOpenServicesActivity = findViewById(R.id.cv_customer_home_open_services_activity);
        btnLogout = findViewById(R.id.rl_customer_home_logout_btn);
    }

    @Override
    public void openCustomerProfile() {
        startActivity(new Intent(this, CustomerProfileActivity.class));
    }

    @Override
    public void openCustomerRequests() {
        startActivity(new Intent(this, CustomerRequestsActivity.class));
    }

    @Override
    public void openCustomerServices() {
        startActivity(new Intent(this, CustomerViewServicesActivity.class));
    }

    @Override
    public void openCustomerAppointments() {
        startActivity(new Intent(this, CustomerAppointmentsActivity.class));
    }

    @Override
    public void onLogout() {
        sessionManager.setAccessToken(null);
        onBackPressed();
    }


}