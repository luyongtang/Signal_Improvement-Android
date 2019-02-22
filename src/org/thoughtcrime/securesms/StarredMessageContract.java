package org.thoughtcrime.securesms;

import org.w3c.dom.Text;

public final class StarredMessageContract {

    private StarredMessageContract(){}

    public static class MessageEntry
    {
        public static final String TABLE_NAME          = "starred_messages";
        public final static String MESSAGE_ID_STAR     = "message_id";
        public final static String THREAD_ID_STAR      = "thread_id";
        public final static String IS_PUSH_GROUP_STAR  = "is_push_group";
        public final static String TYPE_STAR           = "type";
        public final static String ADDRESS_STAR        = "address";
    }
}
