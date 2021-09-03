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
import com.main.sellit.contract.CustomerViewProvidersContract;
import com.main.sellit.helper.AppConstants;
import com.main.sellit.helper.FlagErrors;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.model.ServiceProvider;
import com.main.sellit.presenter.customer.CustomerViewProvidersPresenter;
import com.main.sellit.ui.adapter.ServiceProviderAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class CustomerViewProvidersActivity extends AppCompatActivity implements CustomerViewProvidersContract.View {

    FrameLayout progressBar;
    ImageView ivBackArrow;
    RecyclerView rvProvidersHolder;
    TextView tvNoProvidersMessage, tvServiceName;
    FlagErrors flagErrors;
    CustomerViewProvidersPresenter providersPresenter;
    SessionManager sessionManager;
    List<ServiceProvider> serviceProviders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_providers);
        initViews();
        flagErrors = new FlagErrors(this, this);
        sessionManager = new SessionManager(this);
        String serviceUuid = getIntent().getStringExtra(AppConstants.GET_SERVICE_PROVIDERS_UUID);
        String serviceName= getIntent().getStringExtra("serviceName");
        tvServiceName.setText(serviceName);
        serviceProviders = new ArrayList<>();
        providersPresenter = new CustomerViewProvidersPresenter(this, this);

        //get providers
        providersPresenter.getProviders(sessionManager.getToken(), serviceUuid);

        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });

    }

    private void initViews(){
        progressBar = findViewById(R.id.progress_overlay_bar_customer_view_providers);
        ivBackArrow = findViewById(R.id.iv_customer_view_providers_back_arrow);
        rvProvidersHolder = findViewById(R.id.rv_customer_view_providers);
        tvNoProvidersMessage = findViewById(R.id.tv_no_providers_message);
        tvServiceName = findViewById(R.id.tv_customer_view_providers_service_name);

    }

    @Override
    public void showGetProvidersProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGetProvidersProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    @SneakyThrows
    public void onGetProvidersSuccess(JSONObject apiResponse) {
        JSONArray providers = apiResponse.getJSONArray("data");
        if(providers.length() == 0){
            tvNoProvidersMessage.setVisibility(View.VISIBLE);
        }else {
            for(int x =0; x < providers.length(); x++){
                JSONObject provider = providers.getJSONObject(x);
                Gson gson = new Gson();
                ServiceProvider serviceProvider = gson.fromJson(String.valueOf(provider), ServiceProvider.class);
                serviceProviders.add(serviceProvider);
            }

            ServiceProviderAdapter serviceProviderAdapter = new ServiceProviderAdapter(this, serviceProviders);
            LinearLayoutManager r = new LinearLayoutManager(this);
            rvProvidersHolder.setLayoutManager(r);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvProvidersHolder.getContext(), r.getOrientation());
            rvProvidersHolder.addItemDecoration(dividerItemDecoration);
            rvProvidersHolder.setItemAnimator(new DefaultItemAnimator());
            rvProvidersHolder.setAdapter(serviceProviderAdapter);
        }
    }

    @Override
    public void onGetProvidersError(VolleyError apiError) {
        flagErrors.flagApiError(apiError);
    }
}