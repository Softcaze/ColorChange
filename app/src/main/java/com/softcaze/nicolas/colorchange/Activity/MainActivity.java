package com.softcaze.nicolas.colorchange.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softcaze.nicolas.colorchange.AsyncTask.RefreshLifeUser;
import com.softcaze.nicolas.colorchange.Database.DAO;
import com.softcaze.nicolas.colorchange.Interface.SyncTimeLife;
import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.Model.User;
import com.softcaze.nicolas.colorchange.R;

import org.w3c.dom.Text;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class MainActivity extends FragmentActivity {
    protected TextView btn_play, btn_setting, btn_shop, btn_rate, nbrLife, timeLife;
    protected LinearLayout linearPlay, linearSetting, linearShop, linearRate;
    protected ImageView img_play;
    protected LinearLayout.LayoutParams params;
    protected User user;
    protected DAO dao = null;
    protected long time;
    protected RefreshLifeUser taskRefresh;
    protected Calendar dateActu = Calendar.getInstance();
    protected SimpleDateFormat format;
    protected Calendar dateLastLife = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_play = (TextView) findViewById(R.id.btn_play);
        btn_setting = (TextView) findViewById(R.id.btn_setting);
        btn_shop = (TextView) findViewById(R.id.btn_shop);
        btn_rate = (TextView) findViewById(R.id.btn_rate);
        nbrLife = (TextView) findViewById(R.id.nbrLife);
        timeLife = (TextView) findViewById(R.id.timeLife);
        linearPlay = (LinearLayout) findViewById(R.id.linearPlay);
        linearSetting = (LinearLayout) findViewById(R.id.linearSetting);
        linearShop = (LinearLayout) findViewById(R.id.linearShop);
        linearRate = (LinearLayout) findViewById(R.id.linearRate);
        img_play = (ImageView) findViewById(R.id.img_play);

        dao = new DAO(this);

        dao.open();
        Log.i("ON CREATE", "NBR LIFE : " + dao.getNbrLife());
        dao.close();

        format = new SimpleDateFormat("mm:ss");

        user = new User();

        /**
         * Initialise Time Life
         */
        dao.open();
        if(dao.getNbrLife() < Constance.NBR_LIFE_MAX){
            timeLife.setText(initializeTimeLife());
        }
        else{
            timeLife.setText("" + getResources().getString(R.string.libelle_full_life));
            dao.saveTimeLastLife("");
        }
        dao.close();


        // If user got INTERNET
       /*if(isOnline()){

        }
        else{
            time = System.currentTimeMillis();
        }*/

        if(android.provider.Settings.Global.getInt(getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 0) == 1){
            // Enabled
            dateActu.setTimeInMillis(System.currentTimeMillis());
            Log.i("MAIN ACTIVITY", "APPLICATION ENABLED");
        }
        else{
            // Disabled
            Log.i("MAIN ACTIVITY", "APPLICATION DISABLED");
        }

        dao.open();

        if(dao.getNbrLife() == -1){
            dao.setNbrLife(Constance.NBR_LIFE_MAX);
            nbrLife.setText("" + Constance.NBR_LIFE_MAX);
        }

        user.setNbrLife(dao.getNbrLife());
        nbrLife.setText("" + dao.getNbrLife());

        dao.close();

        Log.i("BEFORE NEW REFRESH", "DateLAstLife : " + dateLastLife);

        taskRefresh = new RefreshLifeUser(user, dateLastLife, new SyncTimeLife() {
            @Override
            public void onTaskCompleted(User u) {
                timeLife.setText(refreshLife(u));
            }
        });

        dao.open();

        if(dao.getNbrLife() < Constance.NBR_LIFE_MAX) {
            taskRefresh.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else{
            if(taskRefresh.getStatus() != AsyncTask.Status.RUNNING){
                timeLife.setText(getResources().getString(R.string.libelle_full_life));
                dao.saveTimeLastLife("");
            }
        }

        dao.close();

        /**
         * CLICK ON PLAY
         */
        linearPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    taskRefresh.cancel(true);
                }
                catch (Exception e){
                    Log.i("CLICK ON PLAY", "Impossible d'arreter aynsctask refresh life");
                }

                Intent intent = new Intent(MainActivity.this, PlayActivity.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("user", user);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        /**
         * CLICK ON SHOP
         */
        linearShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dao.open();

                if(dao.getNbrLife() == 0){
                    dao.setNbrLife(10);
                    Toast.makeText(getApplicationContext(), "Vous obtenes 10 vies supplémentaires", Toast.LENGTH_LONG).show();
                    nbrLife.setText("" + 10);
                    user.setNbrLife(dao.getNbrLife());
                }
                dao.close();

                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });

        /**
         * CLICK ON SETTING
         */
        linearSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        /**
         * CLICK ON RATE
         */
        linearRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public String refreshLife(User u){
        String result = "";
        user.setTimeLastLife(u.getTimeLastLife());
        user.setNbrLife(u.getNbrLife());

        if(user.getNbrLife() == Constance.NBR_LIFE_MAX){
            if(taskRefresh.getStatus() == AsyncTask.Status.RUNNING){
                result = getResources().getString(R.string.libelle_full_life);
                dao.open();
                dao.saveTimeLastLife("");
                dao.close();
                taskRefresh.cancel(true);
            }
        }
        else{
            long diff = user.getTimeLastLife();
            long seconds = diff/1000;
            long minutes = seconds/60;

            if(Constance.TIME_BETWEEN_LIFE - minutes <= 0 && seconds%60 <= 60){
                if(user.getNbrLife() < Constance.NBR_LIFE_MAX){
                    user.setNbrLife(user.getNbrLife() + 1);

                    dao.open();

                    dao.setNbrLife(user.getNbrLife());

                    if(user.getNbrLife() >= Constance.NBR_LIFE_MAX){
                        dao.saveTimeLastLife("");
                    }
                    else{
                        long timeActu = System.currentTimeMillis();
                        dateLastLife.setTimeInMillis(timeActu);
                        user.setTimeLastLife(timeActu);
                        dao.saveTimeLastLife(String.valueOf(timeActu));
                    }


                    dao.close();
                }
                else{
                    if(taskRefresh.getStatus() == AsyncTask.Status.RUNNING){
                        result = getResources().getString(R.string.libelle_full_life);
                        dao.open();
                        dao.saveTimeLastLife("");
                        dao.close();
                        taskRefresh.cancel(true);
                    }
                }
            }

            long minutesRestantes = (Constance.TIME_BETWEEN_LIFE - 1 - user.getTimeLastLife()/1000/60);
            int secondStart = 60;

            if(seconds%60 == 0){
                secondStart = 0;
            }

            long secondesRestantes = (secondStart - (user.getTimeLastLife()/1000)%60);

            if(secondesRestantes%60 == 0){
                minutesRestantes += 1;
            }

            if(taskRefresh.getStatus() == AsyncTask.Status.RUNNING){
                result = "" +  minutesRestantes + ":" + Constance.addZero(String.valueOf(secondesRestantes));
            }

            nbrLife.setText("" + user.getNbrLife());
        }

        return result;
    }

    public String initializeTimeLife(){
        String result;

        dao.open();

        if(!dao.getTimeLastLife().equals("")) {
            dateLastLife.setTimeInMillis(Long.valueOf(dao.getTimeLastLife()));

            long diff = dateActu.getTimeInMillis() - Long.valueOf(dao.getTimeLastLife());
            long seconds = diff / 1000;
            long minutes = seconds / 60;

            Log.i("MAIN ACTIVITY", "Date actu : " + dateActu.getTimeInMillis() + " Date Last : " + dao.getTimeLastLife());

            long minutesRestantes;
            long secondesRestantes;
            long secondStart;

            if(minutes >= Constance.TIME_BETWEEN_LIFE){
                int nbrLifeAdded = (int) (minutes/Constance.TIME_BETWEEN_LIFE);


                if(dao.getNbrLife() + nbrLifeAdded <= Constance.NBR_LIFE_MAX){
                    try{
                        user.setNbrLife(dao.getNbrLife() + nbrLifeAdded);
                        dao.setNbrLife(dao.getNbrLife() + nbrLifeAdded);
                        nbrLife.setText("" + dao.getNbrLife() + nbrLifeAdded);
                    }
                    catch(Exception e){
                        Log.i("ADDING LIFE USER", "Exception e : " + e);
                    }
                }
                else{
                    try{
                        // Si les vies ajoutées dépassent le MAX_LIFE de l'utilisateur
                        user.setNbrLife(Constance.NBR_LIFE_MAX);
                        dao.setNbrLife(Constance.NBR_LIFE_MAX);
                        nbrLife.setText("" + Constance.NBR_LIFE_MAX);

                        result = getResources().getString(R.string.libelle_full_life);
                        dao.open();
                        dao.saveTimeLastLife("");
                        dao.close();
                        return result;
                    }
                    catch(Exception e){
                        Log.i("ADDING LIFE MAX USER", "Exception e : " + e);
                    }
                }

                minutesRestantes = minutes%Constance.TIME_BETWEEN_LIFE;

                secondStart = 60;

                if (seconds % 60 == 0) {
                    secondStart = 0;
                }

                secondesRestantes = (secondStart - (diff / 1000) % 60);

                if (secondesRestantes % 60 == 0) {
                    minutesRestantes += 1;
                }

                dateLastLife = dateActu;
                dateLastLife.set(Calendar.MINUTE, dateActu.get(Calendar.MINUTE) - (int) minutesRestantes);
                dateLastLife.set(Calendar.SECOND, dateActu.get(Calendar.SECOND) - (60 - (int) secondesRestantes));

                //dateLastLife.setTimeInMillis(minutesRestantes*60*1000);
                user.setTimeLastLife(dateLastLife.getTimeInMillis());
                dao.saveTimeLastLife(String.valueOf(user.getTimeLastLife()));
            }
            else{
                minutesRestantes = (Constance.TIME_BETWEEN_LIFE - 1 - diff / 1000 / 60);
                secondStart = 60;

                if (seconds % 60 == 0) {
                    secondStart = 0;
                }

                secondesRestantes = (secondStart - (diff / 1000) % 60);

                if (secondesRestantes % 60 == 0) {
                    minutesRestantes += 1;
                }
            }
            result = "" + minutesRestantes + ":" + Constance.addZero(String.valueOf(secondesRestantes));

        }
        else{
            result = getResources().getString(R.string.libelle_full_life);
            dao.saveTimeLastLife("");
        }

        dao.close();

        return result;
    }
}
