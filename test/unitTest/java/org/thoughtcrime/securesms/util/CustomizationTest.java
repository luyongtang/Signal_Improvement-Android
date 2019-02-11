package org.thoughtcrime.securesms.util;

import org.junit.Test;
import org.thoughtcrime.securesms.BaseUnitTest;

import static junit.framework.Assert.assertEquals;

public class CustomizationTest extends BaseUnitTest {

    @Test
    public void testBackgroundGetter() {
        assertEquals(TextSecurePreferences.getBackground(context) , "getter called");
    }

    @Test
    public void testTextColorGetter() {
        assertEquals(TextSecurePreferences.getText(context) , "getter called");
    }

    @Test
    public void testBubbleColorGetter() {
        assertEquals(TextSecurePreferences.getBubble(context) , "getter called");
    }

    @Test
    public void testTextFontGetter() {
        assertEquals(TextSecurePreferences.getFont(context) , "getter called");
    }
}

