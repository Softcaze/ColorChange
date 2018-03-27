package com.softcaze.nicolas.colorchange.Model;

import android.graphics.Bitmap;

/**
 * Created by Nicolas on 27/03/2018.
 */

public class Objet {
    protected Bitmap img;
    protected int x;
    protected int y;

    public Objet(){
        ;
    }

    public Bitmap getImg() {
        return img;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isClicked(float x, float y){
        if(x >= this.x && x < this.x + this.img.getWidth() && y >= this.y && y < this.y + this.img.getHeight()){
            return true;
        }

        return false;
    }
}
