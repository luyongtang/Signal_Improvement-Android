package org.thoughtcrime.securesms;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PreferenceBubbleBodyColorTest {
    @Rule
    //Start application at chosen activity: ApplicationPreferencesActivity
    public ActivityTestRule<ApplicationPreferencesActivity> myActivity = new ActivityTestRule<>(ApplicationPreferencesActivity.class);

    @Test
    public void changeBodyBubbleColorTest(){
        //Your test here
    }
}
