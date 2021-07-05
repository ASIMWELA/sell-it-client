package com.main.sellit.ui.provider;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.main.sellit.R;

public class ProviderHomeActivity extends AppCompatActivity {
    BottomNavigationView navigationItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_home);


        navigationItemView = findViewById(R.id.provider_bottom_navigation_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.provider_navigation_profile, R.id.provider_navigation_request, R.id.provider_navigation_services)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.provider_nav_fragment_host);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationItemView, navController);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}