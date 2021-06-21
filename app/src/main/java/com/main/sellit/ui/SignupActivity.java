package com.main.sellit.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.main.sellit.R;
import com.main.sellit.ui.customer.SignUpCustomerFragment;
import com.main.sellit.ui.provider.CapturePersonalInfoFragment;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SignupActivity extends AppCompatActivity {
    Fragment fragment;
    Context ctx;

    public SignupActivity(Context cts){
        this.ctx=cts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fragment = new CapturePersonalInfoFragment();
        loadFragment(fragment);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_customer:
                        fragment = new SignUpCustomerFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_provider:
                        fragment = new CapturePersonalInfoFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });
    }
    //load fragment into view  by swapping the views
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}