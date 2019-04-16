package org.thoughtcrime.securesms.instrumenteduitests;

import android.os.SystemClock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thoughtcrime.securesms.ConversationListActivity;
import org.thoughtcrime.securesms.R;

import java.util.Calendar;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BroadcastUITests {
    @Rule
    public ActivityTestRule<ConversationListActivity> myActivity = new ActivityTestRule<>(ConversationListActivity.class);

    @Test
    public void emptyRecipientAndMessage_ExpectError(){
        onView(withContentDescription("More options")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Broadcast message")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("CONFIRM")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please enter the following:\n Message Contacts")).check(matches(isDisplayed()));
    }

    @Test
    public void emptyRecipients_ExpectError(){
        onView(withContentDescription("More options")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Broadcast message")).perform(click());
        SystemClock.sleep(1000);
        onView(ViewMatchers.withId(R.id.broadcast_message_body)).perform(typeText("Hello"));
        SystemClock.sleep(1000);
        onView(withText("CONFIRM")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please enter the following:\n Contacts")).check(matches(isDisplayed()));
    }

    @Test
    public void emptyMessage_ExpectError(){
        onView(withContentDescription("More options")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Broadcast message")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.recipients_text)).perform(typeText("5146556003"));
        SystemClock.sleep(1000);
        onView(withId(R.id.recipients_text)).perform(pressImeActionButton());
        SystemClock.sleep(1000);
        onView(withText("CONFIRM")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please enter the following:\n Message")).check(matches(isDisplayed()));
    }

    @Test
    public void sendBroadcast_2contacts_NoError(){
        Calendar current = Calendar.getInstance();
        int millies = current.get(Calendar.MILLISECOND);
        onView(withContentDescription("More options")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Broadcast message")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.recipients_text)).perform(typeText("5146556003"));
        SystemClock.sleep(1000);
        onView(withId(R.id.recipients_text)).perform(pressImeActionButton());
        SystemClock.sleep(1000);
        onView(withId(R.id.recipients_text)).perform(replaceText(""));
        SystemClock.sleep(1000);
        onView(withId(R.id.recipients_text)).perform(typeText("5149619291"));
        SystemClock.sleep(1000);
        onView(withId(R.id.recipients_text)).perform(pressImeActionButton());
        SystemClock.sleep(1000);
        onView(withId(R.id.broadcast_message_body)).perform(typeText("Hello from Broadcast UI test"+ millies));
        onView(withText("CONFIRM")).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("New conversation")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_view)).perform(typeText("5146556003"));
        SystemClock.sleep(1000);
        onView(withText("New message to...")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Hello from Broadcast UI test"+ millies)).check(matches(isDisplayed()));
        onView(withId(R.id.up_button)).perform(click());
        onView(withContentDescription("New conversation")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_view)).perform(typeText("5149619291"));
        SystemClock.sleep(1000);
        onView(withText("New message to...")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Hello from Broadcast UI test"+millies)).check(matches(isDisplayed()));
    }

}
