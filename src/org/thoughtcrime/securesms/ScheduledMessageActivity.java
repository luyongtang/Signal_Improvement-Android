package org.thoughtcrime.securesms;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.thoughtcrime.securesms.database.Address;
import org.thoughtcrime.securesms.recipients.Recipient;
import org.thoughtcrime.securesms.sms.MessageSender;
import org.thoughtcrime.securesms.sms.OutgoingTextMessage;

import java.util.Calendar;

public class ScheduledMessageActivity extends AppCompatActivity {
    TextView display;
    private String  messageBody;
    private Context context;
    private Recipient recipient;
    private long threadId;

    protected void onPreCreate() {
        setContentView(R.layout.activity_scheduled_message);

        display = findViewById(R.id.debug_display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_message);
        display   = findViewById(R.id.debug_display);
        context   = getApplicationContext();
        recipient = Recipient.from(this, Address.fromSerialized(getIntent().getExtras().getSerializable("address").toString()), true);
        threadId  = getIntent().getExtras().getLong("threadId");
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void sendScheduledMessage(View v) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, TimePickerFragment.TIME_HOURS);
        calendar.set(Calendar.MINUTE, TimePickerFragment.TIME_MINUTE);
        calendar.set(Calendar.YEAR, DatePickerFragment.DATE_YEAR);
        calendar.set(Calendar.MONTH, DatePickerFragment.DATE_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, DatePickerFragment.DATE_DAY);

        messageBody = ((EditText) findViewById(R.id.scheduled_body)).getText().toString();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                OutgoingTextMessage message;

                message = new OutgoingTextMessage(recipient, messageBody, -1);

                MessageSender.send(context, message, threadId, false, null);
            }
        }, calendar.getTimeInMillis()-System.currentTimeMillis());

        onBackPressed();
    }
}
