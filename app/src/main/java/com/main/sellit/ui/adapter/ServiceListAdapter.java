package com.main.sellit.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.sellit.R;
import com.main.sellit.model.Service;
import com.main.sellit.model.ServiceAndCategoryNamesModel;

import java.util.ArrayList;
import java.util.List;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.MyViewHolder> {
    List<ServiceAndCategoryNamesModel> services = new ArrayList<>();
    TextView tvServiceName, tvServiceCategoryName;

    public ServiceListAdapter(List<ServiceAndCategoryNamesModel> services) {
        this.services = services;
    }

    @NonNull
    @Override
    public ServiceListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_layout_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceListAdapter.MyViewHolder holder, int position) {
        tvServiceCategoryName.setText(services.get(position).getServiceCategory());
        tvServiceName.setText(services.get(position).getServiceName());

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvServiceCategoryName = itemView.findViewById(R.id.tv_provider_service_category_name);
            tvServiceName = itemView.findViewById(R.id.tv_provider_service_name);
        }
    }
}
