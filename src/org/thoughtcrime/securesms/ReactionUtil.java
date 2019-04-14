package org.thoughtcrime.securesms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.thoughtcrime.securesms.database.Address;
import org.thoughtcrime.securesms.database.MessagingDatabase;
import org.thoughtcrime.securesms.jobs.SendReadReceiptJob;
import org.thoughtcrime.securesms.logging.Log;

import java.util.ArrayList;
import java.util.List;

public class ReactionUtil {
    private ReactMessageDbHelper db_react;
    private SQLiteDatabase write_database;
    private SQLiteDatabase read_database;
    private Context context;

    public ReactionUtil(Context context){
        //Database setup
        this.context = context;
        db_react = new ReactMessageDbHelper(context);
        write_database = db_react.getWritableDatabase();
        read_database = db_react.getReadableDatabase();

    }
    //Retrieve reaction according to the time stamp
    public String getReactionForCurrentMessage(String sentTimeStamp){
        Cursor cursor = db_react.readReaction(read_database, sentTimeStamp);
        if(cursor.getCount()==1){
            cursor.moveToNext();
            return cursor.getString(0);
        }
        return null;
    }
    // Send reaction over sms network
    public void sendReactionToRecipient(String sentTimeStamp, String reaction, String address, Context context){
        //Send reaction
        String reaction_timeStamp= sentTimeStamp.concat(reaction);
        List<Long> timeStampList = new ArrayList<Long>();
        timeStampList.add(Long.parseLong(reaction_timeStamp));
        ApplicationContext.getInstance(context)
                .getJobManager()
                .add(new SendReadReceiptJob(context, Address.fromSerialized(address),timeStampList ));
    }
    // save reaction on local storage
    public void saveReactionToDatabase(String sentTimeStamp, String reaction, String phoneNumber){
        Log.i("timestamp_sent_reaction", sentTimeStamp);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ReactMessageContract.ReactionEntry.DATE_TIME , sentTimeStamp);
        contentValues.put(ReactMessageContract.ReactionEntry.REACTION , reaction);
        contentValues.put(ReactMessageContract.ReactionEntry.PHONE_NUMBER, phoneNumber);
        db_react.saveReaction(contentValues,read_database,write_database);
    }
    // process incoming reaction from network
    public void processIncomingReaction(String emojiProxy, String rawTimeStamp, long type, MessagingDatabase.SyncMessageId messageId, long threadId){
        if ("01".equals(emojiProxy)){
            /*Testing and logging purpose*/
            Log.i("Refresh","Removal Reaction");
            //Remove reaction from database
            removeReaction(rawTimeStamp);
        }
        else{
            /*Testing and logging purpose*/
            Log.i("real_time_stamp", rawTimeStamp);
            Log.i("emoji_proxy", emojiProxy);
            //Save reaction to database
            saveReactionToDatabase(rawTimeStamp, emojiProxy, messageId.getAddress().serialize());
            //Reaction Notification
            ReactionNotification.pushApplicableReactionNotification(context,messageId.getAddress(), type, threadId, emojiProxy);
        }
    }
    public void removeReaction(String sentTimeStamp){
        db_react.deleteReaction(sentTimeStamp, write_database);
    }
    //Adapter method to radio id using reaction
    public int reactionStringToRadioButtonId(String reaction){
        int radio_type = -1;
        //Better code should exists instead of using switch case
        switch(reaction){
            case "10":
                radio_type=R.id.radio_sad;
                break;
            case "11":
                radio_type=R.id.radio_happy;
                break;
            case "12":
                radio_type=R.id.radio_wow;
                break;
            default:
                break;
        }
        return radio_type;
    }
    //Adapter method to adapter id using reaction
    public static int reactionNumberToDrawableId(String reaction){
        int drawable_type = -1;
        //Better code should exists instead of using switch case
        switch(reaction){
            case "10":
                drawable_type=R.drawable.sad;
                break;
            case "11":
                drawable_type=R.drawable.smile;
                break;
            case "12":
                drawable_type=R.drawable.concerned;
                break;
            default:
                break;
        }
        return drawable_type;
    }
}
