package edu.dami.guiameapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class OnboardingUiTest {

    @Rule
    public ActivityScenarioRule<OnboardingActivity> activityScenarioRule
            = new ActivityScenarioRule<>(OnboardingActivity.class);

    @Test
    public void dado_cargarPantalla_conNoParams_presentarBienvenida() {
        onView(withId(R.id.iv_logo)).check(matches(withContentDescription(R.string.logo_desc)));
        onView(withId(R.id.tv_title)).check(matches(withText(R.string.titulo_bienvenida)));
        onView(withId(R.id.tv_desc)).check(matches(withText(R.string.bienvenida_desc)));
        onView(withId(R.id.tv_afford)).check(matches(withText(R.string.genial_verdad)));
    }

    @Test
    public void dado_clickBotonIniciar_conNoParams_presentarFormRegistro() {
        onView(withId(R.id.btn_signup)).perform(click());
        onView(withId(R.id.tv_title)).check(matches(withText(R.string.empecemos)));
    }
}
