package org.thoughtcrime.securesms;

import android.app.Activity;
import android.content.Intent;
import android.os.SystemClock;
import android.preference.PreferenceActivity;
import android.support.design.internal.NavigationMenuItemView;
import android.widget.TimePicker;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thoughtcrime.securesms.util.TextSecurePreferences;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

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
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static androidx.test.runner.lifecycle.Stage.RESUMED;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NavigationBarUITests {
    @Rule
    //In order to run those tests, an existing conversation with +15146556003 is required
    public ActivityTestRule<ConversationListActivity> myActivity = new ActivityTestRule<>(ConversationListActivity.class);

    @Test
    public void selectSettingsThenBackToConversation(){
        onView(withId(R.id.navigation_bar_settings)).perform(click());
        onView(withText("Analytics Report")).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_bar_chats)).perform(click());
        onView(withContentDescription("New conversation")).check(matches(isDisplayed()));
    }
}
