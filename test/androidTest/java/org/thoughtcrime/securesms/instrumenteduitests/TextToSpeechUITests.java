package org.thoughtcrime.securesms.instrumenteduitests;

import android.os.SystemClock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thoughtcrime.securesms.ConversationListActivity;
import org.thoughtcrime.securesms.R;

import java.util.Calendar;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TextToSpeechUITests {
    @Rule
    public ActivityTestRule<ConversationListActivity> myActivity = new ActivityTestRule<>(ConversationListActivity.class);

    @Test
    public void iconIsDisplayed(){
        Calendar current = Calendar.getInstance();
        int millies = current.get(Calendar.MILLISECOND);
        onView(withContentDescription("New conversation")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_view)).perform(typeText("5149619291"));
        SystemClock.sleep(1000);
        onView(withText("New message to...")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.embedded_text_editor)).perform(typeText("Hey Eglen from UI test"+millies));
        SystemClock.sleep(1000);
        onView(withId(R.id.embedded_text_editor)).perform(pressImeActionButton());
        SystemClock.sleep(1000);
        onView(withText("Hey Eglen from UI test"+millies)).perform(longClick());
        SystemClock.sleep(1000);
        onView(withId(R.id.menu_context_textToSpeech_message)).check(matches(isDisplayed()));
    }
    //A group named "testing" is required for this test
    @Test
    public void iconIsDisplayedInGroups(){
        Calendar current = Calendar.getInstance();
        int millies = current.get(Calendar.MILLISECOND);
        onView(withContentDescription("More options")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("New group")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.group_name)).perform(typeText("testing"+millies));
        SystemClock.sleep(1000);
        onView(withId(R.id.recipients_text)).perform(typeText("5149619291"));
        SystemClock.sleep(1000);
        onView(withId(R.id.recipients_text)).perform(pressImeActionButton());
        SystemClock.sleep(1000);
        onView(withId(R.id.menu_create_group)).perform(click());
        current = Calendar.getInstance();
        millies = current.get(Calendar.MILLISECOND);
        SystemClock.sleep(1000);
        onView(withId(R.id.embedded_text_editor)).perform(typeText("Hey Erdem from UI test"+millies));
        SystemClock.sleep(1000);
        onView(withId(R.id.embedded_text_editor)).perform(pressImeActionButton());
        SystemClock.sleep(1000);
        onView(withText("Hey Erdem from UI test"+millies)).perform(longClick());
        SystemClock.sleep(1000);
        onView(withId(R.id.menu_context_textToSpeech_message)).check(matches(isDisplayed()));
    }
}
