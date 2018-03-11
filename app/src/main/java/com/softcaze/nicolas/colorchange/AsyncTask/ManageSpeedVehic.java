package com.softcaze.nicolas.colorchange.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.softcaze.nicolas.colorchange.Model.Global;
import com.softcaze.nicolas.colorchange.Model.Level;
import com.softcaze.nicolas.colorchange.Model.Vehicule;

import java.util.List;

/**
 * Created by Nicolas on 11/03/2018.
 */

public class ManageSpeedVehic extends AsyncTask<Void, Void, Void> {
    protected Global varGlobal;
    protected Level levelActu;

    public ManageSpeedVehic(Level l, Global g){
        varGlobal = new Global(g);
        levelActu = new Level(l);
    }
    @Override
    protected Void doInBackground(Void... voids) {
        while(true) {
            Log.i("MANAGE SPEED VEHIC", "MSJ");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.i("MANAGE SPEED VEHIC"," VItesse : " + varGlobal.getVitesse());
            varGlobal.setVitesse((int) (varGlobal.getVitesse() * levelActu.getCoefSpeed()));

            if(isCancelled()){
                break;
            }
        }

        return null;
    }
}
