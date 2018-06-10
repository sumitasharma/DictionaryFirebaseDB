package com.example.sumitasharma.loadingrealtimedatabase;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class WordDbPopulatorServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("MyStartServiceReceiver", "Inside MyStartServiceReceiver onReceive");

        Toast.makeText(context, "Toast from WordDBPopulatorServiceReceiver", Toast.LENGTH_LONG).show();
        // scheduleJob(context);

    }
}

