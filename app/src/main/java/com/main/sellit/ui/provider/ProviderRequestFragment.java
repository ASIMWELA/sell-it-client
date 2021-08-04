package com.main.sellit.ui.provider;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.sellit.R;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;



@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProviderRequestFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_provider_request, container, false);

        return view;
    }
}