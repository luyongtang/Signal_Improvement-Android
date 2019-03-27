package org.thoughtcrime.securesms;

import org.whispersystems.signalservice.api.messages.SignalServiceReceiptMessage;

import java.util.List;

public class SignalServiceReactionMessage extends SignalServiceReceiptMessage {

    public enum Type{
        REACTION
    }
    private final String    content;
    private final Type  reaction = Type.REACTION;

    public SignalServiceReactionMessage(List<Long> timestamps, long when, String content) {
        super(null,timestamps,when );
        this.content=content;
    }
    public Type getTypeReact() {
        return reaction;
    }

    public boolean isReactionReceipt() {
        return true;
    }

}
