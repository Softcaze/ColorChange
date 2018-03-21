package com.softcaze.nicolas.colorchange.AsyncTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.softcaze.nicolas.colorchange.Activity.MainActivity;
import com.softcaze.nicolas.colorchange.Interface.SyncTimeLife;
import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.Model.User;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nicolas on 19/03/2018.
 */

public class RefreshLifeUser extends AsyncTask<Void, User, Void>{
    protected User user;
    protected Long spendTime;
    protected Calendar dateActu;
    protected Calendar dateLastLife;
    protected SyncTimeLife syncTimeLife;

    public RefreshLifeUser(User u, Calendar dLastlife, SyncTimeLife s)
    {
        this.user = u;
        dateActu = Calendar.getInstance();
        dateLastLife = Calendar.getInstance();
        dateLastLife = dLastlife;
        this.syncTimeLife = s;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while(true){
            if (isCancelled()) {
                Log.i("REFRESH LIFE USER"," STOP");
                break;
            }

            /*if(user.getNbrLife() == Constance.NBR_LIFE_MAX){
                break;
            }*/

            try{
                Thread.sleep(1000);

                // Initialisation DateActu + DateLastLife
                dateActu.setTime(Calendar.getInstance().getTime());

                long diff = dateActu.getTimeInMillis() - dateLastLife.getTimeInMillis();
                long seconds = diff/1000;
                long minutes = seconds/60;

                user.setTimeLastLife(diff);

                publishProgress(user);

                // Checked if life is recovered
               /* if((Constance.TIME_BETWEEN_LIFE - spendTime) <= 0){
                    //user.setTimeLastLife(0l);
                    if(user.getNbrLife() < Constance.NBR_LIFE_MAX){
                        user.setNbrLife(user.getNbrLife() + 1);
                    }
                }
                else{
                   // user.setTimeLastLife(spendTime);
                }*/
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(User... values) {
        super.onProgressUpdate(values);

        this.syncTimeLife.onTaskCompleted(user);
    }
}
