package com.softcaze.nicolas.colorchange.Model;

import java.io.Serializable;

/**
 * Created by Nicolas on 28/02/2018.
 */

public class User implements Serializable {
    protected int nbrStar;
    protected int nbrLife;

    public User(){
        this.nbrStar = 0;
        this.nbrLife = Constance.NBR_LIFE_MAX;
    }

    public User(User u){
        this.nbrStar = u.nbrStar;
        this.nbrLife = u.nbrLife;
    }

    public int getNbrStar() {
        return nbrStar;
    }

    public int getNbrLife() {
        return nbrLife;
    }

    public void setNbrStar(int nbrStar) {
        this.nbrStar = nbrStar;
    }

    public void setNbrLife(int nbrLife) {
        this.nbrLife = nbrLife;
    }
}
