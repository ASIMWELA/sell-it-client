package com.main.sellit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.contract.AddProductCategoryContract;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.presenter.AddProductCategoryPresenter;

import org.json.JSONObject;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddProductCategoryActivity extends AppCompatActivity implements AddProductCategoryContract.View {

    LoadingButton btnAddCategory;
    EditText etName;
    ImageView ivBackArrow;
    String categoryName;
    AddProductCategoryPresenter addProductCategoryPresenter;
    JSONObject data;
    String accessToken;
    TextView tvErrorMsg, tvSuccessMsg, tvAddService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        initViews();
        addProductCategoryPresenter = new AddProductCategoryPresenter(this, this);
        data = new JSONObject();
        SessionManager sessionManager = new SessionManager(this);
        accessToken = sessionManager.getToken();
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

    }
    private void initViews(){
        etName =  (EditText)findViewById(R.id.et_add_category_cate_name);
        btnAddCategory = (LoadingButton)findViewById(R.id.btn_add_category_send_data);
        ivBackArrow = (ImageView)findViewById(R.id.iv_add_category_back_arrow);
        tvErrorMsg =(TextView)findViewById(R.id.tv_add_category_err_msg);
        tvSuccessMsg = (TextView)findViewById(R.id.tv_add_category_submit_success);
        tvAddService = (TextView)findViewById(R.id.tv_add_category_open_add_service_category);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean validateInput() {
        etName.addTextChangedListener(new TextValidator(etName) {
            @Override
            @SneakyThrows
            public void validate() {
                if(etName.getText().toString().trim().length()<2){
                    etName.setError("Category name too short");
                    etName.setBackgroundResource(R.drawable.rounded_boaders_error);
                    categoryName = null;

                }else {
                    etName.setBackgroundResource(R.drawable.rounded_boaders);
                    categoryName = etName.getText().toString().trim();
                    data.put("serviceCategoryName", categoryName);
                }
            }
        });

        return categoryName != null;
    }

    @Override
    public void onFailedValidation() {
        Snackbar.make(findViewById(R.id.add_category_container), "Error in your input", Snackbar.LENGTH_LONG).show();
    }

    @Override
    @SneakyThrows
    public void onSubmitSuccess(JSONObject apiResponse) {
        etName.setText("");
        etName.setError(null);
        etName.setBackgroundResource(R.drawable.rounded_boaders);
        String successMsg = apiResponse.getString("message");
        if(successMsg != null){
            tvSuccessMsg.setText(successMsg);
            tvSuccessMsg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    @SneakyThrows
    public void onSubmitError(String apiError) {
        JSONObject apiErrObj = new JSONObject(apiError);
        String err = apiErrObj.getString("message");
        if(err != null){
            tvErrorMsg.setText(err);
        }else {
            tvErrorMsg.setText(R.string.add_category_submit_error);
        }
        tvErrorMsg.setVisibility(View.VISIBLE);
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