package org.thoughtcrime.securesms.jobs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import org.thoughtcrime.securesms.ApplicationContext;
import org.thoughtcrime.securesms.database.Address;
import org.thoughtcrime.securesms.database.DatabaseFactory;
import org.thoughtcrime.securesms.database.MessagingDatabase.SyncMessageId;
import org.thoughtcrime.securesms.database.RecipientDatabase;
import org.thoughtcrime.securesms.jobmanager.JobParameters;
import org.thoughtcrime.securesms.logging.Log;
import org.thoughtcrime.securesms.recipients.Recipient;
import org.whispersystems.signalservice.api.messages.SignalServiceEnvelope;

import androidx.work.WorkerParameters;

public abstract class PushReceivedJob extends ContextJob {

  private static final String TAG = PushReceivedJob.class.getSimpleName();

  public static final Object RECEIVE_LOCK = new Object();

  protected PushReceivedJob(@NonNull Context context, @NonNull WorkerParameters workerParameters) {
    super(context, workerParameters);
  }

  protected PushReceivedJob(Context context, JobParameters parameters) {
    super(context, parameters);
  }

  public void processEnvelope(@NonNull SignalServiceEnvelope envelope) {
    Log.i("Eglen","PROCESSING");
    synchronized (RECEIVE_LOCK) {
      if (envelope.hasSource()) {
        Log.i("Eglen","has source");
        Address   source    = Address.fromExternal(context, envelope.getSource());
        Recipient recipient = Recipient.from(context, source, false);

        if (!isActiveNumber(recipient)) {
          DatabaseFactory.getRecipientDatabase(context).setRegistered(recipient, RecipientDatabase.RegisteredState.REGISTERED);
          ApplicationContext.getInstance(context).getJobManager().add(new DirectoryRefreshJob(context, recipient, false));
          Log.i("Eglen","is MIDDLE");
        }
      }

      if (envelope.isReceipt()) {
        Log.i("Eglen","is Receipt");
        handleReceipt(envelope);
      } else if (envelope.isPreKeySignalMessage() || envelope.isSignalMessage() || envelope.isUnidentifiedSender()) {
        Log.i("Eglen","is MESSAGE");
        Log.i("Eglen Handling", envelope.getLegacyMessage().toString());
        handleMessage(envelope);
      } else {
        Log.w(TAG, "Received envelope of unknown type: " + envelope.getType());
      }
    }
  }

  private void handleMessage(SignalServiceEnvelope envelope) {
    new PushDecryptJob(context).processMessage(envelope);
  }

  @SuppressLint("DefaultLocale")
  private void handleReceipt(SignalServiceEnvelope envelope) {
    Log.i(TAG, String.format("Received receipt: (XXXXX, %d)", envelope.getTimestamp()));
    DatabaseFactory.getMmsSmsDatabase(context).incrementDeliveryReceiptCount(new SyncMessageId(Address.fromExternal(context, envelope.getSource()),
                                                                                               envelope.getTimestamp()), System.currentTimeMillis());
  }

  private boolean isActiveNumber(@NonNull Recipient recipient) {
    return recipient.resolve().getRegistered() == RecipientDatabase.RegisteredState.REGISTERED;
  }
}
