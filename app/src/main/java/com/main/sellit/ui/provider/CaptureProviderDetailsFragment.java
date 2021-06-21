package com.main.sellit.ui.provider;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kusu.loadingbutton.LoadingButton;
import com.main.sellit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaptureProviderDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaptureProviderDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    LoadingButton submitProviderInfo;
    ImageView backArrow;
    Context ctx;
    Fragment fragment;

    public CaptureProviderDetailsFragment() {

    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static CaptureProviderDetailsFragment newInstance(String param1, String param2) {
        CaptureProviderDetailsFragment fragment = new CaptureProviderDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ctx = null;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_capture_provider_details, container, false);
        backArrow = view.findViewById(R.id.imv_back_arrow_provider_info);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new CapturePersonalInfoFragment();
                loadFragment(fragment);
            }
        });

        //back button event
        submitProviderInfo = (LoadingButton) view.findViewById(R.id.btn_capture_user_info);
        submitProviderInfo.setCornerRadius(100);
        submitProviderInfo.setShadowHeight(0);
        submitProviderInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitProviderInfo.showLoading();
            }
        });
        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}