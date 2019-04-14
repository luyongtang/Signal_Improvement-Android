package org.thoughtcrime.securesms.instrumentedunittests;

import android.content.Context;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thoughtcrime.securesms.BroadcastUtil;

import org.thoughtcrime.securesms.database.Address;
import org.thoughtcrime.securesms.recipients.Recipient;
import org.thoughtcrime.securesms.util.SelectedRecipientsAdapter;

import javax.inject.Inject;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BroadcastMessageUnitTest {
    @Inject
    private BroadcastUtil broadcastUtil;
    private Context context;
    private SelectedRecipientsAdapter selectedRecipients;
    private Recipient steve;
    private Recipient melissa;
    private String messageBody="Getting there in 5 min";
    private String emptyMessageBody = "";

    @Before
    public void setup(){
        context = ApplicationProvider.getApplicationContext();
        broadcastUtil = new BroadcastUtil(context);
        selectedRecipients = new SelectedRecipientsAdapter(context);
        steve = Recipient.from(context, Address.fromSerialized("1237891234"),false);
        melissa = Recipient.from(context, Address.fromSerialized("78912356789"),false);
    }
    @Test
    public void noMessageBodyAndRecipientsSelected() {
        boolean success = broadcastUtil.sendBroadcastMessage(emptyMessageBody, selectedRecipients);
        //build the expected error message
        String errorMessage = BroadcastUtil.DEFAULT_ERROR+" "+BroadcastUtil.STATUS_MESSAGE+" "+BroadcastUtil.STATUS_CONTACTS;
        assertFalse(success);
        assertEquals(errorMessage,broadcastUtil.getLastOperationStatus());
    }
    @Test
    public void noRecipientsSelected() {
        boolean success = broadcastUtil.sendBroadcastMessage(messageBody, selectedRecipients);
        //build the expected error message
        String errorMessage = BroadcastUtil.DEFAULT_ERROR+" "+BroadcastUtil.STATUS_CONTACTS;
        assertFalse(success);
        assertEquals(errorMessage,broadcastUtil.getLastOperationStatus());
    }
    @Test
    public void noMessageBody() {
        selectedRecipients.add(steve,true);
        boolean success = broadcastUtil.sendBroadcastMessage(emptyMessageBody, selectedRecipients);
        //build the expected error message
        String errorMessage = BroadcastUtil.DEFAULT_ERROR+" "+BroadcastUtil.STATUS_MESSAGE;
        assertFalse(success);
        assertEquals(errorMessage,broadcastUtil.getLastOperationStatus());
    }
    @Test
    public void selectedOneRecipient() {
        selectedRecipients.add(steve,true);
        boolean success = broadcastUtil.sendBroadcastMessage(messageBody, selectedRecipients);
        assertTrue(success);
        assertEquals(BroadcastUtil.STATUS_SENT,broadcastUtil.getLastOperationStatus());
    }
    @Test
    public void multipleSelectedRecipient() {
        selectedRecipients.add(steve,true);
        selectedRecipients.add(melissa,true);
        boolean success = broadcastUtil.sendBroadcastMessage(messageBody, selectedRecipients);
        assertTrue(success);
        assertEquals(BroadcastUtil.STATUS_SENT,broadcastUtil.getLastOperationStatus());
    }
}
