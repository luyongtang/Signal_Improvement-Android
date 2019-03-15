package org.thoughtcrime.securesms.util;


import android.content.Context;
import android.content.SharedPreferences;
import org.thoughtcrime.securesms.R;

public class Analytic{
    private static String outgoing_message_count = "outgoingMessagesCount";
    private static String incoming_message_count = "incomingMessagesCount";
    private static String last_recipient_sent_message= "lastRecipientSentMessage";
    private static String last_recipient_received_message= "lastRecipientReceivedMessage";

    // Last person the message was sent
    public static void setLastRecipientSentMessage(Context context, String value){
        putStringCommit(context,last_recipient_sent_message,value);
    }
    public static String getLastRecipientSentMessage(Context context){
        return getString(context, last_recipient_sent_message,"Not Recorded Yet");
    }
    // Last person the message was received
    public static void setLastRecipientReceivedMessage(Context context, String value){
        putStringCommit(context,last_recipient_received_message,value);
    }
    public static String getLastRecipientReceivedMessage(Context context){
        return getString(context, last_recipient_received_message,"Not Recorded Yet");
    }
    //incoming messages count
    public static void increaseIncomingMessageCountByOne(Context context){
        Long count = getLong(context, incoming_message_count, (long) 0);
        putLongCommit(context, incoming_message_count ,++count);
    }
    public static long getIncomingMessageCount(Context context){
        return getLong(context, incoming_message_count, (long) 0);
    }
    //outgoing messages count
    public static void increaseOutgoingMessageCountByOne(Context context){
        Long count = getLong(context, outgoing_message_count, (long) 0);
        putLongCommit(context, outgoing_message_count ,++count);
    }
    public static long getOutgoingMessageCount(Context context){
        return getLong(context, outgoing_message_count, (long) 0);
    }
    //Long:number Stats
    private static void putLongCommit(Context context, String key, Long value){
        SharedPreferences.Editor edit = getEditor(context);
        edit.putLong(key,value);
        edit.commit();
    }
    private static Long getLong(Context context, String key, Long default_){
        SharedPreferences share = getPreferences(context);
        return share.getLong(key,default_);
    }
    //String Stats
    private static void putStringCommit(Context context, String key, String value){
        SharedPreferences.Editor edit = getEditor(context);
        edit.putString(key,value);
        edit.commit();
    }
    private static String getString(Context context, String key,String default_){
        SharedPreferences share = getPreferences(context);
        return share.getString(key,default_);
    }
    // Using Android SharePreferences API to save the stats
    private static SharedPreferences.Editor getEditor(Context context){
        SharedPreferences share = getPreferences(context);
        SharedPreferences.Editor edit = share.edit();
        return edit;
    }
    private static SharedPreferences getPreferences(Context context){
        String preference_key = context.getResources().getString(R.string.analytic_key);
        return context.getSharedPreferences(preference_key, Context.MODE_PRIVATE);
    }
}
