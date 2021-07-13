package com.main.sellit.ui.provider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.main.sellit.R;
import com.main.sellit.contract.ProviderServiceContract;
import com.main.sellit.presenter.ProviderServicePresenter;
import com.main.sellit.ui.AddProductCategoryActivity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProviderServicesFragment extends Fragment implements ProviderServiceContract.View {

    private FloatingActionButton btnAddCategory;

    private ProviderServicePresenter providerServicePresenter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_provider_services, container, false);
        initViews(view);

        providerServicePresenter = new ProviderServicePresenter(this);

        btnAddCategory.setOnClickListener(v->{
                providerServicePresenter.openCategoryBtnClicked();
        });
        return view;
    }

    private void initViews(View view){
        btnAddCategory = view.findViewById(R.id.btn_floating_open_add_category);
    }

    @Override
    public void openAddCategoryActivity() {
        Intent intent = new Intent(getActivity(), AddProductCategoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}