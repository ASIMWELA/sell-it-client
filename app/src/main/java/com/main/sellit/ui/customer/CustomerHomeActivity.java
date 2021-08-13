package com.main.sellit.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.main.sellit.R;
import com.main.sellit.contract.CustomerHomeContract;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.model.CustomerLoginModel;
import com.main.sellit.presenter.CustomerHomePresenter;
import com.main.sellit.ui.LoginActivity;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerHomeActivity extends AppCompatActivity implements CustomerHomeContract.View {

    RelativeLayout btnLogout;
    CustomerHomePresenter customerHomePresenter;
    SessionManager sessionManager;
    TextView tvCustomerName;
    CardView cvOpenAppointmentsActivity, cvOpenServicesActivity, cvOpenProfileActivity, cvOpenRequestsActivity;
    CustomerLoginModel customerLoginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        initViews();
        Intent i = getIntent();



        sessionManager = new SessionManager(this);
        customerHomePresenter = new CustomerHomePresenter(this);


        Gson gson = new Gson();
        String json = sessionManager.getLoggedInUser();
        customerLoginModel = gson.fromJson(json, CustomerLoginModel.class);

        cvOpenServicesActivity.setOnClickListener(v->{
            customerHomePresenter.onCustomerServicesCardClicked();
        });
        String customerName = customerLoginModel.getFirstName() + " "+customerLoginModel.getLastName();
        tvCustomerName.setText(customerName);
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
        tvCustomerName = findViewById(R.id.tv_customer_home_customer_name);
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
        sessionManager.setLoggedInUser(null);
        sessionManager.setAccessToken(null);
        sessionManager.setLoggedInCustomerUuid(null);
        sessionManager.setIsUserLoggedIn(null);
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }


}