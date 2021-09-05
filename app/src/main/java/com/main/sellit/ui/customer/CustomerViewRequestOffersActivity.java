package com.main.sellit.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.main.sellit.R;
import com.main.sellit.contract.CustomerViewRequestOffersContract;
import com.main.sellit.helper.AppConstants;
import com.main.sellit.helper.FlagErrors;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.model.ProviderOfferModel;
import com.main.sellit.presenter.customer.CustomerViewRequestOffersPresenter;
import com.main.sellit.ui.adapter.CustomerViewOfferAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerViewRequestOffersActivity extends AppCompatActivity implements CustomerViewRequestOffersContract.View {

    FrameLayout progressBar;
    TextView tvNoOffersMessage, tvOfferId;
    ImageView ivBackArrow;
    RecyclerView rvOffers;
    CustomerViewOfferAdapter offerAdapter;
    String requestUuid;
    FlagErrors flagErrors;
    CustomerViewRequestOffersPresenter presenter;
    SessionManager sessionManager;
    List<ProviderOfferModel> offers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_request_offers);
        initViews();
        flagErrors  =  new FlagErrors(this, this);
        presenter = new CustomerViewRequestOffersPresenter(this, this);
        requestUuid = getIntent().getStringExtra(AppConstants.UUID_TO_VIEW_OFFERS_FOR);
        String requestDesc = getIntent().getStringExtra("requestDec");
        sessionManager = new SessionManager(this);
        offers = new ArrayList<>();
        //get offers
        presenter.getOffers(sessionManager.getToken(), requestUuid);
        tvOfferId.setText(requestDesc);

        ivBackArrow.setOnClickListener(v->{
            onBackPressed();
        });
    }

    private void initViews(){
        ivBackArrow = findViewById(R.id.iv_customer_view_offers_back_arrow);
        tvNoOffersMessage = findViewById(R.id.tv_customer_view_request_no_offers);
        tvOfferId = findViewById(R.id.tv_customer_viiew_request_offers_request_name);
        rvOffers = findViewById(R.id.rv_customer_view_offers_recycler_view);
        progressBar = findViewById(R.id.progress_overlay_holder_customer_get_offers);
    }

    @Override
    public void showGetOffersProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGetOffersProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
    @Override
    @SneakyThrows
    public void onGetOffersSuccess(JSONObject apiResponse) {
        JSONArray offersData = apiResponse.getJSONArray("data");
        if(offersData.length() == 0){
            tvNoOffersMessage.setVisibility(View.VISIBLE);
        }else {
            for (int x = 0; x < offersData.length(); x++){
                JSONObject offerObj = offersData.getJSONObject(x);
                int intValue = (int)offerObj.getDouble("estimatedCost");
                String value = intValue+".00";
                ProviderOfferModel offerModel = ProviderOfferModel.builder()
                        .discountInPercent(offerObj.getDouble("discountInPercent"))
                        .email(offerObj.getString("email"))
                        .estimatedCost(value)
                        .experienceInMonths(offerObj.getString("experienceInMonths"))
                        .location(offerObj.getString("location"))
                        .mobileNumber(offerObj.getString("mobileNumber"))
                        .offerBy(offerObj.getString("offerBy"))
                        .overallRating(offerObj.getString("overallRating"))
                        .submissionDate(offerObj.getString("submissionDate"))
                        .uuid(offerObj.getString("uuid"))
                        .build();

                offers.add(offerModel);
            }
            offerAdapter = new CustomerViewOfferAdapter( this, offers);
            LinearLayoutManager r = new LinearLayoutManager(this);
            rvOffers.setLayoutManager(r);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvOffers.getContext(),
                    r.getOrientation());
            rvOffers.addItemDecoration(dividerItemDecoration);
            rvOffers.setItemAnimator(new DefaultItemAnimator());
            rvOffers.setAdapter(offerAdapter);

        }

    }

    @Override
    public void onGetOffersError(VolleyError apiError) {
        flagErrors.flagApiError(apiError);
    }
}