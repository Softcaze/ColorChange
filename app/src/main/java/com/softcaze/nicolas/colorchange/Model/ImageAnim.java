package com.softcaze.nicolas.colorchange.Model;

import android.graphics.Bitmap;

/**
 * Created by Nicolas on 10/03/2018.
 */

public class ImageAnim {
    private Bitmap img;
    private float x;
    private float y;
    private float yBase;

    public ImageAnim(){
        ;
    }

    public ImageAnim(Bitmap i, float xx, float yy, float yb){
        this.img = i;
        this.x = xx;
        this.y = yy;
        this.yBase = yb;
    }

    public ImageAnim(ImageAnim a){
        this.img = a.img;
        this.x = a.x;
        this.y = a.y;
        this.yBase = a.yBase;
    }

    public Bitmap getImg() {
        return img;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public float getYBase() {
        return yBase;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setYBase(float yBase) {
        this.yBase = yBase;
    }
}
