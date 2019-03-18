package org.thoughtcrime.securesms;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ScheduledMessagesTest {
    @Rule
    //Start application at chosen activity: ApplicationPreferencesActivity
    public ActivityTestRule<ScheduledMessageActivity> myActivity = new ActivityTestRule<>(ScheduledMessageActivity.class);

    @Test
    public void pickDateInPast_ExpectError(){
        onView(withText("Pick Date")).perform(click());
        onView(withText("2019")).perform(click());
        onView(withText("2018")).perform(click());
        onView(withText("OK")).perform(click());
        onView(withText("Date must be in the future")).check(matches(isDisplayed()));
    }

    @Test
    public void pickDateInFuture_ExpectNoError(){
        onView(withText("Pick Date")).perform(click());
        onView(withText("2019")).perform(click());
        onView(withText("2020")).perform(click());
        onView(withText("OK")).perform(click());
        onView(withText("Date must be in the future")).check(doesNotExist());
    }
}
