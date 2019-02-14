package org.thoughtcrime.securesms;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PreferenceTextFontTest {

    String selectedOption;
    @Rule
    public ActivityTestRule<ApplicationPreferencesActivity> mActivityRule
            = new ActivityTestRule<>(ApplicationPreferencesActivity.class);

    @Before
    public void setUp() {
        // indicate the option to be selected in the test
        selectedOption = "Font Script";
    }
    @Test
    public void selectFontScriptTest() {
        selectedOption = "Font Script";
        onView(withText("Customization")).perform(click());
        onView(withText("Text Font")).perform(click());
        onView(withText(selectedOption)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void selectDefaultFontTest() {
        selectedOption = "Default Font";
        onView(withText("Customization")).perform(click());
        onView(withText("Text Font")).perform(click());
        onView(withText(selectedOption)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void selectFontSignTest() {
        selectedOption = "Font Sign";
        onView(withText("Customization")).perform(click());
        onView(withText("Text Font")).perform(click());
        onView(withText(selectedOption)).perform(click()).check(matches(isDisplayed()));
    }
}

