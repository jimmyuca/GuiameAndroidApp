package edu.dami.guiameapp;

import android.widget.TextView;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static edu.dami.guiameapp.TestHelper.getStringForTest;
import static edu.dami.guiameapp.TestHelper.hasInputErrorText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignUpUiTest {

    @Rule
    public ActivityScenarioRule<SignUpActivity> activityScenarioRule
            = new ActivityScenarioRule<>(SignUpActivity.class);

    @Test
    public void dado_cargarPantalla_conNoParams_presentarForm() {
        onView(withId(R.id.iv_hero)).check(matches(withContentDescription(R.string.banner)));
        onView(withId(R.id.tv_title)).check(matches(withText(R.string.empecemos)));
    }

    @Test
    public void dado_clickBoton_conNoParams_presentarError() {
        final String username = "";
        final String email = "";

        onView(withId(R.id.et_fullname)).perform(replaceText(username), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(replaceText(email), closeSoftKeyboard());
        onView(withId(R.id.btn_signup)).perform(click());

        onView(withId(R.id.til_fullname))
                .check(matches(hasInputErrorText(R.string.fullname_error)));
    }

    @Test
    public void dado_clickBoton_conEmailInvalido_presentarError() {
        final String username = "jimmy";
        final String email = "";

        onView(withId(R.id.et_fullname)).perform(replaceText(username), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(replaceText(email), closeSoftKeyboard());
        onView(withId(R.id.btn_signup)).perform(click());

        onView(withId(R.id.til_email))
                .check(matches(hasInputErrorText(R.string.email_error)));
    }

    @Test
    public void dado_clickBoton_conParamsCorrectos_navegarPrincipal() {
        final String username = "Jimmy";
        final String email = "jimmy@gmail.com";

        onView(withId(R.id.et_fullname)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.btn_signup)).perform(click());

        String title = getStringForTest(R.string.welcome_user_title, username);
        // si se llegara a cambiar el actionbar por defecto por 1 toolbar,
        // se tiene que reemplazar la logica aqui
        onView(allOf(instanceOf(TextView.class),
                withParent(withResourceName("action_bar"))))
                .check(matches(withText(title)));
    }

}
