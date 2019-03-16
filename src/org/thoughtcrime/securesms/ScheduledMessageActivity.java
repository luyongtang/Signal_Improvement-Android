package org.thoughtcrime.securesms;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

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

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
