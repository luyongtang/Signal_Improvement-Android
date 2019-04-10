package org.thoughtcrime.securesms;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
        display = findViewById(R.id.call_logs_text_view);

        BottomNavigationView navigationView = (BottomNavigationView)findViewById(R.id.call_logs_navigation_bar);

        navigationView.setSelectedItemId(R.id.navigation_bar_call_logs);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_bar_chats: {
                        Intent intent = new Intent(CallLogsActivity.this, ConversationListActivity.class);
                        startActivity(intent);
                        CallLogsActivity.this.overridePendingTransition(R.anim.stationary, R.anim.stationary);
                        break;
                    }
                    case R.id.navigation_bar_settings: {
                        Intent intent = new Intent(CallLogsActivity.this, ApplicationPreferencesActivity.class);
                        startActivity(intent);
                        CallLogsActivity.this.overridePendingTransition(R.anim.stationary, R.anim.stationary);
                        break;
                    }

                }
                return true;
            }
        });

        final Button button = findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                emptyCallLogs();
                finish();
                startActivity(getIntent());
            }
        });


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

    private void emptyCallLogs()
    {
        CallLogDbHelper callLogDbHelper = new CallLogDbHelper(this);
        SQLiteDatabase database_write = callLogDbHelper.getWritableDatabase();

        callLogDbHelper.deleteAllCallLogs(database_write);

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
