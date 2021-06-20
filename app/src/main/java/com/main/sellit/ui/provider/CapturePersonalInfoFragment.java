package com.main.sellit.ui.provider;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.main.sellit.R;
import com.main.sellit.contract.ProviderContract;
import com.main.sellit.presenter.ProviderPresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CapturePersonalInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CapturePersonalInfoFragment extends Fragment implements ProviderContract.View {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button captureProviderUserInfo;
    Fragment fragment;
    Context ctx;
    ProviderPresenter providerPresenter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CapturePersonalInfoFragment() {
        // Required empty public constructor
    }

    public static CapturePersonalInfoFragment newInstance(String param1, String param2) {
        CapturePersonalInfoFragment fragment = new CapturePersonalInfoFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_capture_personal_info, container, false);
        captureProviderUserInfo = (Button)view.findViewById(R.id.btn_capture_user_info);
        providerPresenter = new ProviderPresenter(this, ctx);

        captureProviderUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fragment = new CaptureProviderDetailsFragment();
//                loadFragment(fragment);
                providerPresenter.createPersonalDetailsObject(view);


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