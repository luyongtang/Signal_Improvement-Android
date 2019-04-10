package org.thoughtcrime.securesms.util;

import android.content.Context;

public class DynamicTextFont {

    public String getSelectedTextFont(Context context) {
        return TextSecurePreferences.getFont(context);
    }
}