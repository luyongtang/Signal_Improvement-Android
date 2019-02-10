package org.thoughtcrime.securesms.util;

import android.content.Context;

public class DynamicTextFont {
    private String currentTextFont;

    public void onCreate(Context context) {
        currentTextFont = getSelectedTextFont(context);
    }

    public String getSelectedTextFont(Context context) {
        return TextSecurePreferences.getFont(context);
    }
}