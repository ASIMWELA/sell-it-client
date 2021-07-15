package com.main.sellit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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

import lombok.SneakyThrows;

public class AddService extends AppCompatActivity implements AddServiceContract.View {

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
    TextView tvAddServiceTitle;
    SessionManager sessionManager;

    String token;



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
                        Toast.makeText(AddService.this, serviceCategoryUuid, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        validateInput();

        btnSaveService.setOnClickListener(v->{
            addServicePresenter.saveService(serviceData,token, serviceCategoryUuid);
        });

    }

    
    private void initViews(){
        progressBarHolder = (FrameLayout)findViewById(R.id.progress_overlay_holder_add_service);
        spnServiceCategory = (Spinner)findViewById(R.id.spn_service_category);
        btnSaveService = (LoadingButton)findViewById(R.id.btn_add_service_save_service);
        etServiceName = (EditText)findViewById(R.id.et_add_service_service_name);
        tvAddServiceTitle = (TextView)findViewById(R.id.tv_add_service_title);
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
            etServiceName.setText("No Categories Added!");
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
    public void onSubmitServiceSuccess(JSONObject apiResponse) {
        Toast.makeText(this, "Added success "+ apiResponse.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSubmitServiceError(String volleyError) {
        Toast.makeText(this, volleyError, Toast.LENGTH_SHORT).show();
    }
}