package com.main.sellit.contract;


import org.json.JSONObject;

public interface ProviderContract {
    interface View{

    }
    interface Presenter{
        JSONObject createPersonalDetailsObject(android.view.View view);
    }
}
