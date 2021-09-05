package com.main.sellit.ui.provider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.main.sellit.R;
import com.main.sellit.contract.ProviderRequestsContract;
import com.main.sellit.helper.FlagErrors;
import com.main.sellit.helper.SessionManager;
import com.main.sellit.model.ProviderServiceRequestModel;
import com.main.sellit.presenter.provider.ProviderRequestsPresenter;
import com.main.sellit.ui.LoginActivity;
import com.main.sellit.ui.adapter.ProviderRequestListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;



@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProviderRequestFragment extends Fragment implements ProviderRequestsContract.View {

    RecyclerView recyclerView;
    TextView noRequestMessage;
    Context context;
    FrameLayout fmProgressBar;
    SessionManager sessionManager;
    String token;
    ProviderRequestsPresenter providerRequestsPresenter;
    List<ProviderServiceRequestModel> serviceRequestModelList;
    ProviderRequestListAdapter requestListAdapter;
    ImageView  ivLogout;
    FlagErrors flagErrors;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(requireContext());
        flagErrors = new FlagErrors(requireContext(), requireActivity());
        token =  sessionManager.getToken();
        providerRequestsPresenter = new ProviderRequestsPresenter(this, getContext());
        serviceRequestModelList = new ArrayList<>();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_provider_request, container, false);
        initViews(view);

        providerRequestsPresenter.getRequests(token);
        noRequestMessage.setVisibility(View.GONE);


        ivLogout.setOnClickListener(v->{
            sessionManager.setAccessToken(null);
            sessionManager.setLoggedInUser(null);
            sessionManager.setIsUserLoggedIn(null);
            sessionManager.setProviderUuid(null);
            startActivity(new Intent(requireActivity(), LoginActivity.class));
            requireActivity().finish();
        });



        return view;
    }

    private void initViews(View v){
        recyclerView = v.findViewById(R.id.rv_provider_service_request_list);
        noRequestMessage = v.findViewById(R.id.tv_provider_requests_no_request_message);
        fmProgressBar = v.findViewById(R.id.progress_overlay_holder_provider_get_requests);
        ivLogout = v.findViewById(R.id.btn_logout_provider_service_request);
    }

    @Override
    @SneakyThrows
    public void onGetRequestSuccess(JSONObject apiResponse) {
        JSONArray response = apiResponse.getJSONArray("data");
        if(response.length() == 0){
            noRequestMessage.setVisibility(View.VISIBLE);
        }else {
            for(int x = 0; x < response.length(); x++){
                JSONObject modelObj = response.getJSONObject(x);
                ProviderServiceRequestModel model =
                        ProviderServiceRequestModel.builder()
                        .uuid(modelObj.getString("uuid"))
                        .requestDescription(modelObj.getString("requestDescription"))
                        .requiredDate(modelObj.getString("requiredDate"))
                        .expectedStartTime(modelObj.getString("expectedStartTime"))
                        .expectedHours(modelObj.getLong("expectedHours"))
                        .country(modelObj.getString("country"))
                        .email(modelObj.getString("email"))
                        .locationCity(modelObj.getString("locationCity"))
                        .requestBy(modelObj.getString("requestBy"))
                        .build();
                serviceRequestModelList.add(model);
            }
            requestListAdapter = new ProviderRequestListAdapter(context, serviceRequestModelList);
            LinearLayoutManager r = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(r);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), r.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(requestListAdapter);
        }
    }

    @Override
    public void onGetRequestError(VolleyError apiError) {
        flagErrors.flagApiError(apiError);
    }

    @Override
    public void showGetRequestProgressBar() {
        fmProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGetRequestProgressBar() {
        fmProgressBar.setVisibility(View.GONE);
    }
}