package com.justtherest.dontdoit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by Justin on 8/14/2017.
 */

public class NotificationService extends Service {
    /*TODO move alarm here, Also just say time the alarm will go off until figure out how countdown works
    * TODO: Set An Error if no Ringtone is Set on Phone?
    * TODO: Make it randomly generate notification id but still able to use it*/

    int min;


    //int min = MainActivity.timerNum;// use intent put extra to get value!!!



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int iD){
        final Integer min_left = intent.getExtras().getInt("minute_left");
        final Long startTime = intent.getExtras().getLong("start time");

        Intent backIntent = new Intent(this, MainActivity.class);
        PendingIntent backPendingIntent = PendingIntent.getActivity(this, 0, backIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Intent yesBroadcastIntent = new Intent(this,YesReceiver.class);
        Intent noBroadcastIntent = new Intent(this,NoReceiver.class);
        Intent clearIntent = new Intent(this,ClearReceiver.class);

        PendingIntent pendYesBroadcastIntent = PendingIntent.getBroadcast(this.getApplicationContext(),3233232,
                yesBroadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendNoBroadCastIntent = PendingIntent.getBroadcast(this,43434434,
                noBroadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendClearIntent = PendingIntent.getBroadcast(this,000,
                clearIntent,0);

        Handler handler = new Handler(); //Not sure if needed currently


        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0); // closes 1st notification when displaying second
        int notifyID = 1;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)

                .setSmallIcon(R.mipmap.ic_launcher)  //change to something in drawable
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentTitle("Are You Being Distracted?")
                .setContentText("Should You Get Back To Work?")
                .setContentIntent(pendClearIntent)//Treat pressing notification as Yes or Open Dialog Box? Clears for Now!!
                .addAction(R.mipmap.ic_launcher,"Yes",pendYesBroadcastIntent)
                .addAction(R.mipmap.ic_launcher,"No",pendNoBroadCastIntent); //make drawable when you figure it out



        notificationManager.notify(notifyID, mBuilder.build());//displays notification/call as needed

        playSound();

        int min = min_left;

        backIntent.putExtra("Has_Notif", "1");
        /*TODO Add heads up notification when timer is done and yell at user if they get distracted
          TODO Also Fix timer*/



        return START_STICKY;
    }

     /*change sound with a setting used at a later point*/
    void playSound(){
        //mPlayer = MediaPlayer.create(this, R.raw.tmpsd); //use this for custom sound
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), notification);
        mPlayer.start();
        Log.e("Sound", "Has played");
        //mPlayer.release();//needed?

    }




}
