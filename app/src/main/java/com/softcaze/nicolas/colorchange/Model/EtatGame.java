package com.softcaze.nicolas.colorchange.Model;

/**
 * Created by Nicolas on 16/03/2018.
 */

public class EtatGame {
    protected int etat;
    protected boolean hasRevive;

    public EtatGame(int e){
        this.etat = e;
        this.hasRevive = true;
    }

    public int getEtat() {
        return etat;
    }

    public boolean hasRevive() {
        return hasRevive;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setHasRevive(boolean hasRevive) {
        this.hasRevive = hasRevive;
    }
}
