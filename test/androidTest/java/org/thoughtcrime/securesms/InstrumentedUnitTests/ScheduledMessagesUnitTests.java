package org.thoughtcrime.securesms.InstrumentedUnitTests;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thoughtcrime.securesms.DatePickerFragment;
import org.thoughtcrime.securesms.ScheduledMessageActivity;
import org.thoughtcrime.securesms.ScheduledMessageUtil;
import org.thoughtcrime.securesms.TimePickerFragment;
import org.thoughtcrime.securesms.crypto.DatabaseSecret;
import org.thoughtcrime.securesms.crypto.DatabaseSecretProvider;
import org.thoughtcrime.securesms.database.Address;
import org.thoughtcrime.securesms.database.SmsDatabase;
import org.thoughtcrime.securesms.database.helpers.SQLCipherOpenHelper;
import org.thoughtcrime.securesms.recipients.Recipient;

import java.util.Calendar;
import java.util.HashMap;

import javax.inject.Inject;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static org.junit.Assert.assertTrue;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class ScheduledMessagesUnitTests {

    @Inject
    Context mContext;
    ScheduledMessageActivity scheduledMessageActivity;
    SmsDatabase database;
    SQLCipherOpenHelper databaseHelper;
    DatabaseSecret databaseSecret;
    Calendar current;
    String outgoingNum = "5336635678";
    Address address;

    @Before
    public void setUp() throws Exception {
        mContext = ApplicationProvider.getApplicationContext();
        databaseSecret = new DatabaseSecretProvider(mContext).getOrCreateDatabaseSecret();
        databaseHelper = new SQLCipherOpenHelper(mContext, databaseSecret);
        database = new SmsDatabase(mContext,databaseHelper);
        address = Address.fromSerialized(outgoingNum);
        current = Calendar.getInstance();
    }

    @Test
    public void testSendScheduledMessage_PastDate() {
        // input a past date
        TimePickerFragment.TIME_HOURS = current.get(Calendar.HOUR_OF_DAY);
        TimePickerFragment.TIME_MINUTE = current.get(Calendar.MINUTE)+2;
        DatePickerFragment.DATE_YEAR = current.get(Calendar.YEAR)-1;
        DatePickerFragment.DATE_MONTH = current.get(Calendar.MONTH);
        DatePickerFragment.DATE_DAY = current.get(Calendar.DAY_OF_MONTH);
        String threadId = "28";
        Recipient recipient = Recipient.from(mContext,address,true);
        HashMap<String,Boolean> result = ScheduledMessageUtil.sendScheduledMsg("Test",recipient,Long.parseLong(threadId), mContext);
        assertTrue(result.get("pastTime"));
    }

    @Test
    public void testSendScheduledMessage_PastTime() {
        // input a past time
        TimePickerFragment.TIME_HOURS = current.get(Calendar.HOUR_OF_DAY);
        TimePickerFragment.TIME_MINUTE = current.get(Calendar.MINUTE)-2;
        DatePickerFragment.DATE_YEAR = current.get(Calendar.YEAR);
        DatePickerFragment.DATE_MONTH = current.get(Calendar.MONTH);
        DatePickerFragment.DATE_DAY = current.get(Calendar.DAY_OF_MONTH);
        String threadId = "28";
        Recipient recipient = Recipient.from(mContext,address,true);
        HashMap<String,Boolean> result = ScheduledMessageUtil.sendScheduledMsg("Test",recipient,Long.parseLong(threadId), mContext);
        assertTrue(result.get("pastTime"));
    }

    @Test
    public void testSendScheduledMessage_EmptyTime() {
        TimePickerFragment.TIME_HOURS = -1; // empty time
        TimePickerFragment.TIME_MINUTE = 0;
        DatePickerFragment.DATE_YEAR = current.get(Calendar.YEAR);
        DatePickerFragment.DATE_MONTH = current.get(Calendar.MONTH);
        DatePickerFragment.DATE_DAY = current.get(Calendar.DAY_OF_MONTH);
        String threadId = "28";
        Recipient recipient = Recipient.from(mContext,address,true);
        HashMap<String,Boolean> result = ScheduledMessageUtil.sendScheduledMsg("Test",recipient,Long.parseLong(threadId), mContext);
        assertTrue(result.get("timeNull"));
    }

    @Test
    public void testSendScheduledMessage_EmptyDate() {
        TimePickerFragment.TIME_HOURS = current.get(Calendar.HOUR_OF_DAY);
        TimePickerFragment.TIME_MINUTE = current.get(Calendar.MINUTE)+2;
        DatePickerFragment.DATE_YEAR = 0; // empty date
        DatePickerFragment.DATE_MONTH = 0;
        DatePickerFragment.DATE_DAY = 0;
        String threadId = "28";
        Recipient recipient = Recipient.from(mContext,address,true);
        HashMap<String,Boolean> result = ScheduledMessageUtil.sendScheduledMsg("Test",recipient,Long.parseLong(threadId), mContext);
        assertTrue(result.get("dateNull"));
    }

    @Test
    public void testSendScheduledMessage_EmptyMessageBody() {

        TimePickerFragment.TIME_HOURS = current.get(Calendar.HOUR_OF_DAY);
        TimePickerFragment.TIME_MINUTE = current.get(Calendar.MINUTE)+2;
        DatePickerFragment.DATE_YEAR = current.get(Calendar.YEAR);
        DatePickerFragment.DATE_MONTH = current.get(Calendar.MONTH);
        DatePickerFragment.DATE_DAY = current.get(Calendar.DAY_OF_MONTH);
        String threadId = "28";
        Recipient recipient = Recipient.from(mContext,address,true);
        HashMap<String,Boolean> result = ScheduledMessageUtil.sendScheduledMsg("",recipient,Long.parseLong(threadId), mContext);
        assertTrue(result.get("msgNull"));
    }

    @After
    public void tearDown() {
        TimePickerFragment.TIME_HOURS = -1;
        TimePickerFragment.TIME_MINUTE = 0;
        DatePickerFragment.DATE_YEAR = 0;
        DatePickerFragment.DATE_MONTH = 0;
        DatePickerFragment.DATE_DAY = 0;
    }
}