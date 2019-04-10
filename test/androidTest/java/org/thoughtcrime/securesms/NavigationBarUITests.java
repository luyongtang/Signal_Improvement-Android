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
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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
