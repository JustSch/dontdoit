package com.justtherest.dontdoit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created on 8/14/2017.
 */

//May decide just to use service?

public class TimerReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Log.e("receiver","is started");

        Integer min = intent.getExtras().getInt("minutes_left");

        Log.e("Extra received", String.valueOf(min));

        Long startTime = intent.getExtras().getLong("start time");

        Log.e("Start time received", String.valueOf(startTime));

        Intent notification_service_intent = new Intent(context, NotificationService.class);

        notification_service_intent.putExtra("minute_left", min);

        notification_service_intent.putExtra("start time", startTime);


        context.startService(notification_service_intent);

    }
}
