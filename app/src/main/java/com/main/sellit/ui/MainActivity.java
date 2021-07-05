 package com.main.sellit.ui;

 import android.content.Intent;
 import android.os.Bundle;
 import android.widget.Button;

 import androidx.appcompat.app.AppCompatActivity;

 import com.main.sellit.R;
 import com.main.sellit.contract.MainActivityContract;
 import com.main.sellit.presenter.MainActivityPresenter;

 import lombok.AccessLevel;
 import lombok.experimental.FieldDefaults;

 @FieldDefaults(level = AccessLevel.PRIVATE)
 public class MainActivity extends AppCompatActivity implements MainActivityContract.View {
    MainActivityPresenter presenter;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        presenter = new MainActivityPresenter(this);
         Button btnSignup = (Button) findViewById(R.id.btn_welcome_signup);
        Button btnLogin = (Button) findViewById(R.id.btn_welcome_login);

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
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
     }
 }