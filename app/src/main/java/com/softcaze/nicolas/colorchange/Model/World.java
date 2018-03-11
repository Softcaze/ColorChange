package com.softcaze.nicolas.colorchange.Model;


import com.softcaze.nicolas.colorchange.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 14/02/2018.
 */

public class World implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    int num;
    String name;
    int img;
    boolean isLock;
    int nbrStarToLock;
    List<Level> levels = new ArrayList<Level>();

    public World(int n, String na, int i, int nbr, int h){
        this.num = n;
        this.name = na;
        this.img = i;
        this.nbrStarToLock = nbr;
        this.isLock = true;
        // Check BDD
        // this.isLock = ?

        this.initLevel(h);
    }

    public World(World w){
        this.num = w.num;
        this.name = w.name;
        this.img = w.img;
        this.nbrStarToLock = w.nbrStarToLock;
        this.levels = w.levels;
        this.isLock = w.isLock;
    }

    public void addLevel(Level l){
        this.levels.add(l);
    }

    public int getNum() {
        return num;
    }

    public int getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public boolean isLock() {
        return isLock;
    }

    public int getNbrStarToLock() {
        return nbrStarToLock;
    }

    public List<Level> getlevels() {
        return levels;
    }

    public Level getLevel(int i){
        return this.levels.get(i);
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setListLevel(List<Level> levels) {
        this.levels = levels;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public void setNbrStarToLock(int nbrStarToLock) {
        this.nbrStarToLock = nbrStarToLock;
    }

    public int getNumberStars(){
        int numberStars = 0;

        for(int i = 0; i < this.levels.size(); i++){
            numberStars += this.levels.get(i).getNbrStars();
        }
        return numberStars;
    }

    public void initLevel(int hauteur){
        switch (this.num){
            case 1:
                Level lvl_w1;

                List<Integer> l1_w1 = new ArrayList<Integer>();
                List<Integer> l2_w1 = new ArrayList<Integer>();
                List<Integer> l3_w1 = new ArrayList<Integer>();
                List<Integer> l4_w1 = new ArrayList<Integer>();
                List<Integer> l5_w1 = new ArrayList<Integer>();
                List<Integer> l6_w1 = new ArrayList<Integer>();
                List<Integer> l7_w1 = new ArrayList<Integer>();
                List<Integer> l8_w1 = new ArrayList<Integer>();
                List<Integer> l9_w1 = new ArrayList<Integer>();
                List<Integer> l10_w1 = new ArrayList<Integer>();
                List<Integer> l11_w1 = new ArrayList<Integer>();
                List<Integer> l12_w1 = new ArrayList<Integer>();
                List<Integer> l13_w1 = new ArrayList<Integer>();
                List<Integer> l14_w1 = new ArrayList<Integer>();
                List<Integer> l15_w1 = new ArrayList<Integer>();

                l1_w1.add(R.color.blue);l1_w1.add(R.color.red);
                lvl_w1 = new Level(1, 200, 20, 40, 60, l1_w1, 1, 0, hauteur/2);
                this.addLevel(lvl_w1);

                l2_w1.add(R.color.blue);l2_w1.add(R.color.yellow);
                lvl_w1 = new Level(2, 200, 20, 40, 60, l2_w1, 0.8,0, hauteur/2);
                this.addLevel(lvl_w1);

                l3_w1.add(R.color.blue);l3_w1.add(R.color.dark_grey);
                lvl_w1 = new Level(3, 200, 20, 40, 60, l3_w1, 0.7,0, hauteur/2);
                this.addLevel(lvl_w1);

                l4_w1.add(R.color.blue);l4_w1.add(R.color.light_grey);
                lvl_w1 = new Level(4, 200, 20, 40, 60, l4_w1, 1,0, hauteur/2);
                this.addLevel(lvl_w1);

                l5_w1.add(R.color.blue);l5_w1.add(R.color.green);
                lvl_w1 = new Level(5, 100, 20, 40, 60, l5_w1, 1,0, hauteur/3);
                this.addLevel(lvl_w1);

                l6_w1.add(R.color.yellow);l6_w1.add(R.color.red);
                lvl_w1 = new Level(6, 200, 20, 40, 60, l6_w1, 1,0, hauteur/3);
                this.addLevel(lvl_w1);

                l7_w1.add(R.color.yellow);l7_w1.add(R.color.dark_grey);
                lvl_w1 = new Level(7, 200, 20, 40, 60, l7_w1, 1,0, hauteur/3);
                this.addLevel(lvl_w1);

                l8_w1.add(R.color.yellow);l8_w1.add(R.color.light_grey);
                lvl_w1 = new Level(8, 200, 20, 40, 60, l8_w1, 1,0, hauteur/3);
                this.addLevel(lvl_w1);

                l9_w1.add(R.color.yellow);l9_w1.add(R.color.green);
                lvl_w1 = new Level(9, 200, 20, 40, 60, l9_w1, 1,0, hauteur/3);
                this.addLevel(lvl_w1);

                l10_w1.add(R.color.red);l10_w1.add(R.color.dark_grey);
                lvl_w1 = new Level(10, 200, 20, 40, 60, l10_w1, 1,0, hauteur/3);
                this.addLevel(lvl_w1);

                l11_w1.add(R.color.red);l11_w1.add(R.color.light_grey);
                lvl_w1 = new Level(11, 200, 20, 40, 60, l11_w1, 1,0, hauteur/3);
                this.addLevel(lvl_w1);

                l12_w1.add(R.color.red);l12_w1.add(R.color.green);
                lvl_w1 = new Level(12, 200, 20, 40, 60, l12_w1, 1,0, hauteur/3);
                this.addLevel(lvl_w1);

                l13_w1.add(R.color.dark_grey);l13_w1.add(R.color.light_grey);
                lvl_w1 = new Level(13, 200, 20, 40, 60, l13_w1, 1,0, hauteur/3);
                this.addLevel(lvl_w1);

                l14_w1.add(R.color.dark_grey);l14_w1.add(R.color.green);
                lvl_w1 = new Level(14, 200, 20, 40, 60, l14_w1, 1,0, hauteur/3);
                this.addLevel(lvl_w1);

                l15_w1.add(R.color.light_grey);l15_w1.add(R.color.green);
                lvl_w1 = new Level(15, 200, 20, 40, 60, l15_w1, 1,0, hauteur/3);
                this.addLevel(lvl_w1);
                break;
            case 2:
                Level lvl_w2;

                List<Integer> l1_w2 = new ArrayList<Integer>();
                List<Integer> l2_w2 = new ArrayList<Integer>();
                List<Integer> l3_w2 = new ArrayList<Integer>();

                l1_w2.add(R.color.blue);l1_w2.add(R.color.yellow);
                lvl_w2 = new Level(1, 10, 20, 40, 60, l1_w2, 1, 0, hauteur/3);
                this.addLevel(lvl_w2);

                l2_w2.add(R.color.blue);l2_w2.add(R.color.red);l2_w2.add(R.color.light_grey);
                lvl_w2 = new Level(2, 11, 20, 40, 60, l2_w2, 1,0, hauteur/3);
                this.addLevel(lvl_w2);

                l3_w2.add(R.color.yellow);l3_w2.add(R.color.red);l3_w2.add(R.color.green);l3_w2.add(R.color.dark_grey);l3_w2.add(R.color.light_grey);
                lvl_w2 = new Level(3, 11, 20, 40, 60, l3_w2, 1,0, hauteur/3);
                this.addLevel(lvl_w2);

                break;
            case 3:
                Level lvl_w3;

                List<Integer> l1_w3 = new ArrayList<Integer>();
                List<Integer> l2_w3 = new ArrayList<Integer>();

                l1_w3.add(R.color.blue);l1_w3.add(R.color.red);
                lvl_w3 = new Level(1, 10, 20, 40, 60, l1_w3, 1, 0, hauteur/3);
                this.addLevel(lvl_w3);

                l2_w3.add(R.color.blue);l2_w3.add(R.color.yellow);l2_w3.add(R.color.green);
                l2_w3.add(R.color.red);l2_w3.add(R.color.dark_grey);l2_w3.add(R.color.light_grey);
                lvl_w3 = new Level(2, 11, 20, 40, 60, l2_w3, 1,0, hauteur/3);
                this.addLevel(lvl_w3);
                break;
            case 4:
                Level lvl_w4;

                List<Integer> l1_w4 = new ArrayList<Integer>();

                l1_w4.add(R.color.blue);l1_w4.add(R.color.red);
                lvl_w4 = new Level(1, 10, 20, 40, 60, l1_w4, 1, 0, hauteur/3);
                this.addLevel(lvl_w4);
                break;
        }
    }
}
