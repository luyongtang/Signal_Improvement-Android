package org.thoughtcrime.securesms;

import org.w3c.dom.Text;

public final class StarredMessageContract {

    private StarredMessageContract(){}

    public static class MessageEntry
    {
        public static final String TABLE_NAME          = "starred_messages";
        public final static String MESSAGE_ID_STAR     = "message_id";
        public final static String THREAD_ID_STAR      = "thread_id";
        public final static String TYPE_STAR           = "type";
        public final static String CONTACT             = "contact";
        public final static String MESSAGE_BODY_STAR   = "message_body";
        public final static String TIME_STAMP          = "time_stamp";
        public final static String DATE_RECEIVED       = "date_received";
        public final static String DATE_SENT           = "date_sent";
        public       static String CURRENT_THREAD      = "";

    }
}
