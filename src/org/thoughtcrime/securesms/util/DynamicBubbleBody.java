package org.thoughtcrime.securesms.util;

import android.app.Activity;
import android.content.Intent;

public class DynamicBubbleBody {
    private String currentBubbleColor;

    public void onCreate(Activity activity) {
        currentBubbleColor = getSelectedBubbleColor(activity);
    }

    public void onResume(Activity activity) {
        if (currentBubbleColor != getSelectedBubbleColor(activity)) {
            Intent intent = activity.getIntent();
            activity.finish();
            DynamicBubbleBody.OverridePendingTransition.invoke(activity);
            activity.startActivity(intent);
            DynamicBubbleBody.OverridePendingTransition.invoke(activity);
        }
    }

    public String getSelectedBubbleColor(Activity activity) {
        return TextSecurePreferences.getBubble(activity);
    }

    private static final class OverridePendingTransition {
        static void invoke(Activity activity) {
            activity.overridePendingTransition(0, 0);
        }
    }
}

