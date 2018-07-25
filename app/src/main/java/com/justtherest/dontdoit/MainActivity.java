package com.justtherest.dontdoit;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.LauncherActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;


/**
 * Created by Justin on 8/14/2017.
 */

/*
TODO: Make Menu Do Things and Add Most things To Other Activities!!!!
*TODO: Also Delete Raw mp3s and put stats in main? Also improveLayout!
* TODO: Put a Navigation Drawer Header? Also put error message for no integer!!!
* TODO: Home = MainActivity
* Remove Sensorpotrait from manifest when focusing on landscape!!!
 */
public class MainActivity extends AppCompatActivity {
    AlarmManager timerManager;

    private Button setTimer;
    private Button resetTimer;
    private Button stopTimer;

    private EditText timerNum;

    private Context context;
    private PendingIntent pIntent;
    private AlarmManager alarmManager;
    private DrawerLayout mDrawerLayout;
    private ArrayList <String> dataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (android.support.v4.widget.DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.solid_white));
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);



        closeNotification();
        fileOpen();



        final Intent constructionIntent = new Intent(this, PlaceholderActivity.class);//Placeholder
        Intent statIntent = new Intent(this,StatsActivity.class);

        PendingIntent pendStatsIntent = PendingIntent.getActivity(this,33334,
                statIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        statIntent.putExtra("dataArrayList",dataArrayList);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);

                        switch (menuItem.getItemId()) {
                            case R.id.home:
                                //Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                                //startActivity(intent);

                                Log.d("Home","is pressed");
                                startActivity(constructionIntent);

                                return true;

                            case R.id.stats:
                                //something();
                                Log.d("Stats", "Is Pressed");
                                startActivity(constructionIntent);
                                return true;

                            case R.id.settings:
                                Log.d("Settings", "Is Pressed");
                                startActivity(constructionIntent);
                                return true;

                            case R.id.about:
                                Log.d("About","Is Pressed");
                                startActivity(constructionIntent);
                                return true;
                            //default:
                             //   return super.onNavigationItemSelected(menuItem);
                        }

                        mDrawerLayout.closeDrawers();

                        //menuItem.setChecked(false);  // try to uncheck after check?



                        /*
                        Figure out how to switch Activity or Fragment
                         */



                        return true;
                    }
                });




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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    /* Scream at user when they get distracted (Maybe)
     Ask if user is distracted?*/

    void startTimer() {

        final int min = Integer.parseInt(timerNum.getText().toString());

        long startTime = System.currentTimeMillis()+ (min * 60000); //is actually endtime


        Log.e("the timer set to", String.valueOf(min));

        Intent intent = new Intent(this, TimerReceiver.class); //receiver starts notification service

        intent.putExtra("start time", startTime);
        intent.putExtra("minutes_left", min);
        pIntent = PendingIntent.getBroadcast(this.getApplicationContext(),53787332,intent,0);



        initNotification();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        /*Commented Out For Testing Purposes But Use This in Practice */
        //alarmManager.set(AlarmManager.RTC_WAKEUP,(System.currentTimeMillis()+ (min * 60000)),pIntent); //Opens Notification
        alarmManager.set(AlarmManager.RTC_WAKEUP,(min),pIntent);                                         //That asks if User
                                                                                                        //Is Distracted

        Toast.makeText(this,"This app will remind you in: " + min + " minutes",Toast.LENGTH_LONG ).show();



    }

    void initNotification() {
        /*TODO: make second notification close when timer is reset or tapaction enters app*/

        NotificationManager initNotifManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        int notID = 0;
        NotificationCompat.Builder initBuilder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setOngoing(true) //cancel this when alarm finishes!!!
        .setPriority(Notification.PRIORITY_HIGH)
        .setDefaults(Notification.DEFAULT_SOUND)
        .setContentTitle("Focus Timer Is Active: ")
        .setContentText("Please Do Not Close App");

        initNotifManager.notify(notID, initBuilder.build());

    }

    void closeNotification() {
        NotificationManager closeManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        closeManager.cancelAll();
    }

    void fileOpen() {
        String filename = "answers.txt";
        String newLine = "\n";

        File textFile = new File(getApplicationContext().getFilesDir(),filename);
        dataArrayList = new ArrayList<>();


        if (textFile.exists()) {
            Log.d("File: ","Exists");
            try {
                Scanner scanner = new Scanner(textFile).useDelimiter(System.lineSeparator());
                while (scanner.hasNext()) {
                    dataArrayList.add(scanner.next());
                }
                ListIterator listitr = dataArrayList.listIterator();
                while (listitr.hasNext()) {
                    Log.d("List Has: ",listitr.next().toString());
                }

            }
            catch (IOException exception) {
                exception.printStackTrace();
            }



        }
        else {
            Log.d("File: ","Doesn't Exist");
        }



    }


}

