package com.softcaze.nicolas.colorchange.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.softcaze.nicolas.colorchange.Model.ImageAnim;

/**
 * Created by Nicolas on 07/03/2018.
 */

public class AnimImageY extends AsyncTask<Void, Void, Void> {
    protected ImageAnim hand;
    protected int LARGEUR_ECRAN;
    protected int HAUTEUR_ECRAN;

    public AnimImageY(ImageAnim a, int l, int h) {
        this.hand = a;
        this.LARGEUR_ECRAN = l;
        this.HAUTEUR_ECRAN = h;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while (true) {
            if (isCancelled()) {
                break;
            }

            try {
                Thread.sleep(300);

                if (hand.getY() == hand.getYBase() - HAUTEUR_ECRAN / 80) {
                    hand.setY(hand.getYBase());
                } else if (hand.getY() == hand.getYBase()) {
                    hand.setY(hand.getYBase() - HAUTEUR_ECRAN / 80);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}