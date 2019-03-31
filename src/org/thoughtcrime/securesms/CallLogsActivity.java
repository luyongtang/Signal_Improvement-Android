package org.thoughtcrime.securesms;

import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import org.thoughtcrime.securesms.components.CallLogsFragment;
import org.thoughtcrime.securesms.components.NavigationBarFragment;

public class CallLogsActivity extends PassphraseRequiredActionBarActivity implements NavigationBarFragment.OnFragmentInteractionListener, CallLogsFragment.OnFragmentInteractionListener {

    private NavigationBarFragment navigationBarFragment;
    private ViewGroup navigationBarContainer;
    private CallLogsFragment callLogsFragment;
    private ViewGroup callLogsContainer;
    private TextView callLogTV;

    @Override
    protected void onCreate(Bundle icicle, boolean ready) {
        setContentView(R.layout.activity_call_logs);
        callLogTV = findViewById(R.id.CallLogsTextView);
        callLogTV.setText("dsfdfsdfdsfdsfdsfdsffdfsd");
        callLogsContainer = findViewById(R.id.call_log_call_log_fragment_container);
        callLogsFragment    = initFragment(R.id.call_log_navigation_bar_container, new CallLogsFragment());
        navigationBarContainer = findViewById(R.id.call_log_navigation_bar_container);
        navigationBarFragment = initFragment(R.id.call_log_navigation_bar_container, new NavigationBarFragment());
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
