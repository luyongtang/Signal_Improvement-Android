package org.thoughtcrime.securesms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.thoughtcrime.securesms.util.DynamicLanguage;
import org.thoughtcrime.securesms.util.DynamicTheme;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduledMessageActivity extends AppCompatActivity {
    TextView display;

  /*  private final DynamicTheme    dynamicTheme    = new DynamicTheme();
    private final DynamicLanguage dynamicLanguage = new DynamicLanguage();*/

    protected void onPreCreate() {
       /* dynamicTheme.onCreate(this);
        dynamicLanguage.onCreate(this);*/
        setContentView(R.layout.activity_scheduled_message);

        display = findViewById(R.id.debug_display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_message);
        display = findViewById(R.id.debug_display);
    }

    @Override
    protected void onResume(){
        super.onResume();
        /*dynamicTheme.onCreate(this);
        dynamicLanguage.onCreate(this);*/
    }
}
