package com.softcaze.nicolas.colorchange.Model;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by Nicolas on 22/02/2018.
 */

public class ColorButton {
    protected Bitmap img;
    protected float x;
    protected float y;
    protected int color;

    public ColorButton(Bitmap i, float x, float y, int c){
        this.img = i;
        this.x = x;
        this.y = y;
        this.color = c;
    }

    public Bitmap getImg() {
        return img;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    public float getCenterX(){
        float centerX = 0;
        try{
            centerX = this.getX() + this.getImg().getWidth()/2;
        }
        catch(Exception e){
            Log.i("Color Button","Impossible de récupérer center X, Exception e : " + e);
        }

        return centerX;
    }

    public float getCenterY(){
        float centerY = 0;
        try{
            centerY = this.getY() + this.getImg().getHeight()/2;
        }
        catch(Exception e){
            Log.i("Color Button","Impossible de récupérer center Y, Exception e : " + e);
        }

        return centerY;
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

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isClicked(float x, float y){
        if(x >= this.x && x < this.x + this.img.getWidth() && y >= this.y && y < this.y + this.img.getHeight()){
            return true;
        }

        return false;
    }
}
