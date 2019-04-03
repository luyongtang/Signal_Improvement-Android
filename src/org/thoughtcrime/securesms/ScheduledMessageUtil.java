package org.thoughtcrime.securesms;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;

import org.thoughtcrime.securesms.recipients.Recipient;
import org.thoughtcrime.securesms.sms.MessageSender;
import org.thoughtcrime.securesms.sms.OutgoingTextMessage;

import java.util.Calendar;

public class ScheduledMessageUtil {

    public static boolean sendScheduledMsg(String messageBody, Recipient recipient, Long threadId, Context context) {
        Calendar current = Calendar.getInstance();
        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH);
        int day = current.get(Calendar.DAY_OF_MONTH);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, TimePickerFragment.TIME_HOURS);
        calendar.set(Calendar.MINUTE, TimePickerFragment.TIME_MINUTE);
        calendar.set(Calendar.YEAR, DatePickerFragment.DATE_YEAR);
        calendar.set(Calendar.MONTH, DatePickerFragment.DATE_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, DatePickerFragment.DATE_DAY);

        long delay= calendar.getTimeInMillis() - System.currentTimeMillis();

        boolean dateNull = DatePickerFragment.DATE_YEAR == 0;
        boolean timeNull = TimePickerFragment.TIME_HOURS == 0;
        boolean msgNull = messageBody.isEmpty();

        if (dateNull || timeNull || msgNull) {

            String alertMsg = "Please select the following: \n";
            if(dateNull)
                alertMsg = alertMsg.concat("Date\n");
            if(timeNull)
                alertMsg = alertMsg.concat("Time\n");
            if(msgNull)
                alertMsg = alertMsg.concat("Message\n");

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(alertMsg);
            AlertDialog alert = builder.create();
            alert.show();

        } else if (delay <= 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Time must be in the future");
            AlertDialog alert = builder.create();
            alert.show();

        } else {

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    OutgoingTextMessage message;

                    message = new OutgoingTextMessage(recipient, messageBody, -1);
                    MessageSender.send(context, message, threadId, false, null);
                }
            }, delay);

            return true;

        }

        return false;

    }
}
