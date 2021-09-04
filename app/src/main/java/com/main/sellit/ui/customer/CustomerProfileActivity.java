package com.main.sellit.ui.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.main.sellit.R;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.model.CustomerLoginModel;

public class CustomerProfileActivity extends AppCompatActivity {

    TextView tvCustomerName,
            tvCustomerEmail,
            tvCustomerPhone,
            tvUsername,
            tvCustomerLocation;
    SessionManager sessionManager;
    ImageView ivBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        initViews();
        sessionManager = new SessionManager(this);
        Gson gson = new Gson();
        String json = sessionManager.getLoggedInUser();
        CustomerLoginModel customer = gson.fromJson(json, CustomerLoginModel.class);
        tvUsername.setText(customer.getUserName());
        tvCustomerPhone.setText(customer.getPhoneNumber());
        tvCustomerEmail.setText(customer.getEmail());
        String name = customer.getFirstName() +" "+customer.getLastName();
        tvCustomerName.setText(name);

        String location = customer.getCountry()+
                ", "+customer.getCity()+
                ", "+customer.getStreet()+","+
                "\n"+customer.getLocationDescription();
        tvCustomerLocation.setText(location);

        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });


    }

    private void initViews(){
        tvCustomerEmail = findViewById(R.id.tv_customer_profile_email);
        tvCustomerName = findViewById(R.id.tv_customer_profile_full_name);
        tvCustomerPhone = findViewById(R.id.tv_provider_profile_phone_number);
        tvUsername = findViewById(R.id.tv_customer_profile_username);
        tvCustomerLocation = findViewById(R.id.tv_customer_address);
        ivBackArrow = findViewById(R.id.iv_customer_profile_back_arrow);
    }

}