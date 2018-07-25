package com.justtherest.dontdoit;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotificationAnswerService extends Service {
    public NotificationAnswerService() {
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

   // @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //
        // TODO: Return the communication channel to the service.
        /*
        TODO: Make this Close Notification!!!!
         */
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.d("NotificationAnsService","Is working");
        String filename = "answers.txt";
        String answer = "";
        String newLine = "\n";
        String space = " ";

        if (intent.getBooleanExtra("answerExtra",false)) {
            answer = "0";
        }
        else answer = "1";

        FileOutputStream outputStream;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");// Switch T to V? Not Needed
        String dateTime = sDate.format(cal.getTime());
        Log.d("DAteTime is: ",dateTime);




        try {
            outputStream = openFileOutput(filename, Context.MODE_APPEND); //ModePrivate to Erase!!
            /*
            TODO: Make it write DATE and Time!!!!
             */
            outputStream.write(dateTime.getBytes());
            outputStream.write(space.getBytes());
            outputStream.write(answer.getBytes());
            //outputStream.write(space.getBytes());
            outputStream.write(newLine.getBytes());
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }



        /*
        Using to test what it written
         */
        StringBuffer inString = new StringBuffer("");
        try {
            FileInputStream fileIn = openFileInput(filename );
            InputStreamReader inStreamReader = new InputStreamReader(fileIn);
            BufferedReader bReader = new BufferedReader(inStreamReader);

            String anString = bReader.readLine();

            while (anString !=null){
                //
                inString.append(newLine);
                inString.append(anString);
                anString = bReader.readLine();

            }
            inStreamReader.close();
            Log.d("Inside File is: ",inString.toString());

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


        return START_STICKY;
    }
}