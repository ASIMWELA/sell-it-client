package com.main.sellit.contract;

public interface CustomerHomeContract {
    interface View {
        void openCustomerProfile();
        void openCustomerRequests();
        void openCustomerServices();
        void openCustomerAppointments();
        void onLogout();
    }
    interface Presenter{
        void onCustomerRequestCardClicked();
        void onCustomerProfileCardClicked();
        void onCustomerAppointmentsCardClicked();
        void onCustomerServicesCardClicked();
        void onLogoutBtnClicked();
    }
}
