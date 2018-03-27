package com.softcaze.nicolas.colorchange.AsyncTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.Model.Vehicule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nicolas on 22/02/2018.
 */

public class ManagerDoor extends AsyncTask<Integer, Void, Void> {
    protected int LARGEUR_ECRAN;
    protected int HAUTEUR_ECRAN;
    protected Map<Integer, Bitmap> vehicColorMap = new HashMap<Integer, Bitmap>();
    protected int nbrColumn;

    private int Min = 0;
    private int Max = 0;
    private Context context;
    private List<Integer> listColors = new ArrayList<Integer>();
    private List<Vehicule> vehicules = new ArrayList<Vehicule>();
    private int color = 0;
    private int distance = 0;
    boolean pause = false;
    private String WATCH_DOG = "barabulka";

    private boolean isCanceled = false;

    public ManagerDoor(Context context, List<Integer> listColors, List<Vehicule> vehicules, int largeur, int hauteur, Map<Integer, Bitmap> vehicColorMap, int nbrColumn, int d){
        this.context = context;
        this.listColors = listColors;
        this.vehicules = vehicules;
        this.vehicColorMap = vehicColorMap;

        this.LARGEUR_ECRAN = largeur;
        this.HAUTEUR_ECRAN = hauteur;

        this.distance = d;

        this.nbrColumn = nbrColumn;

        Max = listColors.size() - 1;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        int indexRandom = 0;


        while(true) {
                indexRandom = Min + (int) (Math.random() * ((Max - Min) + 1));
                color = listColors.get(indexRandom);
                Vehicule myVehic = new Vehicule(Constance.getResizedBitmap(vehicColorMap.get(color), LARGEUR_ECRAN / nbrColumn, 0), HAUTEUR_ECRAN / 20, LARGEUR_ECRAN, color);
                myVehic.setX(myVehic.getRandomX(LARGEUR_ECRAN / nbrColumn));

                vehicules.add(myVehic);

                while(myVehic.getY() < distance){
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Toast.makeText(this.context, "Arrêt de l'async Task", Toast.LENGTH_LONG).show();
                    }

                    // On ne fait rien tant que le véhicule n'arrive pas une certaine distance
                    if (isCancelled()) {
                        isCanceled = true;
                        break;
                    }

                    if (pause) {
                        synchronized (WATCH_DOG) {
                            try {
                                WATCH_DOG.wait();

                            } catch (InterruptedException e) {e.printStackTrace();
                            }
                            pause = false;
                        }
                    }
                }

                /*try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(this.context, "Arrêt de l'async Task", Toast.LENGTH_LONG).show();
                }*/

            if (isCancelled()) {
                Log.i("MANAGER DOOR"," STOP");
                break;
            }
        }

        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    /**Wake up task from sleeping*/
    public void wakeUp() {
        synchronized(WATCH_DOG){
            WATCH_DOG.notify();
        }
    }

    public void pauseMyTask() {
        pause = true;
    }

   public boolean getPause() {
        return pause;
    }
}
