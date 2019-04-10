package org.thoughtcrime.securesms.preferences;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.thoughtcrime.securesms.ApplicationPreferencesActivity;
import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.util.Analytic;

public class AnalyticsPreferenceFragment extends ListSummaryPreferenceFragment {
    private static final CharSequence OUTGOING_MESSAGES = "outgoing_messages";
    private static final CharSequence INCOMING_MESSAGES = "incoming_messages";
    private static final CharSequence LAST_MESSAGE_SENT = "last_message_sent";
    private static final CharSequence LAST_MESSAGE_RECEIVED = "last_message_received";

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        this.findPreference(LAST_MESSAGE_RECEIVED).setSummary(AnalyticsPreferenceFragment.getLastMessageReceived(getActivity()));
        this.findPreference(OUTGOING_MESSAGES).setSummary(AnalyticsPreferenceFragment.getOutgoingMessages(getActivity()).toString());
        this.findPreference(INCOMING_MESSAGES).setSummary(AnalyticsPreferenceFragment.getIncomingMessages(getActivity()).toString());
        this.findPreference(LAST_MESSAGE_SENT).setSummary(AnalyticsPreferenceFragment.getLastMessageSent(getActivity()));
    }
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences_analytics);
    }
    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener((ApplicationPreferencesActivity)getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ApplicationPreferencesActivity) getActivity()).getSupportActionBar().setTitle("Analytics");
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener((ApplicationPreferencesActivity) getActivity());
    }

    public static String getLastMessageReceived(Context context){
        return Analytic.getLastRecipientReceivedMessage(context);
    }
    public static Long getOutgoingMessages(Context context){
        return Analytic.getOutgoingMessageCount(context);
    }
    public static Long getIncomingMessages(Context context){
        return Analytic.getIncomingMessageCount(context);
    }
    public static String getLastMessageSent(Context context){
        return Analytic.getLastRecipientSentMessage(context);
    }


    public static CharSequence getSummary(Context context) {
       return context.getString(R.string.ApplicationPreferencesActivity_analytics_summary);
    }
    }

