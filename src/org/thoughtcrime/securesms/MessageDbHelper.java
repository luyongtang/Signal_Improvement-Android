package org.thoughtcrime.securesms;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MessageDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "star_db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table "+ StarredMessageContract.MessageEntry.TABLE_NAME+
            "("+ StarredMessageContract.MessageEntry.MESSAGE_ID_STAR+" text, "
            + StarredMessageContract.MessageEntry.THREAD_ID_STAR +" text, "
            + StarredMessageContract.MessageEntry.TYPE_STAR  +" text, "
            + StarredMessageContract.MessageEntry.CONTACT +" text, "
            + StarredMessageContract.MessageEntry.MESSAGE_BODY_STAR +" text, "
            + StarredMessageContract.MessageEntry.TIME_STAMP +" text, "
            + StarredMessageContract.MessageEntry.DATE_RECEIVED +" text, "
            + StarredMessageContract.MessageEntry.DATE_SENT +" text);";

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
        System.out.println("DbHelper Test");
        database.insert(StarredMessageContract.MessageEntry.TABLE_NAME, null, contentValues);
        Log.d("Database operations", "One Row Inserted , Great success");

    }

    public Cursor readMessage(SQLiteDatabase database,String threadId)
    {
        String[] projections = {StarredMessageContract.MessageEntry.MESSAGE_BODY_STAR, StarredMessageContract.MessageEntry.TIME_STAMP, StarredMessageContract.MessageEntry.CONTACT};
        Cursor cursor = database.query(true,StarredMessageContract.MessageEntry.TABLE_NAME, projections,StarredMessageContract.MessageEntry.THREAD_ID_STAR + "=" + threadId,null, null, null, StarredMessageContract.MessageEntry.TIME_STAMP+" DESC", null);
        Log.d("readMessage", "Messages are retrieved successfully");
        return cursor;
    }

    // For future use
    public void deleteMessage(SQLiteDatabase database, String msgId, String threadId)
    {
        String selection = StarredMessageContract.MessageEntry.MESSAGE_ID_STAR+" = "+msgId+" AND "+StarredMessageContract.MessageEntry.THREAD_ID_STAR+" = "+threadId;
        Log.d("deleteMessage", selection);
        database.delete(StarredMessageContract.MessageEntry.TABLE_NAME, selection,null);
        Log.d("deleteMessage", "Messages are removed successfully");
    }

    public boolean checkMessageStar(SQLiteDatabase database, String threadId, Long msgId)
    {
        boolean isStarred = false;
        Cursor cursor = database.query(true,StarredMessageContract.MessageEntry.TABLE_NAME, null,StarredMessageContract.MessageEntry.THREAD_ID_STAR + "=" + threadId,null, null, null, StarredMessageContract.MessageEntry.TIME_STAMP+" DESC", null);
        for (int i = 0; i < cursor.getColumnNames().length; i++)
        {
        Log.d("column names", cursor.getColumnNames()[i]);
        }
        while(cursor.moveToNext())
        {
            Long dbMsgId = cursor.getLong(cursor.getColumnIndex(StarredMessageContract.MessageEntry.MESSAGE_ID_STAR));
            //long dbMsgId = Long.parseLong(dbMsgIdStr);
            if (dbMsgId == msgId)
            {
                isStarred = true;
                break;
            }
        }
        return isStarred;
    }

}
