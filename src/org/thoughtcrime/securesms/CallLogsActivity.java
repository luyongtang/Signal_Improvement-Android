package org.thoughtcrime.securesms;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import org.thoughtcrime.securesms.components.NavigationBarFragment;
import org.thoughtcrime.securesms.ApplicationPreferencesActivity;
import org.thoughtcrime.securesms.ConversationListActivity;
import org.thoughtcrime.securesms.CallLogsActivity;
import org.thoughtcrime.securesms.InviteActivity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallLogsActivity extends AppCompatActivity {

    TextView display;
    private static final String SELECTED_ITEM = "arg_selected_item";

    private NavigationBarFragment mBottomNav;
    private int mSelectedItem;

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

        BottomNavigationView navigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationView2);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_bar_chats: {
                        Intent intent = new Intent(CallLogsActivity.this, ConversationListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.navigation_bar_settings: {
                        Intent intent = new Intent(CallLogsActivity.this, ApplicationPreferencesActivity.class);
                        startActivity(intent);
                        break;
                    }

                }
                return true;
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