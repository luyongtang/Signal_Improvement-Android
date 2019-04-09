package org.thoughtcrime.securesms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.thoughtcrime.securesms.components.PushRecipientsPanel;
import org.thoughtcrime.securesms.contacts.ContactsCursorLoader;
import org.thoughtcrime.securesms.contacts.RecipientsEditor;
import org.thoughtcrime.securesms.database.DatabaseFactory;
import org.thoughtcrime.securesms.recipients.Recipient;
import org.thoughtcrime.securesms.sms.MessageSender;
import org.thoughtcrime.securesms.sms.OutgoingTextMessage;
import org.thoughtcrime.securesms.util.SelectedRecipientsAdapter;
import org.thoughtcrime.securesms.util.ViewUtil;

import java.util.List;

public class BroadcastMessageActivity extends AppCompatActivity implements SelectedRecipientsAdapter.OnRecipientDeletedListener, PushRecipientsPanel.RecipientsPanelChangedListener {
    TextView display;
    private String   messageBody;
    private Context  context;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_broadcast_message);

        RecipientsEditor recipientsEditor   = ViewUtil.findById(this, R.id.recipients_text);
        PushRecipientsPanel recipientsPanel = ViewUtil.findById(this, R.id.broadcast_recipients);

        context = getApplicationContext();
        lv      = findViewById(R.id.broadcast_selected_contacts_list);

        SelectedRecipientsAdapter adapter = new SelectedRecipientsAdapter(this);
        adapter.setOnRecipientDeletedListener(this);
        lv.setAdapter(adapter);

        recipientsEditor.setHint(R.string.recipients_panel__add_members);
        recipientsPanel.setPanelChangeListener(this);
        findViewById(R.id.contacts_button).setOnClickListener(new AddRecipientButtonListener());

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void onRecipientDeleted(Recipient recipient) {
        getAdapter().remove(recipient);
    }

    @Override
    public void onRecipientsPanelUpdate(List<Recipient> recipients) {
        if (recipients != null && !recipients.isEmpty()) addSelectedContacts(recipients);
    }

    private SelectedRecipientsAdapter getAdapter() {
        return (SelectedRecipientsAdapter)lv.getAdapter();
    }

    private void addSelectedContacts(@NonNull List<Recipient> recipients) {
        for (Recipient recipient : recipients){
            getAdapter().add(recipient, true);
        }
    }

    public void sendBroadcastMessage(View v) {

        messageBody = ((EditText) findViewById(R.id.broadcast_message_body)).getText().toString();

        boolean msgNull = messageBody.isEmpty();

        if (getAdapter().getCount() < 1 || msgNull) {

            String alertMsg = "Please enter the following: \n";
            if(getAdapter().getCount() < 1)
                alertMsg = alertMsg.concat("Contacts");
            if(msgNull)
                alertMsg = alertMsg.concat("\nMessage");

            alertMsg.concat(".");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(alertMsg);
            AlertDialog alert = builder.create();
            alert.show();

        } else {

            for (Recipient recipient : getAdapter().getRecipients()) {
                OutgoingTextMessage message;
                long threadId;

                message = new OutgoingTextMessage(recipient, messageBody, -1);
                threadId = DatabaseFactory.getThreadDatabase(this).getThreadIdIfExistsFor(recipient);

                if (threadId != -1L){
                    MessageSender.send(context, message, threadId, false, null);
                } else {
                    MessageSender.send(context, message, threadId, true, null);
                }
            }
            onBackPressed();
        }

    }

    private class AddRecipientButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(BroadcastMessageActivity.this, PushContactSelectionActivity.class);
            intent.putExtra(ContactSelectionListFragment.DISPLAY_MODE, ContactsCursorLoader.DisplayMode.FLAG_PUSH);
            startActivityForResult(intent, 1);
        }
    }
}
