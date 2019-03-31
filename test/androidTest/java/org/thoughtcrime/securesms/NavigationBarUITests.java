package org.thoughtcrime.securesms;

import android.content.Intent;
import android.os.SystemClock;
import android.support.design.internal.NavigationMenuItemView;
import android.widget.TimePicker;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onData;
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
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NavigationBarUITests {
    @Rule
    //In order to run those tests, an existing conversation with +15146556003 is required
    public ActivityTestRule<ConversationListActivity> myActivity = new ActivityTestRule<>(ConversationListActivity.class);


    @Test
    public void selectCallogs(){
        onView(withId(R.id.navigation_bar_call_logs)).perform(click());
        onView(withClassName(Matchers.equalTo(CallLogsActivity.class.getName()))).check(matches(isDisplayed()));
    }

    @Test
    public void selectChat(){
        onView(withId(R.id.navigation_bar_chats)).perform(click());
        onView(withClassName(Matchers.equalTo(ConversationListActivity.class.getName()))).check(matches(isDisplayed()));
    }

    @Test
    public void selectSettings(){
        onView(withId(R.id.navigation_bar_settings)).perform(click());
        onView(withClassName(Matchers.equalTo(ApplicationPreferencesActivity.class.getName()))).check(matches(isDisplayed()));

    }
}
