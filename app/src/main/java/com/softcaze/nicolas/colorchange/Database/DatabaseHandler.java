package com.softcaze.nicolas.colorchange.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nicolas on 02/03/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper{
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

    public static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + NOM_TABLE_USER + " (" +
            COL_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NBR_LIFE + " INTEGER, " + COL_FIRST_UTILISATION + " INTEGER, " +
            COL_TIME_LAST_LIFE + " TEXT);";

    public static final String CREATE_TABLE_LEVEL = "CREATE TABLE IF NOT EXISTS " + NOM_TABLE_LEVEL + " (" +
            COL_ID_LEVEL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NUM + " INTEGER, " + COL_ID_WORLD_LEVEL + " INTEGER, " + COL_SCORE + " INTEGER);";

    public static final String CREATE_TABLE_WORLD = "CREATE TABLE IF NOT EXISTS " + NOM_TABLE_WORLD + " (" +
            COL_ID_WORLD + " INTEGER PRIMARY KEY, " +
            COL_NBR_STAR + " INTEGER);";

    public static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + NOM_TABLE_USER + ";";
    public static final String DROP_TABLE_LEVEL = "DROP TABLE IF EXISTS " + NOM_TABLE_LEVEL + ";";
    public static final String DROP_TABLE_WORLD = "DROP TABLE IF EXISTS " + NOM_TABLE_WORLD + ";";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_LEVEL);
        db.execSQL(CREATE_TABLE_WORLD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(DROP_TABLE_LEVEL);
        db.execSQL(DROP_TABLE_WORLD);
        onCreate(db);
    }
}
