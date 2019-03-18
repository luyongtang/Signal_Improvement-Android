package org.thoughtcrime.securesms;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReactMessageDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "reaction_db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table "+ ReactMessageContract.ReactionEntry.TABLE_NAME+
            "("+ ReactMessageContract.ReactionEntry.REACTION+" text, "
            + ReactMessageContract.ReactionEntry.DATE_TIME +" text, "
            + ReactMessageContract.ReactionEntry.PHONE_NUMBER  +" text);";

    public static final String DROP_TABLE = "drop table if exists "+ ReactMessageContract.ReactionEntry.TABLE_NAME;

    public ReactMessageDbHelper(Context context)
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

    public void addReactionData(ContentValues contentValues, SQLiteDatabase database)
    {
        System.out.println("DbHelper Test");
        database.insert(ReactMessageContract.ReactionEntry.TABLE_NAME, null, contentValues);
        Log.d("Database operations", "One Row Inserted , Great success");

    }

    public Cursor readReaction(SQLiteDatabase database,String date_time)
    {
        String[] projections = {ReactMessageContract.ReactionEntry.REACTION, ReactMessageContract.ReactionEntry.DATE_TIME,ReactMessageContract.ReactionEntry.PHONE_NUMBER};
        Cursor cursor = database.query(true,ReactMessageContract.ReactionEntry.TABLE_NAME, projections,ReactMessageContract.ReactionEntry.DATE_TIME + "=" + date_time,null, null, null, ReactMessageContract.ReactionEntry.REACTION+" DESC", null);
        Log.d("readMessage", "Messages are retrieved successfully");
        return cursor;
    }

    // For future use
    public void deleteReaction(SQLiteDatabase database, String msgId, String threadId)
    {
        String selection = ReactMessageContract.ReactionEntry.DATE_TIME+" = "+msgId+" AND "+ReactMessageContract.ReactionEntry.PHONE_NUMBER+" = "+threadId;
        Log.d("deleteMessage", selection);
        database.delete(ReactMessageContract.ReactionEntry.TABLE_NAME, selection,null);
        Log.d("deleteMessage", "Messages are removed successfully");
    }
}
