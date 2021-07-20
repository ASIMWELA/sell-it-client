package com.main.sellit.ui.provider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.main.sellit.R;
import com.main.sellit.contract.ProviderServiceContract;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.model.Service;
import com.main.sellit.model.ServiceAndCategoryNamesModel;
import com.main.sellit.model.ServiceCategory;
import com.main.sellit.presenter.ProviderServicePresenter;
import com.main.sellit.ui.AddProductCategoryActivity;
import com.main.sellit.ui.adapter.ServiceListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProviderServicesFragment extends Fragment implements ProviderServiceContract.View {

    FloatingActionButton btnAddCategory;
    ProviderServicePresenter providerServicePresenter;
    FrameLayout fmProgressBar;
    String authToken, providerUuid;
    SessionManager sessionManager;
    RecyclerView recyclerViewServices;
    ArrayList<ServiceAndCategoryNamesModel> servicesList;
    ServiceListAdapter serviceListAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_provider_services, container, false);

        //initialize views
        initViews(view);



        providerServicePresenter = new ProviderServicePresenter(this, getContext());
        sessionManager = new SessionManager(getContext());

        authToken = sessionManager.getToken();
        providerUuid = sessionManager.getProviderUUid();
        servicesList = new ArrayList<>();
        providerServicePresenter.getProviderServices(providerUuid, authToken);
        btnAddCategory.setOnClickListener(v->{
                providerServicePresenter.openCategoryBtnClicked();
        });
        //set click listners to views

        return view;
    }

    private void initViews(View view){
        btnAddCategory = view.findViewById(R.id.btn_floating_open_add_category);
        fmProgressBar = view.findViewById(R.id.progress_overlay_holder_provider_services);
        recyclerViewServices = view.findViewById(R.id.recycler_view_provider_services);
    }

    @Override
    public void openAddCategoryActivity() {
        Intent intent = new Intent(getActivity(), AddProductCategoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void showGetServicesProgressBar() {
        fmProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGetServicesProgressBar() {
        fmProgressBar.setVisibility(View.GONE);
    }

    @Override
    @SneakyThrows
    public void onGetServicesResponse(JSONObject servicesResponse) {
        JSONArray jsonArray = servicesResponse.getJSONArray("data");

        for (int x = 0; x < jsonArray.length(); x++){
            JSONObject serviceOb = jsonArray.getJSONObject(x);

            ServiceAndCategoryNamesModel service = ServiceAndCategoryNamesModel.builder().serviceUuid(serviceOb.getString("serviceUuid"))
                    .serviceName(serviceOb.getString("serviceName"))
                    .serviceCategory(serviceOb.getString("serviceCategoryName"))
                    .build();
            servicesList.add(service);
        }
        serviceListAdapter = new ServiceListAdapter(servicesList);


        LinearLayoutManager r = new LinearLayoutManager(getContext());
        recyclerViewServices.setLayoutManager(r);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewServices.getContext(),
                r.getOrientation());
        recyclerViewServices.addItemDecoration(dividerItemDecoration);

        recyclerViewServices.setItemAnimator(new DefaultItemAnimator());
        recyclerViewServices.setAdapter(serviceListAdapter);
  //      Toast.makeText(getActivity(), servicesList.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetServicesError(String volleyError) {
        Toast.makeText(getActivity(), volleyError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}