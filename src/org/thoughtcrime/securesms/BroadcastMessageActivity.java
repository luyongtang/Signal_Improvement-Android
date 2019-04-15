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

import org.thoughtcrime.securesms.components.PushRecipientsPanel;
import org.thoughtcrime.securesms.contacts.ContactsCursorLoader;
import org.thoughtcrime.securesms.contacts.RecipientsEditor;
import org.thoughtcrime.securesms.logging.Log;
import org.thoughtcrime.securesms.recipients.Recipient;
import org.thoughtcrime.securesms.util.SelectedRecipientsAdapter;
import org.thoughtcrime.securesms.util.ViewUtil;

import java.util.List;

public class BroadcastMessageActivity extends AppCompatActivity implements SelectedRecipientsAdapter.OnRecipientDeletedListener, PushRecipientsPanel.RecipientsPanelChangedListener {
    private Context  context;
    private ListView lv;
    private BroadcastUtil broadcastUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_broadcast_message);

        RecipientsEditor recipientsEditor   = ViewUtil.findById(this, R.id.recipients_text);
        PushRecipientsPanel recipientsPanel = ViewUtil.findById(this, R.id.broadcast_recipients);
        recipientsPanel.findViewById(R.id.contacts_button).setVisibility(View.GONE);
        context = getApplicationContext();
        lv      = findViewById(R.id.broadcast_selected_contacts_list);

        SelectedRecipientsAdapter adapter = new SelectedRecipientsAdapter(this);
        adapter.setOnRecipientDeletedListener(this);
        lv.setAdapter(adapter);

        recipientsEditor.setHint(R.string.recipients_panel__add_members);
        recipientsPanel.setPanelChangeListener(this);
        findViewById(R.id.contacts_button).setOnClickListener(new AddRecipientButtonListener());

        broadcastUtil = new BroadcastUtil(context);

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
        String messageBody = ((EditText) findViewById(R.id.broadcast_message_body)).getText().toString();

        boolean success = broadcastUtil.sendBroadcastMessage(messageBody,getAdapter());
        /*Logging*/
        Log.i("broadcastState", String.valueOf(success));
        Log.i("broadcastState", broadcastUtil.getLastOperationStatus());

        if(success) onBackPressed();
        else showErrorDialog(broadcastUtil.getLastOperationStatus());

    }
    private void showErrorDialog(String alertMsg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(alertMsg);
        AlertDialog alert = builder.create();
        alert.show();
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
