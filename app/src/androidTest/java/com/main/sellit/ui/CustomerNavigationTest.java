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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CustomerNavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void customerNavigationTest() {
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
        appCompatEditText.perform(scrollTo(), replaceText("vsimwela"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.edtx_login_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrl_login_conatiner),
                                        0),
                                7)));
        appCompatEditText2.perform(scrollTo(), replaceText("12345"), closeSoftKeyboard());

        pressBack();

        ViewInteraction loadingButton = onView(
                allOf(withId(R.id.btn_login_send_request), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrl_login_conatiner),
                                        0),
                                8)));
        loadingButton.perform(scrollTo(), click());

        ViewInteraction cardView = onView(
                allOf(withId(R.id.cv_customer_home_open_services_activity),
                        childAtPosition(
                                allOf(withId(R.id.relativeLayout3),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                5)),
                                0)));
        cardView.perform(scrollTo(), click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.ivCustomerServicesBackArrow),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction cardView2 = onView(
                allOf(withId(R.id.cv_customer_home_open_service_request_activity),
                        childAtPosition(
                                allOf(withId(R.id.relativeLayout3),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                5)),
                                1)));
        cardView2.perform(scrollTo(), click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_customer_requests_list_open_offers), withText("Offers"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.customer_request_singles_row),
                                        0),
                                7),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.iv_customer_view_offers_back_arrow),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_customer_requests_list_open_offers), withText("Offers"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.customer_request_singles_row),
                                        0),
                                7),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.iv_customer_view_offers_back_arrow),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageView3.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_customer_requests_list_open_offers), withText("Offers"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.customer_request_singles_row),
                                        0),
                                7),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatImageView4 = onView(
                allOf(withId(R.id.iv_customer_view_offers_back_arrow),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageView4.perform(click());

        ViewInteraction appCompatImageView5 = onView(
                allOf(withId(R.id.iv_customer_back_arrow),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageView5.perform(click());

        ViewInteraction relativeLayout = onView(
                allOf(withId(R.id.rl_customer_home_logout_btn),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        relativeLayout.perform(scrollTo(), click());
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
