package com.softcaze.nicolas.colorchange.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Nicolas on 14/02/2018.
 */

public class Level implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    int num;
    int scoreStar1;
    int scoreStar2;
    int scoreStar3;
    int distance;
    List<Integer> couleurs;
    int score;
    int speedStart;
    int coefSpeed;
    int nbrStars;

    public Level(int n, int spSt, int sSt1, int sSt2, int sSt3, List<Integer> c, int cS, int nbrStars, int d){
        this.num = n;
        this.score = 0;
        this.coefSpeed = cS;
        this.speedStart = spSt;
        this.scoreStar1 = sSt1;
        this.scoreStar2 = sSt2;
        this.scoreStar3 = sSt3;
        this.couleurs = c;
        this.nbrStars = nbrStars;
        this.distance = d;
    }

    public Level(Level l){
        this.num = l.num;
        this.score = l.score;
        this.coefSpeed = l.coefSpeed;
        this.speedStart = l.speedStart;
        this.scoreStar1 = l.scoreStar1;
        this.scoreStar2 = l.scoreStar2;
        this.scoreStar3 = l.scoreStar3;
        this.couleurs = l.couleurs;
        this.nbrStars = l.nbrStars;
        this.distance = l.distance;
    }

    public void addCouleurs(Integer c){
        couleurs.add(c);
    }

    public int getNum() {
        return num;
    }

    public List<Integer> getCouleurs() {
        return couleurs;
    }

    public int getScore() {
        return score;
    }


    public int getScoreStar1() {
        return scoreStar1;
    }

    public int getScoreStar2() {
        return scoreStar2;
    }

    public int getScoreStar3() {
        return scoreStar3;
    }

    public int getCoefSpeed() {
        return coefSpeed;
    }

    public int getSpeedStart() {
        return speedStart;
    }

    public int getNbrStars() {
        return nbrStars;
    }

    public int getDistance() {
        return distance;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setCouleurs(List<Integer> couleurs) {
        this.couleurs = couleurs;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setScoreStar1(int scoreStar1) {
        this.scoreStar1 = scoreStar1;
    }

    public void setScoreStar2(int scoreStar2) {
        this.scoreStar2 = scoreStar2;
    }

    public void setScoreStar3(int scoreStar3) {
        this.scoreStar3 = scoreStar3;
    }

    public void setCoefSpeed(int coefSpeed) {
        this.coefSpeed = coefSpeed;
    }

    public void setSpeedStart(int speedStart) {
        this.speedStart = speedStart;
    }

    public void setNbrStars(int nbrStars) {
        this.nbrStars = nbrStars;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
