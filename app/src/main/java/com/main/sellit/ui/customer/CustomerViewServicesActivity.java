package com.main.sellit.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.main.sellit.R;
import com.main.sellit.contract.CustomerServicesContract;
import com.main.sellit.model.ServiceAndCategoryNamesModel;
import com.main.sellit.presenter.CustomerServicesPresenter;
import com.main.sellit.ui.adapter.CustomerServiceListAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerViewServicesActivity extends AppCompatActivity implements CustomerServicesContract.View {

    FrameLayout progressBar;
    ImageView ivBackArrow;
    RecyclerView recyclerViewServices;
    CustomerServicesPresenter customerServicesPresenter;
    List<ServiceAndCategoryNamesModel> serviceAndCategoryNamesModelList;
    CustomerServiceListAdapter customerServiceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_services);
        initViews();
        customerServicesPresenter = new CustomerServicesPresenter(this, this);
        customerServicesPresenter.getServices();

        serviceAndCategoryNamesModelList = new ArrayList<>();

        for(int x = 0; x < 10; x++){
                ServiceAndCategoryNamesModel s = new ServiceAndCategoryNamesModel();
                s.setServiceCategory("Service "+ x);
                s.setServiceName("Category" + x);
                serviceAndCategoryNamesModelList.add(s);
        }

        customerServiceListAdapter = new CustomerServiceListAdapter(serviceAndCategoryNamesModelList, this);

        recyclerViewServices.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewServices.setAdapter(customerServiceListAdapter);
    }

    private void initViews(){
        progressBar = findViewById(R.id.progress_overlay_holder_customer_services);
        ivBackArrow = findViewById(R.id.ivCustomerServicesBackArrow);
        recyclerViewServices = findViewById(R.id.recycler_view_customer_service_container);
    }
    @Override
    public void onGetServicesSuccess(JSONObject response) {
        Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetServicesError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
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