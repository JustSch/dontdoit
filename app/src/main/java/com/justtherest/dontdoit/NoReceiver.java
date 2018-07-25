package com.justtherest.dontdoit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NoReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");

        Log.d("no Reciever", "works");
        Intent answerIntent = new Intent(context, NotificationAnswerService.class);
        answerIntent.putExtra("answerExtra",false);

        context.startService(answerIntent);
    }
}
