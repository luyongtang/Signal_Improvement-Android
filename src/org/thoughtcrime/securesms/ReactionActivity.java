package org.thoughtcrime.securesms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.thoughtcrime.securesms.logging.Log;

public class ReactionActivity extends AppCompatActivity {
    private ReactionUtil reactUtil;
    private String sentTimeStamp;
    private RadioButton radioButton;
    private Button  removeReactionButton;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reaction);
        //attributes setup
        String message = getIntent().getStringExtra("message");
        sentTimeStamp = getIntent().getStringExtra("date_time");
        String phoneNumber = getIntent().getStringExtra("phone_number");
        address = getIntent().getStringExtra("address_serialize");
        // util setup
        reactUtil = new ReactionUtil(getApplicationContext());
        // Setup view attributes
        TextView textView=findViewById(R.id.sample);
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

    public void onRadioButtonClicked(View view) {
        switch(view.getId()) {
            case R.id.radio_sad:
                handleSelectedReaction("10");
                break;
            case R.id.radio_happy:
                handleSelectedReaction("11");
                break;
            case R.id.radio_wow:
                handleSelectedReaction("12");
                break;
            default: {
                handleSelectedReaction("01");
                break;
            }
        }
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
