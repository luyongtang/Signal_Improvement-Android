package org.thoughtcrime.securesms.util;

import android.app.Activity;
import android.content.Intent;

public class DynamicBackground {
    private String currentBackground;

    public void onCreate(Activity activity) {
        currentBackground = getSelectedBackground(activity);
    }

    public void onResume(Activity activity) {
        if (currentBackground != getSelectedBackground(activity)) {
            Intent intent = activity.getIntent();
            activity.finish();
            DynamicBackground.OverridePendingTransition.invoke(activity);
            activity.startActivity(intent);
            DynamicBackground.OverridePendingTransition.invoke(activity);
        }
    }

    public String getSelectedBackground(Activity activity) {
        return TextSecurePreferences.getBackground(activity);
    }

    private static final class OverridePendingTransition {
        private static void invoke(Activity activity) {
            activity.overridePendingTransition(0, 0);
        }
    }
}
