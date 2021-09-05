package com.main.sellit.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.main.sellit.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProviderNavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void providerNavigationTest() {
        ViewInteraction button = onView(
                allOf(withId(R.id.btn_welcome_login), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        3),
                                1)));
        button.perform(scrollTo(), click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.edt_txt_login_user_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrl_login_conatiner),
                                        0),
                                6)));
        appCompatEditText.perform(scrollTo(), replaceText("malunga"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.edtx_login_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrl_login_conatiner),
                                        0),
                                7)));
        appCompatEditText2.perform(scrollTo(), replaceText("123456"), closeSoftKeyboard());

        pressBack();

        ViewInteraction loadingButton = onView(
                allOf(withId(R.id.btn_login_send_request), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrl_login_conatiner),
                                        0),
                                8)));
        loadingButton.perform(scrollTo(), click());

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.provider_navigation_services), withContentDescription("Services"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.provider_bottom_navigation_view),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.provider_navigation_appointments), withContentDescription("Appointments"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.provider_bottom_navigation_view),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.provider_navigation_profile), withContentDescription("Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.provider_bottom_navigation_view),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction bottomNavigationItemView4 = onView(
                allOf(withId(R.id.provider_navigation_request), withContentDescription("Requests"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.provider_bottom_navigation_view),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView4.perform(click());

        ViewInteraction bottomNavigationItemView5 = onView(
                allOf(withId(R.id.provider_navigation_appointments), withContentDescription("Appointments"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.provider_bottom_navigation_view),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView5.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.iv_provider_appointments_back_arrow),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.btn_logout_provider_service_request),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatImageView2.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
