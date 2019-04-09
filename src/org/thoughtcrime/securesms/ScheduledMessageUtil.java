package org.thoughtcrime.securesms;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import org.thoughtcrime.securesms.recipients.Recipient;
import org.thoughtcrime.securesms.sms.MessageSender;
import org.thoughtcrime.securesms.sms.OutgoingTextMessage;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

public class ScheduledMessageUtil {

    public static HashMap sendScheduledMsg(String messageBody, Recipient recipient, Long threadId, Context context) {
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

        Log.d("yongtang","year is: "+DatePickerFragment.DATE_YEAR);
        Log.d("yongtang","month is: "+DatePickerFragment.DATE_MONTH);
        Log.d("yongtang","day is: "+DatePickerFragment.DATE_DAY);
        Log.d("yongtang","hour is: "+TimePickerFragment.TIME_HOURS);
        Log.d("yongtang","minute is: "+TimePickerFragment.TIME_MINUTE);
        boolean dateNull = (DatePickerFragment.DATE_YEAR == 0 && DatePickerFragment.DATE_MONTH == 0 && DatePickerFragment.DATE_DAY == 0);
        boolean timeNull = (TimePickerFragment.TIME_HOURS == -1 && TimePickerFragment.TIME_MINUTE == 0);
        boolean msgNull = messageBody.isEmpty();
        HashMap<String,Boolean> results = new HashMap<>();
        results.put("dateNull",dateNull);
        results.put("timeNull",timeNull);
        results.put("msgNull",msgNull);
        results.put("pastTime",false);
        results.put("success",false);

        if (dateNull || timeNull || msgNull) {
            return results;
        }
        if (delay <= 0) {
            results.put("pastTime",true);

        }
        else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    OutgoingTextMessage message;

                    message = new OutgoingTextMessage(recipient, messageBody, -1);
                    MessageSender.send(context, message, threadId, false, null);
                }
            }, delay);
            results.put("success",true);
        }

        return results;
    }
}
