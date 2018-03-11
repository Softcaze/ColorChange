package com.softcaze.nicolas.colorchange.Model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.softcaze.nicolas.colorchange.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 21/02/2018.
 */

public class LaneColor {
    protected Bitmap img;
    protected float x;
    protected float y;
    protected int color;

    public LaneColor(Bitmap i, int x, int y, int color){
        this.img = i;
        this.y = y;
        this.x = x;
        this.color = color;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public Bitmap getImg() {
        return img;
    }

    public int getColor() {
        return color;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
