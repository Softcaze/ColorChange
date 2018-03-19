package com.softcaze.nicolas.colorchange.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.softcaze.nicolas.colorchange.AsyncTask.ManagerDoor;
import com.softcaze.nicolas.colorchange.Database.DAO;
import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.Model.EtatGame;
import com.softcaze.nicolas.colorchange.Model.Level;
import com.softcaze.nicolas.colorchange.Model.User;
import com.softcaze.nicolas.colorchange.Model.World;
import com.softcaze.nicolas.colorchange.View.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 18/02/2018.
 */

public class TransitionActivityView extends Activity implements RewardedVideoAdListener {
    protected Level levelActu;
    protected World worldActu;
    protected List<World> listWorld = new ArrayList<World>();
    protected RewardedVideoAd mRewardedVideoAd;
    protected boolean isVideoLoaded = false;
    protected User user;
    protected EtatGame etat = new EtatGame(Constance.TUTORIEL);

    protected DAO dao = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = new DAO(this);

        // ADs Video
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();

        levelActu = (Level) getIntent().getSerializableExtra("levelClicked");
        worldActu = (World) getIntent().getSerializableExtra("worldActu");
        listWorld = (List<World>) getIntent().getSerializableExtra("listWorld");
        user = (User) getIntent().getSerializableExtra("user");

        Game game = new Game(this, getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight(), levelActu, worldActu, listWorld, mRewardedVideoAd, user, etat);

        addContentView(game, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //setContentView(game);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(Game.task != null){
            Game.task.cancel(true);
        }

        if(Game.taskAnimY != null){
            Game.taskAnimY.cancel(true);
        }

        if(Game.taskSpeedVehic != null){
            Game.taskSpeedVehic.cancel(true);
        }

        Intent intent = new Intent(TransitionActivityView.this, ListLevelActivity.class);

        Bundle b = new Bundle();

        b.putSerializable("user", user);
        b.putSerializable("worldClicked", worldActu);
        b.putSerializable("listWorld", (Serializable) listWorld);

        intent.putExtras(b);

        startActivity(intent);
    }

    @Override
    protected void onPause() {
        mRewardedVideoAd.pause(this);

        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);

        super.onResume();

    }

    @Override
    public void onRewardedVideoAdLoaded() {
        isVideoLoaded = true;
        Log.i("VIDEO LOADED","La vidéo est chargé");
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        isVideoLoaded = false;

        loadRewardedVideoAd();

        if(etat.hasRevive()) {
            etat.setEtat(Constance.REWARDING);
        }
        etat.setHasRevive(false);
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        etat.setEtat(Constance.REWARDING);
        etat.setHasRevive(false);

        dao.open();

        if(dao.getNbrLife() < 10){
            user.setNbrLife(user.getNbrLife() - 1);
            dao.setNbrLife(dao.getNbrLife() + 1);
        }

        dao.close();

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Log.i("VIDEO LOADED","La vidéo n'a pas pu charger");
        etat.setHasRevive(true);
    }

    private void loadRewardedVideoAd(){
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }
}
