package com.justtherest.dontdoit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.FileOutputStream;

public class ClearReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Only Meant to clear TextFile for Testing purposes
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        String filename = "answers.txt";
        String space = "";
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE); //ModePrivate to Erase!!

            outputStream.write(space.getBytes());

            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("File is","Cleared");
    }
}
