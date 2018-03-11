package com.softcaze.nicolas.colorchange.Model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.softcaze.nicolas.colorchange.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 21/02/2018.
 */

public class Vehicule {
    protected float x;
    protected float y;
    protected Bitmap img;
    protected int color;
    protected int widthScreen;

    public Vehicule(Bitmap i, int y, int widthScreen, int c){
        this.img = i;
        this.y = y;
        this.widthScreen = widthScreen;
        this.color = c;
    }

    public Vehicule(Vehicule v){
        this.x = v.x;
        this.y = v.y;
        this.img = v.img;
        this.color = v.color;
        this.widthScreen = v.widthScreen;
    }

    public int getColor() {
        return this.color;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Bitmap getImg() {
        return img;
    }

    public int getWidthScreen() {
        return widthScreen;
    }

    public void SetColor(int couleurActu) {
        this.color = couleurActu;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getRandomX(int realWidthImg){
        int numberRandom = 0;
        if(this.img != null){
            int Min = 0;
            int Max = widthScreen - realWidthImg;

            numberRandom = Min + (int)(Math.random() * ((Max - Min) + 1));
            return numberRandom;
        }
        return numberRandom;
    }
}
