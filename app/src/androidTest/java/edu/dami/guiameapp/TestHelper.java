package edu.dami.guiameapp;

import android.view.View;

import androidx.annotation.StringRes;

import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public class TestHelper {

    //src: https://stackoverflow.com/a/34286462/6814301
    public static Matcher<View> hasInputErrorText(final String expectedErrorText) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextInputLayout)) {
                    return false;
                }

                CharSequence error = ((TextInputLayout) view).getError();

                if (error == null) {
                    return false;
                }

                String hint = error.toString();

                return expectedErrorText.equals(hint);
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }

    public static Matcher<View> hasInputErrorText(@StringRes int resId) {
        String string = getStringForTest(resId);
        return hasInputErrorText(string);
    }

    public static String getStringForTest(@StringRes int id) {
        return getInstrumentation()
                .getTargetContext()
                .getResources()
                .getString(id);
    }

    public static String getStringForTest(@StringRes int id, Object... formatArgs) {
        return getInstrumentation()
                .getTargetContext()
                .getResources()
                .getString(id, formatArgs);
    }
}
