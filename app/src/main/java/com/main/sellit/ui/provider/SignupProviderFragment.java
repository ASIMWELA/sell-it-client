package com.main.sellit.ui.provider;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.main.sellit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupProviderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupProviderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button captureProviderUserInfo;
    Fragment fragment;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupProviderFragment() {
        // Required empty public constructor
    }

    public static SignupProviderFragment newInstance(String param1, String param2) {
        SignupProviderFragment fragment = new SignupProviderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
         View view = inflater.inflate(R.layout.fragment_signup_provider, container, false);
        captureProviderUserInfo = (Button)view.findViewById(R.id.btnCaptureProviderInfo);
        captureProviderUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new CaptureProviderDetailsFragment();
                loadFragment(fragment);
                //TODO: swap frags
            }
        });

         return view;
    }

    private void loadFragment(Fragment fragment) {
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}