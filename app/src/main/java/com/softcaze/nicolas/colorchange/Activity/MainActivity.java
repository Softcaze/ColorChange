package com.softcaze.nicolas.colorchange.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softcaze.nicolas.colorchange.AsyncTask.CheckAutoTime;
import com.softcaze.nicolas.colorchange.AsyncTask.RefreshLifeUser;
import com.softcaze.nicolas.colorchange.Database.DAO;
import com.softcaze.nicolas.colorchange.Interface.SyncIsAutoTime;
import com.softcaze.nicolas.colorchange.Interface.SyncTimeLife;
import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.Model.Sounds;
import com.softcaze.nicolas.colorchange.Model.User;
import com.softcaze.nicolas.colorchange.R;

import org.w3c.dom.Text;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;

public class MainActivity extends FragmentActivity {
    protected TextView btn_play, btn_setting, btn_shop, btn_rate, nbrLife, timeLife, btn_popin;
    protected LinearLayout linearPlay, linearSetting, linearShop, linearRate;
    protected ImageView img_play, heart;
    protected LinearLayout.LayoutParams params;
    protected User user;
    protected DAO dao = null;
    protected long time;
    protected RefreshLifeUser taskRefresh;
    protected Calendar dateActu = Calendar.getInstance();
    protected SimpleDateFormat format;
    protected Calendar dateLastLife = Calendar.getInstance();
    protected RelativeLayout popinTime, containerPopin, relative_life;
    protected LinearLayout mainLinear;
    protected static CheckAutoTime taskCheckAutoTime;
    protected MediaPlayer clickBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popinTime = (RelativeLayout) findViewById(R.id.popinTime);
        containerPopin = (RelativeLayout) findViewById(R.id.containerPopin);
        relative_life = (RelativeLayout) findViewById(R.id.relative_life);
        btn_play = (TextView) findViewById(R.id.btn_play);
        btn_setting = (TextView) findViewById(R.id.btn_setting);
        btn_shop = (TextView) findViewById(R.id.btn_shop);
        btn_rate = (TextView) findViewById(R.id.btn_rate);
        nbrLife = (TextView) findViewById(R.id.nbrLife);
        timeLife = (TextView) findViewById(R.id.timeLife);
        btn_popin = (TextView) findViewById(R.id.btn_popin);
        linearPlay = (LinearLayout) findViewById(R.id.linearPlay);
        linearSetting = (LinearLayout) findViewById(R.id.linearSetting);
        linearShop = (LinearLayout) findViewById(R.id.linearShop);
        linearRate = (LinearLayout) findViewById(R.id.linearRate);
        mainLinear = (LinearLayout) findViewById(R.id.mainLinear);
        img_play = (ImageView) findViewById(R.id.img_play);
        heart = (ImageView) findViewById(R.id.heart);

        dao = new DAO(this);
        clickBtn = MediaPlayer.create(getApplicationContext(), R.raw.click_btn);

        format = new SimpleDateFormat("mm:ss");

        dao.open();

        if(dao.getPayLoad() == ""){
            RandomString randomString = new RandomString(36);

            String payLoad = randomString.nextString();

            dao.setPayLoad(payLoad);
        }

        if(dao.getStateSound() == -1){
            dao.setStateSound(Constance.SOUND_ENABLE);
        }
        dao.close();

        user = new User();

