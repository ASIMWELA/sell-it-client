package com.main.sellit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.main.sellit.R;
import com.main.sellit.contract.AddServiceContract;
import com.main.sellit.presenter.AddServicePresenter;

import org.json.JSONObject;

public class AddService extends AppCompatActivity implements AddServiceContract.View {

    FrameLayout progressBarHolder;
    AddServicePresenter addServicePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        initViews();
        addServicePresenter = new AddServicePresenter(this, this);

        addServicePresenter.getServiceCategories();
    }

    private void initViews(){
        progressBarHolder = (FrameLayout)findViewById(R.id.progress_overlay_holder_add_service);
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
    public void onResponse(JSONObject response) {
        Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String error) {

        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}