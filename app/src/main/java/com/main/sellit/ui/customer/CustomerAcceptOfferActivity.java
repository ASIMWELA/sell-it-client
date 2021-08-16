package com.main.sellit.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.helper.AppConstants;

public class CustomerAcceptOfferActivity extends AppCompatActivity {

    LoadingButton btnSendAppointment;
    EditText etAppointmentDesc,
            etAppointmentStartTime,
            etAppointmentEndTime,
            etAppointmentDate;
    ImageView ivBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_accept_offer);
        initViews();

        String serviceDeliverOfferUuid = getIntent().getStringExtra(AppConstants.CREATE_APPOINTMENT_FOR_SERVICE_DELIVERY_OFFER_UUID);



    }

    private void initViews(){
        ivBackArrow = findViewById(R.id.et_customer_create_appointment_appointment_back_arrow);
        etAppointmentDate = findViewById(R.id.et_customer_create_appointment_appointment_date);
        etAppointmentDesc = findViewById(R.id.et_customer_create_appointment_appointment_desc);
        etAppointmentStartTime = findViewById(R.id.et_customer_create_appointment_appointment_start_time);
        etAppointmentEndTime = findViewById(R.id.et_customer_create_appointment_appointment_end_time);
        btnSendAppointment = findViewById(R.id.btn_customer_create_appointment_appointment_send_appointment);
    }
}