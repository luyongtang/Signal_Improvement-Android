package org.thoughtcrime.securesms;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;


import org.thoughtcrime.securesms.database.MmsSmsDatabase;
import org.thoughtcrime.securesms.database.model.MessageRecord;
import org.thoughtcrime.securesms.logging.Log;

public class ReactionActivity extends AppCompatActivity {
    TextView textView;
    String messageId;
    String timeStamp;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reaction);
        textView=(TextView)findViewById(R.id.sample);
        messageId = getIntent().getStringExtra("messages");
        timeStamp = getIntent().getStringExtra("date_time");
        phoneNumber = getIntent().getStringExtra("phone_number");
        //textView.setText(messageId);
        Log.i("Eglen Cecaj",messageId);
        Log.i("Eglen Cecaj",timeStamp);
        Log.i("Eglen Cecaj",phoneNumber);

    }

    private void handleStoreReaction(){
        ReactMessageDbHelper reactMessageDbHelper = new ReactMessageDbHelper(getApplicationContext());
        SQLiteDatabase database = reactMessageDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ReactMessageContract.ReactionEntry.DATE_TIME ,  timeStamp);
        contentValues.put(ReactMessageContract.ReactionEntry.PHONE_NUMBER, phoneNumber);
        contentValues.put(ReactMessageContract.ReactionEntry.REACTION , textView.getText().toString());


        reactMessageDbHelper.addReactionData(contentValues,database);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_happy:
                if (checked)
                    // Pirates are the best
                    textView.setText(":)");
                    break;
            case R.id.radio_sad:
                if (checked)
                    textView.setText(":(");
                    break;
            case R.id.radio_wow:
                if (checked)
                    textView.setText(":O");
                    break;
        }
        handleStoreReaction();
    }
}
