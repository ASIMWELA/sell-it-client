package com.main.sellit.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.main.sellit.R;
import com.main.sellit.contract.CustomerAppointmentsContract;
import com.main.sellit.helper.FlagErrors;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.presenter.CustomerAppointmentsPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import lombok.SneakyThrows;

public class CustomerAppointmentsActivity extends AppCompatActivity implements CustomerAppointmentsContract.View {

    FrameLayout progressBar;
    TextView tvNoAppointmentMessage;
    ImageView ivBackArrow;
    RecyclerView rvAppointments;
    SessionManager sessionManager;
    FlagErrors flagErrors;
    CustomerAppointmentsPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_appointments);
        initViews();
        sessionManager = new SessionManager(this);
        flagErrors = new FlagErrors(this,this);
        presenter = new CustomerAppointmentsPresenter(this, this);
        presenter.getAppointments(sessionManager.getToken(), sessionManager.getLoggedInCustomerUuid());

        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });
    }

    private void initViews(){
        progressBar = findViewById(R.id.progress_overlay_bar_customer_appointments);
        tvNoAppointmentMessage = findViewById(R.id.tv_no_appointments_message);
        ivBackArrow = findViewById(R.id.iv_customer_appointments_back_arrow);
        rvAppointments = findViewById(R.id.rv_customer_appointments);
    }

    @Override
    public void showGetAppointmentProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGetAppointmentProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    @SneakyThrows
    public void onGetAppointmentSuccess(JSONObject apiResponse) {
        JSONArray appointments = apiResponse.getJSONArray("data");
        if(apiResponse.length() == 0){
            tvNoAppointmentMessage.setVisibility(View.VISIBLE);
        }else {
            Toast.makeText(this, appointments.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetAppointmentError(VolleyError apiError) {
        flagErrors.flagApiError(apiError);
    }
}