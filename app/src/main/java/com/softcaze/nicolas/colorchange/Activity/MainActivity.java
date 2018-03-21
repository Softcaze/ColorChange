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

        format = new SimpleDateFormat("mm:ss");

        /**
         * Initialise Time Life
         */
        timeLife.setText(initializeTimeLife());

        // If user got INTERNET
       /*if(isOnline()){

        }
        else{
            time = System.currentTimeMillis();
        }*/

        user = new User();

        dao.open();

        if(dao.getNbrLife() == -1){
            dao.setNbrLife(Constance.NBR_LIFE_MAX);
            nbrLife.setText("" + Constance.NBR_LIFE_MAX);
        }

        user.setNbrLife(dao.getNbrLife());
        nbrLife.setText("" + dao.getNbrLife());

        dao.close();

        taskRefresh = new RefreshLifeUser(user, dateLastLife, new SyncTimeLife() {
            @Override
            public void onTaskCompleted(User u) {
                timeLife.setText(refreshLife(u));
            }
        });

        dao.open();

        if(Constance.NBR_LIFE_MAX != dao.getNbrLife()) {
            taskRefresh.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else{
            if(taskRefresh.getStatus() != AsyncTask.Status.RUNNING){
                timeLife.setText(getResources().getString(R.string.libelle_full_life));
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
            }
        });

        /**
         * CLICK ON SETTING
         */
        linearSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
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
                        user.setTimeLastLife(timeActu - dateLastLife.getTimeInMillis());
                        dao.saveTimeLastLife(String.valueOf(timeActu));
                    }


                    dao.close();
                }
                else{
                    if(taskRefresh.getStatus() == AsyncTask.Status.RUNNING){
                        result = getResources().getString(R.string.libelle_full_life);
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

        if(!dao.getTimeLastLife().equals("")){
            dateLastLife.setTimeInMillis(Long.valueOf(dao.getTimeLastLife()));

            long diff = dateActu.getTimeInMillis() - Long.valueOf(dao.getTimeLastLife());
            long seconds = diff/1000;
            long minutes = seconds/60;

            long minutesRestantes = (Constance.TIME_BETWEEN_LIFE - 1 - diff/1000/60);
            int secondStart = 60;

            if(seconds%60 == 0){
                secondStart = 0;
            }

            long secondesRestantes = (secondStart - (diff/1000)%60);

            if(secondesRestantes%60 == 0){
                minutesRestantes += 1;
            }

            result = "" +  minutesRestantes + ":" + Constance.addZero(String.valueOf(secondesRestantes));
        }
        else{
            result = getResources().getString(R.string.libelle_full_life);
        }

        dao.close();

        return result;
    }
}
