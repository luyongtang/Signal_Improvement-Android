package org.thoughtcrime.securesms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.thoughtcrime.securesms.logging.Log;

public class ReactionActivity extends AppCompatActivity {
    private ReactionUtil reactUtil;
    private TextView textView;
    private String message;
    private String sentTimeStamp;
    private String phoneNumber;
    private RadioButton radioButton;
    private Button  removeReactionButton;
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
        removeReactionButton = findViewById(R.id.removeReaction);
        applyPreviouslySavedReactionOnView();
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
        //return to previous reaction
        finish();
    }
    private void applyPreviouslySavedReactionOnView(){
        String reaction = reactUtil.getReactionForCurrentMessage(sentTimeStamp);
        if(reaction!=null) {
            int radioId = reactUtil.reactionStringToRadioButtonId(reaction);
            if(radioId>-1){
                radioButton = findViewById(radioId);
                radioButton.setChecked(true);
                removeReactionButton.setVisibility(View.VISIBLE);
            }
        }
    }
    // Radio click listener
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        String reaction="";
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_sad:
                if (checked)
                    reaction="10";
                break;
            case R.id.radio_happy:
                if (checked)
                    reaction="11";
                break;
            case R.id.radio_wow:
                if (checked)
                    reaction="12";
                break;
        }
        handleSelectedReaction(reaction);
    }
    // Remove button listener
    public void onRemoveButtonClicked(View view) {
        //Send updated reaction (remove)
        reactUtil.sendReactionToRecipient(sentTimeStamp, "01", address, ReactionActivity.this);
        //Remove reaction from database
        reactUtil.removeReaction(sentTimeStamp);
        finish();
    }
}
