package com.softcaze.nicolas.colorchange.Activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softcaze.nicolas.colorchange.AsyncTask.RefreshLifeUser;
import com.softcaze.nicolas.colorchange.Database.DAO;
import com.softcaze.nicolas.colorchange.Interface.SyncIsAutoTime;
import com.softcaze.nicolas.colorchange.Interface.SyncTimeLife;
import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.Model.Sounds;
import com.softcaze.nicolas.colorchange.Model.User;
import com.softcaze.nicolas.colorchange.Model.World;
import com.softcaze.nicolas.colorchange.R;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlayActivity extends Activity {
    TextView tab_1, tab_2, txt_monde_1, txt_monde_2, txt_monde_3, txt_monde_4, nbr_star_1, nbr_star_2, nbr_star_3, nbr_star_4, nbr_star_to_lock_1, nbr_star_to_lock_2, nbr_star_to_lock_3, nbr_star_to_lock_4;
    RelativeLayout container_play, tab_1_container, tab_2_container, container_monde_1, container_monde_2, container_monde_3, container_monde_4, container_lock_w1, container_lock_w2, container_lock_w3, container_lock_w4;
    World w1, w2, w3, w4;
    List<World> listWorld = new ArrayList<World>();
    ImageView img_monde_1, img_monde_2, img_monde_3, img_monde_4;
    User user;
    ImageView circle_w1, circle_w2, circle_w3, circle_w4, heart;
    View lane_1_w2, lane_1_w3, lane_1_w4, lane_3_w2, lane_3_w3, lane_3_w4;
    RelativeLayout lane_2_w2, lane_2_w3, lane_2_w4;
    TextView nbrLife, timeLife, btn_popin;
    protected DAO dao = null;
    protected int HAUTEUR_ECRAN;
    protected long time;
    protected RefreshLifeUser taskRefresh;
    protected Calendar dateActu = Calendar.getInstance();
    protected SimpleDateFormat format;
    protected Calendar dateLastLife = Calendar.getInstance();
    protected RelativeLayout popinTime;
    protected MediaPlayer clickBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        HAUTEUR_ECRAN = getWindowManager().getDefaultDisplay().getHeight();

        dao = new DAO(this);
        user = (User) getIntent().getSerializableExtra("user");

        clickBtn = MediaPlayer.create(getApplicationContext(), R.raw.click_btn);

        format = new SimpleDateFormat("mm:ss");

        w1 = new World(1, getResources().getString(R.string.tab_text_1) +  " 1", R.drawable.americanfootball, Constance.NBR_STAR_UNLOCK_W1, HAUTEUR_ECRAN);
        w2 = new World(2, getResources().getString(R.string.tab_text_1) +  " 2", R.drawable.racing, Constance.NBR_STAR_UNLOCK_W2, HAUTEUR_ECRAN);
        w3 = new World(3, getResources().getString(R.string.tab_text_1) +  " 3", R.drawable.aireplane, Constance.NBR_STAR_UNLOCK_W3, HAUTEUR_ECRAN);
        w4 = new World(4, getResources().getString(R.string.tab_text_1) +  " 4", R.drawable.kayak, Constance.NBR_STAR_UNLOCK_W4, HAUTEUR_ECRAN);

        listWorld.add(w1);
        listWorld.add(w2);
        listWorld.add(w3);
        listWorld.add(w4);

        initView();

        MainActivity.taskCheckAutoTime.setListener(new SyncIsAutoTime() {
            @Override
            public void checkingAutoTime(Boolean isAuto) {
                checkAutoTime();
            }
        });

        /**
         * Initialise Time Life
         */
        dao.open();
        if(dao.getNbrLife() < Constance.NBR_LIFE_MAX){
            timeLife.setText(initializeTimeLife());
        }
        else{
            dao.saveTimeLastLife("");
            timeLife.setText("" + getResources().getString(R.string.libelle_full_life));
        }
        dao.close();

        loadDataBDD();

        checkLockWorld();

        /***********************************/
        /*** SET ON CLICK ONGLET "WORLD" ***/
        /***********************************/
        tab_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popinTime.getVisibility() != View.VISIBLE) {
                    container_play.setBackgroundColor(getResources().getColor(R.color.red));
                    tab_1_container.setVisibility(View.VISIBLE);
                    tab_2_container.setVisibility(View.GONE);
                    playSounds(clickBtn);
                }
            }
        });

        /***********************************/
        /*** SET ON CLICK ONGLET "SPEED" ***/
        /***********************************/
        tab_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popinTime.getVisibility() != View.VISIBLE) {
                    container_play.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    tab_1_container.setVisibility(View.GONE);
                    tab_2_container.setVisibility(View.VISIBLE);
                    playSounds(clickBtn);
                }
            }
        });

        btn_popin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAutoTime();
            }
        });

        /******************************/
        /*** SET ON CLICK "WORLD 1" ***/
        /******************************/
        container_monde_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popinTime.getVisibility() != View.VISIBLE) {
                    if (user.getNbrStar() >= w1.getNbrStarToLock()) {
                        try {
                            taskRefresh.cancel(true);
                        } catch (Exception e) {
                            Log.i("PLAY ACTIVITY", "Impossible d'arreter aynsctask refresh life");
                        }

                        playSounds(clickBtn);

                        Intent intent = new Intent(PlayActivity.this, ListLevelActivity.class);

                        Bundle b = new Bundle();

                        b.putSerializable("listWorld", (Serializable) listWorld);
                        b.putSerializable("worldClicked", w1);
                        b.putSerializable("user", user);

                        intent.putExtras(b);
                        startActivity(intent);
                    }
                }
            }
        });

        /******************************/
        /*** SET ON CLICK "WORLD 2" ***/
        /******************************/
        container_monde_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popinTime.getVisibility() != View.VISIBLE) {
                    if (user.getNbrStar() >= w2.getNbrStarToLock()) {
                        try {
                            taskRefresh.cancel(true);
                        } catch (Exception e) {
                            Log.i("PLAY ACTIVITY", "Impossible d'arreter aynsctask refresh life");
                        }

                        playSounds(clickBtn);

                        Intent intent = new Intent(PlayActivity.this, ListLevelActivity.class);

                        Bundle b = new Bundle();

                        b.putSerializable("worldClicked", w2);
                        b.putSerializable("listWorld", (Serializable) listWorld);
                        b.putSerializable("user", user);

                        intent.putExtras(b);
                        startActivity(intent);
                    }
                }
            }
        });

        /******************************/
        /*** SET ON CLICK "WORLD 3" ***/
        /******************************/
        container_monde_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popinTime.getVisibility() != View.VISIBLE) {
                    if (user.getNbrStar() >= w3.getNbrStarToLock()) {
                        try {
                            taskRefresh.cancel(true);
                        } catch (Exception e) {
                            Log.i("PLAY ACTIVITY", "Impossible d'arreter aynsctask refresh life");
                        }

                        playSounds(clickBtn);

                        Intent intent = new Intent(PlayActivity.this, ListLevelActivity.class);

                        Bundle b = new Bundle();

                        b.putSerializable("worldClicked", w3);
                        b.putSerializable("listWorld", (Serializable) listWorld);
                        b.putSerializable("user", user);

                        intent.putExtras(b);
                        startActivity(intent);
                    }
                }
            }
        });

        /******************************/
        /*** SET ON CLICK "WORLD 4" ***/
        /******************************/
        container_monde_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popinTime.getVisibility() != View.VISIBLE) {
                    if (user.getNbrStar() >= w4.getNbrStarToLock()) {

                        try {
                            taskRefresh.cancel(true);
                        } catch (Exception e) {
                            Log.i("PLAY ACTIVITY", "Impossible d'arreter aynsctask refresh life");
                        }

                        playSounds(clickBtn);

                        Intent intent = new Intent(PlayActivity.this, ListLevelActivity.class);

                        Bundle b = new Bundle();

                        b.putSerializable("worldClicked", w4);
                        b.putSerializable("listWorld", (Serializable) listWorld);
                        b.putSerializable("user", user);

                        intent.putExtras(b);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public void initView(){
        popinTime = (RelativeLayout) findViewById(R.id.popinTime);
        container_play = (RelativeLayout) findViewById(R.id.container_play);
        tab_1_container = (RelativeLayout) findViewById(R.id.tab_1_container);
        tab_2_container = (RelativeLayout) findViewById(R.id.tab_2_container);

        tab_1 = (TextView) findViewById(R.id.tab_1);
        tab_2 = (TextView) findViewById(R.id.tab_2);

        btn_popin = (TextView) findViewById(R.id.btn_popin);
        txt_monde_1 = (TextView) findViewById(R.id.txt_monde_1);
        txt_monde_2 = (TextView) findViewById(R.id.txt_monde_2);
        txt_monde_3 = (TextView) findViewById(R.id.txt_monde_3);
        txt_monde_4 = (TextView) findViewById(R.id.txt_monde_4);

        nbr_star_1 = (TextView) findViewById(R.id.nbr_star_1);
        nbr_star_2 = (TextView) findViewById(R.id.nbr_star_2);
        nbr_star_3 = (TextView) findViewById(R.id.nbr_star_3);
        nbr_star_4 = (TextView) findViewById(R.id.nbr_star_4);

        nbr_star_to_lock_1 = (TextView) findViewById(R.id.nbr_star_to_lock_1);
        nbr_star_to_lock_2 = (TextView) findViewById(R.id.nbr_star_to_lock_2);
        nbr_star_to_lock_3 = (TextView) findViewById(R.id.nbr_star_to_lock_3);
        nbr_star_to_lock_4 = (TextView) findViewById(R.id.nbr_star_to_lock_4);

        nbrLife = (TextView) findViewById(R.id.nbrLife);
        timeLife = (TextView) findViewById(R.id.timeLife);

        img_monde_1 = (ImageView) findViewById(R.id.img_monde_1);
        img_monde_2 = (ImageView) findViewById(R.id.img_monde_2);
        img_monde_3 = (ImageView) findViewById(R.id.img_monde_3);
        img_monde_4 = (ImageView) findViewById(R.id.img_monde_4);
        heart = (ImageView) findViewById(R.id.heart);

        container_monde_1 = (RelativeLayout) findViewById(R.id.container_monde_1);
        container_monde_2 = (RelativeLayout) findViewById(R.id.container_monde_2);
        container_monde_3 = (RelativeLayout) findViewById(R.id.container_monde_3);
        container_monde_4 = (RelativeLayout) findViewById(R.id.container_monde_4);

        container_lock_w1 = (RelativeLayout) findViewById(R.id.container_lock_w1);
        container_lock_w2 = (RelativeLayout) findViewById(R.id.container_lock_w2);
        container_lock_w3 = (RelativeLayout) findViewById(R.id.container_lock_w3);
        container_lock_w4 = (RelativeLayout) findViewById(R.id.container_lock_w4);

        circle_w1 = (ImageView) findViewById(R.id.circle_w1);
        circle_w2 = (ImageView) findViewById(R.id.circle_w2);
        circle_w3 = (ImageView) findViewById(R.id.circle_w3);
        circle_w4 = (ImageView) findViewById(R.id.circle_w4);

        lane_1_w2 = (View) findViewById(R.id.lane_1_w2);
        lane_3_w2 = (View) findViewById(R.id.lane_3_w2);
        lane_1_w3 = (View) findViewById(R.id.lane_1_w3);
        lane_3_w3 = (View) findViewById(R.id.lane_3_w3);
        lane_1_w4 = (View) findViewById(R.id.lane_1_w4);
        lane_3_w4 = (View) findViewById(R.id.lane_3_w4);

        lane_2_w2 = (RelativeLayout) findViewById(R.id.lane_2_w2);
        lane_2_w3 = (RelativeLayout) findViewById(R.id.lane_2_w3);
        lane_2_w4 = (RelativeLayout) findViewById(R.id.lane_2_w4);

        // SET NAME WORLD
        txt_monde_1.setText(w1.getName());
        txt_monde_2.setText(w2.getName());
        txt_monde_3.setText(w3.getName());
        txt_monde_4.setText(w4.getName());

        // SET IMAGE WORLD
        img_monde_1.setImageResource(w1.getImg());
        img_monde_2.setImageResource(w2.getImg());
        img_monde_3.setImageResource(w3.getImg());
        img_monde_4.setImageResource(w4.getImg());

        // SET NBR STAR
        dao.open();

        nbr_star_1.setText(String.valueOf(dao.getNbrStarByWorld(w1)));
        nbr_star_2.setText(String.valueOf(dao.getNbrStarByWorld(w2)));
        nbr_star_3.setText(String.valueOf(dao.getNbrStarByWorld(w3)));
        nbr_star_4.setText(String.valueOf(dao.getNbrStarByWorld(w4)));

        dao.close();

        taskRefresh = new RefreshLifeUser(user, dateLastLife, getContentResolver(), new SyncTimeLife() {
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
                dao.saveTimeLastLife("");
                timeLife.setText(getResources().getString(R.string.libelle_full_life));
            }
        }

        dao.close();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        try{
            taskRefresh.cancel(true);
        }
        catch (Exception e){
            Log.i("PLAY ACTIVITY", "Impossible d'arreter aynsctask refresh life");
        }

        Intent intent = new Intent(PlayActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * LOADING DATA BDD
     */
    public void loadDataBDD(){
        dao.open();

        user.setNbrLife(dao.getNbrLife());

        int nbrStar = 0;

        for(int i = 0; i < listWorld.size(); i++){
            nbrStar += dao.getNbrStarByWorld(listWorld.get(i));
        }

        nbrLife.setText("" + dao.getNbrLife());

        dao.close();

        user.setNbrStar(nbrStar);

        //nbrLife.setText("" + user.getNbrLife());
    }

    public void checkLockWorld(){

        // W1
        if(user.getNbrStar() >= w1.getNbrStarToLock()){
            container_lock_w1.setVisibility(View.INVISIBLE);
            circle_w1.setBackground(getResources().getDrawable(R.drawable.circle));
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(Constance.getDpSize(45, this), Constance.getDpSize(45, this));
            lp.setMargins(0, Constance.getDpSize(15, this), Constance.getDpSize(30, this), 0);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            circle_w1.setLayoutParams(lp);
        }
        else{
            nbr_star_to_lock_1.setText(getStarMissed(w1));
            container_lock_w1.setVisibility(View.VISIBLE);
            circle_w1.setBackground(getResources().getDrawable(R.drawable.circle_white));
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(Constance.getDpSize(45, this), Constance.getDpSize(45, this));
            lp.setMargins(0, Constance.getDpSize(15, this), Constance.getDpSize(30, this), 0);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            circle_w1.setLayoutParams(lp);
        }

        // W2
        if(user.getNbrStar() >= w2.getNbrStarToLock()){
            container_lock_w2.setVisibility(View.INVISIBLE);
            circle_w2.setBackground(getResources().getDrawable(R.drawable.circle));
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(Constance.getDpSize(45, this), Constance.getDpSize(45, this));
            lp.setMargins(Constance.getDpSize(30, this), Constance.getDpSize(15, this), 0, 0);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            circle_w2.setLayoutParams(lp);

            lane_1_w2.setBackgroundColor(getResources().getColor(R.color.yellow));
            lane_2_w2.setBackgroundColor(getResources().getColor(R.color.yellow));
            lane_3_w2.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
        else{
            nbr_star_to_lock_2.setText(getStarMissed(w2));
            container_lock_w2.setVisibility(View.VISIBLE);

            circle_w2.setBackground(getResources().getDrawable(R.drawable.circle_white));
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(Constance.getDpSize(45, this), Constance.getDpSize(45, this));
            lp.setMargins(Constance.getDpSize(30, this), Constance.getDpSize(15, this), 0, 0);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            circle_w2.setLayoutParams(lp);

            lane_1_w2.setBackgroundColor(getResources().getColor(R.color.white));
            lane_2_w2.setBackgroundColor(getResources().getColor(R.color.white));
            lane_3_w2.setBackgroundColor(getResources().getColor(R.color.white));
        }

        // W3
        if(user.getNbrStar() >= w3.getNbrStarToLock()){
            container_lock_w3.setVisibility(View.INVISIBLE);
            circle_w3.setBackground(getResources().getDrawable(R.drawable.circle));
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(Constance.getDpSize(45, this), Constance.getDpSize(45, this));
            lp.setMargins(0, Constance.getDpSize(15, this), Constance.getDpSize(30, this), 0);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            circle_w3.setLayoutParams(lp);

            lane_1_w3.setBackgroundColor(getResources().getColor(R.color.yellow));
            lane_2_w3.setBackgroundColor(getResources().getColor(R.color.yellow));
            lane_3_w3.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
        else{
            nbr_star_to_lock_3.setText(getStarMissed(w3));
            container_lock_w3.setVisibility(View.VISIBLE);

            circle_w3.setBackground(getResources().getDrawable(R.drawable.circle_white));
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(Constance.getDpSize(45, this), Constance.getDpSize(45, this));
            lp.setMargins(0, Constance.getDpSize(15, this), Constance.getDpSize(30, this), 0);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            circle_w3.setLayoutParams(lp);

            lane_1_w3.setBackgroundColor(getResources().getColor(R.color.white));
            lane_2_w3.setBackgroundColor(getResources().getColor(R.color.white));
            lane_3_w3.setBackgroundColor(getResources().getColor(R.color.white));
        }

        // W4
        if(user.getNbrStar() >= w4.getNbrStarToLock()){
            container_lock_w4.setVisibility(View.INVISIBLE);
            circle_w4.setBackground(getResources().getDrawable(R.drawable.circle));
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(Constance.getDpSize(45, this), Constance.getDpSize(45, this));
            lp.setMargins(Constance.getDpSize(30, this), Constance.getDpSize(15, this), 0, 0);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            circle_w4.setLayoutParams(lp);

            lane_1_w4.setBackgroundColor(getResources().getColor(R.color.yellow));
            lane_2_w4.setBackgroundColor(getResources().getColor(R.color.yellow));
            lane_3_w4.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
        else{
            nbr_star_to_lock_4.setText(getStarMissed(w4));
            container_lock_w4.setVisibility(View.VISIBLE);

            circle_w4.setBackground(getResources().getDrawable(R.drawable.circle_white));
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(Constance.getDpSize(45, this), Constance.getDpSize(45, this));
            lp.setMargins(Constance.getDpSize(30, this), Constance.getDpSize(15, this), 0, 0);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            circle_w4.setLayoutParams(lp);
            lane_1_w4.setBackgroundColor(getResources().getColor(R.color.white));
            lane_2_w4.setBackgroundColor(getResources().getColor(R.color.white));
            lane_3_w4.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    public String getStarMissed(World w){
        return String.valueOf(w.getNbrStarToLock() - user.getNbrStar());
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

            Log.i("PLAY ACTIVITY", "Date actu : " + dateActu.getTimeInMillis() + " Date Last : " + dao.getTimeLastLife());

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

                        dao.saveTimeLastLife("");
                        result = getResources().getString(R.string.libelle_full_life);
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
            dao.saveTimeLastLife("");
            result = getResources().getString(R.string.libelle_full_life);
        }

        dao.close();

        Log.i("PLAY ACTIVITY", "RESULT : " + result);

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
}
