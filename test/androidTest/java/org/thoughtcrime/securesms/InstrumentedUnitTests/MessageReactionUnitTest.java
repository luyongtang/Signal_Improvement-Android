package org.thoughtcrime.securesms.InstrumentedUnitTests;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.ReactionUtil;
import org.thoughtcrime.securesms.crypto.DatabaseSecret;
import org.thoughtcrime.securesms.crypto.DatabaseSecretProvider;
import org.thoughtcrime.securesms.database.Address;
import org.thoughtcrime.securesms.database.MessagingDatabase;
import org.thoughtcrime.securesms.database.SmsDatabase;
import org.thoughtcrime.securesms.database.helpers.SQLCipherOpenHelper;

import java.util.Date;

import javax.inject.Inject;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MessageReactionUnitTest {
    @Inject
    Context sampleContext;
    ReactionUtil reactUtil;
    String sampleSentTimeStamp;
    Long sampleSentTimeStampIncoming;
    SmsDatabase smsDatabase;
    final String sadReactionId = "10";
    final String smileyReactionId = "11";
    final String concernedReactionId = "12";
    final String phoneNumber = "390911404";
    final String phoneNumberIncoming = "6874328369";
    @Before
    public void setup(){
        sampleContext = ApplicationProvider.getApplicationContext();
        //random timeStamp
        Long timeStamp = (new Date()).getTime();
        sampleSentTimeStamp = Long.toString(timeStamp);
        sampleSentTimeStampIncoming = (new Date()).getTime()+100;
        // ReactionUtil is the class containing all methods to handle reactions
        reactUtil = new ReactionUtil(sampleContext);
        //Database used to listen for incoming reaction
        DatabaseSecret databaseSecret   = new DatabaseSecretProvider(sampleContext).getOrCreateDatabaseSecret();
        SQLCipherOpenHelper cipher = new SQLCipherOpenHelper(sampleContext, databaseSecret);
        smsDatabase = new SmsDatabase(sampleContext, cipher);
    }
    @Test
    public void noPreviousReactionSavedOnCurrentMessage(){
        String reaction = reactUtil.getReactionForCurrentMessage(sampleSentTimeStamp);
        //Since no reaction was given to this message, the value returned is null
        assertEquals(reaction,null);
    }
    @Test
    public void adaptersTest(){
        int reactionId;
        //Test if all three reactions are correctly mapped
        //From reaction id to radio id
        reactionId = reactUtil.reactionStringToRadioButtonId(sadReactionId);
        Assert.assertEquals(R.id.radio_sad,reactionId);
        reactionId = reactUtil.reactionStringToRadioButtonId(smileyReactionId);
        assertEquals(R.id.radio_happy,reactionId);
        reactionId = reactUtil.reactionStringToRadioButtonId(concernedReactionId);
        assertEquals(R.id.radio_wow,reactionId);
        //From reaction id to drawable id
        reactionId = reactUtil.reactionNumberToDrawableId(sadReactionId);
        assertEquals(R.drawable.sad,reactionId);
        reactionId = reactUtil.reactionNumberToDrawableId(smileyReactionId);
        assertEquals(R.drawable.smile,reactionId);
        reactionId = reactUtil.reactionNumberToDrawableId(concernedReactionId);
        assertEquals(R.drawable.concerned,reactionId);
    }
    @Test
    public void reactingToAMessageOnce(){
        //save reaction
        reactUtil.saveReactionToDatabase(sampleSentTimeStamp,smileyReactionId,phoneNumber);
        //send reaction over network, should not cause any exception
        reactUtil.sendReactionToRecipient(sampleSentTimeStamp, smileyReactionId, phoneNumber, sampleContext);
        //assert
        String reaction = reactUtil.getReactionForCurrentMessage(sampleSentTimeStamp);
        assertEquals(smileyReactionId,reaction);
    }
    @Test
    public void reactionToAMessageMultipleTime(){
        String reaction;
        /*reacting to the same message multiple times should update the reaction on database*/
        //Reaction 1 : sad reaction
        //save reaction
        reactUtil.saveReactionToDatabase(sampleSentTimeStamp,sadReactionId,phoneNumber);
        //send reaction over network, should not cause any exception
        reactUtil.sendReactionToRecipient(sampleSentTimeStamp, sadReactionId, phoneNumber, sampleContext);
        reaction = reactUtil.getReactionForCurrentMessage(sampleSentTimeStamp);
        //verify is not the last reaction saved
        assertNotEquals(concernedReactionId,reaction);
        //Reaction 2 : happy reaction
        reactUtil.saveReactionToDatabase(sampleSentTimeStamp,smileyReactionId,phoneNumber);
        reactUtil.sendReactionToRecipient(sampleSentTimeStamp, smileyReactionId, phoneNumber, sampleContext);
        reaction = reactUtil.getReactionForCurrentMessage(sampleSentTimeStamp);
        assertNotEquals(concernedReactionId,reaction);
        //Reaction 3 : concerned reaction
        reactUtil.saveReactionToDatabase(sampleSentTimeStamp,concernedReactionId,phoneNumber);
        reactUtil.sendReactionToRecipient(sampleSentTimeStamp, concernedReactionId, phoneNumber, sampleContext);
        //Assert the last reaction made has indeed been updated in the database
        reaction = reactUtil.getReactionForCurrentMessage(sampleSentTimeStamp);
        assertEquals(concernedReactionId,reaction);
    }
    @Test
    public void incomingReactionFromNetwork(){
        //Append reaction (smiley reaction code)
        String appendedReaction = Long.toString(sampleSentTimeStampIncoming)+smileyReactionId;
        //Reaction has been received and being processed
        MessagingDatabase.SyncMessageId messageId = new MessagingDatabase.SyncMessageId(Address.fromSerialized(phoneNumberIncoming), Long.parseLong(appendedReaction));
        //Save the message to Signal database, and the reaction to reaction database
        smsDatabase.incrementReceiptCount(messageId,false,true);
        //retrieve the reaction
        String reaction = reactUtil.getReactionForCurrentMessage(Long.toString(sampleSentTimeStampIncoming));
        assertEquals(smileyReactionId,reaction);
    }
}
