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

import com.main.sellit.ui.customer.CustomerViewRequestOffersActivity;
import com.main.sellit.R;
import com.main.sellit.helper.AppConstants;
import com.main.sellit.model.CustomerServiceRequestModel;

import java.util.ArrayList;
import java.util.List;

public class CustomerRequestsAdapter extends RecyclerView.Adapter<CustomerRequestsAdapter.CustomerRequestViewHolder> {
   List<CustomerServiceRequestModel> requestModelList = new ArrayList<>();
   Context context;

    public CustomerRequestsAdapter(List<CustomerServiceRequestModel> requestModelList, Context context) {
        this.requestModelList = requestModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_requet_row, parent, false);
        return new CustomerRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerRequestViewHolder holder, int position) {
        CustomerServiceRequestModel requestModel = requestModelList.get(position);
        holder.tvExpectedHours.setText(requestModel.getExpectedHours());
        holder.tvStartTime.setText(requestModel.getExpectedStartTime());
        holder.tvDescription.setText(requestModel.getRequirementDescription());
        holder.tvRequiredOn.setText(requestModel.getRequiredOn());
        holder.btnViewOffers.setOnClickListener(v->{
            Intent i = new Intent(context.getApplicationContext(), CustomerViewRequestOffersActivity.class);
            i.putExtra(AppConstants.UUID_TO_VIEW_OFFERS_FOR, requestModel.getUuid());
            i.putExtra("requestDec", requestModel.getRequirementDescription());
            context.startActivity(i);
        });
        holder.cvSingleRow.setOnClickListener(v->{
            Toast.makeText(context, "View offers for\n\tthis request", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return requestModelList.size();
    }

    public class CustomerRequestViewHolder extends RecyclerView.ViewHolder{
        TextView tvRequiredOn, tvStartTime, tvExpectedHours, tvDescription;
        AppCompatButton btnViewOffers;
        CardView cvSingleRow;

        public CustomerRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRequiredOn = itemView.findViewById(R.id.tv_customer_service_request_required_date);
            tvDescription = itemView.findViewById(R.id.tv_customer_request_list_request_description);
            tvStartTime = itemView.findViewById(R.id.tv_customer_service_request_time);
            tvExpectedHours = itemView.findViewById(R.id.tv_customer_services_expected_hours);
            btnViewOffers = itemView.findViewById(R.id.btn_customer_requests_list_open_offers);
            cvSingleRow = itemView.findViewById(R.id.customer_request_singles_row);
        }
    }
}
