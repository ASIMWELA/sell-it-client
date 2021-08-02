package com.main.sellit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.main.sellit.R;
import com.main.sellit.helper.AppConstants;

import java.util.Calendar;
import java.util.Date;

public class RequestServiceActivity extends AppCompatActivity {

    TextView tvServiceName;
    EditText etDateRequiredOn, etStartTime;
    String serviceName, serviceUuid;
    DatePickerDialog datePicker;
    Date serviceRequiredOn;
    TimePickerDialog timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);
        initViews();
        Intent i = getIntent();
        serviceName = i.getStringExtra(AppConstants.REQUEST_SERVICE_SERVICE_NAME);
        serviceUuid = i.getStringExtra(AppConstants.GET_SERVICE_PROVIDERS_UUID);
        tvServiceName.setText(serviceName);

        etDateRequiredOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(RequestServiceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                Calendar c = Calendar.getInstance();
                                c.set(year, month - 1, day, 0, 0);
                                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                etDateRequiredOn.setText(date);
                                serviceRequiredOn = c.getTime();
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });
        etStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(RequestServiceActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                etStartTime.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                timePicker.show();
            }
        });

    }

    private void initViews(){
        tvServiceName = findViewById(R.id.tv_customer_request_service_service_name);
        etDateRequiredOn = findViewById(R.id.et_customer_request_service_pick_required_on_date);
        etStartTime = findViewById(R.id.customer_request_service_pick_time);
    }
}