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

import com.android.volley.VolleyError;
import com.main.sellit.R;
import com.main.sellit.contract.CustomerRequestContract;
import com.main.sellit.helper.FlagErrors;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.model.CustomerServiceRequestModel;
import com.main.sellit.presenter.customer.CustomerRequestPresenter;
import com.main.sellit.ui.adapter.CustomerRequestsAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequestsActivity extends AppCompatActivity implements CustomerRequestContract.View {

    FrameLayout progressBar;
    TextView tvNoRequestMessage;
    RecyclerView requestRecyclerView;
    ImageView ivBackArrow;
    FlagErrors flagErrors;
    CustomerRequestsAdapter customerRequestsAdapter;
    List<CustomerServiceRequestModel> customerServiceRequestModelList;
    CustomerRequestPresenter customerRequestPresenter;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_requests);
        initViews();
        flagErrors = new FlagErrors(this, this);
        customerServiceRequestModelList = new ArrayList<>();
        customerRequestPresenter = new CustomerRequestPresenter(this, this);
        sessionManager = new SessionManager(this);
        customerRequestPresenter.getCustomerRequests(sessionManager.getToken(), sessionManager.getLoggedInCustomerUuid());
        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });
    }

    private void initViews(){
        progressBar = findViewById(R.id.progress_overlay_bar_customer_requests);
        tvNoRequestMessage = findViewById(R.id.tv_customer_requests_no_requests_message);
        requestRecyclerView = findViewById(R.id.rv_customer_requests);
        ivBackArrow = findViewById(R.id.iv_customer_back_arrow);
    }
    @Override
    @SneakyThrows
    public void onGetRequestSuccess(JSONObject apiResponse) {
        JSONArray dataArray = apiResponse.getJSONArray("data");
        if(dataArray.length() == 0){
            tvNoRequestMessage.setVisibility(View.VISIBLE);
        }else {
            for(int x = 0 ; x < dataArray.length(); x++){
                JSONObject requestObject = dataArray.getJSONObject(x);
                CustomerServiceRequestModel requestModel =
                        CustomerServiceRequestModel.builder()
                                .uuid(requestObject.getString("uuid"))
                                .expectedHours(requestObject.getString("expectedHours"))
                                .expectedStartTime(requestObject.getString("expectedStartTime"))
                                .requiredOn(requestObject.getString("requiredOn"))
                                .requirementDescription(requestObject.getString("requirementDescription"))
                                .build();
                customerServiceRequestModelList.add(requestModel);
            }
            customerRequestsAdapter = new CustomerRequestsAdapter(customerServiceRequestModelList, this);
            LinearLayoutManager r = new LinearLayoutManager(this);
            requestRecyclerView.setLayoutManager(r);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requestRecyclerView.getContext(),
                    r.getOrientation());
            requestRecyclerView.addItemDecoration(dividerItemDecoration);
            requestRecyclerView.setItemAnimator(new DefaultItemAnimator());
            requestRecyclerView.setAdapter(customerRequestsAdapter);
        }

    }
    @Override
    public void onGetRequestsError(VolleyError apiError) {
        flagErrors.flagApiError(apiError);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}