        if(taskCheckAutoTime == null){
            taskCheckAutoTime = new CheckAutoTime(getContentResolver(), new SyncIsAutoTime(){

                @Override
                public void checkingAutoTime(Boolean isAuto) {
                    checkAutoTime();
                }
            });

            taskCheckAutoTime.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else{
            MainActivity.taskCheckAutoTime.setListener(new SyncIsAutoTime() {
                @Override
                public void checkingAutoTime(Boolean isAuto) {
                    checkAutoTime();
                }
            });
        }


        /**
         * Initialise Time Life
         */


        dao.open();

        if(dao.getNbrLife() == -1){
            dao.setNbrLife(Constance.NBR_LIFE_MAX);
            nbrLife.setText("" + Constance.NBR_LIFE_MAX);
        }

        if(dao.getCountAD() == -1){
            dao.setCountAD(0);
        }

        user.setNbrLife(dao.getNbrLife());
        nbrLife.setText("" + dao.getNbrLife());

        if(dao.getNbrLife() < Constance.NBR_LIFE_MAX){
            timeLife.setText(initializeTimeLife());
        }
        else{
            dao.saveTimeLastLife("");
            timeLife.setText("" + getResources().getString(R.string.libelle_full_life));
        }
        dao.close();

        checkAutoTime();

        taskRefresh = new RefreshLifeUser(user, dateLastLife, getContentResolver(), new SyncTimeLife() {
            @Override
            public void onTaskCompleted(User u) {
                timeLife.setText(refreshLife(u));
            }
        });

        btn_popin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAutoTime();
            }
        });

        dao.open();

        if(dao.getNbrLife() < Constance.NBR_LIFE_MAX) {
            timeLife.setText(initializeTimeLife());
            taskRefresh.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else{
            if(taskRefresh.getStatus() != AsyncTask.Status.RUNNING){
                timeLife.setText(getResources().getString(R.string.libelle_full_life));
                dao.saveTimeLastLife("");
            }
        }

        dao.close();

        relative_life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });

        /**
         * CLICK ON PLAY
         */
        linearPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popinTime.getVisibility() != View.VISIBLE){
                    try{
                        taskRefresh.cancel(true);
                    }
                    catch (Exception e){
                        Log.i("CLICK ON PLAY", "Impossible d'arreter aynsctask refresh life");
                    }

                    playSounds(clickBtn);

                    Intent intent = new Intent(MainActivity.this, PlayActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putSerializable("user", user);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        /**
         * CLICK ON SHOP
         */
        linearShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popinTime.getVisibility() != View.VISIBLE) {
                    dao.open();

                    if (dao.getNbrLife() == 0) {
                        dao.setNbrLife(10);
                        Toast.makeText(getApplicationContext(), "Vous obtenes 10 vies supplémentaires", Toast.LENGTH_LONG).show();
                        nbrLife.setText("" + 10);
                        user.setNbrLife(dao.getNbrLife());
                    }
                    dao.close();

                    playSounds(clickBtn);

                    Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                    startActivity(intent);
                }
            }
        });

        /**
         * CLICK ON SETTING
         */
        linearSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popinTime.getVisibility() != View.VISIBLE) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);

                    playSounds(clickBtn);
                }
            }
        });

        /**
         * CLICK ON RATE
         */
        linearRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popinTime.getVisibility() != View.VISIBLE){
                    playSounds(clickBtn);
                }
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
            if(minutesRestantes < 0){
                minutesRestantes = 0;
            }

            int secondStart = 60;

            if(seconds%60 == 0){
                secondStart = 0;
            }

            long secondesRestantes = (secondStart - (user.getTimeLastLife()/1000)%60);

            if(secondesRestantes%60 == 0){
                minutesRestantes += 1;
            }

            if(secondesRestantes < 0){
                secondesRestantes = 0;
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

            long minutesRestantes;
            long secondesRestantes;
            long secondStart;

            if(minutes >= Constance.TIME_BETWEEN_LIFE){
                int nbrLifeAdded = (int) (minutes/Constance.TIME_BETWEEN_LIFE);


                if(dao.getNbrLife() + nbrLifeAdded <= Constance.NBR_LIFE_MAX){
                    try{
                        user.setNbrLife(dao.getNbrLife() + nbrLifeAdded);
                        dao.setNbrLife(dao.getNbrLife() + nbrLifeAdded);
                        nbrLife.setText("" + dao.getNbrLife());
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
                if(minutesRestantes < 0){
                    minutesRestantes = 0;
                }
                secondStart = 60;

                if (seconds % 60 == 0) {
                    secondStart = 0;
                }

                secondesRestantes = (secondStart - (diff / 1000) % 60);

                if (secondesRestantes % 60 == 0) {
                    minutesRestantes += 1;
                }

                if(secondesRestantes < 0){
                    secondesRestantes = 0;
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
                if(minutesRestantes < 0){
                    minutesRestantes = 0;
                }
                secondStart = 60;

                if (seconds % 60 == 0) {
                    secondStart = 0;
                }

                secondesRestantes = (secondStart - (diff / 1000) % 60);

                if (secondesRestantes % 60 == 0) {
                    minutesRestantes += 1;
                }

                if(secondesRestantes < 0){
                    secondesRestantes = 0;
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

    public void checkAutoTime(){
        if(android.provider.Settings.Global.getInt(getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 0) == 1){
            // Enabled
            popinTime.setVisibility(View.GONE);
            timeLife.setVisibility(View.VISIBLE);
            nbrLife.setVisibility(View.VISIBLE);
        }
        else {
            // Disabled
            popinTime.setVisibility(View.VISIBLE);
            timeLife.setVisibility(View.GONE);
            nbrLife.setVisibility(View.GONE);
        }
    }

    public void playSounds(MediaPlayer md){
        try{
            dao.open();

            if(dao.getStateSound() == Constance.SOUND_ENABLE){
                md.setVolume(1.0f, 1.0f);
                md.start();
            }

            dao.close();
        }
        catch (Exception e){
            Log.i("List Level Activity", "Sounds play : " + e);
        }
    }

    private static final char[] symbols = new char[36];

    static {
        for (int idx = 0; idx < 10; ++idx)
            symbols[idx] = (char) ('0' + idx);
        for (int idx = 10; idx < 36; ++idx)
            symbols[idx] = (char) ('a' + idx - 10);
    }

    public class RandomString {

        /*
         * static { for (int idx = 0; idx < 10; ++idx) symbols[idx] = (char)
         * ('0' + idx); for (int idx = 10; idx < 36; ++idx) symbols[idx] =
         * (char) ('a' + idx - 10); }
         */

        private final Random random = new Random();

        private final char[] buf;

        public RandomString(int length) {
            if (length < 1)
                throw new IllegalArgumentException("length < 1: " + length);
            buf = new char[length];
        }

        public String nextString() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return new String(buf);
        }

    }

    public final class SessionIdentifierGenerator {

        private SecureRandom random = new SecureRandom();

        public String nextSessionId() {
            return new BigInteger(130, random).toString(32);
        }
    }
}
