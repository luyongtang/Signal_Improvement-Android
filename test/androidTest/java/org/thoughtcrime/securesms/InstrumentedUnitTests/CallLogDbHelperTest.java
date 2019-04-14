package org.thoughtcrime.securesms.InstrumentedUnitTests;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thoughtcrime.securesms.CallLogContract;
import org.thoughtcrime.securesms.CallLogDbHelper;
import org.thoughtcrime.securesms.database.Address;

import javax.inject.Inject;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CallLogDbHelperTest {

    @Inject
    Context mContext;

    CallLogDbHelper callLogDbHelper;
    ContentValues outgoingRecord;
    ContentValues incomingRecord;
    ContentValues missedRecord;
    ContentValues nullRecord;
    SQLiteDatabase writableDatabase;
    SQLiteDatabase readableDatabase;

    @Before
    public void setUp() throws Exception {
        mContext = ApplicationProvider.getApplicationContext();
        callLogDbHelper = new CallLogDbHelper(mContext);
        writableDatabase = callLogDbHelper.getWritableDatabase();
        readableDatabase = callLogDbHelper.getReadableDatabase();
        outgoingRecord = new ContentValues();
        incomingRecord = new ContentValues();
        missedRecord = new ContentValues();
        nullRecord = new ContentValues();

        // Generate fake addresses
        String num = "5336635678";
        Address a1 = Address.fromSerialized(num);
        num = "3256568836";
        Address a2 = Address.fromSerialized(num);

        // input dummy data

        outgoingRecord.put(CallLogContract.MessageEntry.ADDRESS_CLOG, a1.toString());
        outgoingRecord.put(CallLogContract.MessageEntry.TYPE_CLOG, "OUTGOING");
        outgoingRecord.put(CallLogContract.MessageEntry.DATE_CLOG, System.currentTimeMillis());

        incomingRecord.put(CallLogContract.MessageEntry.ADDRESS_CLOG, a1.toString());
        incomingRecord.put(CallLogContract.MessageEntry.TYPE_CLOG, "INCOMING");
        incomingRecord.put(CallLogContract.MessageEntry.DATE_CLOG, System.currentTimeMillis());

        missedRecord.put(CallLogContract.MessageEntry.ADDRESS_CLOG, a2.toString());
        missedRecord.put(CallLogContract.MessageEntry.TYPE_CLOG, "Missed");
        missedRecord.put(CallLogContract.MessageEntry.DATE_CLOG, System.currentTimeMillis());

        // first clear all the old data for testing
        callLogDbHelper.deleteAllCallLogs(writableDatabase);


    }

    @After
    public void tearDown() throws Exception {
        callLogDbHelper.close();
    }

    @Test
    public void addOutgoingCallLog() {

        callLogDbHelper.addCallLog(outgoingRecord, writableDatabase);
        Cursor cursor = callLogDbHelper.readAllCallLog(readableDatabase);

        // the number of call log records should be one
        assertEquals(1, cursor.getCount());

        // the type of record should be "OUTGOING"
        if (cursor.moveToNext())
        {
            String type = cursor.getString(cursor.getColumnIndex(CallLogContract.MessageEntry.TYPE_CLOG));
            assertEquals("OUTGOING", type);
        }

        // clean up the test data
        callLogDbHelper.deleteAllCallLogs(writableDatabase);
    }

    @Test
    public void addIncomingCallLog() {

        callLogDbHelper.addCallLog(incomingRecord, writableDatabase);
        Cursor cursor = callLogDbHelper.readAllCallLog(readableDatabase);

        // the number of call log records should be one
        assertEquals(1, cursor.getCount());

        // the type of record should be "INCOMING"
        if (cursor.moveToNext())
        {
            String type = cursor.getString(cursor.getColumnIndex(CallLogContract.MessageEntry.TYPE_CLOG));
            assertEquals("INCOMING", type);
        }

        // clean up the test data
        callLogDbHelper.deleteAllCallLogs(writableDatabase);
    }

    @Test
    public void addMissedCallLog() {

        callLogDbHelper.addCallLog(missedRecord, writableDatabase);
        Cursor cursor = callLogDbHelper.readAllCallLog(readableDatabase);

        // the number of call log records should be one
        assertEquals(1, cursor.getCount());

        // the type of record should be "MISSED"
        if (cursor.moveToNext())
        {
            String type = cursor.getString(cursor.getColumnIndex(CallLogContract.MessageEntry.TYPE_CLOG));
            assertEquals("Missed", type);
        }

        // clean up the test data
        callLogDbHelper.deleteAllCallLogs(writableDatabase);
    }

    @Test
    public void readAllCallLog() {

        // add multiple call log records
        callLogDbHelper.addCallLog(outgoingRecord, writableDatabase);
        callLogDbHelper.addCallLog(incomingRecord, writableDatabase);
        callLogDbHelper.addCallLog(missedRecord, writableDatabase);
        Cursor cursor = callLogDbHelper.readAllCallLog(readableDatabase);

        // the number of call log records should be three
        assertEquals(3, cursor.getCount());

        // clean up the test data
        callLogDbHelper.deleteAllCallLogs(writableDatabase);
    }

    @Test
    public void deleteAllCallLogs() {

        // add multiple call log records
        callLogDbHelper.addCallLog(outgoingRecord, writableDatabase);
        callLogDbHelper.addCallLog(incomingRecord, writableDatabase);
        callLogDbHelper.addCallLog(missedRecord, writableDatabase);
        Cursor cursor = callLogDbHelper.readAllCallLog(readableDatabase);

        // the number of call log records should be three
        assertEquals(3, cursor.getCount());

        // clean up the test data
        callLogDbHelper.deleteAllCallLogs(writableDatabase);

        // the number of records should be zero after the delete operation
        cursor = callLogDbHelper.readAllCallLog(readableDatabase);
        assertEquals(0, cursor.getCount());
    }

    @Test
    public void addNullRecord() {

        callLogDbHelper.addCallLog(nullRecord, writableDatabase);
        Cursor cursor = callLogDbHelper.readAllCallLog(readableDatabase);

        // null record would not be saved in the database
        // the number of call log records should be zero
        assertEquals(0, cursor.getCount());
    }

    @Test
    public void deleteOneRecord () {

        // add multiple call log records
        callLogDbHelper.addCallLog(outgoingRecord, writableDatabase);
        callLogDbHelper.addCallLog(incomingRecord, writableDatabase);
        callLogDbHelper.addCallLog(missedRecord, writableDatabase);
        Cursor cursor = callLogDbHelper.readAllCallLog(readableDatabase);

        // the number of call log records should be three
        assertEquals(3, cursor.getCount());

        // delete the missed call record
        callLogDbHelper.deleteCallLog(writableDatabase,missedRecord.get(CallLogContract.MessageEntry.ADDRESS_CLOG).toString(),missedRecord.get(CallLogContract.MessageEntry.DATE_CLOG).toString());
        cursor = callLogDbHelper.readAllCallLog(readableDatabase);
        assertEquals(2, cursor.getCount());

        // clean up the test data
        callLogDbHelper.deleteAllCallLogs(writableDatabase);
    }
}