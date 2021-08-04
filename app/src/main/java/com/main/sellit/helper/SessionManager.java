package com.main.sellit.helper;

import android.content.Context;
import android.content.SharedPreferences;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences("sellIt", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }
    public void setLoggedInUser(String userObject){
        editor.putString(AppConstants.LOGGED_IN_USER_SESSION_KEY, userObject).commit();
    }

    public void setProviderUuid(String providerUuid){
        editor.putString(AppConstants.SET_PROVIDER_UUID, providerUuid).commit();
    }

    public String getProviderUUid(){
        return sharedPreferences.getString(AppConstants.SET_PROVIDER_UUID, null);
    }
    public String getLoggedInUser(){
       return sharedPreferences.getString(AppConstants.LOGGED_IN_USER_SESSION_KEY, null);
    }
    public void setAccessToken(String token){
        editor.putString(AppConstants.API_ACCESS_TOKEN, token).commit();
    }
    public String getToken(){
        return sharedPreferences.getString(AppConstants.API_ACCESS_TOKEN, null);
    }

    public void setLoggedInCustomerUuid(String customerUuid){
        editor.putString(AppConstants.LOGGED_IN_CUSTOMER_UUID, customerUuid).commit();
    }

    public String getLoggedInCustomerUuid(){
        return sharedPreferences.getString(AppConstants.LOGGED_IN_CUSTOMER_UUID, null);
    }

}
