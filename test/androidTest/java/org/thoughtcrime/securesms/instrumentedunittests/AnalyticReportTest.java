package org.thoughtcrime.securesms.instrumentedunittests;


import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thoughtcrime.securesms.util.Analytic;

import javax.inject.Inject;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AnalyticReportTest {

    @Inject
    Context sampleContext;

    @Before
    public void setup(){
        sampleContext = ApplicationProvider.getApplicationContext();
    }
    @Test
    public void saveSentMessageCountTest(){
        //Get latest count of sent messages
        long sent_count = Analytic.getOutgoingMessageCount(sampleContext);
        //A message is sent, and recorded by Analytic class
        Analytic.increaseOutgoingMessageCountByOne(sampleContext);
        // expected vs actual
        long expected_count = sent_count+1;
        long actual_count = Analytic.getOutgoingMessageCount(sampleContext);
        assertEquals(expected_count,actual_count);
    }
    @Test
    public void saveReceivedMessageCountTest(){
        //Get latest count of received messages
        long received_count = Analytic.getIncomingMessageCount(sampleContext);
        //A message is sent, and recorded by Analytic class
        Analytic.increaseIncomingMessageCountByOne(sampleContext);
        // expected vs actual
        long expected_count = received_count+1;
        long actual_count = Analytic.getIncomingMessageCount(sampleContext);
        assertEquals(expected_count,actual_count);
    }
    @Test
    public void setLastContactSentMessageTest(){
        //A previously contact to whom a message was sent
        String nameOfContactPreviously = "Michel Garcier";
        Analytic.setLastRecipientSentMessage(sampleContext,nameOfContactPreviously);
        //The new contact to whom a message will be sent
        String nameOfContactRecent = "Levier Framboise";
        //previously saved contact name VS the new unsaved contact
        String nameOfContactPreviously_saved = Analytic.getLastRecipientSentMessage(sampleContext);
        assertNotEquals(nameOfContactPreviously_saved,nameOfContactRecent);
        //Save the new contact to whom a message was sent
        Analytic.setLastRecipientSentMessage(sampleContext,nameOfContactRecent);
        // expected vs actual
        String actual_contactName = Analytic.getLastRecipientSentMessage(sampleContext);
        assertEquals(nameOfContactRecent,actual_contactName);
    }
    @Test
    public void setLastContactReceivedMessageTest(){
        //A previously contact from whom a message was received
        String nameOfContactPreviously = "Jack Antrium";
        Analytic.setLastRecipientReceivedMessage(sampleContext,nameOfContactPreviously);
        //The new contact from whom a message will be received
        String nameOfContactRecent = "Samantha Perly";
        //previously saved contact name VS the new unsaved contact
        String nameOfContactPreviously_saved = Analytic.getLastRecipientReceivedMessage(sampleContext);
        assertNotEquals(nameOfContactPreviously_saved,nameOfContactRecent);
        //Save the new contact from whom a message was received
        Analytic.setLastRecipientReceivedMessage(sampleContext,nameOfContactRecent);
        // expected vs actual
        String actual_contactName = Analytic.getLastRecipientReceivedMessage(sampleContext);
        assertEquals(nameOfContactRecent,actual_contactName);
    }

}
