package org.thoughtcrime.securesms;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StarredMessageActivity extends AppCompatActivity {

    TextView display;

    //Get the bundle
   // Bundle bundle = getIntent().getExtras();

    //Extract the data
   // String test = bundle.getString("threadId");

    String threadId = StarredMessageContract.MessageEntry.CURRENT_THREAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starred_message);

        display = findViewById(R.id.debug_display);

    }

    private void readMessages()
    {
        MessageDbHelper messageDbHelper = new MessageDbHelper(this);
        SQLiteDatabase database = messageDbHelper.getReadableDatabase();

        Cursor cursor = messageDbHelper.readMessage(database, threadId);
        String msgs = "";

        while (cursor.moveToNext())
        {
            String msgBody = cursor.getString(cursor.getColumnIndex(StarredMessageContract.MessageEntry.MESSAGE_BODY_STAR));
            String cntc = cursor.getString(cursor.getColumnIndex(StarredMessageContract.MessageEntry.CONTACT));
            String timeStamp = cursor.getString(cursor.getColumnIndex(StarredMessageContract.MessageEntry.TIME_STAMP));
            Long tmp = Long.parseLong(timeStamp);
            timeStamp = convertTime(tmp);
            msgs = msgs + "\n\nsender: "+cntc+"\nbody: "+msgBody+"\ntime: "+timeStamp;
        }
        if(msgs.equals("")) {
            display.setText("No starred messages with this user");
        }
        else {
            display.setText(msgs);
        }

        messageDbHelper.close();
    }

    // For time format convert
    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @Override
    protected void onResume() {
        super.onResume();
        readMessages();
    }


}
