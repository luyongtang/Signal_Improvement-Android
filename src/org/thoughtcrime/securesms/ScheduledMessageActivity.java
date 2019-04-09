package org.thoughtcrime.securesms;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.thoughtcrime.securesms.database.Address;
import org.thoughtcrime.securesms.recipients.Recipient;

import java.util.HashMap;

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
        initPickersData();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        initPickersData();
        super.onBackPressed();
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

        HashMap<String,Boolean> tmp = ScheduledMessageUtil.sendScheduledMsg(messageBody,recipient, threadId, this);

        if (tmp.get("success")){
            onBackPressed();
            Log.d("yongtang","success");
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if (tmp.get("pastTime")) {
                builder.setMessage("Time must be in the future");
                Log.d("yongtang","pastTime");
                AlertDialog alert = builder.create();
                alert.show();
            }
            else {
                String alertMsg = "Please select the following: \n";
                if (tmp.get("dateNull")) {
                    alertMsg = alertMsg.concat("Date\n");
                    Log.d("yongtang","dateNull");
                }
                if(tmp.get("timeNull")) {
                    alertMsg = alertMsg.concat("Time\n");
                    Log.d("yongtang","timeNull");
                }
                if(tmp.get("msgNull")) {
                    alertMsg = alertMsg.concat("Message\n");
                    Log.d("yongtang","msgNull");
                }
                builder.setMessage(alertMsg);
                Log.d("yongtang",alertMsg);
                AlertDialog alert = builder.create();
                alert.show();
                Log.d("yongtang","alert show");
            }

        }

    }

    private void initPickersData() {
        TimePickerFragment.TIME_HOURS = -1;
        TimePickerFragment.TIME_MINUTE = 0;
        DatePickerFragment.DATE_YEAR = 0;
        DatePickerFragment.DATE_MONTH = 0;
        DatePickerFragment.DATE_DAY = 0;
    }
}
