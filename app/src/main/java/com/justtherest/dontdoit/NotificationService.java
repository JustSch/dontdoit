package com.justtherest.dontdoit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.EditText;

/**
 * Created on 8/14/2017.
 */

public class NotificationService extends Service {
    //TODO move alarm here, Also just say time the alarm will go off until figure out how countdown works
    MediaPlayer mPlayer;

    int iD;
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


        Handler handler = new Handler();



        //ToDO add notifications and stopservice and stop broadcast and test mp3 also settings eventually?
        //Need Intent and pendingIntent stuff for notification!!!!! also heads up notification when done
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        int notifyID = 1;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)

                .setSmallIcon(R.mipmap.ic_launcher)  //change to something in drawable
                .setOngoing(true)
                .setContentTitle("Timer Notification")
                .setContentText("Timer Set!");

                //.setAutoCancel(true); //not sure if should be used. Look for something to cancel notification

        Intent backIntent = new Intent(this, MainActivity.class);
        PendingIntent backPendingIntent = PendingIntent.getActivity(this, 0, backIntent, 0);
        //Move this
        playSound();

        int min = min_left;
        //Retrieve minutes left in timer/alarm and
        Runnable updateTask = new Runnable() {
            @Override
            public void run() {
                updateNotification();


                //handler.postDelayed(this, 60000); //takes too long
            }
        };


        while (min >= 0) {

            if (min == 0) {
                notificationManager.cancel(notifyID); //might work?

                Log.e("notification should", "close");

                break;
                //Log.e("if this displays","something is wrong");
            }
            //Logs value that is put in notification
            Log.e("min in notification is", String.valueOf(min));


            mBuilder.setContentText("Will check if you've been distracted in: " + min + " minutes");

            //handler.postDelayed(updateTask, 60000); //takes too long
            min--;
        }

        notificationManager.notify(notifyID, mBuilder.build());
        //TODO Add heads up notification when timer is done and yell at user if they get distracted
        //TODO Also Fix timer



        return START_NOT_STICKY;
    }
    void updateNotification(){
        //put minutes and receive put extra in here to use them then have runnable activate this
        //long timeRemaining = startTime - System.currentTimeMillis(); //put in while loop?

        min--;
        //mBuilder.setContentText("Will check if you've been distracted in: " + min + " minutes");



    }
    // change sound used at a later point
    void playSound(){
        mPlayer = MediaPlayer.create(this, R.raw.tmpsd);
        mPlayer.start();
        Log.e("Sound", "Has played");

    }




}
