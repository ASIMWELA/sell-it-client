package com.main.sellit.ui;

import android.content.ComponentName;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.main.sellit.R;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


//the test should be run one by one as both require to start from main activity
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public  ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class, true, true);


    @BeforeClass
    public static void beforeClass() {
        Intents.init();
    }

    @Test
    public void navigateToSignUp() {
        onView(withId(R.id.btn_welcome_signup)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), SignupActivity.class)));

    }

    @Test
    public void navigateToLogin() {
        onView(withId(R.id.btn_welcome_login)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), LoginActivity.class)));

    }
}