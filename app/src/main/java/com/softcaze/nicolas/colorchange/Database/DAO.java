package com.softcaze.nicolas.colorchange.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.Model.Level;
import com.softcaze.nicolas.colorchange.Model.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 02/03/2018.
 */

public class DAO {
    public static int VERSION = 35;
    public static String NOM_DB = "colorchangedatabase.db";

    public static final String NOM_TABLE_USER = "user";
    public static final String COL_ID_USER = "id_user";
    public static final int NUM_COL_ID_USER = 0;
    public static final String COL_NBR_LIFE = "nbr_life";
    public static final int NUM_COL_NBR_LIFE = 1;
    public static final String COL_FIRST_UTILISATION = "first_utilisation";
    public static final int NUM_COL_FIRST_UTILISATION = 2;
    public static final String COL_TIME_LAST_LIFE = "time_last_life";
    public static final int NUM_COL_TIME_LAST_LIFE = 3;

    public static final String NOM_TABLE_LEVEL = "level";
    public static final String COL_ID_LEVEL = "id_level";
    public static final int NUM_COL_ID_LEVEL = 0;
    public static final String COL_NUM = "num";
    public static final int NUM_COL_NUM = 1;
    public static final String COL_ID_WORLD_LEVEL = "idWorldLevel";
    public static final int NUM_COL_ID_WORLD_LEVEL = 2;
    public static final String COL_SCORE = "score";
    public static final int NUM_COL_SCORE = 3;

    public static final String NOM_TABLE_WORLD = "world";
    public static final String COL_ID_WORLD = "id_world";
    public static final int NUM_COL_ID_WORLD = 0;
    public static final String COL_NBR_STAR = "nbr_star";
    public static final int NUM_COL_NBR_STAR = 1;

    protected SQLiteDatabase database;
    protected DatabaseHandler handler;

    public DAO(Context context)
    {
        handler = new DatabaseHandler(context, NOM_DB, null, VERSION);
        // TODO Auto-generated constructor stub
    }

    public void open()
    {
        database = handler.getWritableDatabase();
    }

    public void close()
    {
        database.close();
    }

    public SQLiteDatabase getDatabase()
    {
        return database;
    }

    public int getNbrLife(){
        Cursor c = database.rawQuery("SELECT * FROM " + NOM_TABLE_USER, null);

        if(c.getCount() != 0){
            try{
                c.moveToFirst();

                return c.getInt(NUM_COL_NBR_LIFE);
            }
            catch (Exception e){
                Log.i("GetNBRLIFE", "Exception : " + e);
            }
        }

        return -1;
    }

    public String getTimeLastLife(){
        String timeLastLife = "";

        Cursor c = database.rawQuery("SELECT * FROM " + NOM_TABLE_USER, null);

        if(c.getCount() != 0){
            try{
                c.moveToFirst();

                timeLastLife = c.getString(NUM_COL_TIME_LAST_LIFE);
            }
            catch (Exception e){
                Log.i("GetTimeLastLife", "Exception : " + e);
            }
        }

        if(timeLastLife == null){
            timeLastLife = "";
        }

        return timeLastLife;
    }

    public void saveTimeLastLife(){
        Cursor c = database.rawQuery("SELECT * FROM " + NOM_TABLE_USER, null);

        Long currentTimeMillis = System.currentTimeMillis();
        String currentTimeMillisStr = currentTimeMillis.toString();
        ContentValues value = new ContentValues();
        value.put( COL_TIME_LAST_LIFE, currentTimeMillisStr);

        if(c.getCount() == 0){
            try{
                database.insert(NOM_TABLE_USER, null, value);
            }
            catch (Exception e){
                Log.i("SAVE TIME LIFE", "Exception : " + e);
            }
        }
        else{
            try{
                database.update(NOM_TABLE_USER, value, null, null);
            }
            catch (Exception e){
                Log.i("UPDATE TIME LIFE", "Expcetion : " + e);
            }
        }
    }

    public void setNbrLife(int nbrLife){
        Cursor c = database.rawQuery("SELECT * FROM " + NOM_TABLE_USER, null);

        ContentValues value = new ContentValues();
        value.put(COL_NBR_LIFE, nbrLife);

        if(c.getCount() == 0){
            try{
                database.insert(NOM_TABLE_USER, null, value);
            }
            catch (Exception e) {
                Log.i("INSERT NBR LIFE", "Expcetion : " + e);
            }
        }
        else {
            try{
                database.update(NOM_TABLE_USER, value, null, null);
            }
            catch (Exception e){
                Log.i("UPDATE NBR LIFE", "Expcetion : " + e );
            }
        }
    }

