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
import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.Model.Level;
import com.softcaze.nicolas.colorchange.Model.User;
import com.softcaze.nicolas.colorchange.Model.World;
import com.softcaze.nicolas.colorchange.View.Game;

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
    protected boolean isRewarded = false;
    protected User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        // ADs Video
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();

        levelActu = (Level) getIntent().getSerializableExtra("levelClicked");
        worldActu = (World) getIntent().getSerializableExtra("worldActu");
        listWorld = (List<World>) getIntent().getSerializableExtra("listWorld");
        user = (User) getIntent().getSerializableExtra("user");

        Game game = new Game(this, getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight(), levelActu, worldActu, listWorld, mRewardedVideoAd, isVideoLoaded, user);

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

        isRewarded = false;
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        isRewarded = true;
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    private void loadRewardedVideoAd(){
        Log.i("LOADRWARDED METHODE", "BONJOUR");
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }
}
