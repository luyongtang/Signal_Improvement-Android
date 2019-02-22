package org.thoughtcrime.securesms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.thoughtcrime.securesms.util.DynamicLanguage;
import org.thoughtcrime.securesms.util.DynamicTheme;

public class ScheduledMessageActivity extends AppCompatActivity {

    private final DynamicTheme    dynamicTheme    = new DynamicTheme();
    private final DynamicLanguage dynamicLanguage = new DynamicLanguage();

    protected void onPreCreate() {
        dynamicTheme.onCreate(this);
        dynamicLanguage.onCreate(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_activity_scheduled_message);
    }

    @Override
    protected void onResume(){
        super.onResume();
        dynamicTheme.onCreate(this);
        dynamicLanguage.onCreate(this);
    }
}
