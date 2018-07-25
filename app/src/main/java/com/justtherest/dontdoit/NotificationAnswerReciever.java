package com.justtherest.dontdoit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
/**
 *TODO: Probably wont need this due to having yes and no recievers!!!!!
 */
public class NotificationAnswerReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        throw new UnsupportedOperationException("Not yet implemented");

        /* REwrite this code
        Intent notIntent = new Intent(this, NotificationService.class);
        if (notIntent.hasExtra("Has Notif")) {
            Log.d("new Extra REcieved", notIntent.getStringExtra("Has Notif"));
            Log.d("yes", "yes");
        }*/
    }
}
