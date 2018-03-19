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
import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.Model.User;
import com.softcaze.nicolas.colorchange.R;

import org.w3c.dom.Text;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    public static final String TIME_SERVER = "time-a.nist.gov";
    protected Calendar dateLastLife = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new DAO(this);

        format = new SimpleDateFormat("mm:ss");

        dao.open();
        if(!dao.getTimeLastLife().equals("")){
            dateLastLife.setTimeInMillis(Long.valueOf(dao.getTimeLastLife()));
        }

        dao.close();

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

        // If user got INTERNET
       /*if(isOnline()){

        }
        else{
            time = System.currentTimeMillis();
        }*/

        user = new User();

        dao.open();

        if(dao.getNbrLife() == -1){
            dao.setNbrLife(10);
            nbrLife.setText("" + 10);
        }

        user.setNbrLife(dao.getNbrLife());
        nbrLife.setText("" + dao.getNbrLife());

        taskRefresh = new RefreshLifeUser(user, dateLastLife);

        if(Constance.NBR_LIFE_MAX != dao.getNbrLife()) {
            taskRefresh.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            long diff = user.getTimeLastLife();
            long seconds = diff/1000;
            long minutes = seconds/60;

            Log.i("MAIN ACTIVITY ", "USER GET TIME : " + user.getTimeLastLife());
            timeLife.setText("" + (Constance.TIME_BETWEEN_LIFE  - user.getTimeLastLife()/1000/60) + ":" + (user.getTimeLastLife()/1000)%60);
        }
        else{
            timeLife.setText(getResources().getString(R.string.libelle_full_life));
        }

        dao.close();



        /**
         * CLICK ON PLAY
         */
        linearPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
}
