package edu.dami.guiameapp;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import edu.dami.guiameapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainFlowUiTest {

    @Rule
    public ActivityTestRule<StartActivity> mActivityTestRule = new ActivityTestRule<>(StartActivity.class);

    @Test
    public void mainFlowUiTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_title), withText("Welcome to Guiame"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btn_signup), withText("LET'S START"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.et_fullname), withText("Jimmy Sáenz Rizo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_fullname),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Jimmy Sáenz Rizo"));

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.et_fullname), withText("Jimmy Sáenz Rizo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_fullname),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.et_email), withText("jimmy.saenz.rizo@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_email),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("jimmy.saenz.rizo@gmail.com"));

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.et_email), withText("jimmy.saenz.rizo@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_email),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.et_email), withText("jimmy.saenz.rizo@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_email),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(pressImeActionButton());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btn_signup), withText("SIGN UP"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.points_ph),
                        withParent(allOf(withId(R.id.ly_root),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_points),
                        childAtPosition(
                                withId(R.id.ly_root),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tv_name), withText("Laboratorios J"),
                        withParent(allOf(withId(R.id.ly_root),
                                withParent(withId(R.id.point_ph)))),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());
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
