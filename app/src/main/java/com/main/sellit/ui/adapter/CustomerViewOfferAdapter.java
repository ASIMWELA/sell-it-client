package com.main.sellit.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.main.sellit.R;
import com.main.sellit.helper.AppConstants;
import com.main.sellit.model.ProviderOfferModel;
import com.main.sellit.ui.customer.CustomerAcceptOfferActivity;

import java.util.List;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerViewOfferAdapter extends RecyclerView.Adapter<CustomerViewOfferAdapter.OfferViewHolder> {

    Context context;
    List<ProviderOfferModel> offers;

    public CustomerViewOfferAdapter(Context context, List<ProviderOfferModel> offers) {
        this.context = context;
        this.offers = offers;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offer_row, parent, false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        ProviderOfferModel offer = offers.get(position);
        holder.tvRating.setText(offer.getOverallRating());
        holder.tvOfferDate.setText(offer.getSubmissionDate());
        holder.tvSenderPhoneNumber.setText(offer.getMobileNumber());
        holder.tvSenderEmail.setText(offer.getEmail());
        holder.tvDiscount.setText(String.valueOf(offer.getDiscountInPercent()));
        holder.tvAmount.setText(String.valueOf(offer.getEstimatedCost()));
        holder.btnAcceptOffer.setOnClickListener(v->{
            Intent i = new Intent(context.getApplicationContext(), CustomerAcceptOfferActivity.class);
            i.putExtra(AppConstants.CREATE_APPOINTMENT_FOR_SERVICE_DELIVERY_OFFER_UUID,offer.getUuid());
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return offers.size();
    }
    public static class OfferViewHolder extends RecyclerView.ViewHolder{
        TextView tvAmount, tvDiscount, tvSenderEmail,tvSenderPhoneNumber,tvOfferDate,tvRating;
        AppCompatButton btnAcceptOffer;
        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tv_provider_offer_amount);
            tvDiscount = itemView.findViewById(R.id.tv_provider_offer_discount);
            tvSenderEmail = itemView.findViewById(R.id.tv_provider_offer_sender_email);
            tvSenderPhoneNumber = itemView.findViewById(R.id.tv_provider_offer_sender_mobile);
            tvOfferDate = itemView.findViewById(R.id.tv_provider_offer_offered_date);
            tvRating = itemView.findViewById(R.id.tv_provider_offer_rating);
            btnAcceptOffer = itemView.findViewById(R.id.btn_customer_views_offers_accept_offers);

        }
    }
}
