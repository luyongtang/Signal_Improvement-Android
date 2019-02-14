package org.thoughtcrime.securesms;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PreferenceBackgroundColorTest {
    @Rule
    //This starts the application at ApplicationPreferencesActivity
    public ActivityTestRule<ApplicationPreferencesActivity> myActivity = new ActivityTestRule<>(ApplicationPreferencesActivity.class);

    @Test
    public void changeBackgroundColorTest(){
        //This is the chosen color to apply on chat background
        String newColor = "White";
        //These navigate to the Background Color preference
        onView(withText("Customization")).perform(click());
        onView(withText("Background Color")).perform(click());
        //This selects the color from list
        onView(withText(newColor)).perform(click());
        /*The new selected color will now be displayed as the chosen one,
        the reference value used to create the color for the background chat*/
        onView(withText(newColor)).check(matches(isDisplayed()));
    }
}
