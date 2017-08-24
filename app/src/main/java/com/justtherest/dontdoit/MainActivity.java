package com.justtherest.dontdoit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    

    void startTimer() {
        //TODO
        final int min = Integer.parseInt(timerNum.getText().toString());

        Log.e("the timer set to", String.valueOf(min));

        Intent intent = new Intent(this, TimerReceiver.class);
        intent.putExtra("minutes_left", min);
        pIntent = PendingIntent.getBroadcast(this.getApplicationContext(),53787332,intent,0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,(System.currentTimeMillis()+ (min * 60000)),pIntent);
        long startTime = System.currentTimeMillis()+ (min * 60000);
        intent.putExtra("start time", startTime);

        Toast.makeText(this,"This app will remind you in " + min + " minutes",Toast.LENGTH_LONG ).show();





    }



}

