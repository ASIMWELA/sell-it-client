package com.main.sellit.ui.provider;

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
import com.main.sellit.contract.ProviderSendOfferContract;
import com.main.sellit.helper.AppConstants;
import com.main.sellit.helper.FlagErrors;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.helper.TextValidator;
import com.main.sellit.presenter.provider.ProviderSendOfferPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import lombok.SneakyThrows;

public class ProviderSendOfferActivity extends AppCompatActivity implements ProviderSendOfferContract.View {

    String requestUuid;
    EditText edDiscount, etAmount;
    LoadingButton btnSendRequest;
    ImageView ivBackArrow;
    FlagErrors flagErrors;
    SessionManager sessionManager;
    ProviderSendOfferPresenter providerSendOfferPresenter;
    JSONObject offer;
    double discount = 0.0, amount = 0.0;
    TextView tvSuccessMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_send_offer);
        initViews();

        requestUuid = getIntent().getStringExtra(AppConstants.UUID_FOR_REQUEST_TO_SEND_OFFER_FOR);
        flagErrors = new FlagErrors(this, this);
        sessionManager = new SessionManager(this);
        providerSendOfferPresenter = new ProviderSendOfferPresenter(this, this);
        offer = new JSONObject();
        tvSuccessMessage.setVisibility(View.GONE);


        validateInput();

        btnSendRequest.setOnClickListener(v->{
            tvSuccessMessage.setVisibility(View.GONE);
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            String sDay = day < 10 ? "0"+day:day+"";
            String sMonth = month<10?"0"+month:month+"";
            String formattedDate = year +"-"+sMonth+"-"+sDay;
            try {
                offer.put("offerSubmissionDate", formattedDate);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String serviceProviderUuid = sessionManager.getServiceProviderUuid();
            if(serviceProviderUuid.equals("notAvailable")){
                Toast.makeText(this, "You are ineligible to submit\nan offer since you are not\noffering any service.", Toast.LENGTH_SHORT).show();
            }else {
                providerSendOfferPresenter.sendOfferRequest(sessionManager.getToken(), requestUuid, serviceProviderUuid,offer);
            }
        });
        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });

    }

    private void initViews(){
        edDiscount = findViewById(R.id.et_provider_send_offer_discount);
        etAmount = findViewById(R.id.et_provider_send_offer_amount);
        btnSendRequest = findViewById(R.id.btn_provider_send_offer_send_request);
        ivBackArrow = findViewById(R.id.iv_provider_send_offer_back_arrow);
        tvSuccessMessage = findViewById(R.id.tv_proviser_send_offer_sucess_message);
    }

    @Override
    public boolean validateInput() {
        etAmount.addTextChangedListener(new TextValidator(etAmount) {
            @Override
            @SneakyThrows
            public void validate() {
                if(etAmount.getText().toString().trim().isEmpty()){
                    amount = 0.0;
                    etAmount.setError("Amount required");
                    etAmount.setBackgroundResource(R.drawable.rounded_boaders_error);
                }else {
                    etAmount.setBackgroundResource(R.drawable.rounded_boaders);
                    amount = Double.parseDouble(etAmount.getText().toString().trim());
                    offer.put("estimatedCost", amount);
                }

            }
        });
        edDiscount.addTextChangedListener(new TextValidator(edDiscount) {
            @Override
            @SneakyThrows
            public void validate() {
                if(edDiscount.getText().toString().trim().isEmpty()){
                    edDiscount.setBackgroundResource(R.drawable.rounded_boaders_error);
                    edDiscount.setError("Discount required");
                    discount = 0.0;
                }else if(Double.parseDouble(edDiscount.getText().toString().trim())>100 || Double.parseDouble(edDiscount.getText().toString().trim())<0){
                    edDiscount.setBackgroundResource(R.drawable.rounded_boaders_error);
                    edDiscount.setError("Invalid value for discount");
                    discount = 0.0;
                }
                else {
                    edDiscount.setBackgroundResource(R.drawable.rounded_boaders);
                    discount = Double.parseDouble(edDiscount.getText().toString().trim());
                    offer.put("discountInPercent", discount);
                }
            }
        });

        return (amount != 0.0) && (discount != 0.0);
    }

    @Override
    public void onFailedValidation() {
        flagErrors.flagValidationError(R.id.provider_send_offer_base_view);
    }

    @Override
    public void showLoadingButton() {
        btnSendRequest.setEnabled(false);
        btnSendRequest.showLoading();
    }

    @Override
    public void hideLoadingButton() {
        btnSendRequest.setEnabled(true);
        btnSendRequest.hideLoading();
    }

    @Override
    @SneakyThrows
    public void onSendOfferResponse(JSONObject apiResponse) {
        amount = 0.0;
        discount = 0.0;
        String message = apiResponse.getString("message");
        edDiscount.setText("");
        etAmount.setText("");

        edDiscount.setBackgroundResource(R.drawable.rounded_boaders);
        etAmount.setError(null);
        etAmount.setBackgroundResource(R.drawable.rounded_boaders);
        edDiscount.setError(null);
        tvSuccessMessage.setText(message);
        tvSuccessMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSendOfferError(VolleyError apiError) {
        flagErrors.flagApiError(apiError);
    }
}