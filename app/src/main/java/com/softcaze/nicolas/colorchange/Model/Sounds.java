package com.softcaze.nicolas.colorchange.Model;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.softcaze.nicolas.colorchange.R;

/**
 * Created by Nicolas on 02/04/2018.
 */

public final class Sounds {

    public static void createSounds(int id, MediaPlayer mediaPlayer, Context context){
        switch (id){
            case Constance.CLICK_BTN_1:
                mediaPlayer = MediaPlayer.create(context, R.raw.click_btn);
                mediaPlayer.setVolume(50, 50);
                break;
            case Constance.VEHICULE_OK_1:
                mediaPlayer = MediaPlayer.create(context, R.raw.vehicule_ok);
                break;
        }
    }
}
