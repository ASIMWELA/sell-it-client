package com.main.sellit.ui.adapter;

import android.content.Context;
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
import com.main.sellit.model.Service;
import com.main.sellit.model.ServiceAndCategoryNamesModel;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceListAdapter extends RecyclerView.Adapter<CustomerServiceListAdapter.MyViewHolder> {

    List<ServiceAndCategoryNamesModel> services = new ArrayList<>();
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

            Toast.makeText(ctx, "Clicked + "+ services.get(position).getServiceName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
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
