package com.main.sellit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.contract.MapServiceToProviderContract;
import com.main.sellit.model.Service;
import com.main.sellit.presenter.MapServiceToProviderPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import lombok.SneakyThrows;

public class MapServiceToProviderActivity extends AppCompatActivity implements MapServiceToProviderContract.View {

    EditText etBillingPerHour, etExperienceInMonths, etServiceOfferDescription;
    FrameLayout fmLoadingProgressBar;
    LoadingButton btnSendProviderDetails;
    String serviceUuid;
    TextView tvActivityTitle;
    ImageView ivBackArrow;
    MapServiceToProviderPresenter presenter;
    Spinner spinnerServicesHolder;
    ArrayList<String> serviceNames;
    ArrayList<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_service_to_provider);
        presenter = new MapServiceToProviderPresenter(this, this);
        initViews();

        presenter.getServices();
        services = new ArrayList<>();
        serviceNames = new ArrayList<>();

        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });

        spinnerServicesHolder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String serviceName = String.valueOf(parent.getSelectedItem());
                for(int x = 0 ; x < services.size(); x++){
                    if(serviceName.equalsIgnoreCase(services.get(x).getServiceName())){
                        serviceUuid = services.get(x).getUuid();

                        Toast.makeText(MapServiceToProviderActivity.this, serviceUuid, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void initViews(){
        ivBackArrow = (ImageView)findViewById(R.id.iv_map_service_to_provider_back_arrow);
        tvActivityTitle = (TextView)findViewById(R.id.tv_map_service_to_provider_activity_title);
        btnSendProviderDetails = (LoadingButton)findViewById(R.id.btn_map_service_to_provider_send_request);
        fmLoadingProgressBar =(FrameLayout)findViewById(R.id.progress_overlay_holder_map_service_to_provider);
        etBillingPerHour =(EditText)findViewById(R.id.et_map_service_to_provider_billir_rate);
        etExperienceInMonths = (EditText)findViewById(R.id.et_map_service_to_provider_experience_in_months);
        etServiceOfferDescription = (EditText)findViewById(R.id.et_map_service_to_provider_service_offer_desc);
        spinnerServicesHolder = (Spinner)findViewById(R.id.spn_map_service_to_provider_services);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void showGetServicesProgressBar() {
        fmLoadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGetServicesProgressBar() {
        fmLoadingProgressBar.setVisibility(View.GONE);
    }

    @Override
    @SneakyThrows
    public void onGetServicesResponse(JSONObject response) {
        JSONArray serviceData = response.getJSONArray("data");
        if(serviceData.length()<1){
            btnSendProviderDetails.setEnabled(false);
            tvActivityTitle.setText(R.string.map_service_2_provider_empty_services_error_msg);
            etBillingPerHour.setEnabled(false);
            etExperienceInMonths.setEnabled(false);
            etServiceOfferDescription.setEnabled(false);
            spinnerServicesHolder.setEnabled(false);
        }else {
            for(int x = 0; x < serviceData.length(); x++){
                serviceNames.add(serviceData.getJSONObject(x).getString("serviceName"));

                Service service = Service.builder()
                        .uuid(serviceData.getJSONObject(x).getString("uuid"))
                        .serviceName(serviceData.getJSONObject(x).getString("serviceName"))
                        .build();
                services.add(service);
            }
            spinnerServicesHolder.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, serviceNames));
        }
    }

    @Override
    public void onGetServicesError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean validateInput() {
        return false;
    }

    @Override
    public void onFailedValidation() {

    }

    @Override
    public void showLoadingButton() {

    }

    @Override
    public void hideLoadingButton() {

    }

    @Override
    public void onSubmitServiceSuccess(JSONObject apiResponse) {

    }

    @Override
    public void onSubmitServiceError(String volleyError) {

    }
}