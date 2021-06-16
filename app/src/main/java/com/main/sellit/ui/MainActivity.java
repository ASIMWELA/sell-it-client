 package com.main.sellit.ui;

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
    Button btnLogin, btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        presenter = new MainActivityPresenter(this, this);
        btnSignup = (Button)findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);

        //navigate to login and signup activities
        btnSignup.setOnClickListener(v -> navigateToSignUp());
        btnLogin.setOnClickListener(v->navigateToLogin());
    }

     @Override
     public void navigateToSignUp() {
        presenter.signUpButtonClicked();
     }

     @Override
     public void navigateToLogin() {
        presenter.loginButtonClicked();
     }
 }