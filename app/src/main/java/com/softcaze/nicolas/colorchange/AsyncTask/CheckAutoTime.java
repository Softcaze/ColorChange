package com.softcaze.nicolas.colorchange.AsyncTask;

import android.content.ContentResolver;
import android.os.AsyncTask;
import android.util.Log;

import com.softcaze.nicolas.colorchange.Interface.SyncIsAutoTime;
import com.softcaze.nicolas.colorchange.Model.Global;

/**
 * Created by Nicolas on 01/04/2018.
 */

public class CheckAutoTime extends AsyncTask<Void, Boolean, Void> {
    protected Boolean isAutomaticTime = false;
    protected ContentResolver contentResolver;
    protected SyncIsAutoTime syncIsAutoTime;

    public CheckAutoTime(ContentResolver cR, SyncIsAutoTime s){
        this.contentResolver = cR;
        this.syncIsAutoTime = s;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        while(true){
            if (isCancelled()) {
                Log.i("CHECK AUTO TIME"," STOP");
                break;
            }

            if(contentResolver != null){
                if(android.provider.Settings.Global.getInt(contentResolver, android.provider.Settings.Global.AUTO_TIME, 0) == 1){
                    // Enabled
                    isAutomaticTime = true;
                }
                else {
                    // Disabled
                    isAutomaticTime = false;
                }
            }

            try {
                Thread.sleep(1000);

                publishProgress(isAutomaticTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Boolean... values) {
        super.onProgressUpdate(values);

        syncIsAutoTime.checkingAutoTime(values[0]);
    }

    public void setListener(SyncIsAutoTime s){
        this.syncIsAutoTime = s;
    }
}
