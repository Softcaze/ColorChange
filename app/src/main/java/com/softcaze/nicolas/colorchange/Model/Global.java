package com.softcaze.nicolas.colorchange.Model;

/**
 * Created by Nicolas on 11/03/2018.
 */

public class Global {
    protected int vitesse;

    public Global(int v){
        this.vitesse = v;
    }

    public Global(Global g){
        this.vitesse = g.vitesse;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }
}
