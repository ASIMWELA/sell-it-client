package com.main.sellit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.contract.RequestServiceContract;
import com.main.sellit.helper.AppConstants;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.presenter.RequestServicePresenter;

import org.json.JSONObject;

import java.util.Calendar;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestServiceActivity extends AppCompatActivity implements RequestServiceContract.View {

    TextView tvServiceName, tvApiResponse;
    EditText etDateRequiredOn, etStartTime, etServiceDesc, etExpectedHours;
    Long expectedHours;
    String serviceDescription;
    String formattedRequiredDate;
    String formattedExpectedStartTime;
    DatePickerDialog datePicker;
    TimePickerDialog timePicker;
    LoadingButton btnSendRequest;
    JSONObject data;
    ImageView ivBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);
        initViews();
        RequestServicePresenter requestServicePresenter = new RequestServicePresenter(this, this);
        data = new JSONObject();


        Intent i = getIntent();
        String serviceName = i.getStringExtra(AppConstants.REQUEST_SERVICE_SERVICE_NAME);
        String serviceUuid = i.getStringExtra(AppConstants.REQUEST_SERVICE_UUID);
        tvServiceName.setText(serviceName);
        SessionManager sessionManager = new SessionManager(this);



        validateInput();


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
                            @SneakyThrows
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                etDateRequiredOn.setText(date);

                                String getMonth = (monthOfYear+1)<10? "0"+(monthOfYear+1):(monthOfYear+1)+"";
                                String getDay = dayOfMonth<10? "0"+dayOfMonth:dayOfMonth+"";
                                formattedRequiredDate = year +"-"+getMonth+"-"+getDay;
                                data.put("requiredOn", formattedRequiredDate);
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
                            @SneakyThrows
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String time = sHour + ":" + sMinute;
                                etStartTime.setText(time);

                                //TODO: CHANGE IT TO STRING (time) AFTER CHANGING DB SCHEMA
                                final Calendar cldr = Calendar.getInstance();
                                int day = cldr.get(Calendar.DAY_OF_MONTH);
                                int month = cldr.get(Calendar.MONTH);
                                int year = cldr.get(Calendar.YEAR);
                                String getMonth = (month-1)<10? "0"+(month-1):(month-1)+"";
                                String getDay = day<10? "0"+day:day+"";
                                formattedExpectedStartTime = year +"-"+getMonth+"-"+getDay;
                                data.put("expectedStartTime", formattedExpectedStartTime);
                            }
                        }, hour, minutes, true);
                timePicker.show();
            }
        });

        String customerUuid = sessionManager.getLoggedInCustomerUuid();
        String token = sessionManager.getToken();

        btnSendRequest.setOnClickListener(v->{
            tvApiResponse.setVisibility(View.GONE);
            requestServicePresenter.createRequest(data, customerUuid, serviceUuid, token);
        });
        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });

    }

    private void initViews(){
        tvServiceName = findViewById(R.id.tv_customer_request_service_service_name);
        etDateRequiredOn = findViewById(R.id.et_customer_request_service_pick_required_on_date);
        etStartTime = findViewById(R.id.customer_request_service_pick_time);
        etServiceDesc=findViewById(R.id.et_customer_request_service_service_description);
        etExpectedHours = findViewById(R.id.et_customer_request_service_service_required_hours);
        btnSendRequest = findViewById(R.id.btn_customer_request_service_send_request);
        ivBackArrow = findViewById(R.id.iv_customer_service_bac_arrow);
        tvApiResponse = findViewById(R.id.tv_api_response);

    }

    @Override
    public boolean validateInput() {
        etExpectedHours.addTextChangedListener(new TextValidator(etExpectedHours) {
            @Override
            @SneakyThrows
            public void validate() {
                if(!etExpectedHours.getText().toString().trim().isEmpty()){
                    if(etExpectedHours.getText().toString().trim().length()==0){
                        etExpectedHours.setError("invalid expected hours");
                        etExpectedHours.setBackgroundResource(R.drawable.rounded_boaders_error);
                        expectedHours = -1L;
                    }else {
                        etExpectedHours.setBackgroundResource(R.drawable.rounded_boaders);
                        expectedHours = Long.parseLong(etExpectedHours.getText().toString().trim());
                        data.put("expectedTentativeEffortRequiredInHours", expectedHours);
                    }
                }

            }
        });

        etServiceDesc.addTextChangedListener(new TextValidator(etServiceDesc) {
            @Override
            @SneakyThrows
            public void validate() {
                if(!etServiceDesc.getText().toString().trim().isEmpty()){
                    if(etServiceDesc.getText().toString().trim().length()<20){
                        etServiceDesc.setError("Service description too short");
                        etServiceDesc.setBackgroundResource(R.drawable.input_text_area_border_error);
                        serviceDescription = null;
                    }else {
                        etServiceDesc.setBackgroundResource(R.drawable.input_text_area_bg);
                        serviceDescription = etServiceDesc.getText().toString().trim();
                        data.put("requirementDescription", serviceDescription);
                    }
                }

            }
        });
        return (serviceDescription != null)
                &&
                (formattedRequiredDate != null)
                &&
                (formattedExpectedStartTime != null)
                &&
                (expectedHours != -1L);
    }

    @Override
    public void onFailedValidation() {
        Snackbar.make(findViewById(R.id.customer_request_service_base_view), "There are errors in Your Inputs", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingButton() {
        btnSendRequest.setEnabled(false);
        btnSendRequest.showLoading();
    }

    @Override
    public void hideLoadingButton() {
        btnSendRequest.hideLoading();
        btnSendRequest.setEnabled(true);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    @SneakyThrows
    public void onCreateRequestSuccess(JSONObject apiResponse) {

        etDateRequiredOn.setText(null);
        etStartTime.setText(null);
        etServiceDesc.setText(null);
        etServiceDesc.setError(null);
        etServiceDesc.setBackgroundResource(R.drawable.input_text_area_bg);
        etExpectedHours.setText(null);
        etExpectedHours.setError(null);

        serviceDescription = null;
        formattedRequiredDate = null;
        formattedExpectedStartTime = null;
        expectedHours = -1L;
        etExpectedHours.setBackgroundResource(R.drawable.rounded_boaders);
        tvApiResponse.setTextColor(getResources().getColor(R.color.color_secondary));
        tvApiResponse.setText(apiResponse.getString("message"));
        tvApiResponse.setVisibility(View.VISIBLE);
        Toast.makeText(this, apiResponse.getString("message"), Toast.LENGTH_SHORT).show();
    }

    @Override
    @SneakyThrows
    public void onCreateRequestError(String apiError) {
        JSONObject error = new JSONObject(apiError);
        etExpectedHours.setBackgroundResource(R.drawable.rounded_boaders);
        tvApiResponse.setTextColor(getResources().getColor(R.color.color_secondary_blend));
        tvApiResponse.setText(error.getString("message"));
        tvApiResponse.setVisibility(View.VISIBLE);
        Toast.makeText(this, error.getString("message"), Toast.LENGTH_SHORT).show();
    }
}