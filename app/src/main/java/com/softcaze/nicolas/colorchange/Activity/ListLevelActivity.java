package com.softcaze.nicolas.colorchange.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.softcaze.nicolas.colorchange.Adapter.ListLevelAdapter;
import com.softcaze.nicolas.colorchange.AsyncTask.RefreshLifeUser;
import com.softcaze.nicolas.colorchange.Database.DAO;
import com.softcaze.nicolas.colorchange.Interface.SyncTimeLife;
import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.Model.User;
import com.softcaze.nicolas.colorchange.Model.World;
import com.softcaze.nicolas.colorchange.R;
import com.softcaze.nicolas.colorchange.View.Game;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListLevelActivity extends Activity {
    World myWorld;
    List<World> listWorld = new ArrayList<World>();

    TextView txt_monde_1, nbr_star, nbrLife, timeLife;
    ImageView img_monde, left_arrow, right_arrow;

    ListLevelAdapter adapter;
    ListView listViewLevel;

    DAO dao = null;

    User user;

    protected long time;
    protected RefreshLifeUser taskRefresh;
    protected Calendar dateActu = Calendar.getInstance();
    protected SimpleDateFormat format;
    protected Calendar dateLastLife = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_level);

        dao = new DAO(this);

        format = new SimpleDateFormat("mm:ss");

        initView();

        /**
         * Initialise Time Life
         */
        timeLife.setText(initializeTimeLife());

        // LOAD WORLD
        myWorld = (World) getIntent().getSerializableExtra("worldClicked");
        listWorld = (List<World>) getIntent().getSerializableExtra("listWorld");
        user = (User) getIntent().getSerializableExtra("user");

        loadDataBDD();

        adapter = new ListLevelAdapter(this, myWorld.getlevels());

        listViewLevel.setAdapter(adapter);

        txt_monde_1.setText(myWorld.getName());
        nbr_star.setText(String.valueOf(myWorld.getNumberStars()));
        img_monde.setImageResource(myWorld.getImg());

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

        /*************************/
        /*** RIGHT ARROW CLICK ***/
        /*************************/
        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myWorld = new World(getNext(myWorld));

                loadDataBDD();

                txt_monde_1.setText(myWorld.getName());
                nbr_star.setText(String.valueOf(myWorld.getNumberStars()));
                img_monde.setImageResource(myWorld.getImg());

                adapter = new ListLevelAdapter(getApplicationContext(), myWorld.getlevels());

                listViewLevel.setAdapter(adapter);
            }
        });

        /************************/
        /*** LEFT ARROW CLICK ***/
        /************************/
        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myWorld = new World(getPrevious(myWorld));

                loadDataBDD();

                txt_monde_1.setText(myWorld.getName());
                nbr_star.setText(String.valueOf(myWorld.getNumberStars()));
                img_monde.setImageResource(myWorld.getImg());

                adapter = new ListLevelAdapter(getApplicationContext(), myWorld.getlevels());

                listViewLevel.setAdapter(adapter);
            }
        });

        listViewLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(user.getNbrLife() > 0){
                    try{
                        taskRefresh.cancel(true);
                    }
                    catch (Exception e){
                        Log.i("LIST LEVEL", "Impossible d'arreter aynsctask refresh life");
                    }

                    Intent intent = new Intent(ListLevelActivity.this, TransitionActivityView.class);

                    Bundle b = new Bundle();

                    b.putSerializable("levelClicked", myWorld.getlevels().get(position));
                    b.putSerializable("worldActu", myWorld);
                    b.putSerializable("listWorld", (Serializable) listWorld);
                    b.putSerializable("user", user);

                    intent.putExtras(b);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Plus de vie", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // GET previous World
    public World getPrevious(World w){
        World t = new World(listWorld.get(0));
        int num = w.getNum();

        int nbrWorldUnlock = 0;

        for(int i = 0; i < listWorld.size(); i++){
            if(user.getNbrStar() >= listWorld.get(i).getNbrStarToLock()){
                nbrWorldUnlock++;
            }
        }

        for(int i = 0; i < nbrWorldUnlock; i++){
            if(num == listWorld.get(i).getNum()){
                if(i == 0){
                    if(user.getNbrStar() >= listWorld.get(nbrWorldUnlock - 1).getNbrStarToLock()){
                        t = new World(listWorld.get(nbrWorldUnlock - 1));
                    }
                }
                else{
                    if(user.getNbrStar() >= listWorld.get(i - 1).getNbrStarToLock()) {
                        t = new World(listWorld.get(i - 1));
                    }
                }
            }
        }

        return t;
    }

    // GET next World
    public World getNext(World w){
        World t = new World(listWorld.get(0));
        int num = w.getNum();
        int nbrWorldUnlock = 0;

        for(int i = 0; i < listWorld.size(); i++){
            if(user.getNbrStar() >= listWorld.get(i).getNbrStarToLock()){
                nbrWorldUnlock++;
            }
        }

        for(int i = 0; i < nbrWorldUnlock; i++){
            if(num == listWorld.get(i).getNum()){
                if(i == nbrWorldUnlock -1){
                    if(user.getNbrStar() >= listWorld.get(0).getNbrStarToLock()) {
                        t = new World(listWorld.get(0));
                    }
                }
                else{
                    if(user.getNbrStar() >= listWorld.get(i + 1).getNbrStarToLock()) {
                        t = new World(listWorld.get(i + 1));
                    }
                }
            }
        }

        return t;
    }

    public void initView(){
        txt_monde_1 = (TextView) findViewById(R.id.txt_monde_1);
        nbrLife = (TextView) findViewById(R.id.nbrLife);
        timeLife = (TextView) findViewById(R.id.timeLife);
        nbr_star = (TextView) findViewById(R.id.nbr_star);
        img_monde = (ImageView) findViewById(R.id.img_monde);
        left_arrow = (ImageView) findViewById(R.id.left_arrow);
        right_arrow = (ImageView) findViewById(R.id.right_arrow);

        listViewLevel = (ListView) findViewById(R.id.list_view_level);
    }

    @Override
    public void onBackPressed() {
        try{
            taskRefresh.cancel(true);
        }
        catch (Exception e){
            Log.i("LIST LEVEL", "Impossible d'arreter aynsctask refresh life");
        }

        //super.onBackPressed();
        Intent intent = new Intent(ListLevelActivity.this, PlayActivity.class);

        Bundle b = new Bundle();

        b.putSerializable("user", user);

        intent.putExtras(b);

        startActivity(intent);
    }

    public void loadDataBDD(){
        dao.open();

        user.setNbrLife(dao.getNbrLife());
        Log.i("ListLEvelActivity","NBr Life : " + user.getNbrLife());
        dao.close();

        nbrLife.setText("" + user.getNbrLife());

        for(int i = 0; i < myWorld.getlevels().size(); i++){
            dao.open();

            myWorld.getLevel(i).setScore(dao.getScoreLevel(myWorld, myWorld.getLevel(i)));

            dao.close();

            if(myWorld.getLevel(i).getScore() >= myWorld.getLevel(i).getScoreStar3()){
                myWorld.getLevel(i).setNbrStars(3);
            }
            else if(myWorld.getLevel(i).getScore() >= myWorld.getLevel(i).getScoreStar2()){
                myWorld.getLevel(i).setNbrStars(2);
            }
            else if(myWorld.getLevel(i).getScore() >= myWorld.getLevel(i).getScoreStar1()){
                myWorld.getLevel(i).setNbrStars(1);
            }
            else {
                myWorld.getLevel(i).setNbrStars(0);
            }
        }
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
