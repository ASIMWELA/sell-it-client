package com.main.sellit.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.sellit.R;
import com.main.sellit.model.ServiceProvider;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceProviderAdapter extends RecyclerView.Adapter<ServiceProviderAdapter.ProviderHolder> {

    Context context;
    List<ServiceProvider> serviceProviders;

    @NonNull
    @Override
    public ProviderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.provider_row_holder, parent, false);
        return new ProviderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderHolder holder, int position) {
        ServiceProvider provider = serviceProviders.get(position);
        holder.tvPriceRating.setText(provider.getAvgPriceRating());
        holder.tvPrice.setText(provider.getBillingRatePerHour());
        holder.tvProviderName.setText(provider.getProviderName());
        holder.tvOverallRating.setText(provider.getOverallRating());
        holder.tvProviderEmail.setText(provider.getProviderEmail());
        holder.tvPunctuality.setText(provider.getAvgPunctualityRating());
        holder.tvExpMonths.setText(provider.getExperienceInMonths());
        holder.tvCommunicationRating.setText(provider.getAvgCommunicationRating());
    }

    @Override
    public int getItemCount() {
        return serviceProviders.size();
    }

    public static class ProviderHolder extends RecyclerView.ViewHolder {

        TextView tvProviderName, tvProviderEmail, tvPrice, tvExpMonths,
                    tvPriceRating, tvCommunicationRating, tvPunctuality,
                    tvOverallRating;
        public ProviderHolder(@NonNull View itemView) {
            super(itemView);
            tvCommunicationRating = itemView.findViewById(R.id.tv_customer_view_providers_p_communication);
            tvExpMonths = itemView.findViewById(R.id.tv_customer_view_providers_p_exp);
            tvPunctuality = itemView.findViewById(R.id.tv_customer_view_providers_p_punctuality);
            tvProviderEmail = itemView.findViewById(R.id.tv_customer_view_providers_p_email);
            tvPrice = itemView.findViewById(R.id.tv_customer_view_providers_p_billing);
            tvOverallRating = itemView.findViewById(R.id.tv_customer_view_providers_p_overall);
            tvProviderName =itemView.findViewById(R.id.tv_customer_view_providers_p_name);
            tvPriceRating = itemView.findViewById(R.id.tv_customer_view_providers_p_price);
        }
    }
}
