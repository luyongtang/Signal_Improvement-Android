package org.thoughtcrime.securesms.util;
/*
* This class is a sample run to only test the Analytic class
* used for storing and retrieving the stats
* */


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.thoughtcrime.securesms.logging.Log;

public class runSample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onResume(){
        super.onResume();
        //count outgoing message
        Analytic.increaseOutgoingMessageCountByOne(getApplicationContext());
        //count incoming message
        Analytic.increaseIncomingMessageCountByOne(getApplicationContext());
        //Save the last person name you sent a message to
       // Analytic.setLastRecipientSentMessage(getApplicationContext(),"Antoine Ferrier");
        //Save the last person you received a message from
       // Analytic.setLastRecipientReceivedMessage(getApplicationContext(),"Alexa Philo");
        //Retrieve the stats and display them
        String count = Long.toString(Analytic.getOutgoingMessageCount(getApplicationContext()));
        String count_in = Long.toString(Analytic.getIncomingMessageCount(getApplicationContext()));
        String analytic_tag = "Analytic";
        Log.i(analytic_tag, "Number of outgoing messages: "+count);
        Log.i(analytic_tag, "Number of incoming messages: "+count_in);
        Log.i(analytic_tag, "Last message sent to: "+ Analytic.getLastRecipientSentMessage(getApplicationContext()));
        Log.i(analytic_tag, "Last message received from (read): "+ Analytic.getLastRecipientReceivedMessage(getApplicationContext()));
    }
}
