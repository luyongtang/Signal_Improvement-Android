package org.thoughtcrime.securesms;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thoughtcrime.securesms.util.Analytic;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AnalyticReportNewTest {

    Context sampleContext;

    @Rule
    public ActivityTestRule<ApplicationPreferencesActivity> mActivityRule
            = new ActivityTestRule<>(ApplicationPreferencesActivity.class);

    @Before
    public void setUp(){
        sampleContext = ApplicationProvider.getApplicationContext();
    }

    // UI test to show the page of Analytic Report is able to open correctly
    @Test
    public void AnalyticReportPagePass() {
        onView(withText("Analytics Report")).perform(click());
        onView(withText("Outgoing Messages")).check(matches(isDisplayed()));
        onView(withText(""+Analytic.getOutgoingMessageCount(sampleContext))).check(matches(isDisplayed()));
        onView(withText("Incoming Messages")).check(matches(isDisplayed()));
        onView(withText(""+Analytic.getIncomingMessageCount(sampleContext))).check(matches(isDisplayed()));
        onView(withText("Last Message Sent To")).check(matches(isDisplayed()));
        onView(withText(Analytic.getLastRecipientSentMessage(sampleContext))).check(matches(isDisplayed()));
        onView(withText("Last Message Received By")).check(matches(isDisplayed()));
        onView(withText(Analytic.getLastRecipientReceivedMessage(sampleContext))).check(matches(isDisplayed()));
    }

    @Test
    public void AnalyticReportPageWrongOutgoingMessageCount() {
        onView(withText("10000"+Analytic.getOutgoingMessageCount(sampleContext))).check(doesNotExist());
    }

    @Test
    public void AnalyticReportPageWrongIncomingMessageCount() {
        onView(withText("10001"+Analytic.getIncomingMessageCount(sampleContext))).check(doesNotExist());
    }

    @Test
    public void AnalyticReportPageWrongLastReceivedMessageCount() {
        onView(withText("s6ugmhncbxfsgr"+Analytic.getLastRecipientSentMessage(sampleContext))).check(doesNotExist());
    }

    @Test
    public void AnalyticReportPageWrongSentMessageCount() {
        onView(withText("s6ugmhncbxfsgr"+Analytic.getLastRecipientReceivedMessage(sampleContext))).check(doesNotExist());
    }

}
