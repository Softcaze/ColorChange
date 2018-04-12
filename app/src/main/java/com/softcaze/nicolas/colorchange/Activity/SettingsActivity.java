package com.softcaze.nicolas.colorchange.Activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softcaze.nicolas.colorchange.BuildConfig;
import com.softcaze.nicolas.colorchange.Database.DAO;
import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.R;

public class SettingsActivity extends Activity {
    protected RelativeLayout son, report_bug, rate_us;
    protected MediaPlayer clickBtn;
    protected TextView setting_son_txt, versionApp;
    protected DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        clickBtn = MediaPlayer.create(getApplicationContext(), R.raw.click_btn);
        son = (RelativeLayout)findViewById(R.id.son);
        report_bug = (RelativeLayout)findViewById(R.id.report_bug);
        rate_us = (RelativeLayout) findViewById(R.id.rate_us);
        setting_son_txt = (TextView) findViewById(R.id.setting_son_txt);
        versionApp = (TextView) findViewById(R.id.versionApp);

        versionApp.setText(BuildConfig.VERSION_NAME);
        dao = new DAO(this);

        loadData();

        son.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSounds(clickBtn);

                dao.open();

                if(setting_son_txt.getText().equals(getResources().getString(R.string.setting_no))){
                    dao.setStateSound(Constance.SOUND_ENABLE);
                    setting_son_txt.setText(getResources().getString(R.string.setting_yes));
                }
                else{
                    dao.setStateSound(Constance.SOUND_DISABLE);
                    setting_son_txt.setText(getResources().getString(R.string.setting_no));
                }

                dao.close();
            }
        });

        report_bug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSounds(clickBtn);

                Intent intent = new Intent(SettingsActivity.this, ContactFormActivity.class);
                startActivity(intent);
            }
        });

        rate_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
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

    public void loadData(){
        dao.open();

        if(dao.getStateSound() == Constance.SOUND_ENABLE){
            setting_son_txt.setText(getResources().getString(R.string.setting_yes));
        }
        else{
            setting_son_txt.setText(getResources().getString(R.string.setting_no));
        }

        dao.close();
    }
}
