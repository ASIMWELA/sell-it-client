 package com.main.sellit.ui;

 import android.content.Intent;
 import android.os.Bundle;
 import android.widget.Button;

 import androidx.appcompat.app.AppCompatActivity;

 import com.main.sellit.R;
 import com.main.sellit.contract.MainContract;
 import com.main.sellit.helper.SessionManager;
 import com.main.sellit.helper.UserRoles;
 import com.main.sellit.presenter.MainPresenter;
 import com.main.sellit.ui.admin.AdminHomeActivity;
 import com.main.sellit.ui.customer.CustomerHomeActivity;
 import com.main.sellit.ui.provider.ProviderHomeActivity;

 import lombok.AccessLevel;
 import lombok.experimental.FieldDefaults;

 @FieldDefaults(level = AccessLevel.PRIVATE)
 public class MainActivity extends AppCompatActivity implements MainContract.View {
    MainPresenter presenter;
    SessionManager sessionManager;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
         Button btnSignup = (Button) findViewById(R.id.btn_welcome_signup);
        Button btnLogin = (Button) findViewById(R.id.btn_welcome_login);
        sessionManager = new SessionManager(this);

        //navigate to login and signup activities
        btnSignup.setOnClickListener(v -> presenter.signUpButtonClicked());
        btnLogin.setOnClickListener(v->presenter.loginButtonClicked());
    }

     @Override
     public void navigateToSignUp() {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
     }

     @Override
     public void navigateToLogin() {
         if(sessionManager.getLoggedInUser() == null){
             Intent i = new Intent(this, LoginActivity.class);
             startActivity(i);
         }else {
             String userRole = sessionManager.getIsUserLoggedIn();
             if(userRole.equals(UserRoles.ROLE_ADMIN.name())){
                 startActivity(new Intent(this, AdminHomeActivity.class));
             }

             if(userRole.equals(UserRoles.ROLE_CUSTOMER.name())){
                 startActivity(new Intent(this, CustomerHomeActivity.class));
             }
             if(userRole.equals(UserRoles.ROLE_PROVIDER.name())){
                 startActivity(new Intent(this, ProviderHomeActivity.class));
             }

         }
     }
 }