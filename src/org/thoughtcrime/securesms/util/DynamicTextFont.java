package org.thoughtcrime.securesms.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;

public class DynamicTextFont {
    private Typeface currentTextFont;

    public void onCreate(Context context) {
        currentTextFont = getSelectedTextFont(context);
    }

    public Typeface getSelectedTextFont(Context context) {
        return TextSecurePreferences.getFont(context);
    }
}
