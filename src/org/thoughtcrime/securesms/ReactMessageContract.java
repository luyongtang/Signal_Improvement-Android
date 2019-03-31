package org.thoughtcrime.securesms;

public final class ReactMessageContract
{
    private ReactMessageContract(){}

    public static class ReactionEntry
    {
        public static final String TABLE_NAME = "message_reactions";
        public static final String DATE_TIME = "date_time";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String REACTION = "reaction";



    }
}
