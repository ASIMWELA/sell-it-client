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

import com.main.sellit.R;
import com.main.sellit.contract.CustomerServicesContract;
import com.main.sellit.model.ServiceAndCategoryNamesModel;
import com.main.sellit.presenter.CustomerServicesPresenter;
import com.main.sellit.ui.adapter.CustomerServiceListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerViewServicesActivity extends AppCompatActivity implements CustomerServicesContract.View {

    FrameLayout progressBar;
    ImageView ivBackArrow;
    RecyclerView recyclerViewServices;
    List<ServiceAndCategoryNamesModel> serviceAndCategoryNamesModelList;
    TextView tvServicesNotAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_services);
        initViews();
        CustomerServicesPresenter customerServicesPresenter = new CustomerServicesPresenter(this, this);
        customerServicesPresenter.getServices();
        serviceAndCategoryNamesModelList = new ArrayList<>();
        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });

    }

    private void initViews(){
        progressBar = findViewById(R.id.progress_overlay_holder_customer_services);
        ivBackArrow = findViewById(R.id.ivCustomerServicesBackArrow);
        recyclerViewServices = findViewById(R.id.recycler_view_customer_service_container);
        tvServicesNotAvailable = findViewById(R.id.tv_customer_view_services_no_services_messages);
    }
    @Override
    @SneakyThrows
    public void onGetServicesSuccess(JSONObject response) {
        JSONArray servicesArray = response.getJSONArray("data");
        if(servicesArray.length()==0){
            tvServicesNotAvailable.setVisibility(View.VISIBLE);
        }else {
            for(int x = 0; x < servicesArray.length(); x++){
                ServiceAndCategoryNamesModel s = ServiceAndCategoryNamesModel
                                                    .builder()
                                                    .serviceName(servicesArray.getJSONObject(x).getString("serviceName"))
                                                    .serviceCategory(servicesArray.getJSONObject(x).getString("categoryName"))
                                                    .serviceUuid(servicesArray.getJSONObject(x).getString("uuid"))
                                                    .build();
                serviceAndCategoryNamesModelList.add(s);
            }
            CustomerServiceListAdapter customerServiceListAdapter = new CustomerServiceListAdapter(serviceAndCategoryNamesModelList, this);
            LinearLayoutManager r = new LinearLayoutManager(this);
            recyclerViewServices.setLayoutManager(r);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewServices.getContext(),
                    r.getOrientation());
            recyclerViewServices.addItemDecoration(dividerItemDecoration);
            recyclerViewServices.setItemAnimator(new DefaultItemAnimator());
            recyclerViewServices.setAdapter(customerServiceListAdapter);
        }
    }

    @Override
    @SneakyThrows
    public void onGetServicesError(String error) {
        JSONObject errorObj = new JSONObject(error);
        String errorMessage = errorObj.getString("message");
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showGetServicesProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void hideGetServicesProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}