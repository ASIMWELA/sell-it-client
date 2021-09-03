package com.main.sellit.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.main.sellit.R;
import com.main.sellit.helper.AppConstants;
import com.main.sellit.model.ServiceAndCategoryNamesModel;
import com.main.sellit.ui.RequestServiceActivity;
import com.main.sellit.ui.customer.CustomerViewProvidersActivity;

import java.util.List;

public class CustomerServiceListAdapter extends RecyclerView.Adapter<CustomerServiceListAdapter.MyViewHolder> {

    List<ServiceAndCategoryNamesModel> services;
    Context ctx;
    public CustomerServiceListAdapter(List<ServiceAndCategoryNamesModel> services, Context ctx) {
        this.services = services;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public CustomerServiceListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.customer_service_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerServiceListAdapter.MyViewHolder holder, int position) {
        holder.serviceName.setText(services.get(position).getServiceName());
        holder.serviceCategory.setText(services.get(position).getServiceCategory());

        holder.container.setOnClickListener(v->{
            Intent getServiceProviderIntent = new Intent(ctx.getApplicationContext(), CustomerViewProvidersActivity.class);
            getServiceProviderIntent.putExtra(AppConstants.GET_SERVICE_PROVIDERS_UUID, services.get(position).getServiceUuid());
            getServiceProviderIntent.putExtra("serviceName", services.get(position).getServiceName());
            ctx.startActivity(getServiceProviderIntent);
        });
        holder.btnRequestService.setOnClickListener(v->{
            Intent requestServiceIntent = new Intent(ctx.getApplicationContext(), RequestServiceActivity.class);
            requestServiceIntent.putExtra(AppConstants.REQUEST_SERVICE_UUID, services.get(position).getServiceUuid());
            requestServiceIntent.putExtra(AppConstants.REQUEST_SERVICE_SERVICE_NAME, services.get(position).getServiceName());
            ctx.startActivity(requestServiceIntent);
        });
    }
    @Override
    public int getItemCount() {
        return services.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder{
        CardView container;
        TextView serviceName;
        TextView serviceCategory;
        AppCompatButton btnRequestService;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btnRequestService = itemView.findViewById(R.id.btn_customer_service_request);
            container = itemView.findViewById(R.id.customer_service_container_row);
            serviceName = itemView.findViewById(R.id.tv_customer_services_service_name);
            serviceCategory = itemView.findViewById(R.id.tv_customer_service_service_category);
        }
    }
}
