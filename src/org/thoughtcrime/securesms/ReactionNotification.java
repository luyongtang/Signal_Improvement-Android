package org.thoughtcrime.securesms;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import org.thoughtcrime.securesms.database.Address;
import org.thoughtcrime.securesms.database.MmsSmsColumns;
import org.thoughtcrime.securesms.logging.Log;
import org.thoughtcrime.securesms.recipients.Recipient;

import java.util.List;

public class ReactionNotification {

    public static void pushApplicableReactionNotification(Context context, Address address, long type, long threadId, String reaction){
        //No notification if app if is on view or reaction made to own message (for further precision, adjust according to the conversation activity)
        if(isSignalOnForeground(context) || !MmsSmsColumns.Types.isOutgoingMessageType(type)) return;
        Recipient recipient = Recipient.from(context, address, true);
        //Create ConversationAcitivty values
        Intent intent = new Intent(context, ConversationActivity.class);
        intent.putExtra(ConversationActivity.ADDRESS_EXTRA,address);
        intent.putExtra(ConversationActivity.TIMING_EXTRA, System.currentTimeMillis());
        intent.putExtra(ConversationActivity.DISTRIBUTION_TYPE_EXTRA, (int) type);
        intent.putExtra(ConversationActivity.THREAD_ID_EXTRA, threadId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //Create the notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder .setContentTitle(recipient.getName())
                .setContentText("Reacted with "+reactionStringIdToText(reaction)+ " to your message")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.icon_notification)
                .setContentIntent(pendingIntent);
        //Send notification to system
        NotificationManagerCompat.from(context).notify(123, notificationBuilder.build());
    }
    private static boolean isSignalOnForeground(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        Log.i("Package Name", componentInfo.getPackageName());
        if (componentInfo.getPackageName().equalsIgnoreCase("org.thoughtcrime.securesms")) {
            return true;
        } else {
            return false;
        }
    }
    //Adapter method to text reaction reaction for notication
    private static String reactionStringIdToText(String reaction){
        String react = "";
        //Better code should exists instead of using switch case
        switch(reaction){
            case "10":
                react="SAD";
                break;
            case "11":
                react="HAPPY";
                break;
            case "12":
                react="WOW";
                break;
        }
        return react;
    }
}
