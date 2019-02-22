package org.thoughtcrime.securesms;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MessageDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "star_db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table "+ StarredMessageContract.MessageEntry.TABLE_NAME+
            "("+ StarredMessageContract.MessageEntry.MESSAGE_ID_STAR+" text, "+ StarredMessageContract.MessageEntry.THREAD_ID_STAR +" text, "+
            StarredMessageContract.MessageEntry.IS_PUSH_GROUP_STAR +" text, "+ StarredMessageContract.MessageEntry.TYPE_STAR  +" text, "+ StarredMessageContract.MessageEntry.ADDRESS_STAR +" text);";

    public static final String DROP_TABLE = "drop table if exists "+ StarredMessageContract.MessageEntry.TABLE_NAME;

    public MessageDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION );
        Log.d("Database operations", "Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.d("Database operations", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);

    }

    public void addMessage(ContentValues contentValues, SQLiteDatabase database)
    {
        database.insert(StarredMessageContract.MessageEntry.TABLE_NAME, null, contentValues);
        Log.d("Database operations", "One Row Inserted , Great success, hahahahahhahaha");

    }

}
