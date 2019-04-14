package org.thoughtcrime.securesms.InstrumentedUITests;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.thoughtcrime.securesms.ConversationListActivity;
import org.thoughtcrime.securesms.R;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class EmptyCallLogUITest {
    @Rule
    //In order to run those tests, an existing conversation with +15146556003 is required
    public ActivityTestRule<ConversationListActivity> myActivity = new ActivityTestRule<>(ConversationListActivity.class);

    @Before
    //This method calles a number in case the conversation activity is empty
    public void callMyself(){

        ViewInteraction pulsingFloatingActionButton = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.fab), withContentDescription("New conversation"),

                        isDisplayed()));
        pulsingFloatingActionButton.perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.search_view),isDisplayed()));
        appCompatEditText4.perform(replaceText("5145493505"), closeSoftKeyboard());

        onView(withText("New message to...")).perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menu_call_secure), withContentDescription("Signal call"),

                        isDisplayed()));
        actionMenuItemView.perform(click());


        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.hangup_fab), withContentDescription("End call"),

                        isDisplayed()));
        floatingActionButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.up_button),
                        isDisplayed()));
        appCompatImageView.perform(click());

    }
    @Test
    //for this test to puss you must have never had a missed, outgoing, incoming call
    public void emptyCallLog() {
        onView(withId(R.id.navigation_bar_call_logs)).perform(click());
        onView(withId(R.id.button5)).perform(click());
        onView(withId(R.id.navigation_bar_chats)).perform(click());
        onView(withContentDescription("New conversation")).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_bar_call_logs)).perform(click());
        onView(withText("History is empty")).check(matches(isDisplayed()));
    }
}
