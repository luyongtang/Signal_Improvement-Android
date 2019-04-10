package org.thoughtcrime.securesms;

import android.content.Intent;
import android.os.SystemClock;
import android.widget.TimePicker;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ScheduledMessagesUITests {
    @Rule
    public ActivityTestRule<ConversationListActivity> myActivity = new ActivityTestRule<>(ConversationListActivity.class);

    @Test
    public void pickDateInPast_ExpectError(){
        onView(withContentDescription("New conversation")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_view)).perform(typeText("5146556003"));
        SystemClock.sleep(1000);
        onView(withText("New message to...")).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Scheduled message")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Pick Date")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("2019")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("2018")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Date must be in the future")).check(matches(isDisplayed()));
    }

    @Test
    public void pickDateInFuture_ExpectNoError(){
        onView(withContentDescription("New conversation")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_view)).perform(typeText("5146556003"));
        SystemClock.sleep(1000);
        onView(withText("New message to...")).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Scheduled message")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Pick Date")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("2019")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("2020")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("CONFIRM")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Date must be in the future")).check(doesNotExist());
    }

    @Test
    public void emptyMessage_ExpectError(){
        onView(withContentDescription("New conversation")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_view)).perform(typeText("5146556003"));
        SystemClock.sleep(1000);
        onView(withText("New message to...")).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Scheduled message")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Pick Date")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Pick Time")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("CONFIRM")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please select the following: \nMessage\n")).check(matches(isDisplayed()));
    }

    @Test
    public void emptyTime_ExpectError(){
        onView(withContentDescription("New conversation")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_view)).perform(typeText("5146556003"));
        SystemClock.sleep(1000);
        onView(withText("New message to...")).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Scheduled message")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.scheduled_body)).perform(typeText("Hello"));
        SystemClock.sleep(1000);
        onView(withText("Pick Date")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("CONFIRM")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please select the following: \nTime\n")).check(matches(isDisplayed()));
    }

    @Test
    public void emptyDate_ExpectError(){
        onView(withContentDescription("New conversation")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_view)).perform(typeText("5146556003"));
        SystemClock.sleep(1000);
        onView(withText("New message to...")).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Scheduled message")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.scheduled_body)).perform(typeText("Hello"));
        SystemClock.sleep(1000);
        onView(withText("Pick Time")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("CONFIRM")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please select the following: \nDate\n")).check(matches(isDisplayed()));
    }

    @Test
    public void emptyTimeAndMessage_ExpectError(){
        onView(withContentDescription("New conversation")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_view)).perform(typeText("5146556003"));
        SystemClock.sleep(1000);
        onView(withText("New message to...")).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Scheduled message")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Pick Date")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("CONFIRM")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please select the following: \nTime\nMessage\n")).check(matches(isDisplayed()));
    }

    @Test
    public void emptyDateAndMessage_ExpectError(){
        onView(withContentDescription("New conversation")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_view)).perform(typeText("5146556003"));
        SystemClock.sleep(1000);
        onView(withText("New message to...")).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Scheduled message")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Pick Time")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("CONFIRM")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please select the following: \nDate\nMessage\n")).check(matches(isDisplayed()));
    }

    @Test
    public void emptyTimeAndMessageAndDate_ExpectError(){
        onView(withContentDescription("New conversation")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_view)).perform(typeText("5146556003"));
        SystemClock.sleep(1000);
        onView(withText("New message to...")).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Scheduled message")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("CONFIRM")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please select the following: \nDate\nTime\nMessage\n")).check(matches(isDisplayed()));
    }

    @Test
    public void emptyTimeAndDate_ExpectError(){
        onView(withContentDescription("New conversation")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_view)).perform(typeText("5146556003"));
        SystemClock.sleep(1000);
        onView(withText("New message to...")).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Scheduled message")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.scheduled_body)).perform(typeText("Hello"));
        SystemClock.sleep(1000);
        onView(withText("CONFIRM")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please select the following: \nDate\nTime\n")).check(matches(isDisplayed()));
    }

    @Test
    public void sendMessageTest(){
        Calendar current = Calendar.getInstance();
        int millies = current.get(Calendar.MILLISECOND);
        onView(withContentDescription("New conversation")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_view)).perform(typeText("5145493505"));
        SystemClock.sleep(1000);
        onView(withText("New message to...")).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withContentDescription("More options")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Scheduled message")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.scheduled_body)).perform(typeText("Hey Eglen from UI test"+millies));
        SystemClock.sleep(1000);
        onView(withText("Pick Date")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Pick Time")).perform(click());
        SystemClock.sleep(1000);
        int hour = current.get(Calendar.HOUR_OF_DAY);
        int min = current.get(Calendar.MINUTE);
        SystemClock.sleep(1000);
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour, ++min));
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("CONFIRM")).perform(click());
        SystemClock.sleep(90000);
        onView(withText("Hey Eglen from UI test"+millies)).check(matches(isDisplayed()));
    }
}
