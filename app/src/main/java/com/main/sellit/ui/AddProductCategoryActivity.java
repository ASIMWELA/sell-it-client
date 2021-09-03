package com.main.sellit.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.contract.AddProductCategoryContract;
import com.main.sellit.helper.FlagErrors;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.presenter.provider.AddProductCategoryPresenter;

import org.json.JSONObject;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddProductCategoryActivity extends AppCompatActivity implements AddProductCategoryContract.View {

    LoadingButton btnAddCategory;
    ImageView ivBackArrow;
    String categoryName;
    AddProductCategoryPresenter addProductCategoryPresenter;
    JSONObject data;
    String accessToken;
    TextView tvErrorMsg, tvSuccessMsg, tvAddService;
    FlagErrors flagErrors;
    Spinner productSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        initViews();
        addProductCategoryPresenter = new AddProductCategoryPresenter(this, this);
        data = new JSONObject();
        SessionManager sessionManager = new SessionManager(this);
        accessToken = sessionManager.getToken();
        flagErrors = new FlagErrors(this, this);
        validateInput();
        btnAddCategory.setOnClickListener(v->{
            if(accessToken == null){
                Toast.makeText(this, "Submit error: no token provided", Toast.LENGTH_SHORT).show();
            }else {
                    tvSuccessMsg.setVisibility(View.INVISIBLE);
                    tvErrorMsg.setVisibility(View.INVISIBLE);
                    addProductCategoryPresenter.submitData(data, accessToken);
            }
        });

        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });

        tvAddService.setOnClickListener(v->{
            startActivity(new Intent(AddProductCategoryActivity.this, AddServiceActivity.class));
        });

        setupProductSpinner();

    }

    private void setupProductSpinner() {
        String[] categories = {"Select Category",
                "Car and Motorbike",
                "Toys, Children and Baby",
                "Sports",
                "Business and Science",
                "Health and Beauty",
        "Food and Grocery",
        "Clothes and Shoes",
        "Electronics and Computers",
                "Education and Recreation",
                "Entertainment",
        };
        productSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        });

        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            @SneakyThrows
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    categoryName = null;
                }else {
                    categoryName = (String) parent.getItemAtPosition(position);
                    data.put("serviceCategoryName", categoryName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private void initViews(){
        btnAddCategory = findViewById(R.id.btn_add_category_send_data);
        ivBackArrow = findViewById(R.id.iv_add_category_back_arrow);
        tvErrorMsg = findViewById(R.id.tv_add_category_err_msg);
        tvSuccessMsg = findViewById(R.id.tv_add_category_submit_success);
        tvAddService = findViewById(R.id.tv_add_category_open_add_service_category);
        productSpinner = findViewById(R.id.spinner_product_categories);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean validateInput() {

          return categoryName != null;

    }

    @Override
    public void onFailedValidation() {
        flagErrors.flagValidationError(R.id.add_category_container);
    }

    @Override
    @SneakyThrows
    public void onSubmitSuccess(JSONObject apiResponse) {

        categoryName = null;
        String successMsg = apiResponse.getString("message");
        if(successMsg != null){
            tvSuccessMsg.setText(successMsg);
            tvSuccessMsg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    @SneakyThrows
    public void onSubmitError(VolleyError apiError) {
        flagErrors.flagApiError(apiError);
    }

    @Override
    public void startLoadingButton() {
        btnAddCategory.setEnabled(false);
        btnAddCategory.showLoading();
    }

    @Override
    public void stopLoadingButton() {
        btnAddCategory.hideLoading();
        btnAddCategory.setEnabled(true);
    }
}