package org.thoughtcrime.securesms;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import org.thoughtcrime.securesms.database.Address;
import org.thoughtcrime.securesms.jobs.SendReadReceiptJob;
import org.thoughtcrime.securesms.logging.Log;

import java.util.ArrayList;
import java.util.List;

public class ReactionActivity extends AppCompatActivity {
    private ReactionUtil reactUtil;
    private TextView textView;
    private String message;
    private String sentTimeStamp;
    private String reaction_timeStamp;
    private String phoneNumber;
    private RadioButton radioButton;
    private ReactMessageDbHelper db_react;
    private SQLiteDatabase write_database;
    private SQLiteDatabase read_database;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reaction);
        //attributes setup
        message = getIntent().getStringExtra("message");
        sentTimeStamp = getIntent().getStringExtra("date_time");
        phoneNumber = getIntent().getStringExtra("phone_number");
        address = getIntent().getStringExtra("address_serialize");
        // util setup
        reactUtil = new ReactionUtil(getApplicationContext());
        // Setup view attributes
        textView=findViewById(R.id.sample);
        textView.setText(message);
        applyPreviousReaction();
        /*For logging purpose*/
        Log.i("intent_loader",message);
        Log.i("intent_loader", sentTimeStamp);
        Log.i("intent_loader",phoneNumber);
    }

    private void handleSelectedReaction(String reaction){
        //Send reaction
        reactUtil.sendReactionToRecipient(sentTimeStamp, reaction, address, ReactionActivity.this);
        // Save reaction to database
        reactUtil.saveReactionToDatabase(sentTimeStamp, reaction, address);
        //return to previous reaciton
        finish();
    }
    private void applyPreviousReaction(){
        String reaction = reactUtil.getReactionForCurrentMessage(sentTimeStamp);
        if(reaction!=null) {
            int radioId = reactUtil.reactionStringToRadioButtonId(reaction);
            if(radioId>-1){
                radioButton = findViewById(radioId);
                radioButton.setChecked(true);
            }
        }
    }
    // Radio click listener
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        String reaction="";
        handleSelectedReaction(reaction);
    }
}
