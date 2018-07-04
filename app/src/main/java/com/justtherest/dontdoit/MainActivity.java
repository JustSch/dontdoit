package com.justtherest.dontdoit;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    AlarmManager timerManager;

    private Button setTimer;
    private Button resetTimer;
    private Button stopTimer;

    private EditText timerNum;

    private Context context;
    private PendingIntent pIntent;
    private AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerNum = (EditText) findViewById(R.id.timerNum);

        setTimer = (Button) findViewById(R.id.setBtn);
        setTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //receive number
                Log.e("User press set timer ","Timer will be set" );
                startTimer(); // is actually alarm
            }
        });

        resetTimer = (Button) findViewById(R.id.resetBtn); //change to pause alarm?
        resetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("User has pressed", "reset");

                timerNum.setText(""); //reset number


            }
        });

        stopTimer = (Button) findViewById(R.id.stopBtn);
        stopTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("User has pressed", "stop");

                timerNum.setText(""); //reset number

            }
        });



    }
    // Create Timer with help from https://developer.android.com/reference/android/os/CountDownTimer.html
    // and https://developer.android.com/reference/java/util/Timer.html and Google
    // YOU CAN DO IT!!!!!!!!!!!!!
    // Scream at user when they get distracted
    // Ask if user is distracted?
    // notifications!!!
    //use alarm or some kind of background thing to keep app in background Delete These!!!!!

    void startTimer() {
        //TODO
        final int min = Integer.parseInt(timerNum.getText().toString());

        long startTime = System.currentTimeMillis()+ (min * 60000); //is actually endtime


        Log.e("the timer set to", String.valueOf(min));

        Intent intent = new Intent(this, TimerReceiver.class); //receiver starts notification service

        intent.putExtra("start time", startTime);
        intent.putExtra("minutes_left", min);
        pIntent = PendingIntent.getBroadcast(this.getApplicationContext(),53787332,intent,0);

       // Intent startIntent = new Intent(getApplicationContext(), TimerReceiver.class); //Crashes since null Context!!

        //context.startService(startIntent); //now crashes here

        initNotification();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,(System.currentTimeMillis()+ (min * 60000)),pIntent); //change to intent to
                                                                                                        //updatenotification
                                                                                            //aka after initial notification


        Toast.makeText(this,"This app will remind you in: " + min + " minutes",Toast.LENGTH_LONG ).show();





    }

    void initNotification() {
        //TODO: make second notification close when timer is reset or tapaction enters app

        NotificationManager initNotifManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        int notID = 0;
        NotificationCompat.Builder initBuilder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setOngoing(true) //cancel this when alarm finishes!!!
        .setPriority(Notification.PRIORITY_HIGH)
        .setDefaults(Notification.DEFAULT_SOUND)
        .setContentTitle("Focus Timer: ")
        .setContentText("Is Active");

        initNotifManager.notify(notID, initBuilder.build());
        //initNotifManager.setTimeOut();


    }



}

