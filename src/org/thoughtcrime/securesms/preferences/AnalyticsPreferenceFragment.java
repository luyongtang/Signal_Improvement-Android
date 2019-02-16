package org.thoughtcrime.securesms.preferences;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v14.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.util.TextSecurePreferences;

public class AnalyticsPreferenceFragment extends Fragment {
    public AnalyticsPreferenceFragment() {
    }


    public static CharSequence getSummary(Context context) {
       return context.getString(R.string.ApplicationPreferencesActivity_analytics_summary);
    }
    }

