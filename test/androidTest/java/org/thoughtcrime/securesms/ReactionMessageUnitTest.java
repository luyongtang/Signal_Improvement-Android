package org.thoughtcrime.securesms;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ReactionMessageUnitTest {
    @Inject
    Context sampleContext;
    @Before
    public void setup(){
        sampleContext = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void noPreviousReactionSaved(){


    }
}
