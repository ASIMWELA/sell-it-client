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

import com.google.android.material.snackbar.Snackbar;
import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.contract.MapServiceToProviderContract;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.model.Service;
import com.main.sellit.presenter.MapServiceToProviderPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class MapServiceToProviderActivity extends AppCompatActivity implements MapServiceToProviderContract.View {

    EditText etBillingPerHour, etExperienceInMonths, etServiceOfferDescription;
    FrameLayout fmLoadingProgressBar;
    LoadingButton btnSendProviderDetails;
    String serviceUuid;
    TextView tvActivityTitle, tvApiResponse;
    ImageView ivBackArrow;
    MapServiceToProviderPresenter presenter;
    Spinner spinnerServicesHolder;
    ArrayList<String> serviceNames;
    ArrayList<Service> services;
    String offerDesc;
    int experienceInMonths;
    double billingPerHour;
    SessionManager sessionManager;
    String providerUuid;
    JSONObject serviceOfferObj;
    String token;
    @Override
    @SneakyThrows
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_service_to_provider);
        presenter = new MapServiceToProviderPresenter(this, this);
        initViews();

        presenter.getServices();
        services = new ArrayList<>();
        serviceNames = new ArrayList<>();
        sessionManager = new SessionManager(this);
        providerUuid = sessionManager.getProviderUUid();
        serviceOfferObj = new JSONObject();
        token = sessionManager.getToken();

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
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSendProviderDetails.setOnClickListener(v->{
            tvApiResponse.setVisibility(View.INVISIBLE);
            presenter.mapServiceToProvider(serviceOfferObj, serviceUuid, providerUuid, token);

        });

        validateInput();
    }
    private void initViews(){
        ivBackArrow = findViewById(R.id.iv_map_service_to_provider_back_arrow);
        tvActivityTitle = findViewById(R.id.tv_map_service_to_provider_activity_title);
        btnSendProviderDetails = findViewById(R.id.btn_map_service_to_provider_send_request);
        fmLoadingProgressBar = findViewById(R.id.progress_overlay_holder_map_services_to_provider);
        etBillingPerHour = findViewById(R.id.et_map_service_to_provider_billir_rate);
        etExperienceInMonths = findViewById(R.id.et_map_service_to_provider_experience_in_months);
        etServiceOfferDescription = findViewById(R.id.et_map_service_to_provider_service_offer_desc);
        spinnerServicesHolder = findViewById(R.id.spn_map_service_to_provider_services);
        tvApiResponse = findViewById(R.id.tv_map_service_to_provider_api_response);
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
        etServiceOfferDescription.addTextChangedListener(new TextValidator(etServiceOfferDescription) {
            @Override
            @SneakyThrows
            public void validate() {
                if(etServiceOfferDescription.getText().toString().trim().length()<10){
                    etServiceOfferDescription.setError("Service offer too short");
                    etServiceOfferDescription.setBackgroundResource(R.drawable.input_text_area_border_error);
                    offerDesc = null;
                }else {
                    etServiceOfferDescription.setError(null);
                    etServiceOfferDescription.setBackgroundResource(R.drawable.input_text_area_bg);
                    offerDesc = etServiceOfferDescription.getText().toString().trim();
                    serviceOfferObj.put("serviceOfferingDescription", offerDesc);
                }
            }
        });

        etExperienceInMonths.addTextChangedListener(new TextValidator(etExperienceInMonths) {
            @Override
            @SneakyThrows
            public void validate() {
                if(!etExperienceInMonths.getText().toString().isEmpty()){
                    if(Integer.parseInt(etExperienceInMonths.getText().toString().trim()) < 0){
                        etExperienceInMonths.setError("Invalid experience in Months");
                        etExperienceInMonths.setBackgroundResource(R.drawable.rounded_boaders_error);
                        experienceInMonths = -1;
                    }else {
                        etExperienceInMonths.setError(null);
                        etExperienceInMonths.setBackgroundResource(R.drawable.rounded_boaders);
                        experienceInMonths = Integer.parseInt(etExperienceInMonths.getText().toString().trim());
                        serviceOfferObj.put("experienceInMonths",experienceInMonths);
                    }
                }
            }
        });

        etBillingPerHour.addTextChangedListener(new TextValidator(etBillingPerHour) {
            @Override
            @SneakyThrows
            public void validate() {
                if(!etBillingPerHour.getText().toString().isEmpty()){
                    if(Double.parseDouble(etBillingPerHour.getText().toString().trim())<0.0){
                        etBillingPerHour.setError("Invalid billing rate");
                        etBillingPerHour.setBackgroundResource(R.drawable.rounded_boaders_error);
                        billingPerHour = -1.0;
                    }else {
                        etBillingPerHour.setError(null);
                        etBillingPerHour.setBackgroundResource(R.drawable.rounded_boaders);
                        billingPerHour = Double.parseDouble(etBillingPerHour.getText().toString().trim());
                        serviceOfferObj.put("billingRatePerHour", billingPerHour);
                    }
                }

            }
        });
        return billingPerHour != -1.0
                && experienceInMonths != -1
                && offerDesc != null
                && serviceUuid != null;
    }

    @Override
    public void onFailedValidation() {
        Snackbar.make(findViewById(R.id.map_service_to_provider_base_view), "There are errors in your inputs", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoadingButton() {
        btnSendProviderDetails.setEnabled(false);
        btnSendProviderDetails.showLoading();
    }

    @Override
    public void hideLoadingButton() {
        btnSendProviderDetails.setEnabled(true);
        btnSendProviderDetails.hideLoading();
    }

    @Override
    @SneakyThrows
    public void onSubmitServiceSuccess(JSONObject apiResponse) {
        Toast.makeText(this, apiResponse.toString(), Toast.LENGTH_SHORT).show();
        String message = apiResponse.getString("message");

        //TODO: clear the input fields
//        etBillingPerHour.setText("");
//        etBillingPerHour.setError(null);
//
//        etExperienceInMonths.setText("");
//        etExperienceInMonths.setError(null);
//
//        etServiceOfferDescription.setText("");
//        etServiceOfferDescription.setError(null);

        tvApiResponse.setText(message);
        tvApiResponse.setTextColor(getResources().getColor(R.color.color_secondary));
        tvApiResponse.setVisibility(View.VISIBLE);
    }

    @Override
    @SneakyThrows
    public void onSubmitServiceError(String volleyError) {
        JSONObject errorOb = new JSONObject(volleyError);
        String message =  errorOb.getString("message");
        tvApiResponse.setText(message);
        tvApiResponse.setTextColor(getResources().getColor(R.color.color_secondary_blend));
        tvApiResponse.setVisibility(View.VISIBLE);
    }
}