package com.main.sellit.ui.customer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.contract.CustomerAcceptOfferContract;
import com.main.sellit.helper.AppConstants;
import com.main.sellit.helper.FlagErrors;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.presenter.CustomerAcceptOfferPresenter;
import com.main.sellit.ui.RequestServiceActivity;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerAcceptOfferActivity extends AppCompatActivity implements CustomerAcceptOfferContract.View {

    LoadingButton btnSendAppointment;
    EditText etAppointmentDesc,
            etAppointmentStartTime,
            etAppointmentEndTime,
            etAppointmentDate;
    FlagErrors flagErrors;
    JSONObject appointmentData;
    DatePickerDialog datePicker;
    TimePickerDialog startTime;
    TimePickerDialog endTime;
    TextView tvMessage;

    String formattedStartTime, formattedEndTime, formattedDate, appointmentDesc;
    ImageView ivBackArrow;
    CustomerAcceptOfferPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_accept_offer);
        initViews();

        String serviceDeliverOfferUuid = getIntent().getStringExtra(AppConstants.CREATE_APPOINTMENT_FOR_SERVICE_DELIVERY_OFFER_UUID);
        appointmentData = new JSONObject();
        flagErrors = new FlagErrors(this, this);
        presenter = new CustomerAcceptOfferPresenter(this, this);
        SessionManager sessionManager = new SessionManager(this);
        validateInput();



        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        prepareDatePicker(day, month, year);
        prepareStartTimePicker(calendar);
        prepareEndTimePicker(calendar);

        etAppointmentDate.setOnClickListener(v->{
            datePicker.show();
        });

        etAppointmentStartTime.setOnClickListener(v->{
            startTime.show();
        });

        etAppointmentEndTime.setOnClickListener(v->{
            endTime.show();
        });

        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });

        btnSendAppointment.setOnClickListener(v->{
            tvMessage.setVisibility(View.GONE);
            presenter.sendAppointmentDetails(sessionManager.getToken(), serviceDeliverOfferUuid, appointmentData);
        });

    }

    private void initViews(){
        ivBackArrow = findViewById(R.id.et_customer_create_appointment_appointment_back_arrow);
        etAppointmentDate = findViewById(R.id.et_customer_create_appointment_appointment_date);
        etAppointmentDesc = findViewById(R.id.et_customer_create_appointment_appointment_desc);
        etAppointmentStartTime = findViewById(R.id.et_customer_create_appointment_appointment_start_time);
        etAppointmentEndTime = findViewById(R.id.et_customer_create_appointment_appointment_end_time);
        btnSendAppointment = findViewById(R.id.btn_customer_create_appointment_appointment_send_appointment);
        tvMessage = findViewById(R.id.tv_appointment_success_msg);
    }

    @Override
    public boolean validateInput() {
        etAppointmentDesc.addTextChangedListener(new TextValidator(etAppointmentDesc) {
            @Override
            @SneakyThrows
            public void validate() {
                if(!etAppointmentDesc.getText().toString().trim().isEmpty()){
                    if(etAppointmentDesc.getText().toString().trim().length() < 20){
                        etAppointmentDesc.setError("Description too short");
                        etAppointmentDesc.setBackgroundResource(R.drawable.input_text_area_border_error);
                        appointmentDesc = null;
                    }else {
                        etAppointmentDesc.setBackgroundResource(R.drawable.input_text_area_bg);
                        appointmentDesc = etAppointmentDesc.getText().toString().trim();
                        appointmentData.put("appointmentDescription", appointmentDesc);
                    }

                }else {
                    appointmentDesc = null;
                }
            }
        });
        return appointmentDesc != null
                && formattedDate != null
                && formattedEndTime != null
                && formattedStartTime != null;
    }

    @Override
    public void onFailedValidation() {
        flagErrors.flagValidationError(R.id.customer_accept_offer_base_view);
    }

    @Override
    public void showLoadingButton() {
        btnSendAppointment.setEnabled(false);
        btnSendAppointment.showLoading();
    }

    @Override
    public void hideLoadingButton() {
        btnSendAppointment.setEnabled(true);
        btnSendAppointment.hideLoading();
    }

    @Override
    @SneakyThrows
    public void onSendAppointmentSuccess(JSONObject apiResponse) {

               appointmentDesc = null;
                 formattedDate = null;
                formattedEndTime = null;
                formattedStartTime = null;

                etAppointmentEndTime.setText("");
                etAppointmentStartTime.setText("");
                etAppointmentDate.setText("");
                etAppointmentDesc.setText("");
        String msg = apiResponse.getString("message");
        tvMessage.setText(msg);
        tvMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSendAppointmentError(VolleyError apiError) {
        String body = new String(apiError.networkResponse.data, StandardCharsets.UTF_8);
        Toast.makeText(this, body, Toast.LENGTH_SHORT).show();
        flagErrors.flagApiError(apiError);
    }

    private void prepareDatePicker(int day, int month, int year) {
        datePicker = new DatePickerDialog(CustomerAcceptOfferActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    @SneakyThrows
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar c = Calendar.getInstance();



                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        etAppointmentDate.setText(date);

                        String getMonth = (monthOfYear+1)<10? "0"+(monthOfYear+1):(monthOfYear+1)+"";
                        String getDay = dayOfMonth<10? "0"+dayOfMonth:dayOfMonth+"";

                        c.set(year, Integer.parseInt(getMonth)-1, Integer.parseInt(getDay), 0, 0);

                        if(LocalDate.of(year, Integer.parseInt(getMonth), Integer.parseInt(getDay)).isBefore(LocalDate.now())){
                            formattedDate = null;
                            etAppointmentDate.setError("Wrong appointment date");
                            etAppointmentDate.setBackgroundResource(R.drawable.rounded_boaders_error);
                        }else {
                            formattedDate = year +"-"+getMonth+"-"+getDay;
                            appointmentData.put("serviceDeliveredOn", formattedDate);
                            etAppointmentDate.setError(null);
                            etAppointmentDate.setBackgroundResource(R.drawable.rounded_boaders);
                        }

                    }
                }, year, month, day);
    }

    private void prepareStartTimePicker(Calendar cldr) {
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog
        startTime = new TimePickerDialog(CustomerAcceptOfferActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    @SneakyThrows
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {


                        final Calendar cldr = Calendar.getInstance();
                        int day = cldr.get(Calendar.DAY_OF_MONTH);
                        int month = cldr.get(Calendar.MONTH);
                        int year = cldr.get(Calendar.YEAR);
                        String getMonth = (month-1)<10? "0"+(month-1):(month-1)+"";
                        String getDay = day<10? "0"+day:day+"";
                        //format dates
                        String h = sHour < 10?"0"+sHour:sHour+"";
                        String m = sMinute<10? "0"+sMinute:sMinute+"";
                        String time = h + ":" + m;
                        etAppointmentStartTime.setText(time);
                        formattedStartTime = year +"-"+getMonth+"-"+getDay+"T"+h+":"+m+":43.511Z";
                        appointmentData.put("serviceStartTime", formattedStartTime);
                    }
                }, hour, minutes, true);
    }

    private void prepareEndTimePicker(Calendar cldr) {
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);


        // time picker dialog
        endTime = new TimePickerDialog(CustomerAcceptOfferActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    @SneakyThrows
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {

                        final Calendar cldr = Calendar.getInstance();
                        int day = cldr.get(Calendar.DAY_OF_MONTH);
                        int month = cldr.get(Calendar.MONTH);
                        int year = cldr.get(Calendar.YEAR);
                        String getMonth = (month-1)<10? "0"+(month-1):(month-1)+"";
                        String getDay = day<10? "0"+day:day+"";
                        //format dates
                        String h = sHour < 10?"0"+sHour:sHour+"";
                        String m = sMinute<10? "0"+sMinute:sMinute+"";
                        String time = h + ":" + m;
                        etAppointmentEndTime.setText(time);
                        formattedEndTime = year +"-"+getMonth+"-"+getDay+"T"+h+":"+m+":43.511Z";
                        appointmentData.put("serviceEndTime", formattedEndTime);
                    }
                }, hour, minutes, true);
    }
}