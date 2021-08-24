package com.main.sellit.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.main.sellit.R;
import com.main.sellit.contract.CustomerAppointmentsContract;
import com.main.sellit.helper.FlagErrors;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.model.CustomerAppointmentModel;
import com.main.sellit.presenter.CustomerAppointmentsPresenter;
import com.main.sellit.ui.adapter.CustomerAppointmentsAdapter;
import com.main.sellit.ui.adapter.CustomerRequestsAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerAppointmentsActivity extends AppCompatActivity implements CustomerAppointmentsContract.View {

    FrameLayout progressBar;
    TextView tvNoAppointmentMessage;
    ImageView ivBackArrow;
    RecyclerView rvAppointments;
    SessionManager sessionManager;
    FlagErrors flagErrors;
    CustomerAppointmentsPresenter presenter;
    List<CustomerAppointmentModel> customerAppointmentModelList;
    CustomerAppointmentsAdapter customerAppointmentsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_appointments);
        initViews();
        sessionManager = new SessionManager(this);
        flagErrors = new FlagErrors(this,this);
        presenter = new CustomerAppointmentsPresenter(this, this);
        presenter.getAppointments(sessionManager.getToken(), sessionManager.getLoggedInCustomerUuid());
        customerAppointmentModelList = new ArrayList<>();

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
            for (int x = 0; x < appointments.length(); x++){
                Gson gson = new Gson();
                JSONObject appoint= appointments.getJSONObject(x);
                CustomerAppointmentModel model = gson.fromJson(String.valueOf(appoint), CustomerAppointmentModel.class);
                customerAppointmentModelList.add(model);
            }

            customerAppointmentsAdapter = new CustomerAppointmentsAdapter(this, customerAppointmentModelList);
            LinearLayoutManager r = new LinearLayoutManager(this);
            rvAppointments.setLayoutManager(r);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvAppointments.getContext(),
                    r.getOrientation());
            rvAppointments.addItemDecoration(dividerItemDecoration);
            rvAppointments.setItemAnimator(new DefaultItemAnimator());
            rvAppointments.setAdapter(customerAppointmentsAdapter);
        }
    }

    @Override
    public void onGetAppointmentError(VolleyError apiError) {
        flagErrors.flagApiError(apiError);
    }
}