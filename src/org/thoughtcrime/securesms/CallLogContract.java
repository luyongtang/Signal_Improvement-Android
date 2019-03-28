package org.thoughtcrime.securesms;

public class CallLogContract {
    private CallLogContract(){}

    public static class MessageEntry {

        public static final String TABLE_NAME       = "clog_records";
        public static final String MESSAGE_ID_CLOG  = "message_id";
        public static final String THREAD_ID_CLOG   = "thread_id";
        public static final String TYPE_CLOG        = "type";
        public static final String CONTACT_CLOG     = "contact";
        public static final String DATE_CLOG        = "date";
        public static final String ADDRESS_CLOG     = "address";


    }
}
