package org.thoughtcrime.securesms.instrumentedunittests;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thoughtcrime.securesms.CallLogContract;
import org.thoughtcrime.securesms.CallLogDbHelper;
import org.thoughtcrime.securesms.CallLogUtil;
import org.thoughtcrime.securesms.database.Address;

import javax.inject.Inject;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CallLogUtilUnitTests {

    @Inject
    private Context mContext;

    private CallLogDbHelper callLogDbHelper;
    private SQLiteDatabase writableDatabase;
    private SQLiteDatabase readableDatabase;

    private Address outgoingAddress;
    private Address incomingAddress;
    private Address missedAddress;

    @Before
    public void setUp() throws Exception {
        mContext = ApplicationProvider.getApplicationContext();
        callLogDbHelper = new CallLogDbHelper(mContext);
        writableDatabase = callLogDbHelper.getWritableDatabase();
        readableDatabase = callLogDbHelper.getReadableDatabase();

        // Generate fake addresses
        String num = "5336635678";
        outgoingAddress = Address.fromSerialized(num);
        num = "3256568836";
        incomingAddress = Address.fromSerialized(num);
        num = "4537215899";
        missedAddress = Address.fromSerialized(num);

        // first clear all the old data for testing
        callLogDbHelper.deleteAllCallLogs(writableDatabase);
    }

    @After
    public void tearDown() throws Exception {
        callLogDbHelper.deleteAllCallLogs(writableDatabase);
        callLogDbHelper.close();
    }

    @Test
    public void saveOutgoingCallInfo() {

        CallLogUtil.saveCallInfo(mContext, outgoingAddress, "OUTGOING");
        Cursor cursor = callLogDbHelper.readAllCallLog(readableDatabase);

        // the number of call log records should be one
        assertEquals(1, cursor.getCount());

        // the type of record should be "OUTGOING"
        if (cursor.moveToNext())
        {
            String type = cursor.getString(cursor.getColumnIndex(CallLogContract.MessageEntry.TYPE_CLOG));
            assertEquals("OUTGOING", type);
        }
    }

    @Test
    public void saveIncomingCallInfo() {

        CallLogUtil.saveCallInfo(mContext, incomingAddress, "INCOMING");
        Cursor cursor = callLogDbHelper.readAllCallLog(readableDatabase);

        // the number of call log records should be one
        assertEquals(1, cursor.getCount());

        // the type of record should be "INCOMING"
        if (cursor.moveToNext())
        {
            String type = cursor.getString(cursor.getColumnIndex(CallLogContract.MessageEntry.TYPE_CLOG));
            assertEquals("INCOMING", type);
        }
    }

    @Test
    public void saveMissedCallInfo() {

        CallLogUtil.saveCallInfo(mContext, missedAddress, "MISSED");
        Cursor cursor = callLogDbHelper.readAllCallLog(readableDatabase);

        // the number of call log records should be one
        assertEquals(1, cursor.getCount());

        // the type of record should be "OUTGOING"
        if (cursor.moveToNext())
        {
            String type = cursor.getString(cursor.getColumnIndex(CallLogContract.MessageEntry.TYPE_CLOG));
            assertEquals("MISSED", type);
        }
    }
}