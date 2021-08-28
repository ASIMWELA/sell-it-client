package com.main.sellit.ui.provider;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.main.sellit.R;
import com.main.sellit.contract.ProviderAppointmentsContract;
import com.main.sellit.helper.FlagErrors;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.model.CustomerAppointmentModel;
import com.main.sellit.model.ProviderAppointmentModel;
import com.main.sellit.model.ProviderLoginModel;
import com.main.sellit.presenter.ProviderAppointmentsPresenter;
import com.main.sellit.ui.adapter.CustomerAppointmentsAdapter;
import com.main.sellit.ui.adapter.ProviderAppointmentsAdapter;
import com.main.sellit.ui.adapter.ProviderRequestListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProviderAppointments extends Fragment implements ProviderAppointmentsContract.View {

    FlagErrors flagErrors;
    ImageView ivBackArrow;
    TextView tvNoAppointmentsMessage;
    RecyclerView rvAppointments;
    FrameLayout progressBar;
    List<ProviderAppointmentModel> providerAppointmentModels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_provider_appointments, container, false);
       initViews(view);

        ProviderAppointmentsPresenter presenter = new ProviderAppointmentsPresenter(requireContext(), this);
       flagErrors = new FlagErrors(requireContext(), requireActivity());
        SessionManager sessionManager = new SessionManager(requireContext());
       Gson gson = new Gson();
       String json = sessionManager.getLoggedInUser();
       ProviderLoginModel providerLoginModel = gson.fromJson(json, ProviderLoginModel.class);
       presenter.getProviderAppointments(sessionManager.getToken(), providerLoginModel.getUuid());
        providerAppointmentModels = new ArrayList<>();



       ivBackArrow.setOnClickListener(v->{
           requireActivity().onBackPressed();
       });
       return view;
    }

    private void initViews(View view){
        ivBackArrow = view.findViewById(R.id.iv_provider_appointments_back_arrow);
        rvAppointments = view.findViewById(R.id.rv_provider_appointments);
        progressBar = view.findViewById(R.id.progress_overlay_bar_provider_appointments);
        tvNoAppointmentsMessage = view.findViewById(R.id.tv_provider_no_appointments_message);
    }

    @Override
    public void showGetAppointmentsProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGetAppointmentsProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    @SneakyThrows
    public void onGetAppointmentSuccess(JSONObject apiResponse) {
        JSONArray appointmentData = apiResponse.getJSONArray("data");
        if(appointmentData.length()==0){
            tvNoAppointmentsMessage.setVisibility(View.VISIBLE);
        }else {
            for(int x = 0; x < appointmentData.length(); x++){
                JSONObject appoint = appointmentData.getJSONObject(x);
                Gson gson = new Gson();
                ProviderAppointmentModel model =  gson.fromJson(String.valueOf(appoint), ProviderAppointmentModel.class);
                providerAppointmentModels.add(model);
            }
            ProviderAppointmentsAdapter providerAppointmentsAdapter = new ProviderAppointmentsAdapter(providerAppointmentModels);
            LinearLayoutManager r = new LinearLayoutManager(getContext());
            rvAppointments.setLayoutManager(r);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvAppointments.getContext(), r.getOrientation());
            rvAppointments.addItemDecoration(dividerItemDecoration);
            rvAppointments.setItemAnimator(new DefaultItemAnimator());
            rvAppointments.setAdapter(providerAppointmentsAdapter);
        }
    }

    @Override
    public void onGetAppointmentsError(VolleyError apiError) {
        flagErrors.flagApiError(apiError);
    }
}