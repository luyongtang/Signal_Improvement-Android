package org.thoughtcrime.securesms;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import org.thoughtcrime.securesms.logging.Log;

public class ReactionActivity extends AppCompatActivity {
    private TextView textView;
    private String message;
    private String timeStamp;
    private String phoneNumber;
    private RadioButton radioButton;
    private ReactMessageDbHelper db_react;
    private SQLiteDatabase write_database;
    private SQLiteDatabase read_database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reaction);
        db_react = new ReactMessageDbHelper(getApplicationContext());

        write_database = db_react.getWritableDatabase();
        read_database = db_react.getReadableDatabase();

        message = getIntent().getStringExtra("message");
        timeStamp = getIntent().getStringExtra("date_time");
        phoneNumber = getIntent().getStringExtra("phone_number");

        textView=findViewById(R.id.sample);
        textView.setText(message);

        applyPreviousReaction();

        Cursor cursor = db_react.readReaction(read_database, timeStamp, phoneNumber);
        String[] names = cursor.getColumnNames();
        for(String s: names){
            Log.i("data",s);
        }
        Log.i("data_raw", Integer.toString(cursor.getCount()));
        while(cursor.moveToNext()){
            Log.i("data_raw",cursor.getString(1));
            Log.i("data_raw",cursor.getString(0));
            Log.i("data_raw",cursor.getString(2));
        }

        Log.i("Eglen Cecaj",message);
        Log.i("Eglen Cecaj",timeStamp);
        Log.i("Eglen Cecaj",phoneNumber);


    }

    private void handleSelectedReaction(String reaction){

        ContentValues contentValues = new ContentValues();

        contentValues.put(ReactMessageContract.ReactionEntry.DATE_TIME ,  timeStamp);
        contentValues.put(ReactMessageContract.ReactionEntry.PHONE_NUMBER, phoneNumber);
        contentValues.put(ReactMessageContract.ReactionEntry.REACTION , reaction);

        db_react.saveReaction(contentValues,read_database,write_database);
        finish();
    }
    private void applyPreviousReaction(){
        Cursor cursor = db_react.readReaction(read_database, timeStamp, phoneNumber);
        String reaction = "";
        if(cursor.getCount()==1){
            cursor.moveToNext();
            reaction = cursor.getString(0);
            setRadioReaction(reaction);

        }
    }
    private void setRadioReaction(String reaction){
        int radio_type = -1;
        //Better code should exists instead of using switch case
        switch(reaction){
            case ":(":
                radio_type=R.id.radio_sad;
                break;
            case ":)":
                radio_type=R.id.radio_happy;
                break;
            case ":O":
                radio_type=R.id.radio_wow;
                break;
        }
        if(radio_type>-1){
            radioButton = findViewById(radio_type);
            radioButton.setChecked(true);

        }
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        String reaction="";
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_sad:
                if (checked)
                    // Pirates are the best
                    reaction=":(";
                    break;
            case R.id.radio_happy:
                if (checked)
                    reaction=":)";
                    break;
            case R.id.radio_wow:
                if (checked)
                    reaction=":O";
                    break;
        }
        handleSelectedReaction(reaction);
    }
}
