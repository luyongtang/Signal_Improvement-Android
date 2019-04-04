package org.thoughtcrime.securesms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CallLogDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "clog_db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table "+ CallLogContract.MessageEntry.TABLE_NAME
            +"("+ CallLogContract.MessageEntry.ADDRESS_CLOG+" TEXT, "
            + CallLogContract.MessageEntry.TYPE_CLOG +" TEXT, "
            + CallLogContract.MessageEntry.DATE_CLOG +" TEXT);";

    public static final String DROP_TABLE = "drop table if exists "+ CallLogContract.MessageEntry.TABLE_NAME;

    public CallLogDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION );
        Log.d("Call Log", "Database Created for Call Log");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.d("Call Log", "Table Created for Call Log");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);

    }

    public void addCallLog(ContentValues contentValues, SQLiteDatabase database) {

        database.insert(CallLogContract.MessageEntry.TABLE_NAME, null, contentValues);
        Log.d("Call Log Database", "One Row Inserted , Great success");
    }

    public Cursor readAllCallLog(SQLiteDatabase database) {

        String[] projections = {CallLogContract.MessageEntry.ADDRESS_CLOG, CallLogContract.MessageEntry.TYPE_CLOG, CallLogContract.MessageEntry.DATE_CLOG};
        Cursor cursor = database.query(CallLogContract.MessageEntry.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }

    // used for testing
    public void deleteCallLog(SQLiteDatabase database, String address, String date) {

        String selection = CallLogContract.MessageEntry.ADDRESS_CLOG+" = "+address+" AND "+CallLogContract.MessageEntry.DATE_CLOG+" = "+date;
        database.delete(CallLogContract.MessageEntry.TABLE_NAME, selection, null);
        Log.i("CallLogDbHelper", "A call log is deleted");
    }


}
