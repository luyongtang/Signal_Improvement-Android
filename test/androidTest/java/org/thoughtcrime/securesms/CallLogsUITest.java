package org.thoughtcrime.securesms;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.ViewInteraction;
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

public class CallLogsUITest {

    @Rule
    public ActivityTestRule<ConversationListActivity> myActivity = new ActivityTestRule<>(ConversationListActivity.class);


    @Before
    //This method calles a number in case the conversation activity is empty
    public void callMyself(){

        ViewInteraction pulsingFloatingActionButton = onView(
                allOf(withId(R.id.fab), withContentDescription("New conversation"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                3),
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
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
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
                        childAtPosition(
                                allOf(withId(R.id.incall_screen),
                                        childAtPosition(
                                                withId(R.id.callScreen),
                                                0)),
                                3),
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
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView.perform(click());

    }


    @Test
    //for this test to puss you must have never had a missed, outgoing, incoming call
    public void nonEmptyCallLog() {
        onView(withId(R.id.navigation_bar_call_logs)).perform(click());
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.call_logs_text_view),
                        childAtPosition(
                                allOf(withId(R.id.call_logs_scroll_view),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                0)),
                                0),
                        isDisplayed()));

        textView2.check(matches(isDisplayed()));
        onView(withId(R.id.navigation_bar_chats)).perform(click());
        onView(withContentDescription("New conversation")).check(matches(isDisplayed()));
    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
