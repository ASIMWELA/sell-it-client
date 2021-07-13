package com.main.sellit.helper;

import android.content.Context;
import android.content.SharedPreferences;

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
    public String getLoggedInUser(){
       return sharedPreferences.getString(AppConstants.LOGGED_IN_USER_SESSION_KEY, null);
    }
    public void setAccessToken(String token){
        editor.putString(AppConstants.API_ACCESS_TOKEN, token).commit();
    }
    public String getToken(){
        return sharedPreferences.getString(AppConstants.API_ACCESS_TOKEN, null);
    }


}
