package org.thoughtcrime.securesms;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import org.thoughtcrime.securesms.components.NavigationBarFragment;

public class CallLogsActivity extends PassphraseRequiredActionBarActivity implements  NavigationBarFragment.OnFragmentInteractionListener{

    private NavigationBarFragment    navigationBarFragment;
    private ViewGroup navigationBarContainer;

    @Override
    protected void onCreate(Bundle icicle, boolean ready) {
        setContentView(R.layout.activity_call_logs);
        navigationBarContainer   = findViewById(R.id.call_log_navigation_bar_container);
        navigationBarFragment    = initFragment(R.id.call_log_navigation_bar_container, new NavigationBarFragment());
    }
    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
