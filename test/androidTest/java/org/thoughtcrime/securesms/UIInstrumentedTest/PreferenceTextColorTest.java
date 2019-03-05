package org.thoughtcrime.securesms.UIInstrumentedTest;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thoughtcrime.securesms.ApplicationPreferencesActivity;

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
public class PreferenceTextColorTest {
    @Rule
    //Start application at chosen activity: ApplicationPreferencesActivity
    public ActivityTestRule<ApplicationPreferencesActivity> myActivity = new ActivityTestRule<>(ApplicationPreferencesActivity.class);

    @Test
    public void changeTextColorTest(){
        //Chosen color to apply on bubble text chat
        String newColor = "Blue";
        //Navigate to text Color preference
        onView(withText("Customization")).perform(click());
        onView(withText("Text Color")).perform(click());
        //select the color from list
        onView(withText(newColor)).perform(click());
        //the new selected color is now displayed as the selected one,
        // which is the reference value used to create the color for the bubble text chat
        onView(withText(newColor)).check(matches(isDisplayed()));
    }
}

