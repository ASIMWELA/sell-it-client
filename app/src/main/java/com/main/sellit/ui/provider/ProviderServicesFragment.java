package com.main.sellit.ui.provider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.main.sellit.R;
import com.main.sellit.contract.ProviderServiceContract;
import com.main.sellit.helper.FlagErrors;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.model.ServiceAndCategoryNamesModel;
import com.main.sellit.presenter.ProviderServicePresenter;
import com.main.sellit.ui.AddProductCategoryActivity;
import com.main.sellit.ui.LoginActivity;
import com.main.sellit.ui.adapter.ServiceListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProviderServicesFragment extends Fragment implements ProviderServiceContract.View {

    ProviderServicePresenter providerServicePresenter;
    FrameLayout fmProgressBar;
    String authToken, providerUuid;
    SessionManager sessionManager;
    RecyclerView recyclerViewServices;
    ArrayList<ServiceAndCategoryNamesModel> servicesList;
    ServiceListAdapter serviceListAdapter;
    TextView tvNoServicesMessage;
    ImageView ivBackArrow, ivOpenOptionsMenu;
    FlagErrors flagErrors;
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

        tvNoServicesMessage.setVisibility(View.GONE);



        providerServicePresenter = new ProviderServicePresenter(this, getContext());
        sessionManager = new SessionManager(requireContext());
        flagErrors = new FlagErrors(requireContext(), requireActivity());

        authToken = sessionManager.getToken();
        providerUuid = sessionManager.getProviderUUid();
        servicesList = new ArrayList<>();
        providerServicePresenter.getProviderServices(providerUuid, authToken);

        //set click listners to views

        ivBackArrow.setOnClickListener(v->{
            requireActivity().onBackPressed();
        });

        PopupMenu pm = new PopupMenu(requireActivity(), ivOpenOptionsMenu);
        pm.getMenuInflater().inflate(R.menu.provider_top_menu, pm.getMenu());
        pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item){
                switch (item.getItemId()){
                    case R.id. logout_provider:
                        sessionManager.setLoggedInUser(null);
                        sessionManager.setAccessToken(null);
                        startActivity(new Intent(requireActivity(), LoginActivity.class));
                        requireActivity().finish();
                        return true;
                    case R.id.create_product_category:
                        providerServicePresenter.openCategoryBtnClicked();
                        return true;
                }
                return true;
            }
        });
        ivOpenOptionsMenu.setOnClickListener(v->{
            pm.show();
        });

        return view;
    }

    private void initViews(View view){
        ivBackArrow = view.findViewById(R.id.iv_view_services_back_arrow);
        fmProgressBar = view.findViewById(R.id.progress_overlay_holder_provider_services);
        recyclerViewServices = view.findViewById(R.id.recycler_view_provider_services);
        tvNoServicesMessage = view.findViewById(R.id.tv_provider_services_no_services_messages);
        ivOpenOptionsMenu = view.findViewById(R.id.iv_pop_up_menu);
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
        if(jsonArray.length() == 0){
            tvNoServicesMessage.setVisibility(View.VISIBLE);
        }else {
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
        }

  //      Toast.makeText(getActivity(), servicesList.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetServicesError(VolleyError volleyError) {
        flagErrors.flagApiError(volleyError);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}