    public void saveLevel(int numLevel, int numWorld, int score){
        Log.i("SAVE LEVEL", " ID LEVEL : " + numLevel + " ID WORLD : " + numWorld);
        Cursor c = database.rawQuery("SELECT * FROM " + NOM_TABLE_LEVEL + " WHERE " + COL_NUM + " = " + numLevel + " AND " + COL_ID_WORLD_LEVEL + " = " + numWorld, null);
        ContentValues value = new ContentValues();

        value.put(COL_SCORE, score);

        Log.i("SAVE LEVEL", "score : " + score);
        if(c.getCount() == 0){
            try{
                value.put(COL_NUM, numLevel);
                value.put(COL_ID_WORLD_LEVEL, numWorld);
                database.insert(NOM_TABLE_LEVEL, null, value);
            }
            catch (Exception e){
                Log.i("INSERT SAVE LEVEL", "Exception : " + e);
            }
        }
        else{
            try{
                database.update(NOM_TABLE_LEVEL, value, COL_NUM + " = " + numLevel + " AND " + COL_ID_WORLD_LEVEL + " = " + numWorld, null);
            }
            catch (Exception e){
                Log.i("UPDATE SAVE LEVEL", "Exception : " + e);
            }
        }
    }

    public int getScoreLevel(World world, Level level){
        //List<Level> levels = new ArrayList<Level>();
        int nbrScore = 0;

        Cursor c = database.rawQuery("SELECT * FROM " + NOM_TABLE_LEVEL + " WHERE " + COL_ID_WORLD_LEVEL + " = " + world.getNum() + " AND " + COL_NUM + " = " + level.getNum(), null);

        if(c.getCount() != 0){
            c.moveToFirst();

            nbrScore = c.getInt(NUM_COL_SCORE);
        }

        return nbrScore;
    }

    public void setNbrStarWorld(World world, int nbrStar){
        Cursor c = database.rawQuery("SELECT * FROM " + NOM_TABLE_WORLD + " WHERE " + COL_ID_WORLD + " = " + world.getNum(), null);

        ContentValues values = new ContentValues();
        values.put(COL_NBR_STAR, nbrStar);

        if(c.getCount() == 0){
            try{
                database.insert(NOM_TABLE_WORLD, null, values);
            }
            catch (Exception e){
                Log.i("SET NBR STAR INSERT", "Exception : " + e);
            }
        }
        else{
            c.moveToFirst();

            try{
                database.update(NOM_TABLE_WORLD, values, COL_ID_WORLD + " = " + world.getNum(), null);
            }
            catch (Exception e){
                Log.i("SET NBR STAR UPDATE", "Exception : " + e);
            }
        }
    }

    public int getNbrStarByWorld(World world){
        Cursor c = database.rawQuery("SELECT * FROM " + NOM_TABLE_WORLD + " WHERE " + COL_ID_WORLD + " = " + world.getNum(), null);

        int nbrStar = 0;

        if(c.getCount() != 0){
            c.moveToFirst();

            try{
                nbrStar = c.getInt(NUM_COL_NBR_STAR);
            }
            catch (Exception e){
                Log.i("GET NBR STAR", "Exception : " + e);
            }
        }


        return nbrStar;
    }

    public int getFirstUtilisation(){
        int firstUse;

        Cursor c = database.rawQuery("SELECT " + COL_FIRST_UTILISATION + " FROM " + NOM_TABLE_USER, null);

        if(c.getCount() != 0){
            c.moveToFirst();

            firstUse = c.getInt(0);

            if(firstUse == 0){
                firstUse = Constance.TRUE;
            }
        }
        else{
            firstUse = Constance.TRUE;
        }

        Log.i("GET FIRST USE", " USE : " + firstUse);
        return firstUse;
    }

    public void setFirstUtilisation(int use){
        Log.i("SET FIRST USE", " USE : " + use);
        Cursor c = database.rawQuery("SELECT * FROM " + NOM_TABLE_USER, null);
        ContentValues values = new ContentValues();
        values.put(COL_FIRST_UTILISATION, use);

        if(c.getCount() == 0){
            try{
                database.insert(NOM_TABLE_USER, null, values);
            }
            catch(Exception e){
                Log.i("SET FIRST USE INSERT", "Exception : " + e);
            }
        }
        else{
            try{
                database.update(NOM_TABLE_USER, values, null, null);
            }
            catch(Exception e){
                Log.i("SET FIRST USE UPDATE", "Exception : " + e);
            }
        }
    }
}
