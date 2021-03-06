package com.main.sellit.ui.provider;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.main.sellit.R;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.model.CustomerLoginModel;
import com.main.sellit.model.ProviderLoginModel;
import com.main.sellit.ui.LoginActivity;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProviderProfileFragment extends Fragment {
    TextView tvProviderUsername, tvProviderFullName,
    tvProviderEmail, tvProviderPhoneNumber, tvProviderOfficeLocation,
    tvProviderBusinessDesc;
    SessionManager sessionManager;
    ImageView ivBackArrow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_provider_profile, container, false);
        initView(view);
        sessionManager = new SessionManager(requireContext());
        Gson gson = new Gson();
        String json = sessionManager.getLoggedInUser();
        ProviderLoginModel providerLoginModel = gson.fromJson(json, ProviderLoginModel.class);
        String fullName = providerLoginModel.getFirstName() + "  "+ providerLoginModel.getLastName();

        //set to views
        tvProviderBusinessDesc.setText(providerLoginModel.getProviderDescription());
        tvProviderFullName.setText(fullName);
        tvProviderUsername.setText(providerLoginModel.getUserName());
        tvProviderOfficeLocation.setText(providerLoginModel.getOfficeAddress());
        tvProviderPhoneNumber.setText(providerLoginModel.getPhoneNumber());
        tvProviderEmail.setText(providerLoginModel.getEmail());

        //clear the provider credential
        ivBackArrow.setOnClickListener(v->{
            requireActivity().onBackPressed();
        });

        return view;
    }

    private void initView(View view){
        tvProviderBusinessDesc = view.findViewById(R.id.tv_provider_profile_business_desc);
        tvProviderEmail = view.findViewById(R.id.tv_provider_profile_email);
        tvProviderPhoneNumber = view.findViewById(R.id.tv_provider_profile_phone_number);
        tvProviderOfficeLocation = view.findViewById(R.id.tv_provider_profile_office_location);
        tvProviderUsername = view.findViewById(R.id.tv_provider_profile_username);
        tvProviderFullName = view.findViewById(R.id.tv_provider_profile_full_name);
        ivBackArrow = view.findViewById(R.id.iv_provider_profile_back_arrow);
    }
}