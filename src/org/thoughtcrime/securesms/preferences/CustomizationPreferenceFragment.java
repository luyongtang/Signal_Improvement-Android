package org.thoughtcrime.securesms.preferences;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.ListPreference;

import org.thoughtcrime.securesms.ApplicationPreferencesActivity;
import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.util.TextSecurePreferences;

import java.util.Arrays;

public class CustomizationPreferenceFragment extends ListSummaryPreferenceFragment {
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);

        this.findPreference(TextSecurePreferences.BACKGROUND_PREF).setOnPreferenceChangeListener(new ListSummaryListener());
        this.findPreference(TextSecurePreferences.TEXT_PREF).setOnPreferenceChangeListener(new ListSummaryListener());
        this.findPreference(TextSecurePreferences.BUBBLE_PREF).setOnPreferenceChangeListener(new ListSummaryListener());
        this.findPreference(TextSecurePreferences.FONT_PREF).setOnPreferenceChangeListener(new ListSummaryListener());
        initializeListSummary((ListPreference)findPreference(TextSecurePreferences.BACKGROUND_PREF));
        initializeListSummary((ListPreference)findPreference(TextSecurePreferences.TEXT_PREF));
        initializeListSummary((ListPreference)findPreference(TextSecurePreferences.BUBBLE_PREF));
        initializeListSummary((ListPreference)findPreference(TextSecurePreferences.FONT_PREF));
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences_customization);
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener((ApplicationPreferencesActivity)getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ApplicationPreferencesActivity) getActivity()).getSupportActionBar().setTitle("Customization");
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener((ApplicationPreferencesActivity) getActivity());
    }

    public static CharSequence getSummary(Context context) {
        String[] backgroundEntries     = context.getResources().getStringArray(R.array.pref_background_entries);
        String[] backgroundEntryValues = context.getResources().getStringArray(R.array.pref_background_values);
        String[] textEntries           = context.getResources().getStringArray(R.array.text_entries);
        String[] textEntryValues       = context.getResources().getStringArray(R.array.text_values);
        String[] bubbleEntries         = context.getResources().getStringArray(R.array.pref_bubble_entries);
        String[] bubbleEntryValues     = context.getResources().getStringArray(R.array.pref_bubble_values);
        String[] fontEntries           = context.getResources().getStringArray(R.array.font_entries);
        String[] fontEntryValues       = context.getResources().getStringArray(R.array.font_values);

        int backgroundIndex  = Arrays.asList(backgroundEntryValues).indexOf(TextSecurePreferences.getBackground(context));
        int textIndex = Arrays.asList(textEntryValues).indexOf(TextSecurePreferences.getText(context));
        int bubbleIndex  = Arrays.asList(bubbleEntryValues).indexOf(TextSecurePreferences.getBubble(context));
        int fontIndex = Arrays.asList(fontEntryValues).indexOf(TextSecurePreferences.getFont(context));

        if (backgroundIndex == -1)  backgroundIndex = 0;
        if (textIndex == -1) textIndex = 0;
        if (bubbleIndex == -1)  bubbleIndex = 0;
        if (fontIndex == -1) fontIndex = 0;

        return context.getString(R.string.ApplicationPreferencesActivity_customization_summary,
                backgroundEntries[backgroundIndex],
                textEntries[textIndex],
                bubbleEntries[bubbleIndex],
                fontEntries[fontIndex]);
    }


}
