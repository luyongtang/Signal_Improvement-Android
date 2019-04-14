package org.thoughtcrime.securesms;

import android.content.Context;

import org.thoughtcrime.securesms.database.DatabaseFactory;
import org.thoughtcrime.securesms.logging.Log;
import org.thoughtcrime.securesms.recipients.Recipient;
import org.thoughtcrime.securesms.sms.MessageSender;
import org.thoughtcrime.securesms.sms.OutgoingTextMessage;
import org.thoughtcrime.securesms.util.SelectedRecipientsAdapter;

public class BroadcastUtil {
    public static final String DEFAULT_ERROR = "Please enter the following:\n";
    public static final String STATUS_CONTACTS = "Contacts";
    public static final String STATUS_MESSAGE = "Message";
    public static final String STATUS_SENT = "Message sent successfully";
    private Context context;
    private String lastOperationStatus;

    public BroadcastUtil(Context context){
        this.context = context;
        this.lastOperationStatus="";
    }

    public boolean sendBroadcastMessage(String messageBody, SelectedRecipientsAdapter adapter){
        //Reset status
        lastOperationStatus="";
        boolean msgNull = messageBody.isEmpty();
        boolean recipientsNotSelected = adapter.getCount() < 1;
        Log.i("broadcastState","count "+adapter.getCount());

        if( !(msgNull || recipientsNotSelected) ){
            sendMessageToRecipients(messageBody,adapter);
            lastOperationStatus = STATUS_SENT;
            return true;
        }
        lastOperationStatus +=DEFAULT_ERROR;
        if (msgNull) lastOperationStatus +=" "+STATUS_MESSAGE;
        if (recipientsNotSelected) lastOperationStatus +=" "+STATUS_CONTACTS;
        return false;

    }
    public String getLastOperationStatus(){
        return lastOperationStatus;
    }
    private void sendMessageToRecipients(String messageBody, SelectedRecipientsAdapter adapter){
        for (Recipient recipient : adapter.getRecipients()) {
            OutgoingTextMessage message;
            long threadId;

            message = new OutgoingTextMessage(recipient, messageBody, -1);
            threadId = DatabaseFactory.getThreadDatabase(context).getThreadIdIfExistsFor(recipient);

            if (threadId != -1L){
                MessageSender.send(context, message, threadId, false, null);
            } else {
                MessageSender.send(context, message, threadId, true, null);
            }
        }
    }
}
