package org.thoughtcrime.securesms;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
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
    private TextView textView;
    private String message;
    private String timeStamp;
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
        //Database setup
        db_react = new ReactMessageDbHelper(getApplicationContext());
        write_database = db_react.getWritableDatabase();
        read_database = db_react.getReadableDatabase();
        //attributes setup
        message = getIntent().getStringExtra("message");
        timeStamp = getIntent().getStringExtra("date_time");
        phoneNumber = getIntent().getStringExtra("phone_number");
        address = getIntent().getStringExtra("address_serialize");
        /*Log.i("address printing Manpreeeeet",address);
        if(address1==null) Log.i("Manpreeeet","NULLLLLLL");
        else Log.i("Manpreeeet","NOTTTTT NULLLLLLL");
        */
        // Setup view attributes
        textView=findViewById(R.id.sample);
        textView.setText(message);
        applyPreviousReaction();
        //Simple test of database for loggin purpose
        Cursor cursor = db_react.readReaction(read_database, timeStamp);
        Log.i("data_raw", Integer.toString(cursor.getCount()));
        while(cursor.moveToNext()){
            Log.i("data_raw",cursor.getString(1));
            Log.i("data_raw",cursor.getString(0));
            Log.i("data_raw",cursor.getString(2));
        }
        Log.i("intent_loader",message);
        Log.i("intent_loader",timeStamp);
        Log.i("intent_loader",phoneNumber);
    }

    private void handleSelectedReaction(String reaction){
        /*Log.i("I break here ","BOOOOOOOM");
        Log.i("i am the address to be sent ",address1.serialize());
        */
        //Send reaction
       reaction_timeStamp=timeStamp.concat(reaction);
       List<Long> timeStampList = new ArrayList<Long>();
       timeStampList.add(Long.parseLong(reaction_timeStamp));
       ApplicationContext.getInstance(ReactionActivity.this)
                .getJobManager()
                .add(new SendReadReceiptJob(ReactionActivity.this, Address.fromSerialized(address),timeStampList ));
        // Save reaction to database
        ContentValues contentValues = new ContentValues();
        Log.i("timestamp_sent_reaction",timeStamp);
        contentValues.put(ReactMessageContract.ReactionEntry.DATE_TIME ,  timeStamp);
        contentValues.put(ReactMessageContract.ReactionEntry.PHONE_NUMBER, phoneNumber);
        contentValues.put(ReactMessageContract.ReactionEntry.REACTION , reaction);
        db_react.saveReaction(contentValues,read_database,write_database);
        finish();
    }
    private void applyPreviousReaction(){
        Cursor cursor = db_react.readReaction(read_database, timeStamp);

        String reaction = "";
        if(cursor.getCount()==1){
            cursor.moveToNext();
            reaction = cursor.getString(0);
            setRadioReaction(reaction);
        }
    }
    private void setRadioReaction(String reaction){
        int radio_type = reactionNumberToRadioButtonId(reaction);
        if(radio_type>-1){
            radioButton = findViewById(radio_type);
            radioButton.setChecked(true);
        }
    }
    public static int reactionNumberToRadioButtonId(String reaction){
        int radio_type = -1;
        //Better code should exists instead of using switch case
        switch(reaction){
            case "10":
                radio_type=R.id.radio_sad;
                break;
            case "11":
                radio_type=R.id.radio_happy;
                break;
            case "12":
                radio_type=R.id.radio_wow;
                break;
        }
        return radio_type;
    }
    public static int reactionNumberToDrawableId(String reaction){
        int drawable_type = -1;
        //Better code should exists instead of using switch case
        switch(reaction){
            case "10":
                drawable_type=R.drawable.sad;
                break;
            case "11":
                drawable_type=R.drawable.smile;
                break;
            case "12":
                drawable_type=R.drawable.concerned;
                break;
        }
        return drawable_type;
    }
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
}
