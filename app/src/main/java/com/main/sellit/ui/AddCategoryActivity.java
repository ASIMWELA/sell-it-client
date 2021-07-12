package com.main.sellit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.contract.AddCategoryContract;

import org.json.JSONObject;

public class AddCategoryActivity extends AppCompatActivity implements AddCategoryContract.View {

    LoadingButton btnAddCategory;
    EditText etName;
    ImageView ivBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        initViews();

        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });

    }

    private void initViews(){
        etName =  (EditText)findViewById(R.id.et_add_category_cate_name);
        btnAddCategory = (LoadingButton)findViewById(R.id.btn_add_category_send_data);
        ivBackArrow = (ImageView)findViewById(R.id.iv_add_category_back_arrow);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean validateInput() {
        return false;
    }

    @Override
    public void onFailedValidation() {

    }

    @Override
    public void onSubmitSuccess(JSONObject apiResponse) {

    }

    @Override
    public void onSubmitError(String apiError) {

    }

    @Override
    public void startLoadingButton() {

    }

    @Override
    public void stopLoadingButton() {

    }
}