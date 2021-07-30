package com.main.sellit.presenter;

import com.main.sellit.contract.CustomerHomeContract;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerHomePresenter implements CustomerHomeContract.Presenter{
    final CustomerHomeContract.View view;
    public CustomerHomePresenter(CustomerHomeContract.View view) {
        this.view = view;
    }

    @Override
    public void onCustomerRequestCardClicked() {
        view.openCustomerRequests();
    }

    @Override
    public void onCustomerProfileCardClicked() {
        view.openCustomerProfile();
    }

    @Override
    public void onCustomerAppointmentsCardClicked() {
        view.openCustomerAppointments();
    }

    @Override
    public void onCustomerServicesCardClicked() {
        view.openCustomerServices();
    }

    @Override
    public void onLogoutBtnClicked() {
        view.onLogout();
    }
}
