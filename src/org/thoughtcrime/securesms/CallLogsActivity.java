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

public class CallLogsActivity extends AppCompatActivity {

    TextView display;

    //Get the bundle
    // Bundle bundle = getIntent().getExtras();

    //Extract the data
    // String test = bundle.getString("threadId");

    //String threadId = StarredMessageContract.MessageEntry.CURRENT_THREAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_logs);

        display = findViewById(R.id.debug_display);

    }

    private void readCallLogs()
    {
        CallLogDbHelper callLogDbHelper = new CallLogDbHelper(this);
        SQLiteDatabase database = callLogDbHelper.getReadableDatabase();

        Cursor cursor = callLogDbHelper.readAllCallLog(database);
        String logs = "";

        while (cursor.moveToNext())
        {
            String from = cursor.getString(cursor.getColumnIndex(CallLogContract.MessageEntry.ADDRESS_CLOG));
            String type = cursor.getString(cursor.getColumnIndex(CallLogContract.MessageEntry.TYPE_CLOG));
            String timeStamp = cursor.getString(cursor.getColumnIndex(CallLogContract.MessageEntry.DATE_CLOG));
            Long tmp = Long.parseLong(timeStamp);
            timeStamp = convertTime(tmp);
            logs = logs + "\n\nFrom: "+from+"\nType: "+type+"\nDate: "+timeStamp;
        }
        if(logs.equals("")) {
            display.setText("History is empty");
        }
        else {
            display.setText(logs);
        }

        callLogDbHelper.close();
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
        readCallLogs();
    }


}
