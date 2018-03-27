package com.softcaze.nicolas.colorchange.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.softcaze.nicolas.colorchange.Database.DAO;
import com.softcaze.nicolas.colorchange.Database.DatabaseHandler;
import com.softcaze.nicolas.colorchange.R;

import java.util.HashMap;

/**
 * Created by Nicolas on 20/02/2018.
 */

public class Constance {
    /**
     * SETTING
     */
    public static final boolean hasSounds = true;


    // ID WORLD
    public static final int ID_WORLD_FOOTBALL_AMERICAIN = 1;
    public static final int ID_WORLD_RACING = 2;
    public static final int ID_WORLD_AIRPLANE = 3;
    public static final int ID_WORLD_KAYAK = 4;

    // => DIFINIS LA TAILLE DE L'IMAGE
    public static final int NBR_COLUMN = 6;

    // ETAT DE LA PARTIE
    public static final int TUTORIEL = 1;
    public static final int EN_JEU = 2;
    public static final int END = 3;
    public static final int FIRST_USE = 4;
    public static final int REWARDING = 5;

    // ID PAGE
    public static final int MENU = 1;
    public static final int LIST_WORLD = 2;
    public static final int LIST_LEVEL = 3;
    public static final int GAME = 4;

    // First Utilisation
    public static final int TRUE = 1;
    public static final int FALSE = 2;

    public static final int STEP_1 = 1;
    public static final int STEP_2 = 2;
    public static final int STEP_3 = 3;
    public static final int STEP_4 = 4;
    public static final int STEP_5 = 5;
    public static final int STEP_6 = 6;
    public static final int STEP_7 = 7;
    public static final int STEP_8 = 8;

    public static final int NBR_LIFE_MAX = 10;
    public static final long TIME_BETWEEN_LIFE = 1; // Minutes

    public static int getDpSize(float v, Context c){
        float value = v; // margin in dips
        float d = c.getResources().getDisplayMetrics().density;
        return (int)(value * d);
    }

    public static Bitmap getResizedBitmap(Bitmap bm, double newWidth, double newHeight)
    {
        if(newHeight == 0)
        {
            newHeight = newWidth / ((double) bm.getWidth()/bm.getHeight());
        }
        else if(newWidth == 0)
        {
            newWidth = newHeight / ((double) bm.getHeight()/bm.getWidth());
        }
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public static Bitmap rotateBitmap(Bitmap original, float degrees) {
        int width = original.getWidth();
        int height = original.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);
//        Canvas canvas = new Canvas(rotatedBitmap);
//        canvas.drawBitmap(original, 5.0f, 0.0f, null);

        return rotatedBitmap;
    }

    public static String addZero(String nbr){
        if(Integer.valueOf(nbr) < 10){
            return "0" + nbr;
        }
        else{
            return nbr;
        }
    }
}
