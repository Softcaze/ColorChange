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

                l18_w1.add(R.color.green);l18_w1.add(R.color.red);;l18_w1.add(R.color.blue);
                lvl_w1 = new Level(18, 210, 30, 50, 80, l18_w1, hauteur/310,0, hauteur/3);
                this.addLevel(lvl_w1);

                l19_w1.add(R.color.light_grey);l19_w1.add(R.color.green);;l19_w1.add(R.color.yellow);
                lvl_w1 = new Level(19, 210, 32, 45, 85, l19_w1, hauteur/270,0, hauteur/4);
                this.addLevel(lvl_w1);

                l20_w1.add(R.color.dark_grey);l20_w1.add(R.color.yellow);;l20_w1.add(R.color.blue);
                lvl_w1 = new Level(20, 200, 44, 56, 72, l20_w1, hauteur/270,0, hauteur/4);
                this.addLevel(lvl_w1);


                l21_w1.add(R.color.red);l21_w1.add(R.color.blue);;l21_w1.add(R.color.yellow);
                lvl_w1 = new Level(21, 190, 40, 55, 70, l21_w1, hauteur/260,0, hauteur/4);
                this.addLevel(lvl_w1);

                l22_w1.add(R.color.light_grey);l22_w1.add(R.color.green);;l22_w1.add(R.color.yellow);
                lvl_w1 = new Level(22, 230, 55, 80, 140, l22_w1, hauteur/500,0, hauteur/4);
                this.addLevel(lvl_w1);

                l23_w1.add(R.color.red);l23_w1.add(R.color.blue);;l23_w1.add(R.color.yellow);
                lvl_w1 = new Level(23, 240, 72, 90, 160, l23_w1, hauteur/500,0, hauteur/4);
                this.addLevel(lvl_w1);

                l24_w1.add(R.color.dark_grey);l24_w1.add(R.color.green);;l24_w1.add(R.color.blue);
                lvl_w1 = new Level(24, 180, 80, 140, 200, l24_w1, hauteur/1000,0, hauteur/4);
                this.addLevel(lvl_w1);

                l25_w1.add(R.color.dark_grey);l25_w1.add(R.color.blue);;l25_w1.add(R.color.yellow);
                lvl_w1 = new Level(25, 160, 100, 165, 180, l25_w1, hauteur/1000,0, hauteur/4);
                this.addLevel(lvl_w1);

                l26_w1.add(R.color.light_grey);l26_w1.add(R.color.yellow);;l26_w1.add(R.color.dark_grey);
                lvl_w1 = new Level(26, 160, 110, 152, 190, l26_w1, hauteur/1000,0, hauteur/4);
                this.addLevel(lvl_w1);

                l27_w1.add(R.color.green);l27_w1.add(R.color.yellow);;l27_w1.add(R.color.blue);
                lvl_w1 = new Level(27, 150, 60, 80, 100, l27_w1, hauteur/600,0, hauteur/4);
                this.addLevel(lvl_w1);

                l28_w1.add(R.color.light_grey);l28_w1.add(R.color.green);;l28_w1.add(R.color.yellow);
                lvl_w1 = new Level(28, 140, 40, 60, 80, l28_w1, hauteur/500,0, hauteur/4);
                this.addLevel(lvl_w1);

                l29_w1.add(R.color.blue);l29_w1.add(R.color.red);;l29_w1.add(R.color.light_grey);
                lvl_w1 = new Level(29, 120, 20, 35, 50, l29_w1, hauteur/350,0, hauteur/4);
                this.addLevel(lvl_w1);

                l30_w1.add(R.color.light_grey);l30_w1.add(R.color.green);;l30_w1.add(R.color.dark_grey);
                lvl_w1 = new Level(30, 130, 40, 68, 95, l30_w1, hauteur/1000,0, hauteur/4);
                this.addLevel(lvl_w1);

                break;
            case 2:
                Level lvl_w2;

                List<Integer> l1_w2 = new ArrayList<Integer>();List<Integer> l2_w2 = new ArrayList<Integer>();List<Integer> l3_w2 = new ArrayList<Integer>();
                List<Integer> l4_w2 = new ArrayList<Integer>();List<Integer> l5_w2 = new ArrayList<Integer>();List<Integer> l6_w2 = new ArrayList<Integer>();
                List<Integer> l7_w2 = new ArrayList<Integer>();List<Integer> l8_w2 = new ArrayList<Integer>();List<Integer> l9_w2 = new ArrayList<Integer>();
                List<Integer> l10_w2 = new ArrayList<Integer>();List<Integer> l11_w2 = new ArrayList<Integer>();List<Integer> l12_w2 = new ArrayList<Integer>();
                List<Integer> l13_w2 = new ArrayList<Integer>();List<Integer> l14_w2 = new ArrayList<Integer>();List<Integer> l15_w2 = new ArrayList<Integer>();
                List<Integer> l16_w2 = new ArrayList<Integer>();List<Integer> l17_w2 = new ArrayList<Integer>();List<Integer> l18_w2 = new ArrayList<Integer>();
                List<Integer> l19_w2 = new ArrayList<Integer>();List<Integer> l20_w2 = new ArrayList<Integer>();List<Integer> l21_w2 = new ArrayList<Integer>();
                List<Integer> l22_w2 = new ArrayList<Integer>();List<Integer> l23_w2 = new ArrayList<Integer>();List<Integer> l24_w2 = new ArrayList<Integer>();
                List<Integer> l25_w2 = new ArrayList<Integer>();List<Integer> l26_w2 = new ArrayList<Integer>();List<Integer> l27_w2 = new ArrayList<Integer>();
                List<Integer> l28_w2 = new ArrayList<Integer>();List<Integer> l29_w2 = new ArrayList<Integer>();List<Integer> l30_w2 = new ArrayList<Integer>();

                l1_w2.add(R.color.blue);l1_w2.add(R.color.yellow);l1_w2.add(R.color.dark_grey);l1_w2.add(R.color.red);
                lvl_w2 = new Level(1, 200, 70, 100, 130, l1_w2, hauteur/800, 0, hauteur/3);
                this.addLevel(lvl_w2);

                l2_w2.add(R.color.green);l2_w2.add(R.color.yellow);l2_w2.add(R.color.dark_grey);l2_w2.add(R.color.light_grey);
                lvl_w2 = new Level(2, 190, 70, 105, 135, l2_w2, hauteur/800,0, hauteur/3);
                this.addLevel(lvl_w2);

                l3_w2.add(R.color.light_grey);l3_w2.add(R.color.green);l3_w2.add(R.color.red);l3_w2.add(R.color.blue);
                lvl_w2 = new Level(3, 200, 30, 45, 60, l3_w2, hauteur/250,0, hauteur/4);
                this.addLevel(lvl_w2);

                l4_w2.add(R.color.red);l4_w2.add(R.color.yellow);l4_w2.add(R.color.dark_grey);l4_w2.add(R.color.green);
                lvl_w2 = new Level(4, 140, 35, 50, 70, l4_w2, hauteur/800,0, hauteur/3);
                this.addLevel(lvl_w2);

                l5_w2.add(R.color.green);l5_w2.add(R.color.red);l5_w2.add(R.color.blue);l5_w2.add(R.color.yellow);
                lvl_w2 = new Level(5, 140, 40, 60, 80, l5_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l6_w2.add(R.color.blue);l6_w2.add(R.color.red);l6_w2.add(R.color.yellow);l6_w2.add(R.color.light_grey);
                lvl_w2 = new Level(6, 180, 80, 110, 150, l6_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l7_w2.add(R.color.blue);l7_w2.add(R.color.red);l7_w2.add(R.color.green);l7_w2.add(R.color.dark_grey);
                lvl_w2 = new Level(7, 175, 80, 110, 150, l7_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l8_w2.add(R.color.blue);l8_w2.add(R.color.dark_grey);l8_w2.add(R.color.light_grey);l8_w2.add(R.color.red);
                lvl_w2 = new Level(8, 160, 30, 50, 68, l8_w2, hauteur/550,0, hauteur/4);
                this.addLevel(lvl_w2);

                l9_w2.add(R.color.blue);l9_w2.add(R.color.dark_grey);l9_w2.add(R.color.green);l9_w2.add(R.color.yellow);
                lvl_w2 = new Level(9, 155, 35, 52, 70, l9_w2, hauteur/550,0, hauteur/4);
                this.addLevel(lvl_w2);

                l10_w2.add(R.color.blue);l10_w2.add(R.color.light_grey);l10_w2.add(R.color.green);l10_w2.add(R.color.yellow);
                lvl_w2 = new Level(10, 200, 100, 140, 200, l10_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l11_w2.add(R.color.yellow);l11_w2.add(R.color.blue);l11_w2.add(R.color.red);l11_w2.add(R.color.green);
                lvl_w2 = new Level(11, 200, 105, 142, 200, l11_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l12_w2.add(R.color.yellow);l12_w2.add(R.color.red);l12_w2.add(R.color.light_grey);l12_w2.add(R.color.dark_grey);
                lvl_w2 = new Level(12, 190, 102, 140, 198, l12_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l13_w2.add(R.color.yellow);l13_w2.add(R.color.dark_grey);l13_w2.add(R.color.red);l13_w2.add(R.color.blue);
                lvl_w2 = new Level(13, 190, 100, 140, 195, l13_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l14_w2.add(R.color.yellow);l14_w2.add(R.color.red);l14_w2.add(R.color.blue);l14_w2.add(R.color.light_grey);
                lvl_w2 = new Level(14, 110, 15, 27, 40, l14_w2, hauteur/1000,0, hauteur/3);
                this.addLevel(lvl_w2);

                l15_w2.add(R.color.yellow);l15_w2.add(R.color.dark_grey);l15_w2.add(R.color.light_grey);l15_w2.add(R.color.blue);
                lvl_w2 = new Level(15, 110, 18, 28, 45, l15_w2, hauteur/1000,0, hauteur/3);
                this.addLevel(lvl_w2);

                l16_w2.add(R.color.red);l16_w2.add(R.color.yellow);l16_w2.add(R.color.blue);l16_w2.add(R.color.green);
                lvl_w2 = new Level(16, 190, 85, 150, 185, l16_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l17_w2.add(R.color.green);l17_w2.add(R.color.red);l17_w2.add(R.color.blue);l17_w2.add(R.color.yellow);
                lvl_w2 = new Level(17, 180, 80, 145, 180, l17_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l18_w2.add(R.color.dark_grey);l18_w2.add(R.color.green);l18_w2.add(R.color.red);l18_w2.add(R.color.yellow);
                lvl_w2 = new Level(18, 185, 142, 160, 200, l18_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l19_w2.add(R.color.light_grey);l19_w2.add(R.color.red);l19_w2.add(R.color.yellow);l19_w2.add(R.color.green);
                lvl_w2 = new Level(19, 180, 112, 135, 155, l19_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l20_w2.add(R.color.yellow);l20_w2.add(R.color.dark_grey);l20_w2.add(R.color.green);l20_w2.add(R.color.light_grey);
                lvl_w2 = new Level(20, 130, 30, 50, 68, l20_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l21_w2.add(R.color.yellow);l21_w2.add(R.color.dark_grey);l21_w2.add(R.color.green);l21_w2.add(R.color.red);
                lvl_w2 = new Level(21, 130, 35, 52, 70, l21_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l22_w2.add(R.color.blue);l22_w2.add(R.color.red);l22_w2.add(R.color.yellow);l22_w2.add(R.color.green);
                lvl_w2 = new Level(22, 170, 110, 140, 165, l22_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l23_w2.add(R.color.yellow);l23_w2.add(R.color.red);l23_w2.add(R.color.dark_grey);l23_w2.add(R.color.light_grey);
                lvl_w2 = new Level(23, 170, 100, 150, 180, l23_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l24_w2.add(R.color.red);l24_w2.add(R.color.light_grey);l24_w2.add(R.color.green);l24_w2.add(R.color.blue);
                lvl_w2 = new Level(24, 155, 85, 110, 135, l24_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l25_w2.add(R.color.yellow);l25_w2.add(R.color.dark_grey);l25_w2.add(R.color.green);l25_w2.add(R.color.light_grey);
                lvl_w2 = new Level(25, 150, 40, 60, 80, l25_w2, hauteur/500,0, hauteur/4);
                this.addLevel(lvl_w2);

                l26_w2.add(R.color.yellow);l26_w2.add(R.color.dark_grey);l26_w2.add(R.color.blue);l26_w2.add(R.color.red);
                lvl_w2 = new Level(26, 145, 40, 60, 80, l26_w2, hauteur/500,0, hauteur/4);
                this.addLevel(lvl_w2);

                l27_w2.add(R.color.green);l27_w2.add(R.color.yellow);l27_w2.add(R.color.blue);l27_w2.add(R.color.red);
                lvl_w2 = new Level(27, 145, 45, 65, 90, l27_w2, hauteur/500,0, hauteur/4);
                this.addLevel(lvl_w2);

                l28_w2.add(R.color.light_grey);l28_w2.add(R.color.green);l28_w2.add(R.color.dark_grey);l28_w2.add(R.color.blue);
                lvl_w2 = new Level(28, 140, 25, 40, 60, l28_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l29_w2.add(R.color.light_grey);l29_w2.add(R.color.dark_grey);l29_w2.add(R.color.blue);l29_w2.add(R.color.red);
                lvl_w2 = new Level(29, 140, 28, 50, 70, l29_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                l30_w2.add(R.color.dark_grey);l30_w2.add(R.color.green);l30_w2.add(R.color.red);l30_w2.add(R.color.blue);
                lvl_w2 = new Level(30, 125, 32, 48, 60, l30_w2, hauteur/800,0, hauteur/4);
                this.addLevel(lvl_w2);

                break;
            case 3:
                Level lvl_w3;

                List<Integer> l1_w3 = new ArrayList<Integer>();List<Integer> l2_w3 = new ArrayList<Integer>();List<Integer> l3_w3 = new ArrayList<Integer>();
                List<Integer> l4_w3 = new ArrayList<Integer>();List<Integer> l5_w3 = new ArrayList<Integer>();List<Integer> l6_w3 = new ArrayList<Integer>();
                List<Integer> l7_w3 = new ArrayList<Integer>();List<Integer> l8_w3 = new ArrayList<Integer>();List<Integer> l9_w3 = new ArrayList<Integer>();
                List<Integer> l10_w3 = new ArrayList<Integer>();List<Integer> l11_w3 = new ArrayList<Integer>();List<Integer> l12_w3 = new ArrayList<Integer>();
                List<Integer> l13_w3 = new ArrayList<Integer>();List<Integer> l14_w3 = new ArrayList<Integer>();List<Integer> l15_w3 = new ArrayList<Integer>();
                List<Integer> l16_w3 = new ArrayList<Integer>();List<Integer> l17_w3 = new ArrayList<Integer>();List<Integer> l18_w3 = new ArrayList<Integer>();
                List<Integer> l19_w3 = new ArrayList<Integer>();List<Integer> l20_w3 = new ArrayList<Integer>();List<Integer> l21_w3 = new ArrayList<Integer>();
                List<Integer> l22_w3 = new ArrayList<Integer>();List<Integer> l23_w3 = new ArrayList<Integer>();List<Integer> l24_w3 = new ArrayList<Integer>();
                List<Integer> l25_w3 = new ArrayList<Integer>();List<Integer> l26_w3 = new ArrayList<Integer>();List<Integer> l27_w3 = new ArrayList<Integer>();
                List<Integer> l28_w3 = new ArrayList<Integer>();List<Integer> l29_w3 = new ArrayList<Integer>();List<Integer> l30_w3 = new ArrayList<Integer>();


                l1_w3.add(R.color.blue);l1_w3.add(R.color.red);l1_w3.add(R.color.light_grey);l1_w3.add(R.color.yellow);
                l1_w3.add(R.color.dark_grey);
                lvl_w3 = new Level(1, 240, 80, 160, 240, l1_w3, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l2_w3.add(R.color.green);l2_w3.add(R.color.red);l2_w3.add(R.color.light_grey);l2_w3.add(R.color.yellow);
                l2_w3.add(R.color.blue);
                lvl_w3 = new Level(2, 230, 75, 155, 230, l2_w3, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l3_w3.add(R.color.red);l3_w3.add(R.color.blue);l3_w3.add(R.color.dark_grey);l3_w3.add(R.color.light_grey);
                l3_w3.add(R.color.yellow);
                lvl_w3 = new Level(3, 150, 25, 40, 62, l3_w3, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l4_w3.add(R.color.yellow);l4_w3.add(R.color.light_grey);l4_w3.add(R.color.blue);l4_w3.add(R.color.dark_grey);
                l4_w3.add(R.color.red);
                lvl_w3 = new Level(4, 145, 30, 55, 70, l4_w3, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l5_w3.add(R.color.dark_grey);l5_w3.add(R.color.blue);l5_w3.add(R.color.green);l5_w3.add(R.color.light_grey);
                l5_w3.add(R.color.red);
                lvl_w3 = new Level(5, 210, 90, 160, 255, l5_w3, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l6_w3.add(R.color.red);l6_w3.add(R.color.dark_grey);l6_w3.add(R.color.blue);l6_w3.add(R.color.green);
                l6_w3.add(R.color.light_grey);
                lvl_w3 = new Level(6, 190, 40, 60, 80, l6_w3, hauteur/350, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l7_w3.add(R.color.light_grey);l7_w3.add(R.color.green);l7_w3.add(R.color.red);l7_w3.add(R.color.dark_grey);
                l7_w3.add(R.color.blue);
                lvl_w3 = new Level(7, 180, 35, 55, 72, l7_w3, hauteur/300, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l8_w3.add(R.color.yellow);l8_w3.add(R.color.green);l8_w3.add(R.color.red);l8_w3.add(R.color.blue);
                l8_w3.add(R.color.dark_grey);
                lvl_w3 = new Level(8, 200, 120, 185, 220, l8_w3, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l9_w3.add(R.color.dark_grey);l9_w3.add(R.color.yellow);l9_w3.add(R.color.red);l9_w3.add(R.color.green);
                l9_w3.add(R.color.blue);
                lvl_w3 = new Level(9, 190, 102, 175, 205, l9_w3, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l10_w3.add(R.color.yellow);l10_w3.add(R.color.blue);l10_w3.add(R.color.dark_grey);l10_w3.add(R.color.red);
                l10_w3.add(R.color.green);
                lvl_w3 = new Level(10, 135, 30, 45, 65, l10_w3, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l11_w3.add(R.color.green);l11_w3.add(R.color.dark_grey);l11_w3.add(R.color.blue);l11_w3.add(R.color.light_grey);
                l11_w3.add(R.color.yellow);
                lvl_w3 = new Level(11, 125, 20, 35, 50, l11_w3, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l12_w3.add(R.color.light_grey);l12_w3.add(R.color.red);l12_w3.add(R.color.yellow);l12_w3.add(R.color.green);
                l12_w3.add(R.color.blue);
                lvl_w3 = new Level(12, 125, 22, 37, 55, l12_w3, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l13_w3.add(R.color.green);l13_w3.add(R.color.blue);l13_w3.add(R.color.light_grey);l13_w3.add(R.color.yellow);
                l13_w3.add(R.color.red);
                lvl_w3 = new Level(13, 120, 20, 35, 52, l13_w3, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l14_w3.add(R.color.blue);l14_w3.add(R.color.yellow);l14_w3.add(R.color.dark_grey);l14_w3.add(R.color.green);
                l14_w3.add(R.color.light_grey);
                lvl_w3 = new Level(14, 180, 75, 110, 160, l14_w3, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l15_w3.add(R.color.red);l15_w3.add(R.color.blue);l15_w3.add(R.color.yellow);l15_w3.add(R.color.dark_grey);
                l15_w3.add(R.color.green);
                lvl_w3 = new Level(15, 190, 80, 160, 240, l15_w3, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l16_w3.add(R.color.red);l16_w3.add(R.color.blue);l16_w3.add(R.color.dark_grey);l16_w3.add(R.color.green);
                l16_w3.add(R.color.yellow);
                lvl_w3 = new Level(16, 110, 24, 32, 48, l16_w3, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l17_w3.add(R.color.light_grey);l17_w3.add(R.color.blue);l17_w3.add(R.color.red);l17_w3.add(R.color.yellow);
                l17_w3.add(R.color.green);
                lvl_w3 = new Level(17, 110, 25, 32, 48, l17_w3, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l18_w3.add(R.color.green);l18_w3.add(R.color.yellow);l18_w3.add(R.color.red);l18_w3.add(R.color.blue);
                l18_w3.add(R.color.dark_grey);
                lvl_w3 = new Level(18, 180, 80, 160, 240, l18_w3, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l19_w3.add(R.color.blue);l19_w3.add(R.color.green);l19_w3.add(R.color.dark_grey);l19_w3.add(R.color.yellow);
                l19_w3.add(R.color.light_grey);
                lvl_w3 = new Level(19, 180, 75, 150, 220, l19_w3, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l20_w3.add(R.color.red);l20_w3.add(R.color.green);l20_w3.add(R.color.yellow);l20_w3.add(R.color.dark_grey);
                l20_w3.add(R.color.blue);
                lvl_w3 = new Level(20, 175, 75, 150, 205, l20_w3, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l21_w3.add(R.color.blue);l21_w3.add(R.color.dark_grey);l21_w3.add(R.color.red);l21_w3.add(R.color.light_grey);
                l21_w3.add(R.color.yellow);
                lvl_w3 = new Level(21, 130, 40, 60, 80, l21_w3, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l22_w3.add(R.color.light_grey);l22_w3.add(R.color.red);l22_w3.add(R.color.green);l22_w3.add(R.color.yellow);
                l22_w3.add(R.color.blue);
                lvl_w3 = new Level(22, 130, 50, 70, 90, l22_w3, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l23_w3.add(R.color.blue);l23_w3.add(R.color.light_grey);l23_w3.add(R.color.dark_grey);l23_w3.add(R.color.red);
                l23_w3.add(R.color.green);
                lvl_w3 = new Level(23, 175, 85, 160, 210, l23_w3, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l24_w3.add(R.color.yellow);l24_w3.add(R.color.blue);l24_w3.add(R.color.red);l24_w3.add(R.color.green);
                l24_w3.add(R.color.light_grey);
                lvl_w3 = new Level(24, 150, 65, 100, 135, l24_w3, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l25_w3.add(R.color.yellow);l25_w3.add(R.color.blue);l25_w3.add(R.color.red);l25_w3.add(R.color.dark_grey);
                l25_w3.add(R.color.light_grey);
                lvl_w3 = new Level(25, 180, 30, 45, 65, l25_w3, hauteur/300, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l26_w3.add(R.color.dark_grey);l26_w3.add(R.color.blue);l26_w3.add(R.color.yellow);l26_w3.add(R.color.red);
                l26_w3.add(R.color.green);
                lvl_w3 = new Level(26, 125, 60, 80, 100, l26_w3, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l27_w3.add(R.color.green);l27_w3.add(R.color.yellow);l27_w3.add(R.color.red);l27_w3.add(R.color.blue);
                l27_w3.add(R.color.light_grey);
                lvl_w3 = new Level(27, 130, 65, 82, 105, l27_w3, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l28_w3.add(R.color.dark_grey);l28_w3.add(R.color.red);l28_w3.add(R.color.blue);l28_w3.add(R.color.green);
                l28_w3.add(R.color.light_grey);
                lvl_w3 = new Level(28, 140, 75, 90, 125, l28_w3, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l29_w3.add(R.color.dark_grey);l29_w3.add(R.color.yellow);l29_w3.add(R.color.green);l29_w3.add(R.color.red);
                l29_w3.add(R.color.blue);
                lvl_w3 = new Level(29, 160, 95, 130, 175, l29_w3, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w3);

                l30_w3.add(R.color.yellow);l30_w3.add(R.color.green);l30_w3.add(R.color.light_grey);l30_w3.add(R.color.blue);
                l30_w3.add(R.color.red);
                lvl_w3 = new Level(30, 150, 85, 100, 120, l30_w3, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w3);
                break;
            case 4:
                Level lvl_w4;

                List<Integer> l1_w4 = new ArrayList<Integer>();List<Integer> l2_w4 = new ArrayList<Integer>();List<Integer> l3_w4 = new ArrayList<Integer>();
                List<Integer> l4_w4 = new ArrayList<Integer>();List<Integer> l5_w4 = new ArrayList<Integer>();List<Integer> l6_w4 = new ArrayList<Integer>();
                List<Integer> l7_w4 = new ArrayList<Integer>();List<Integer> l8_w4 = new ArrayList<Integer>();List<Integer> l9_w4 = new ArrayList<Integer>();
                List<Integer> l10_w4 = new ArrayList<Integer>();List<Integer> l11_w4 = new ArrayList<Integer>();List<Integer> l12_w4 = new ArrayList<Integer>();
                List<Integer> l13_w4 = new ArrayList<Integer>();List<Integer> l14_w4 = new ArrayList<Integer>();List<Integer> l15_w4 = new ArrayList<Integer>();
                List<Integer> l16_w4 = new ArrayList<Integer>();List<Integer> l17_w4 = new ArrayList<Integer>();List<Integer> l18_w4 = new ArrayList<Integer>();
                List<Integer> l19_w4 = new ArrayList<Integer>();List<Integer> l20_w4 = new ArrayList<Integer>();List<Integer> l21_w4 = new ArrayList<Integer>();
                List<Integer> l22_w4 = new ArrayList<Integer>();List<Integer> l23_w4 = new ArrayList<Integer>();List<Integer> l24_w4 = new ArrayList<Integer>();
                List<Integer> l25_w4 = new ArrayList<Integer>();List<Integer> l26_w4 = new ArrayList<Integer>();List<Integer> l27_w4 = new ArrayList<Integer>();
                List<Integer> l28_w4 = new ArrayList<Integer>();List<Integer> l29_w4 = new ArrayList<Integer>();List<Integer> l30_w4 = new ArrayList<Integer>();

                l1_w4.add(R.color.blue);l1_w4.add(R.color.red);l1_w4.add(R.color.light_grey);l1_w4.add(R.color.yellow);
                l1_w4.add(R.color.dark_grey);l1_w4.add(R.color.green);
                lvl_w4 = new Level(1, 240, 80, 160, 240, l1_w4, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l2_w4.add(R.color.green);l2_w4.add(R.color.red);l2_w4.add(R.color.light_grey);l2_w4.add(R.color.yellow);
                l2_w4.add(R.color.blue);l2_w4.add(R.color.dark_grey);
                lvl_w4 = new Level(2, 230, 75, 155, 230, l2_w4, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l3_w4.add(R.color.red);l3_w4.add(R.color.blue);l3_w4.add(R.color.dark_grey);l3_w4.add(R.color.light_grey);
                l3_w4.add(R.color.yellow);l3_w4.add(R.color.green);
                lvl_w4 = new Level(3, 150, 25, 40, 62, l3_w4, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l4_w4.add(R.color.yellow);l4_w4.add(R.color.light_grey);l4_w4.add(R.color.blue);l4_w4.add(R.color.dark_grey);
                l4_w4.add(R.color.red);l4_w4.add(R.color.green);
                lvl_w4 = new Level(4, 145, 30, 55, 70, l4_w4, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l5_w4.add(R.color.dark_grey);l5_w4.add(R.color.blue);l5_w4.add(R.color.green);l5_w4.add(R.color.light_grey);
                l5_w4.add(R.color.red);l5_w4.add(R.color.yellow);
                lvl_w4 = new Level(5, 210, 90, 160, 255, l5_w4, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l6_w4.add(R.color.red);l6_w4.add(R.color.dark_grey);l6_w4.add(R.color.blue);l6_w4.add(R.color.green);
                l6_w4.add(R.color.light_grey);l6_w4.add(R.color.yellow);
                lvl_w4 = new Level(6, 190, 40, 60, 80, l6_w4, hauteur/350, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l7_w4.add(R.color.light_grey);l7_w4.add(R.color.green);l7_w4.add(R.color.red);l7_w4.add(R.color.dark_grey);
                l7_w4.add(R.color.blue);l7_w4.add(R.color.yellow);
                lvl_w4 = new Level(7, 180, 35, 55, 72, l7_w4, hauteur/300, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l8_w4.add(R.color.yellow);l8_w4.add(R.color.green);l8_w4.add(R.color.red);l8_w4.add(R.color.blue);
                l8_w4.add(R.color.dark_grey);l8_w4.add(R.color.light_grey);
                lvl_w4 = new Level(8, 200, 120, 185, 220, l8_w4, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l9_w4.add(R.color.dark_grey);l9_w4.add(R.color.yellow);l9_w4.add(R.color.red);l9_w4.add(R.color.green);
                l9_w4.add(R.color.blue);l9_w4.add(R.color.light_grey);
                lvl_w4 = new Level(9, 190, 102, 175, 205, l9_w4, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l10_w4.add(R.color.yellow);l10_w4.add(R.color.blue);l10_w4.add(R.color.dark_grey);l10_w4.add(R.color.red);
                l10_w4.add(R.color.green);l10_w4.add(R.color.light_grey);
                lvl_w4 = new Level(10, 135, 30, 45, 65, l10_w4, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l11_w4.add(R.color.green);l11_w4.add(R.color.dark_grey);l11_w4.add(R.color.blue);l11_w4.add(R.color.light_grey);
                l11_w4.add(R.color.yellow);l11_w4.add(R.color.red);
                lvl_w4 = new Level(11, 125, 20, 35, 50, l11_w4, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l12_w4.add(R.color.light_grey);l12_w4.add(R.color.red);l12_w4.add(R.color.yellow);l12_w4.add(R.color.green);
                l12_w4.add(R.color.blue);l12_w4.add(R.color.dark_grey);
                lvl_w4 = new Level(12, 125, 22, 37, 55, l12_w4, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l13_w4.add(R.color.green);l13_w4.add(R.color.blue);l13_w4.add(R.color.light_grey);l13_w4.add(R.color.yellow);
                l13_w4.add(R.color.red);l13_w4.add(R.color.dark_grey);
                lvl_w4 = new Level(13, 120, 20, 35, 52, l13_w4, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l14_w4.add(R.color.blue);l14_w4.add(R.color.yellow);l14_w4.add(R.color.dark_grey);l14_w4.add(R.color.green);
                l14_w4.add(R.color.light_grey);l14_w4.add(R.color.red);
                lvl_w4 = new Level(14, 180, 75, 110, 160, l14_w4, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l15_w4.add(R.color.red);l15_w4.add(R.color.blue);l15_w4.add(R.color.yellow);l15_w4.add(R.color.dark_grey);
                l15_w4.add(R.color.green);l15_w4.add(R.color.light_grey);
                lvl_w4 = new Level(15, 190, 80, 160, 240, l15_w4, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l16_w4.add(R.color.red);l16_w4.add(R.color.blue);l16_w4.add(R.color.dark_grey);l16_w4.add(R.color.green);
                l16_w4.add(R.color.yellow);l16_w4.add(R.color.light_grey);
                lvl_w4 = new Level(16, 110, 24, 32, 48, l16_w4, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l17_w4.add(R.color.light_grey);l17_w4.add(R.color.blue);l17_w4.add(R.color.red);l17_w4.add(R.color.yellow);
                l17_w4.add(R.color.green);l17_w4.add(R.color.dark_grey);
                lvl_w4 = new Level(17, 110, 25, 32, 48, l17_w4, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l18_w4.add(R.color.green);l18_w4.add(R.color.yellow);l18_w4.add(R.color.red);l18_w4.add(R.color.blue);
                l18_w4.add(R.color.dark_grey);l18_w4.add(R.color.light_grey);
                lvl_w4 = new Level(18, 180, 80, 160, 240, l18_w4, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l19_w4.add(R.color.blue);l19_w4.add(R.color.green);l19_w4.add(R.color.dark_grey);l19_w4.add(R.color.yellow);
                l19_w4.add(R.color.light_grey);l19_w4.add(R.color.red);
                lvl_w4 = new Level(19, 180, 75, 150, 220, l19_w4, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l20_w4.add(R.color.red);l20_w4.add(R.color.green);l20_w4.add(R.color.yellow);l20_w4.add(R.color.dark_grey);
                l20_w4.add(R.color.blue);l20_w4.add(R.color.light_grey);
                lvl_w4 = new Level(20, 175, 75, 150, 205, l20_w4, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l21_w4.add(R.color.blue);l21_w4.add(R.color.dark_grey);l21_w4.add(R.color.red);l21_w4.add(R.color.light_grey);
                l21_w4.add(R.color.yellow);l21_w4.add(R.color.green);
                lvl_w4 = new Level(21, 130, 40, 60, 80, l21_w4, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l22_w4.add(R.color.light_grey);l22_w4.add(R.color.red);l22_w4.add(R.color.green);l22_w4.add(R.color.yellow);
                l22_w4.add(R.color.blue);l22_w4.add(R.color.dark_grey);
                lvl_w4 = new Level(22, 130, 50, 70, 90, l22_w4, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l23_w4.add(R.color.blue);l23_w4.add(R.color.light_grey);l23_w4.add(R.color.dark_grey);l23_w4.add(R.color.red);
                l23_w4.add(R.color.green);l23_w4.add(R.color.yellow);
                lvl_w4 = new Level(23, 175, 85, 160, 210, l23_w4, hauteur/800, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l24_w4.add(R.color.yellow);l24_w4.add(R.color.blue);l24_w4.add(R.color.red);l24_w4.add(R.color.green);
                l24_w4.add(R.color.light_grey);l24_w4.add(R.color.dark_grey);
                lvl_w4 = new Level(24, 150, 65, 100, 135, l24_w4, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l25_w4.add(R.color.yellow);l25_w4.add(R.color.blue);l25_w4.add(R.color.red);l25_w4.add(R.color.dark_grey);
                l25_w4.add(R.color.light_grey);l25_w4.add(R.color.green);
                lvl_w4 = new Level(25, 180, 30, 45, 65, l25_w4, hauteur/300, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l26_w4.add(R.color.dark_grey);l26_w4.add(R.color.blue);l26_w4.add(R.color.yellow);l26_w4.add(R.color.red);
                l26_w4.add(R.color.green);l26_w4.add(R.color.light_grey);
                lvl_w4 = new Level(26, 125, 60, 80, 100, l26_w4, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l27_w4.add(R.color.green);l27_w4.add(R.color.yellow);l27_w4.add(R.color.red);l27_w4.add(R.color.blue);
                l27_w4.add(R.color.light_grey);l27_w4.add(R.color.dark_grey);
                lvl_w4 = new Level(27, 130, 65, 82, 105, l27_w4, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l28_w4.add(R.color.dark_grey);l28_w4.add(R.color.red);l28_w4.add(R.color.blue);l28_w4.add(R.color.green);
                l28_w4.add(R.color.light_grey);l28_w4.add(R.color.yellow);
                lvl_w4 = new Level(28, 140, 75, 90, 125, l28_w4, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l29_w4.add(R.color.dark_grey);l29_w4.add(R.color.yellow);l29_w4.add(R.color.green);l29_w4.add(R.color.red);
                l29_w4.add(R.color.blue);l29_w4.add(R.color.light_grey);
                lvl_w4 = new Level(29, 160, 95, 130, 175, l29_w4, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w4);

                l30_w4.add(R.color.yellow);l30_w4.add(R.color.green);l30_w4.add(R.color.light_grey);l30_w4.add(R.color.blue);
                l30_w4.add(R.color.red);l30_w4.add(R.color.dark_grey);
                lvl_w4 = new Level(30, 150, 85, 100, 120, l30_w4, hauteur/1000, 0, hauteur/4);
                this.addLevel(lvl_w4);
                break;
        }
    }
}
