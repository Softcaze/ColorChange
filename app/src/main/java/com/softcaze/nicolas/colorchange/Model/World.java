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

                List<Integer> l1_w1 = new ArrayList<Integer>();List<Integer> l2_w1 = new ArrayList<Integer>();List<Integer> l3_w1 = new ArrayList<Integer>();
                List<Integer> l4_w1 = new ArrayList<Integer>();List<Integer> l5_w1 = new ArrayList<Integer>();List<Integer> l6_w1 = new ArrayList<Integer>();
                List<Integer> l7_w1 = new ArrayList<Integer>();List<Integer> l8_w1 = new ArrayList<Integer>();List<Integer> l9_w1 = new ArrayList<Integer>();
                List<Integer> l10_w1 = new ArrayList<Integer>();List<Integer> l11_w1 = new ArrayList<Integer>();List<Integer> l12_w1 = new ArrayList<Integer>();
                List<Integer> l13_w1 = new ArrayList<Integer>();List<Integer> l14_w1 = new ArrayList<Integer>();List<Integer> l15_w1 = new ArrayList<Integer>();
                List<Integer> l16_w1 = new ArrayList<Integer>();List<Integer> l17_w1 = new ArrayList<Integer>();List<Integer> l18_w1 = new ArrayList<Integer>();
                List<Integer> l19_w1 = new ArrayList<Integer>();List<Integer> l20_w1 = new ArrayList<Integer>();List<Integer> l21_w1 = new ArrayList<Integer>();
                List<Integer> l22_w1 = new ArrayList<Integer>();List<Integer> l23_w1 = new ArrayList<Integer>();List<Integer> l24_w1 = new ArrayList<Integer>();
                List<Integer> l25_w1 = new ArrayList<Integer>();List<Integer> l26_w1 = new ArrayList<Integer>();List<Integer> l27_w1 = new ArrayList<Integer>();
                List<Integer> l28_w1 = new ArrayList<Integer>();List<Integer> l29_w1 = new ArrayList<Integer>();List<Integer> l30_w1 = new ArrayList<Integer>();

                l1_w1.add(R.color.blue);l1_w1.add(R.color.red);
                lvl_w1 = new Level(1, 250, 15, 30, 50, l1_w1, hauteur/300, 0, hauteur/2);
                this.addLevel(lvl_w1);

                l2_w1.add(R.color.blue);l2_w1.add(R.color.yellow);
                lvl_w1 = new Level(2, 250, 17, 32, 55, l2_w1, hauteur/300,0, hauteur/2);
                this.addLevel(lvl_w1);

                l3_w1.add(R.color.blue);l3_w1.add(R.color.dark_grey);
                lvl_w1 = new Level(3, 240, 17, 30, 56, l3_w1, hauteur/290,0, hauteur/2);
                this.addLevel(lvl_w1);

                l4_w1.add(R.color.blue);l4_w1.add(R.color.light_grey);
                lvl_w1 = new Level(4, 230, 20, 35, 50, l4_w1, hauteur/290,0, hauteur/2);
                this.addLevel(lvl_w1);

                l5_w1.add(R.color.blue);l5_w1.add(R.color.green);
                lvl_w1 = new Level(5, 250, 30, 55, 70, l5_w1, hauteur/290,0, hauteur/3);
                this.addLevel(lvl_w1);

                l6_w1.add(R.color.yellow);l6_w1.add(R.color.red);
                lvl_w1 = new Level(6, 230, 20, 30, 55, l6_w1, hauteur/290,0, hauteur/3);
                this.addLevel(lvl_w1);

                l7_w1.add(R.color.yellow);l7_w1.add(R.color.dark_grey);
                lvl_w1 = new Level(7, 240, 18, 32, 55, l7_w1, hauteur/280,0, hauteur/3);
                this.addLevel(lvl_w1);

                l8_w1.add(R.color.yellow);l8_w1.add(R.color.light_grey);l8_w1.add(R.color.red);
                lvl_w1 = new Level(8, 240, 22, 38, 57, l8_w1, hauteur/280,0, hauteur/3);
                this.addLevel(lvl_w1);

                l9_w1.add(R.color.yellow);l9_w1.add(R.color.green);;l9_w1.add(R.color.blue);
                lvl_w1 = new Level(9, 230, 25, 35, 54, l9_w1, hauteur/280,0, hauteur/3);
                this.addLevel(lvl_w1);

                l10_w1.add(R.color.red);l10_w1.add(R.color.dark_grey);l10_w1.add(R.color.green);
                lvl_w1 = new Level(10, 220, 20, 30, 40, l10_w1, hauteur/250,0, hauteur/3);
                this.addLevel(lvl_w1);

                l11_w1.add(R.color.red);l11_w1.add(R.color.light_grey);
                lvl_w1 = new Level(11, 150, 25, 42, 60, l11_w1, hauteur/290,0, hauteur/3);
                this.addLevel(lvl_w1);

                l12_w1.add(R.color.red);l12_w1.add(R.color.green);
                lvl_w1 = new Level(12, 120, 30, 50, 70, l12_w1, hauteur/400,0, hauteur/3);
                this.addLevel(lvl_w1);

                l13_w1.add(R.color.dark_grey);l13_w1.add(R.color.light_grey);l13_w1.add(R.color.red);
                lvl_w1 = new Level(13, 230, 42, 65, 90, l13_w1, hauteur/300,0, hauteur/4);
                this.addLevel(lvl_w1);

                l14_w1.add(R.color.dark_grey);l14_w1.add(R.color.green);;l14_w1.add(R.color.blue);
                lvl_w1 = new Level(14, 230, 55, 80, 110, l14_w1, hauteur/600,0, hauteur/4);
                this.addLevel(lvl_w1);

                l15_w1.add(R.color.light_grey);l15_w1.add(R.color.green);;l15_w1.add(R.color.yellow);
                lvl_w1 = new Level(15, 220, 35, 48, 70, l15_w1, hauteur/280,0, hauteur/4);
                this.addLevel(lvl_w1);

                l16_w1.add(R.color.dark_grey);l16_w1.add(R.color.yellow);;l16_w1.add(R.color.red);
                lvl_w1 = new Level(16, 220, 24, 35, 62, l16_w1, hauteur/270,0, hauteur/3);
                this.addLevel(lvl_w1);

                l17_w1.add(R.color.red);l17_w1.add(R.color.green);;l17_w1.add(R.color.yellow);
                lvl_w1 = new Level(17, 210, 32, 60, 92, l17_w1, hauteur/330,0, hauteur/3);
                this.addLevel(lvl_w1);

                l18_w1.add(R.color.light_grey);l18_w1.add(R.color.green);;l18_w1.add(R.color.yellow);
                lvl_w1 = new Level(18, 170, 30, 50, 80, l18_w1, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w1);

                l19_w1.add(R.color.light_grey);l19_w1.add(R.color.green);;l19_w1.add(R.color.yellow);
                lvl_w1 = new Level(19, 170, 30, 50, 80, l19_w1, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w1);

                l20_w1.add(R.color.light_grey);l20_w1.add(R.color.green);;l20_w1.add(R.color.yellow);
                lvl_w1 = new Level(20, 170, 30, 50, 80, l20_w1, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w1);


                l21_w1.add(R.color.light_grey);l21_w1.add(R.color.green);;l21_w1.add(R.color.yellow);
                lvl_w1 = new Level(21, 170, 30, 50, 80, l21_w1, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w1);

                l22_w1.add(R.color.light_grey);l22_w1.add(R.color.green);;l22_w1.add(R.color.yellow);
                lvl_w1 = new Level(22, 170, 30, 50, 80, l22_w1, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w1);

                l23_w1.add(R.color.light_grey);l23_w1.add(R.color.green);;l23_w1.add(R.color.yellow);
                lvl_w1 = new Level(23, 170, 30, 50, 80, l23_w1, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w1);

                l24_w1.add(R.color.light_grey);l24_w1.add(R.color.green);;l24_w1.add(R.color.yellow);
                lvl_w1 = new Level(24, 170, 30, 50, 80, l24_w1, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w1);

                l25_w1.add(R.color.light_grey);l25_w1.add(R.color.green);;l25_w1.add(R.color.yellow);
                lvl_w1 = new Level(25, 170, 30, 50, 80, l25_w1, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w1);

                l26_w1.add(R.color.light_grey);l26_w1.add(R.color.green);;l26_w1.add(R.color.yellow);
                lvl_w1 = new Level(26, 170, 30, 50, 80, l26_w1, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w1);

                l27_w1.add(R.color.light_grey);l27_w1.add(R.color.green);;l27_w1.add(R.color.yellow);
                lvl_w1 = new Level(27, 170, 30, 50, 80, l27_w1, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w1);

                l28_w1.add(R.color.light_grey);l28_w1.add(R.color.green);;l28_w1.add(R.color.yellow);
                lvl_w1 = new Level(28, 170, 30, 50, 80, l28_w1, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w1);

                l29_w1.add(R.color.light_grey);l29_w1.add(R.color.green);;l29_w1.add(R.color.yellow);
                lvl_w1 = new Level(29, 170, 30, 50, 80, l29_w1, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w1);

                l30_w1.add(R.color.light_grey);l30_w1.add(R.color.green);;l30_w1.add(R.color.yellow);
                lvl_w1 = new Level(30, 170, 30, 50, 80, l30_w1, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w1);

                break;
            case 2:
                Level lvl_w2;

                List<Integer> l1_w2 = new ArrayList<Integer>();List<Integer> l2_w2 = new ArrayList<Integer>();List<Integer> l3_w2 = new ArrayList<Integer>();
                List<Integer> l4_w2 = new ArrayList<Integer>();List<Integer> l5_w2 = new ArrayList<Integer>();List<Integer> l6_w2 = new ArrayList<Integer>();
                List<Integer> l7_w2 = new ArrayList<Integer>();List<Integer> l8_w2 = new ArrayList<Integer>();List<Integer> l9_w2 = new ArrayList<Integer>();
                List<Integer> l10_w2 = new ArrayList<Integer>();List<Integer> l11_w2 = new ArrayList<Integer>();List<Integer> l12_w2 = new ArrayList<Integer>();
                List<Integer> l13_w2 = new ArrayList<Integer>();List<Integer> l14_w2 = new ArrayList<Integer>();List<Integer> l15_w2 = new ArrayList<Integer>();

                l1_w2.add(R.color.blue);l1_w2.add(R.color.yellow);l1_w2.add(R.color.red);
                lvl_w2 = new Level(1, 160, 40, 60, 80, l1_w2, hauteur/160, 0, hauteur/3);
                this.addLevel(lvl_w2);

                l2_w2.add(R.color.blue);l2_w2.add(R.color.yellow);l2_w2.add(R.color.dark_grey);
                lvl_w2 = new Level(2, 160, 35, 65, 85, l2_w2, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w2);

                l3_w2.add(R.color.blue);l3_w2.add(R.color.yellow);l3_w2.add(R.color.light_grey);
                lvl_w2 = new Level(3, 155, 30, 50, 90, l3_w2, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w2);

                l4_w2.add(R.color.blue);l4_w2.add(R.color.yellow);l4_w2.add(R.color.green);
                lvl_w2 = new Level(4, 150, 50, 80, 130, l4_w2, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w2);

                l5_w2.add(R.color.blue);l5_w2.add(R.color.red);l5_w2.add(R.color.dark_grey);
                lvl_w2 = new Level(5, 190, 30, 50, 90, l5_w2, hauteur/200,0, hauteur/4);
                this.addLevel(lvl_w2);

                l6_w2.add(R.color.blue);l6_w2.add(R.color.red);l6_w2.add(R.color.light_grey);
                lvl_w2 = new Level(6, 180, 40, 90, 140, l6_w2, hauteur/190,0, hauteur/4);
                this.addLevel(lvl_w2);

                l7_w2.add(R.color.blue);l7_w2.add(R.color.red);l7_w2.add(R.color.green);
                lvl_w2 = new Level(7, 175, 42, 55, 135, l7_w2, hauteur/185,0, hauteur/4);
                this.addLevel(lvl_w2);

                l8_w2.add(R.color.blue);l8_w2.add(R.color.dark_grey);l8_w2.add(R.color.light_grey);
                lvl_w2 = new Level(8, 170, 50, 100, 150, l8_w2, hauteur/180,0, hauteur/4);
                this.addLevel(lvl_w2);

                l9_w2.add(R.color.blue);l9_w2.add(R.color.dark_grey);l9_w2.add(R.color.green);
                lvl_w2 = new Level(9, 155, 30, 50, 90, l9_w2, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w2);

                l10_w2.add(R.color.blue);l10_w2.add(R.color.light_grey);l10_w2.add(R.color.green);
                lvl_w2 = new Level(10, 155, 30, 50, 90, l10_w2, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w2);

                l11_w2.add(R.color.yellow);l11_w2.add(R.color.red);l11_w2.add(R.color.dark_grey);
                lvl_w2 = new Level(11, 155, 30, 50, 90, l11_w2, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w2);

                l12_w2.add(R.color.yellow);l12_w2.add(R.color.red);l12_w2.add(R.color.light_grey);
                lvl_w2 = new Level(12, 155, 30, 50, 90, l12_w2, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w2);

                l13_w2.add(R.color.yellow);l13_w2.add(R.color.red);l13_w2.add(R.color.green);
                lvl_w2 = new Level(13, 155, 30, 50, 90, l13_w2, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w2);

                l14_w2.add(R.color.yellow);l14_w2.add(R.color.dark_grey);l14_w2.add(R.color.light_grey);
                lvl_w2 = new Level(14, 155, 30, 50, 90, l14_w2, hauteur/150,0, hauteur/3);
                this.addLevel(lvl_w2);

                l15_w2.add(R.color.yellow);l15_w2.add(R.color.dark_grey);l15_w2.add(R.color.green);
                lvl_w2 = new Level(15, 155, 30, 50, 90, l15_w2, hauteur/150,0, hauteur/5);
                this.addLevel(lvl_w2);

                break;
            case 3:
                Level lvl_w3;

                List<Integer> l1_w3 = new ArrayList<Integer>();
                List<Integer> l2_w3 = new ArrayList<Integer>();

                l1_w3.add(R.color.blue);l1_w3.add(R.color.red);l1_w3.add(R.color.light_grey);l1_w3.add(R.color.green);
                lvl_w3 = new Level(1, 200, 20, 40, 60, l1_w3, hauteur/120, 0, hauteur/3);
                this.addLevel(lvl_w3);

                l2_w3.add(R.color.blue);l2_w3.add(R.color.yellow);l2_w3.add(R.color.green);
                l2_w3.add(R.color.red);l2_w3.add(R.color.dark_grey);l2_w3.add(R.color.light_grey);
                lvl_w3 = new Level(2, 150, 20, 40, 60, l2_w3, hauteur/260,0, hauteur/3);
                this.addLevel(lvl_w3);
                break;
            case 4:
                Level lvl_w4;

                List<Integer> l1_w4 = new ArrayList<Integer>();
                List<Integer> l2_w4 = new ArrayList<Integer>();
                List<Integer> l3_w4 = new ArrayList<Integer>();

                l1_w4.add(R.color.blue);l1_w4.add(R.color.red);l1_w4.add(R.color.yellow);l1_w4.add(R.color.dark_grey);l1_w4.add(R.color.light_grey);
                lvl_w4 = new Level(1, 160, 20, 40, 60, l1_w4, hauteur/150, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l2_w4.add(R.color.blue);l2_w4.add(R.color.red);l2_w4.add(R.color.yellow);l2_w4.add(R.color.dark_grey);l2_w4.add(R.color.light_grey);l2_w4.add(R.color.green);
                lvl_w4 = new Level(2, 150, 20, 40, 60, l2_w4, hauteur/140, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l3_w4.add(R.color.blue);l3_w4.add(R.color.red);l3_w4.add(R.color.yellow);l3_w4.add(R.color.dark_grey);l3_w4.add(R.color.light_grey);l3_w4.add(R.color.green);
                lvl_w4 = new Level(3, 120, 20, 40, 60, l3_w4, hauteur/120, 0, hauteur/5);
                this.addLevel(lvl_w4);
                break;
        }
    }
}
