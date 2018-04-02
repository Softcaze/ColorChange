package com.softcaze.nicolas.colorchange.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

import com.softcaze.nicolas.colorchange.R;

/**
 * Created by Nicolas on 31/03/2018.
 */

public class Popup {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected boolean isDisplay;
    protected TextInView title = new TextInView();
    protected TextInView content = new TextInView();
    protected TextInView btn1 = new TextInView();
    protected TextInView btn2 = new TextInView();

    public Popup(Context context){
        this.isDisplay = false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public TextInView getContent() {
        return content;
    }

    public TextInView getTitle() {
        return title;
    }

    public TextInView getBtn1() {
        return btn1;
    }

    public TextInView getBtn2() {
        return btn2;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public boolean isDisplay() {
        return isDisplay;
    }

    public void setDisplay(boolean display) {
        isDisplay = display;
    }

    public void setTitle(TextInView title) {
        this.title = title;
    }

    public void setContent(TextInView content) {
        this.content = content;
    }

    public void setBtn1(TextInView btn1) {
        this.btn1 = btn1;
    }

    public void setBtn2(TextInView btn2) {
        this.btn2 = btn2;
    }

    public void display(Canvas canvas, int l_ecran, int h_ecran, Context context){
        Rect bounds = new Rect();

        // Placement du titre
        title.setX(x + Constance.getDpSize(20, context));
        title.setY(y + Constance.getDpSize(40, context));

        // Placement du contenu
        content.setX(title.getX());
        content.setY(title.getY() + Constance.getDpSize(40, context));

        // Background Black transparent
        Paint paintBackground = new Paint();
        paintBackground.setColor(Color.parseColor("#434343"));
        paintBackground.setAlpha(240);
        canvas.drawRect(0, 0, l_ecran, h_ecran, paintBackground);

        // Background White popup
        paintBackground.setColor(Color.WHITE);
        paintBackground.setAlpha(255);
        canvas.drawRect(this.x, this.y, this.width, this.height, paintBackground);

        // Title
        Paint paintTxt = new Paint();
        paintTxt.setColor(Color.BLACK);
        paintTxt.setTextSize(Constance.getDpSize(24, context));
        paintTxt.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paintTxt.getTextBounds(title.getTxt(), 0, title.getTxt().length(), bounds);
        canvas.drawText(title.getTxt(), title.getX(), title.getY(), paintTxt);

        Bitmap imgHeart = Constance.getResizedBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.heart), l_ecran/15, 0);
        canvas.drawBitmap(imgHeart, title.getX() + bounds.width() + Constance.getDpSize(10, context), title.getY() - imgHeart.getHeight() + imgHeart.getHeight()/4, null);

        // Content
        paintTxt.setTextSize(Constance.getDpSize(20, context));
        paintTxt.setColor(Color.parseColor("#999999"));
        paintTxt.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

       //canvas.drawText(content.getTxt(), content.getX(), content.getY(), paintTxt);

        displayCenterContent(content.getTxt(), canvas, content.getX(), content.getY(), paintTxt, context, l_ecran);

        // Button 1
        Paint btnTxt = new Paint();
        btnTxt.setColor(Color.parseColor("#4A86E8"));
        btnTxt.setTextSize(Constance.getDpSize(20, context));


        // Placement du btn 2
        btnTxt.getTextBounds(btn2.getTxt(), 0, btn2.getTxt().length(), bounds);

        btn2.setX(width - bounds.width() - Constance.getDpSize(20, context));
        btn2.setY(height - bounds.height()/2 - Constance.getDpSize(20, context));

        // Placemet du btn 1
        btnTxt.getTextBounds(btn1.getTxt(), 0, btn1.getTxt().length(), bounds);
        btn1.setX(btn2.getX() - Constance.getDpSize(20, context) - bounds.width());
        btn1.setY(height - bounds.height()/2 - Constance.getDpSize(20, context));

        Bitmap imgShopBlue = Constance.getResizedBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shopping_cart_blue), l_ecran/20, 0);

        // Display Button 1
        canvas.drawBitmap(imgShopBlue, btn1.getX() - Constance.getDpSize(10, context) - imgShopBlue.getWidth(), btn1.getY() - imgShopBlue.getHeight(), null);
        canvas.drawText(btn1.getTxt(), btn1.getX(), btn1.getY(), btnTxt);

        // Display Button 2
        canvas.drawText(btn2.getTxt(), btn2.getX(), btn2.getY(), btnTxt);
    }


    public void displayCenterContent(String txt, Canvas canvas, float x, float y, Paint paint, Context context, float largeur_ecran){
        Rect b = new Rect();
        boolean multiLane = false;
        int indexEndFirstLane = 0;

        for(int i = 0; i < txt.length(); i++){
            paint.getTextBounds(txt, 0, i, b);

            if(b.width() > width - largeur_ecran/6 - x){
                multiLane = true;
                indexEndFirstLane = i;
                if(txt.charAt(i) == ' ') {
                    break;
                }
            }
        }
        if(multiLane){
            canvas.drawText(txt.substring(0, indexEndFirstLane), x, y, paint);
            canvas.drawText(txt.substring(indexEndFirstLane, txt.length()), x, y + Constance.getDpSize(25, context), paint);
        }
    }
}
