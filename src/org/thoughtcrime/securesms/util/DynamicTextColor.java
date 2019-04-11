package org.thoughtcrime.securesms.util;

import android.content.Context;

public class DynamicTextColor {

    public String getSelectedTextColor(Context context) {
        return TextSecurePreferences.getText(context);
    }
}
