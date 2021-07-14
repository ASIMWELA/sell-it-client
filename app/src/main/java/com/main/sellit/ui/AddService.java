package com.main.sellit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.main.sellit.R;
import com.main.sellit.contract.AddServiceContract;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        initViews();
        addServicePresenter = new AddServicePresenter(this, this);
        addServicePresenter.getServiceCategories();
        categoryNames = new ArrayList<>();
        categories = new ArrayList<>();

    }

    private void initViews(){
        progressBarHolder = (FrameLayout)findViewById(R.id.progress_overlay_holder_add_service);
        spnServiceCategory = (Spinner)findViewById(R.id.spn_service_category);
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
        for(int x = 0 ; x < categoriesArray.length(); x++){
            JSONObject categoryObj = categoriesArray.getJSONObject(x);
            ServiceCategory serviceCategory = ServiceCategory.builder()
                    .serviceCategoryName(categoryObj.getString("serviceCategoryName"))
                    .Uuid(categoryObj.getString("uuid")).build();

                    categoryNames.add(categoryObj.getString("serviceCategoryName"));
                    categories.add(serviceCategory);
        }

        Toast.makeText(this, categoryNames.toString(), Toast.LENGTH_SHORT).show();
        spnServiceCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryNames));
    }

    @Override
    public void onError(String error) {

        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}