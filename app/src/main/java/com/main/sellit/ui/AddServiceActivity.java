package com.main.sellit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
import com.main.sellit.contract.AddServiceContract;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.model.ServiceCategory;
import com.main.sellit.presenter.AddServicePresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddServiceActivity extends AppCompatActivity implements AddServiceContract.View {

    FrameLayout progressBarHolder;
    AddServicePresenter addServicePresenter;
    Spinner spnServiceCategory;
    ArrayList<String> categoryNames;
    ArrayList<ServiceCategory> categories;
    LoadingButton btnSaveService;
    EditText etServiceName;
    String serviceName;
    JSONObject serviceData;
    String serviceCategoryUuid;
    TextView tvErrorSuccessMsg;
    SessionManager sessionManager;
    TextView tvAddServiceTitle;
    String token;
    ImageView ivBackArrow;
    TextView tvOpenMapServiceActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        initViews();
        addServicePresenter = new AddServicePresenter(this, this);
        addServicePresenter.getServiceCategories();
        categoryNames = new ArrayList<>();
        categories = new ArrayList<>();
        serviceData = new JSONObject();
        sessionManager = new SessionManager(this);
        token = sessionManager.getToken();

        spnServiceCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String serviceCategoryName = String.valueOf(parent.getSelectedItem());
                for(int x = 0 ; x < categories.size(); x++){
                    if(serviceCategoryName.equalsIgnoreCase(categories.get(x).getServiceCategoryName())){
                        serviceCategoryUuid = categories.get(x).getUuid();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        validateInput();

        btnSaveService.setOnClickListener(v->{
            tvErrorSuccessMsg.setVisibility(View.GONE);
            addServicePresenter.saveService(serviceData,token, serviceCategoryUuid);
        });
        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });
        tvOpenMapServiceActivity.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), MapServiceToProviderActivity.class));
        });

    }

    private void initViews(){
        progressBarHolder = findViewById(R.id.progress_overlay_holder_add_service);
        spnServiceCategory = findViewById(R.id.spn_service_category);
        btnSaveService = findViewById(R.id.btn_add_service_save_service);
        etServiceName = findViewById(R.id.et_add_service_service_name);
        tvAddServiceTitle = findViewById(R.id.tv_add_service_title);
        tvErrorSuccessMsg = findViewById(R.id.tv_add_service_error_success_meg);
        ivBackArrow = findViewById(R.id.iv_add_service_back_arrow);
        tvOpenMapServiceActivity = findViewById(R.id.tv_add_services_open_map_service_activity);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showProgressBar() {
        progressBarHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarHolder.setVisibility(View.GONE);
    }

    @Override
    @SneakyThrows
    public void onResponse(JSONObject response) {
        JSONArray categoriesArray = response.getJSONArray("data");
        if(categoriesArray.length()<1){
            btnSaveService.setEnabled(false);
            etServiceName.setEnabled(false);
            tvAddServiceTitle.setText(R.string.no_category_added_error);
        }else {
            for(int x = 0 ; x < categoriesArray.length(); x++){
                JSONObject categoryObj = categoriesArray.getJSONObject(x);
                ServiceCategory serviceCategory = ServiceCategory.builder()
                        .serviceCategoryName(categoryObj.getString("serviceCategoryName"))
                        .Uuid(categoryObj.getString("uuid")).build();

                categoryNames.add(categoryObj.getString("serviceCategoryName"));
                categories.add(serviceCategory);
            }
            spnServiceCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryNames));

        }
    }
    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean validateInput() {
        etServiceName.addTextChangedListener(new TextValidator(etServiceName) {
            @Override
            @SneakyThrows
            public void validate() {
                if(!etServiceName.getText().toString().isEmpty()){
                    if(etServiceName.getText().toString().trim().length()<3){
                        etServiceName.setError("Service name too short");
                        etServiceName.setBackgroundResource(R.drawable.rounded_boaders_error);
                        serviceName = null;
                    }else {
                        etServiceName.setError(null);
                        etServiceName.setBackgroundResource(R.drawable.rounded_boaders);
                        serviceName = etServiceName.getText().toString().trim();
                        serviceData.put("serviceName", serviceName);
                    }
                }
            }
        });
        return serviceName != null && serviceCategoryUuid != null;
    }

    @Override
    public void onFailedValidation() {
        Snackbar.make(findViewById(R.id.add_service_base_container), "There are errors in your inputs", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoadingButton() {
          btnSaveService.setEnabled(false);
          btnSaveService.showLoading();
    }

    @Override
    public void hideLoadingButton() {
          btnSaveService.hideLoading();
          btnSaveService.setEnabled(true);
    }

    @Override
    @SneakyThrows
    public void onSubmitServiceSuccess(JSONObject apiResponse) {
        String message = apiResponse.getString("message");
        if(message != null){
            tvErrorSuccessMsg.setText(message);
        }else {
            tvErrorSuccessMsg.setText(R.string.add_service_succes_msg);
        }
        etServiceName.setText("");
        etServiceName.setError(null);
        etServiceName.setBackgroundResource(R.drawable.rounded_boaders);
        tvErrorSuccessMsg.setVisibility(View.VISIBLE);
        tvErrorSuccessMsg.setTextColor(getResources().getColor(R.color.color_secondary));
    }

    @Override
    @SneakyThrows
    public void onSubmitServiceError(String volleyError) {
        JSONObject errorObj = new JSONObject(volleyError);
        String erroMsg = errorObj.getString("message");
        if(erroMsg != null){
            tvErrorSuccessMsg.setText(erroMsg);
        }else {
            tvErrorSuccessMsg.setText(R.string.add_service_error_msg);
        }
        tvErrorSuccessMsg.setTextColor(Color.RED);
        tvErrorSuccessMsg.setVisibility(View.VISIBLE);
    }
}