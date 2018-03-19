package com.softcaze.nicolas.colorchange.Model;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Nicolas on 28/02/2018.
 */

public class User implements Serializable {
    protected int nbrStar;
    protected int nbrLife;
    protected Long timeLastLife;

    public User(){
        this.nbrStar = 0;
        this.nbrLife = Constance.NBR_LIFE_MAX;
        this.timeLastLife = System.currentTimeMillis();
    }

    public User(User u){
        this.nbrStar = u.nbrStar;
        this.nbrLife = u.nbrLife;
        this.timeLastLife = u.timeLastLife;
    }

    public int getNbrStar() {
        return nbrStar;
    }

    public int getNbrLife() {
        return nbrLife;
    }

    public Long getTimeLastLife() {
        return timeLastLife;
    }

    public void setNbrStar(int nbrStar) {
        this.nbrStar = nbrStar;
    }

    public void setNbrLife(int nbrLife) {
        this.nbrLife = nbrLife;
    }

    public void setTimeLastLife(Long timeLastLife) {
        this.timeLastLife = timeLastLife;
    }
}
