package org.thoughtcrime.securesms.util;

import android.content.Context;
import android.content.Intent;

public class DynamicTextColor {
    private String currentTextColor;

    public void onCreate(Context context) {
        currentTextColor = getSelectedTextColor(context);
    }

    public String getSelectedTextColor(Context context) {
        return TextSecurePreferences.getText(context);
    }
}
