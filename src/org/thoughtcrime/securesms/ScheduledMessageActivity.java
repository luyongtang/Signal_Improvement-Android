package org.thoughtcrime.securesms;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import org.thoughtcrime.securesms.DatePickerFragment;
import org.thoughtcrime.securesms.TimePickerFragment;
import org.thoughtcrime.securesms.logging.Log;
import org.thoughtcrime.securesms.sms.OutgoingEncryptedMessage;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ScheduledMessageActivity extends AppCompatActivity {
    TextView display;

  /*  private final DynamicTheme    dynamicTheme    = new DynamicTheme();
    private final DynamicLanguage dynamicLanguage = new DynamicLanguage();*/

    protected void onPreCreate() {
       /* dynamicTheme.onCreate(this);
        dynamicLanguage.onCreate(this);*/
        setContentView(R.layout.activity_scheduled_message);

        display = findViewById(R.id.debug_display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_message);
        display = findViewById(R.id.debug_display);
    }

    @Override
    protected void onResume(){
        super.onResume();
        /*dynamicTheme.onCreate(this);
        dynamicLanguage.onCreate(this);*/
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @TargetApi(19)
    public void setAlarm(View v) {
        AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, TimePickerFragment.TIME_HOURS);
        calendar.set(Calendar.MINUTE, TimePickerFragment.TIME_MINUTE);
        calendar.set(Calendar.YEAR, DatePickerFragment.DATE_YEAR);
        calendar.set(Calendar.MONTH, DatePickerFragment.DATE_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, DatePickerFragment.DATE_DAY);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sendMessage());
    }

    public PendingIntent sendMessage() {
        TextView textview = this.findViewById(R.id.dateDisplay);
        textview.setText("SUCCESS");
    return null;
    }


}
