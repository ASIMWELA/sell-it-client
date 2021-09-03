package com.main.sellit.ui.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;
import com.main.sellit.contract.ReviewProviderContract;
import com.main.sellit.helper.FlagErrors;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.presenter.customer.ReviewProviderPresenter;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewProviderActivity extends AppCompatActivity implements ReviewProviderContract.View {

    EditText edPunctuality,
            edProficiency,
            edProfessionalism,
            edCommunication,
            edPricing,
            edReviewSummary;
    FlagErrors flagErrors;
    ImageView ivBackError;
    TextView tvProviderName;
    TextView tvSuccessMessage;
    LoadingButton btSendReview;
    JSONObject data;
    ReviewProviderPresenter presenter;
    SessionManager sessionManager;

    double punctuality = -1.0,
            proficiency = -1.0,
            professionalism = -1.0,
            community = -1.0,
            pricing = -1.0;
    String reviewSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_provider);
        initViews();
        validateData();
        data = new JSONObject();
        String providerName = getIntent().getStringExtra("providerName");
        String appointmentUuid = getIntent().getStringExtra("appointmentUuid");
        tvProviderName.setText(providerName);
        presenter = new ReviewProviderPresenter(this, this);
        flagErrors = new FlagErrors(this, this);
        sessionManager = new SessionManager(this);
        ivBackError.setOnClickListener(v->{
            onBackPressed();
        });

        btSendReview.setOnClickListener(v->{
            tvSuccessMessage.setVisibility(View.GONE);
            presenter.sendProviderReview(sessionManager.getToken(), appointmentUuid, data);
        });
    }

    private void initViews(){
        edPunctuality = findViewById(R.id.et_review_provider_punctulity);
        edProficiency = findViewById(R.id.et_review_provider_proficiency);
        edProfessionalism = findViewById(R.id.et_review_provider_professionalism);
        edCommunication = findViewById(R.id.et_review_provider_comminucation);
        edPricing = findViewById(R.id.et_review_provider_pricing);
        edReviewSummary = findViewById(R.id.et_review_provider_review_summary);
        ivBackError = findViewById(R.id.et_customer_review_provider_back_arrow);
        tvProviderName = findViewById(R.id.tv_customer_review_provider_p_name);
        btSendReview = findViewById(R.id.bt_send_review);
        tvSuccessMessage = findViewById(R.id.tv_review_provider_success_message);
    }

    @Override
    public boolean validateData() {
        edReviewSummary.addTextChangedListener(new TextValidator(edReviewSummary) {
            @Override
            @SneakyThrows
            public void validate() {
                if(!edReviewSummary.getText().toString().trim().isEmpty()){
                    if(edReviewSummary.getText().toString().trim().length() < 15){
                        edReviewSummary.setBackgroundResource(R.drawable.input_text_area_border_error);
                        edReviewSummary.setError("Review too short");
                    }else {
                        edReviewSummary.setBackgroundResource(R.drawable.input_text_area_bg);
                        edReviewSummary.setError(null);
                        reviewSummary = edReviewSummary.getText().toString().trim();
                        data.put("review", reviewSummary);
                    }
                }
            }
        });

        edPricing.addTextChangedListener(new TextValidator(edPricing) {
            @Override
            @SneakyThrows
            public void validate() {
                if(!edPricing.getText().toString().trim().isEmpty()){
                    if(Double.parseDouble(edPricing.getText().toString().trim())>6){
                        edPricing.setBackgroundResource(R.drawable.rounded_boaders_error);
                        edPricing.setError("Invalid review");
                    }else {
                        edPricing.setBackgroundResource(R.drawable.rounded_boaders);
                        edPricing.setError(null);
                        pricing = Double.parseDouble(edPricing.getText().toString().trim());
                        data.put("avgPriceRating", pricing);
                    }
                }
            }
        });

        edCommunication.addTextChangedListener(new TextValidator(edCommunication) {
            @Override
            @SneakyThrows
            public void validate() {
                if(!edCommunication.getText().toString().trim().isEmpty()){
                    if(Double.parseDouble(edCommunication.getText().toString().trim())>6){
                        edCommunication.setBackgroundResource(R.drawable.rounded_boaders_error);
                        edCommunication.setError("Invalid review");
                    }else {
                        edCommunication.setBackgroundResource(R.drawable.rounded_boaders);
                        edCommunication.setError(null);
                        community = Double.parseDouble(edCommunication.getText().toString().trim());
                        data.put("avgCommunicationRating", community);
                    }
                }
            }
        });

        edProfessionalism.addTextChangedListener(new TextValidator(edProfessionalism) {
            @Override
            @SneakyThrows
            public void validate() {
                if(!edProfessionalism.getText().toString().trim().isEmpty()){
                    if(Double.parseDouble(edProfessionalism.getText().toString().trim())>6){
                        edProfessionalism.setBackgroundResource(R.drawable.rounded_boaders_error);
                        edProfessionalism.setError("Invalid review");
                    }else {
                        edProfessionalism.setBackgroundResource(R.drawable.rounded_boaders);
                        edProfessionalism.setError(null);
                        professionalism = Double.parseDouble(edProfessionalism.getText().toString().trim());
                        data.put("avgProfessionalismRating", professionalism);
                    }
                }
            }
        });
        edPunctuality.addTextChangedListener(new TextValidator(edPunctuality) {
            @Override
            @SneakyThrows
            public void validate() {
                if(!edPunctuality.getText().toString().trim().isEmpty()){
                    if(Double.parseDouble(edPunctuality.getText().toString().trim())>6){
                        edPunctuality.setBackgroundResource(R.drawable.rounded_boaders_error);
                        edPunctuality.setError("Invalid review");
                    }else {
                        edPunctuality.setBackgroundResource(R.drawable.rounded_boaders);
                        edPunctuality.setError(null);
                        punctuality = Double.parseDouble(edPunctuality.getText().toString().trim());
                        data.put("avgPunctualityRating", punctuality);
                    }
                }
            }
        });

        edProficiency.addTextChangedListener(new TextValidator(edProficiency) {
            @Override
            @SneakyThrows
            public void validate() {
                if(!edProficiency.getText().toString().trim().isEmpty()){
                    if(Double.parseDouble(edProficiency.getText().toString().trim())>6){
                        edProficiency.setBackgroundResource(R.drawable.rounded_boaders_error);
                        edProficiency.setError("Invalid review");
                    }else {
                        edProficiency.setBackgroundResource(R.drawable.rounded_boaders);
                        edProficiency.setError(null);
                        proficiency = Double.parseDouble(edProficiency.getText().toString().trim());
                        data.put("avgProficiencyRating", proficiency);
                    }
                }
            }
        });
        return (pricing != -1.0)
                &&(punctuality != -1.0)
                &&(proficiency != -1.0)
                &&(professionalism != -1.0)
                &&(community != -1.0)
                &&(reviewSummary != null);
    }

    @Override
    public void showLoadingButton() {
        btSendReview.setEnabled(false);
        btSendReview.showLoading();
    }

    @Override
    public void hideLoadingButton() {
        btSendReview.setEnabled(true);
        btSendReview.hideLoading();
    }

    @Override
    public void onFailedValidation() {
        flagErrors.flagValidationError(R.id.review_provider_base_view);
    }

    @Override
    @SneakyThrows
    public void onSendReviewSuccess(JSONObject response) {
        String message = response.getString("message");
        tvSuccessMessage.setText(message);
        tvSuccessMessage.setVisibility(View.VISIBLE);
        punctuality = -1.0;
        proficiency = -1.0;
        professionalism = -1.0;
        community = -1.0;
        pricing = -1.0;
        reviewSummary = null;
    }

    @Override
    public void onSendReviewError(VolleyError error) {

         String body = new String(error.networkResponse.data, StandardCharsets.UTF_8);

        Toast.makeText(this, body, Toast.LENGTH_SHORT).show();
        //flagErrors.flagApiError(error);
    }
}