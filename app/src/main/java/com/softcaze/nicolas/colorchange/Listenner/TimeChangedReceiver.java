package com.softcaze.nicolas.colorchange.Listenner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Nicolas on 11/03/2018.
 */

public class TimeChangedReceiver extends BroadcastReceiver {

    public TimeChangedReceiver(){
        ;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        Log.i("TIMECHANGED RECEIVER"," Temps changé !!");
    }
}
