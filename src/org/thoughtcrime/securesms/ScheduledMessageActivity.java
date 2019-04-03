package org.thoughtcrime.securesms;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
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


        messageBody = ((EditText) findViewById(R.id.scheduled_body)).getText().toString();

        boolean tmp = ScheduledMessageUtil.sendScheduledMsg(messageBody,recipient, threadId, this);

        if (tmp) {
            onBackPressed();
        }
    }
}
