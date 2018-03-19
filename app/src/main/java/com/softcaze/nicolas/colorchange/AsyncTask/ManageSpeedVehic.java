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

public class ManageSpeedVehic extends AsyncTask<Integer, Void, Void> {
    protected Global varGlobal;
    protected Level levelActu;

    public ManageSpeedVehic(Level l, Global g){
        this.varGlobal =  g;
        levelActu = l;
    }
    @Override
    protected Void doInBackground(Integer... voids) {
        while(true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.varGlobal.setVitesse(this.varGlobal.getVitesse() - levelActu.getCoefSpeed());

            if(isCancelled()){
                break;
            }
        }

        return null;
    }
}
