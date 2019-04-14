package org.thoughtcrime.securesms;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import org.thoughtcrime.securesms.CallLogDbHelper;

import org.thoughtcrime.securesms.database.Address;

public class CallLogUtil {

    // A helper method to read a call log record from WebRtcCallService and save it in our datatbase
    public static void saveCallInfo(Context context, Address address, String type) {

        // To store a call log record
        ContentValues cLogRecord = new ContentValues();
        cLogRecord.put(CallLogContract.MessageEntry.ADDRESS_CLOG, address.toString());
        cLogRecord.put(CallLogContract.MessageEntry.TYPE_CLOG, type);
        cLogRecord.put(CallLogContract.MessageEntry.DATE_CLOG, System.currentTimeMillis());

        // Write the record to the database using CallLogDbhelper
        CallLogDbHelper callLogDbHelper = new CallLogDbHelper(context);
        SQLiteDatabase database = callLogDbHelper.getWritableDatabase();
        callLogDbHelper.addCallLog(cLogRecord, database);
        callLogDbHelper.close();
    }
}
