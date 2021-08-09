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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.main.sellit.R;
import com.main.sellit.helper.AppConstants;
import com.main.sellit.model.ProviderServiceRequestModel;
import com.main.sellit.ui.provider.ProviderSendOfferActivity;

import java.util.List;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProviderRequestListAdapter extends RecyclerView.Adapter<ProviderRequestListAdapter.ServiceListViewHolder> {

    Context context;
    List<ProviderServiceRequestModel> requestModelList;

    public ProviderRequestListAdapter(Context context, List<ProviderServiceRequestModel> requestModelList) {
        this.context = context;
        this.requestModelList = requestModelList;
    }

    @NonNull
    @Override
    public ServiceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.provider_request_list_row, parent, false);

        return new ServiceListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceListViewHolder holder, int position) {
        holder.requestDesc.setText(requestModelList.get(position).getRequestDescription());
        holder.customerEmail.setText(requestModelList.get(position).getEmail());
        holder.customerLocationCity.setText(requestModelList.get(position).getLocationCity());
        holder.expectedHours.setText(String.valueOf(requestModelList.get(position).getExpectedHours()));
        holder.startTime.setText(requestModelList.get(position).getExpectedStartTime());
        holder.requiredDate.setText(requestModelList.get(position).getRequiredDate());
        // set onclick listner for the create offer button

        holder.btnCreateOffer.setOnClickListener(v->{
            Intent intent = new Intent(context.getApplicationContext(), ProviderSendOfferActivity.class);
            intent.putExtra(AppConstants.UUID_FOR_REQUEST_TO_SEND_OFFER_FOR, requestModelList.get(position).getUuid());
            context.startActivity(intent);
        });


        holder.singleRowContainer.setOnClickListener(v->{
            Toast.makeText(context, "Send An Offer to \n"+requestModelList.get(position).getRequestBy(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return requestModelList.size();
    }

    public static class ServiceListViewHolder extends RecyclerView.ViewHolder{
        TextView requestDesc, expectedHours, requiredDate,startTime,customerEmail, customerLocationCity;
        AppCompatButton btnCreateOffer;
        CardView singleRowContainer;

        public ServiceListViewHolder(@NonNull View itemView) {
            super(itemView);
            btnCreateOffer = itemView.findViewById(R.id.btn_provider_requests_create_offer);
            requestDesc = itemView.findViewById(R.id.tv_customer_request_list_request_description);
            expectedHours = itemView.findViewById(R.id.tv_customer_services_expected_hours);
            requiredDate = itemView.findViewById(R.id.tv_customer_service_request_required_date);
            startTime = itemView.findViewById(R.id.tv_customer_service_request_time);
            customerEmail = itemView.findViewById(R.id.tv_provider_request_customer_email);
            customerLocationCity = itemView.findViewById(R.id.tv_provider_requests_customer_location_city);
            singleRowContainer = itemView.findViewById(R.id.provider_request_list_single_row);
        }
    }
}
