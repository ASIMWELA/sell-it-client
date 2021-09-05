package com.main.sellit.ui;

import android.content.ComponentName;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.main.sellit.R;
import com.main.sellit.ui.provider.ProviderHomeActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> loginActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }


    //integration test
    @Test
    public void providerCanLogin() throws InterruptedException {
        onView(withId(R.id.btn_welcome_login)).perform(click());
        onView(withId(R.id.edt_txt_login_user_name)).perform(typeText("malunga")).perform(closeSoftKeyboard());
        onView(withId(R.id.edtx_login_password)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.btn_login_send_request)).perform(click());
        //network call
        Thread.sleep(30000);
        intended(hasComponent(new ComponentName(getTargetContext(), ProviderHomeActivity.class)));
    }


}