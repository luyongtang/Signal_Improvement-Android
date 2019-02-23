package org.thoughtcrime.securesms;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class StarredMessageActivity extends AppCompatActivity {

    TextView display;

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

        Cursor cursor = messageDbHelper.readMessage(database);
        String msgs = "";

        while (cursor.moveToNext())
        {
            String msgId = Integer.toString(cursor.getInt(cursor.getColumnIndex(StarredMessageContract.MessageEntry.MESSAGE_ID_STAR)));
            String threadId = cursor.getString(cursor.getColumnIndex(StarredMessageContract.MessageEntry.THREAD_ID_STAR));
            String msgBody = cursor.getString(cursor.getColumnIndex(StarredMessageContract.MessageEntry.MESSAGE_BODY_STAR));
            msgs = msgs + "\n\nmsgId : "+msgId+"\nthreadId: "+threadId+"\nbody: "+msgBody;
        }
        display.setText(msgs);
        messageDbHelper.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        readMessages();
    }


}
