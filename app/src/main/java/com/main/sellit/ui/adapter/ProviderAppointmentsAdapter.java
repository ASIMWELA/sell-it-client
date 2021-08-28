package com.main.sellit.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.main.sellit.R;
import com.main.sellit.model.ProviderAppointmentModel;

import java.util.List;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProviderAppointmentsAdapter extends RecyclerView.Adapter<ProviderAppointmentsAdapter.ProviderAppointmentViewHolder> {

    List<ProviderAppointmentModel> appointmentModelList;

    public ProviderAppointmentsAdapter(List<ProviderAppointmentModel> appointmentModelList) {
        this.appointmentModelList = appointmentModelList;
    }

    @NonNull
    @Override
    public ProviderAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.provider_appointment_row, parent, false);
        return new ProviderAppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderAppointmentViewHolder holder, int position) {
       ProviderAppointmentModel appointmentModel = appointmentModelList.get(position);
        holder.customerName.setText(appointmentModel.getAppointmentWith());
        holder.customerPhone.setText(appointmentModel.getCustomerPhone());
        holder.appointmentStartTime.setText(appointmentModel.getAppointmentStartTime());
        holder.appointmentDesc.setText(appointmentModel.getAppointmentDesc());
        holder.appointmentDate.setText(appointmentModel.getAppointmentDate());
        holder.customerEmail.setText(appointmentModel.getCustomerEmail());
    }

    @Override
    public int getItemCount() {
        return appointmentModelList.size();
    }

    static class ProviderAppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView customerPhone, customerEmail, appointmentDesc, appointmentDate, appointmentStartTime, customerName;
        public ProviderAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.tv_provider_appointments_customer_name);
            customerEmail = itemView.findViewById(R.id.tv_provider_appointment_customer_email);
            appointmentDate = itemView.findViewById(R.id.tv_provider_appointments_date);
            customerPhone = itemView.findViewById(R.id.tv_provider_appointment_customer_mobile);
            appointmentDesc = itemView.findViewById(R.id.tv_provider_appoitnment_appointment_desc);
            appointmentStartTime = itemView.findViewById(R.id.tv_provider_appointment_time);
        }
    }
}
