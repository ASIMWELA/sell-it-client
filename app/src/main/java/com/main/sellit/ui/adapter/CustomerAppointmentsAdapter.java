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
import com.main.sellit.model.CustomerAppointmentModel;
import com.main.sellit.ui.customer.ReviewProviderActivity;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomerAppointmentsAdapter extends RecyclerView.Adapter<CustomerAppointmentsAdapter.AppointmentAdapter> {
    Context context;
    List<CustomerAppointmentModel> customerAppointments;
    @NonNull
    @Override
    public AppointmentAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_appointment_row, parent, false);
        return new AppointmentAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter holder, int position) {
        CustomerAppointmentModel appointmentModel = customerAppointments.get(position);

        holder.providerName.setText(appointmentModel.getAppointmentWith());
        holder.providerPhone.setText(appointmentModel.getProviderPhone());
        holder.appointmentStartTime.setText(appointmentModel.getAppointmentStartTime());
        holder.appointmentDesc.setText(appointmentModel.getAppointmentDesc());
        holder.appointmentDate.setText(appointmentModel.getAppointmentDate());
        holder.providerEmail.setText(appointmentModel.getProviderEmail());

        holder.reviewProvider.setOnClickListener(v->{
            Intent i = new Intent(context.getApplicationContext(), ReviewProviderActivity.class);
            i.putExtra("providerName", appointmentModel.getAppointmentWith());
            i.putExtra("appointmentUuid", appointmentModel.getUuid());


            context.startActivity(i);

        });
    }

    @Override
    public int getItemCount() {
        return customerAppointments.size();
    }

    static class AppointmentAdapter extends RecyclerView.ViewHolder{

        TextView providerPhone, providerEmail, appointmentDesc, appointmentDate, appointmentStartTime, providerName;
        AppCompatButton reviewProvider;
        public AppointmentAdapter(@NonNull View itemView) {
            super(itemView);
            providerEmail = itemView.findViewById(R.id.tv_customer_appointment_provider_email);
            appointmentDate = itemView.findViewById(R.id.tv_customer_appointments_date);
            appointmentDesc = itemView.findViewById(R.id.tv_customer_appoitnment_appointment_desc);
            appointmentStartTime = itemView.findViewById(R.id.tv_customer_appointment_time);
            reviewProvider = itemView.findViewById(R.id.btn_customer_appointment_review_provider);
            providerPhone = itemView.findViewById(R.id.tv_customer_appointment_provider_mobile);
            providerName = itemView.findViewById(R.id.tv_customer_appointments_provider_name);
        }
    }
}
