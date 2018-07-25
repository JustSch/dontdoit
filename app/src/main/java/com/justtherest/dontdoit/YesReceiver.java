package com.justtherest.dontdoit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class YesReceiver extends BroadcastReceiver {
    /**
     * Add Another Notification and Keep track of how often distracted
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.d("yes Reciever", "works");

        Intent answerIntent = new Intent (context,NotificationAnswerService.class);
        answerIntent.putExtra("answerExtra",true);

        context.startService(answerIntent);
    }
}
