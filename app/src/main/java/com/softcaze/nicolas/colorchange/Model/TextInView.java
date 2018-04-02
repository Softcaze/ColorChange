package com.softcaze.nicolas.colorchange.Model;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Nicolas on 31/03/2018.
 */

public class TextInView {
    protected float x;
    protected float y;
    protected String txt;

    public TextInView(){
    }

    public TextInView(String s){
        txt = s;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public String getTxt() {
        return txt;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public boolean isClicked(float x, float y, Context context){
        Paint p = new Paint();
        Rect r = new Rect();
        p.setTextSize(Constance.getDpSize(20, context));

        p.getTextBounds(txt, 0, txt.length(), r);

        if(x >= this.x && x < this.x + (float) r.width() && y <= this.y && y > this.y - (float) r.height()){
            return true;
        }

        return false;
    }
}
