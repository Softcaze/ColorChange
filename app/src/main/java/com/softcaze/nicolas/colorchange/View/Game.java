package com.softcaze.nicolas.colorchange.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.softcaze.nicolas.colorchange.Activity.ListLevelActivity;
import com.softcaze.nicolas.colorchange.Activity.LoadingActivity;
import com.softcaze.nicolas.colorchange.Activity.MainActivity;
import com.softcaze.nicolas.colorchange.Activity.ShopActivity;
import com.softcaze.nicolas.colorchange.Activity.TransitionActivityView;
import com.softcaze.nicolas.colorchange.AsyncTask.AnimImageY;
import com.softcaze.nicolas.colorchange.AsyncTask.CheckAutoTime;
import com.softcaze.nicolas.colorchange.AsyncTask.ManageSpeedVehic;
import com.softcaze.nicolas.colorchange.AsyncTask.ManagerDoor;
import com.softcaze.nicolas.colorchange.Database.DAO;
import com.softcaze.nicolas.colorchange.Model.ColorButton;
import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.Model.EtatGame;
import com.softcaze.nicolas.colorchange.Model.Global;
import com.softcaze.nicolas.colorchange.Model.ImageAnim;
import com.softcaze.nicolas.colorchange.Model.LaneColor;
import com.softcaze.nicolas.colorchange.Model.Level;
import com.softcaze.nicolas.colorchange.Model.Objet;
import com.softcaze.nicolas.colorchange.Model.Popup;
import com.softcaze.nicolas.colorchange.Model.TextInView;
import com.softcaze.nicolas.colorchange.Model.User;
import com.softcaze.nicolas.colorchange.Model.Vehicule;
import com.softcaze.nicolas.colorchange.Model.World;
import com.softcaze.nicolas.colorchange.R;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nicolas on 18/02/2018.
 */

public class Game extends View {
    protected static Map<Integer, Bitmap> btnColorMap = new HashMap<Integer, Bitmap>();
    protected static Map<Integer, Bitmap> vehiculeColorMap = new HashMap<Integer, Bitmap>();
    protected static Map<Integer, Bitmap> doorColorMap = new HashMap<Integer, Bitmap>();

    protected int LARGEUR_ECRAN;
    protected int HAUTEUR_ECRAN;

    protected TextInView btn1;
    protected TextInView btn2;

    protected Popup popup;
    protected Level levelActu;
    protected List<ColorButton> listColorBtn = new ArrayList<ColorButton>();
    protected List<Vehicule> listVehicules = new ArrayList<Vehicule>();
    protected LaneColor laneColor;

    protected RewardedVideoAd mRewardedVideoAd;

    protected DAO dao = null;
    protected MediaPlayer clickBtn;
    protected MediaPlayer vehiculeOk;
    protected MediaPlayer gameOver;

    protected World worldActu;
    protected int intDisparition = R.drawable.disparition;
    protected int intPause = R.drawable.pause;
    protected int score = 0;
    protected EtatGame etatGame;
    protected int bestScore = 0;

    protected int btnXRevive = 0;
    protected int btnYRevive = 0;

    protected int btnXAgain = 0;
    protected int btnYAgain = 0;
    protected int btnWidth = 0;

    protected int btnXMenu = 0;
    protected int btnYMenu = 0;
    protected int btnMenuWidth = 0;

    protected int btnXNext = 0;
    protected int btnYNext = 0;
    protected int btnNextWidth = 0;

    protected int btnHeight = 0;
    protected List<World> listWorld = new ArrayList<World>();
    protected InterstitialAd interAd;

    public static ManagerDoor task;
    public static AnimImageY taskAnimY;
    public static ManageSpeedVehic taskSpeedVehic;

    protected int nbrColumn = 0;

    protected User user;
    protected Objet btnPause;


    // ABOUT TUTORIEL FIRST USE
    protected int firstUse = Constance.TRUE;
    protected int etatFirstUse = Constance.STEP_1;
    protected boolean taskAnimIsStarted = false;

    protected ImageAnim hand;
    protected Vehicule vehicTuto;
    protected Global varGlobal;

    protected int countADInterstitel = 0;

    public Game(final Context context, int largeur, int hauteur, Level myLevel, World w, List<World> worlds, RewardedVideoAd ad, User u, EtatGame etat) {
        super(context);

        LARGEUR_ECRAN = largeur;
        HAUTEUR_ECRAN = hauteur;

        dao = new DAO(context);

        btnPause = new Objet();
        clickBtn = MediaPlayer.create(context, R.raw.click_btn);
        vehiculeOk = MediaPlayer.create(context, R.raw.vehicule_ok);
        gameOver = MediaPlayer.create(context, R.raw.game_over);

        Bitmap logoPause = Constance.getResizedBitmap(BitmapFactory.decodeResource(getResources(), intPause), LARGEUR_ECRAN / 13, 0);
        btnPause.setImg(logoPause);
        btnPause.setX(LARGEUR_ECRAN - logoPause.getWidth() - logoPause.getWidth() / 2);
        btnPause.setY(HAUTEUR_ECRAN / 30 - logoPause.getHeight() / 2);

        dao.open();

        countADInterstitel = dao.getCountAD();

        etatGame = etat;

        firstUse = dao.getFirstUtilisation();

        if(firstUse == Constance.TRUE){
            etatGame.setEtat(Constance.FIRST_USE);
            dao.setFirstUtilisation(Constance.TRUE);

            if(myLevel.getNum() != 1){
                etatGame.setEtat(Constance.TUTORIEL);
                dao.setFirstUtilisation(Constance.FALSE);
            }
        }

        dao.close();

        this.user = u;

        levelActu = new Level(myLevel);
        worldActu = w;
        listWorld = worlds;

        varGlobal = new Global(levelActu.getSpeedStart());
        nbrColumn = Constance.NBR_COLUMN;

        initStaticData();
        initColorButton();

        mRewardedVideoAd = ad;

        laneColor = new LaneColor(doorColorMap.get(levelActu.getCouleurs().get(0)), 0, HAUTEUR_ECRAN - HAUTEUR_ECRAN/4 - HAUTEUR_ECRAN/10, levelActu.getCouleurs().get(0));

        if(levelActu.getCouleurs().size() > 3){
            laneColor.setY(laneColor.getY() - HAUTEUR_ECRAN/25);
        }

        interAd = new InterstitialAd(context);
        interAd.setAdUnitId(context.getResources().getString(R.string.interstitial_unit_id));

        AdRequest adRequest = new AdRequest.Builder().build();
        interAd.loadAd(adRequest);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*
        Initialisation des viariable Paint (Fond des menu tutoriel et END)
         */
        // FOND
        Paint paintBlackRect = new Paint();
        paintBlackRect.setColor(Color.BLACK);
        paintBlackRect.setStyle(Paint.Style.FILL);

        // BORDER
        Paint paintBorderRect = new Paint();
        paintBorderRect.setStyle(Paint.Style.STROKE);
        paintBorderRect.setStrokeWidth(Constance.getDpSize(2, getContext()));
        paintBorderRect.setColor(Color.rgb(169,169,169));

        if(etatGame.getEtat() == Constance.REWARDING){
            etatGame.setEtat(Constance.TUTORIEL);
        }

        /**
         * Affichage du Tutoriel
         */
        if(etatGame.getEtat() == Constance.TUTORIEL){
            // Affichage Fond
            canvas.drawRect(0, HAUTEUR_ECRAN/2, LARGEUR_ECRAN, HAUTEUR_ECRAN/4, paintBlackRect);
            // Affichage Border
            canvas.drawRect(0, HAUTEUR_ECRAN/2, LARGEUR_ECRAN, HAUTEUR_ECRAN/4, paintBorderRect);

            // Affiche name LVL Tutoriel
            Paint paintTxtLvlTutoriel = new Paint();
            String txtLvlTutoriel = getResources().getString(R.string.libelle_lvl) + " " + levelActu.getNum();
            paintTxtLvlTutoriel.setColor(Color.WHITE);
            paintTxtLvlTutoriel.setTextAlign(Paint.Align.CENTER);
            paintTxtLvlTutoriel.setTextSize(Constance.getDpSize(40, getContext()));
            canvas.drawText(txtLvlTutoriel, LARGEUR_ECRAN/2, HAUTEUR_ECRAN/2 - HAUTEUR_ECRAN/6, paintTxtLvlTutoriel);

            String libelleTutoriel = "";

            if(score != 0){
                libelleTutoriel = getResources().getString(R.string.libelle__tutoriel_continue);
            }
            else{
                libelleTutoriel = getResources().getString(R.string.libelle_tutoriel_choose);
            }
            paintTxtLvlTutoriel.setTextSize(Constance.getDpSize(26, getContext()));
            paintTxtLvlTutoriel.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
            canvas.drawText(libelleTutoriel, LARGEUR_ECRAN/2, HAUTEUR_ECRAN/2 - HAUTEUR_ECRAN/6 + HAUTEUR_ECRAN/10, paintTxtLvlTutoriel);
        }

        /**
         * ENTETE (Logo + Nom du Niveau + SCORE + BOUTON PAUSE)
         */
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawLine(0, HAUTEUR_ECRAN / 15, LARGEUR_ECRAN, HAUTEUR_ECRAN / 15, paint);

        // Affichage Logo World
        Bitmap logoWorld = Constance.getResizedBitmap(BitmapFactory.decodeResource(getResources(), worldActu.getImg()), LARGEUR_ECRAN / 13, 0);
        canvas.drawBitmap(logoWorld, LARGEUR_ECRAN / 15 - logoWorld.getWidth() / 2, HAUTEUR_ECRAN / 30 - logoWorld.getHeight() / 2, null);

        // Affichage Name World
        Paint paintNameWorld = new Paint();
        paintNameWorld.setTextSize(Constance.getDpSize(18, getContext()));
        paintNameWorld.setColor(Color.WHITE);
        canvas.drawText(worldActu.getName(), LARGEUR_ECRAN / 15 * 2, HAUTEUR_ECRAN / 30 + paintNameWorld.getTextSize() / 2, paintNameWorld);

        // Affichage Score
        if(etatGame.getEtat() != Constance.FIRST_USE) {
            Paint paintScore = new Paint();
            paintScore.setTextSize(Constance.getDpSize(25, getContext()));
            paintScore.setColor(Color.WHITE);

            canvas.drawText("" + score, LARGEUR_ECRAN / 2, HAUTEUR_ECRAN / 30 + paintScore.getTextSize() / 2, paintScore);
        }


        // Affichage Pause button
        //canvas.drawBitmap(btnPause.getImg(), btnPause.getX(), btnPause.getY(), null);

        Bitmap star = Constance.getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.star), LARGEUR_ECRAN/15, 0);

        if(score >= levelActu.getScoreStar3()){
            if(taskSpeedVehic != null){
                taskSpeedVehic.cancel(true);

                if(taskSpeedVehic.isCancelled()){
                    taskSpeedVehic = null;
                }
            }
            canvas.drawBitmap(star, LARGEUR_ECRAN - btnPause.getImg().getWidth() - btnPause.getImg().getWidth() / 2 - star.getWidth()/2 - star.getWidth() - star.getWidth()/4, HAUTEUR_ECRAN/30 - star.getHeight()/2, null);
            canvas.drawBitmap(star, LARGEUR_ECRAN - btnPause.getImg().getWidth() - btnPause.getImg().getWidth() / 2 - star.getWidth()/2 - star.getWidth()*2 - star.getWidth()/4, HAUTEUR_ECRAN/30 - star.getHeight()/2, null);
            canvas.drawBitmap(star, LARGEUR_ECRAN - btnPause.getImg().getWidth() - btnPause.getImg().getWidth() / 2 - star.getWidth()/2 - star.getWidth()*3 - star.getWidth()/4, HAUTEUR_ECRAN/30 - star.getHeight()/2, null);
        }
        else if(score >= levelActu.getScoreStar2()){
            canvas.drawBitmap(star, LARGEUR_ECRAN - btnPause.getImg().getWidth() - btnPause.getImg().getWidth() / 2 - star.getWidth()/2 - star.getWidth() - star.getWidth()/4, HAUTEUR_ECRAN/30 - star.getHeight()/2, null);
            canvas.drawBitmap(star, LARGEUR_ECRAN - btnPause.getImg().getWidth() - btnPause.getImg().getWidth() / 2 - star.getWidth()/2 - star.getWidth()*2 - star.getWidth()/4, HAUTEUR_ECRAN/30 - star.getHeight()/2, null);
        }
        else if(score >= levelActu.getScoreStar1()){
            canvas.drawBitmap(star, LARGEUR_ECRAN - btnPause.getImg().getWidth() - btnPause.getImg().getWidth() / 2 - star.getWidth()/2 - star.getWidth() - star.getWidth()/4, HAUTEUR_ECRAN/30 - star.getHeight()/2, null);
        }


        /**
         * AFFICHAGE DU PLATEAU DE JEU
         */
        for (int i = listVehicules.size() - 1; i >= 0; i--) {
            if (listVehicules.get(i).getImg() != null) {
                canvas.drawBitmap(listVehicules.get(i).getImg(), listVehicules.get(i).getX(), listVehicules.get(i).getY(), null);
                if(etatGame.getEtat() != Constance.END && !task.getPause()){
                    listVehicules.get(i).setY(listVehicules.get(i).getY() + HAUTEUR_ECRAN / varGlobal.getVitesse());
                }
            }

            // Si la porte sort de l'écran, on la supprime
            if (listVehicules.get(i).getY() + listVehicules.get(i).getImg().getHeight() / 3 > laneColor.getY()) {
                if (laneColor.getColor() == listVehicules.get(i).getColor()) {
                    playSounds(vehiculeOk);
                    score++;
                } else {
                    playSounds(gameOver);
                    // L'utilisateur ne perd pas de vie si il obtient plus de 1 étoile
                    if(score >= levelActu.getScoreStar1()){
                        //if(user.getNbrLife() > 0){
                            user.setNbrLife(user.getNbrLife() + 1);

                            dao.open();

                           /* if(dao.getTimeLastLife().equals("")){
                                dao.saveTimeLastLife(String.valueOf(System.currentTimeMillis()));
                            }*/

                            dao.setNbrLife(dao.getNbrLife() + 1);

                            dao.close();
                        //}
                    }

                    int nbrStarMyScore = 0;
                    int nbrStarBestScore = 0;
                    int nbrStarToAdd = 0;

                    //user.setNbrStar(user.getNbrStar() + nbrStar);
                    dao.open();

                    bestScore = dao.getScoreLevel(worldActu, levelActu);

                    nbrStarMyScore = getStarByTypeScore(score);
                    nbrStarBestScore = getStarByTypeScore(bestScore);

                    if(nbrStarMyScore > nbrStarBestScore){
                        nbrStarToAdd = nbrStarMyScore - nbrStarBestScore;
                    }
                    else if(nbrStarMyScore == nbrStarBestScore){
                        nbrStarToAdd = 0;
                    }
                    else if(nbrStarMyScore < nbrStarBestScore){
                        nbrStarToAdd = 0;
                    }

                    dao.close();

                    if(score > bestScore){
                        bestScore = score;
                    }

                    dao.open();

                    //dao.setNbrStarWorld(worldActu, nbrStar);
                    dao.saveLevel(levelActu.getNum(), worldActu.getNum(), bestScore);

                    dao.setNbrStarWorld(worldActu, dao.getNbrStarByWorld(worldActu) + nbrStarToAdd);

                    dao.close();

                    etatGame.setEtat(Constance.END);
                }

                Bitmap imgAnimDisparaitre = Constance.getResizedBitmap(BitmapFactory.decodeResource(getResources(), intDisparition), listVehicules.get(i).getImg().getWidth() * 1.2, 0);
                canvas.drawBitmap(imgAnimDisparaitre, listVehicules.get(i).getX() + listVehicules.get(i).getImg().getWidth() / 2 - imgAnimDisparaitre.getWidth() / 2, laneColor.getY() - imgAnimDisparaitre.getHeight(), null);
                canvas.drawBitmap(imgAnimDisparaitre, listVehicules.get(i).getX() + listVehicules.get(i).getImg().getWidth() / 2 - imgAnimDisparaitre.getWidth() / 2, laneColor.getY() - imgAnimDisparaitre.getHeight(), null);
                canvas.drawBitmap(imgAnimDisparaitre, listVehicules.get(i).getX() + listVehicules.get(i).getImg().getWidth() / 2 - imgAnimDisparaitre.getWidth() / 2, laneColor.getY() - imgAnimDisparaitre.getHeight(), null);
                listVehicules.remove(i);
            }
        }


        /**
         * AFFICHAGE BOUTON COULEUR
         */
        for (int i = 0; i < listColorBtn.size(); i++) {
            canvas.drawBitmap(listColorBtn.get(i).getImg(), listColorBtn.get(i).getX(), listColorBtn.get(i).getY(), null);
        }

        /**
         * TUTORIEL FIRST USE
         */
        if(etatGame.getEtat() == Constance.FIRST_USE){
            switch (etatFirstUse){
                case Constance.STEP_1:
                    // Affichage Fond
                    canvas.drawRect(0, HAUTEUR_ECRAN/2, LARGEUR_ECRAN, HAUTEUR_ECRAN/4, paintBlackRect);
                    // Affichage Border
                    canvas.drawRect(0, HAUTEUR_ECRAN/2, LARGEUR_ECRAN, HAUTEUR_ECRAN/4, paintBorderRect);

                    /**
                     * Affiche Question Tutoriel
                      */
                    Paint paintTxtLvlTutoriel = new Paint();
                    Rect bounds = new Rect();

                    String questionTutoriel1 = getResources().getString(R.string.question_tutoriel_1);
                    String questionTutoriel2 = getResources().getString(R.string.question_tutoriel_2);
                    paintTxtLvlTutoriel.setColor(Color.WHITE);
                    paintTxtLvlTutoriel.setTextAlign(Paint.Align.CENTER);
                    paintTxtLvlTutoriel.setTextSize(Constance.getDpSize(30, getContext()));
                    paintTxtLvlTutoriel.getTextBounds(questionTutoriel1, 0, questionTutoriel1.length(), bounds);
                    paintTxtLvlTutoriel.getTextBounds(questionTutoriel2, 0, questionTutoriel2.length(), bounds);
                    canvas.drawText(questionTutoriel1, LARGEUR_ECRAN/2, HAUTEUR_ECRAN/2 - HAUTEUR_ECRAN/7, paintTxtLvlTutoriel);
                    canvas.drawText(questionTutoriel2, LARGEUR_ECRAN/2, HAUTEUR_ECRAN/2 - HAUTEUR_ECRAN/7 + bounds.height()*2, paintTxtLvlTutoriel);

                    /**
                     * Affichage Réponse dans les buttons de color
                      */
                    // ANSWER BUTTON 1
                    if(listColorBtn.get(0) != null && listColorBtn.get(1) != null){
                        float centerXColorBtn = listColorBtn.get(0).getCenterX();
                        float centerYColorBtn = listColorBtn.get(0).getCenterY();

                        String answerTuto1 = getResources().getString(R.string.answer_tuto_1);

                        bounds = new Rect();
                        Paint paintAnswerTuto = new Paint();
                        paintAnswerTuto.setColor(Color.WHITE);
                        paintAnswerTuto.setTypeface(Typeface.create("Segoe UI Black", Typeface.BOLD));
                        paintAnswerTuto.setTextSize(Constance.getDpSize(30, getContext()));
                        paintAnswerTuto.getTextBounds(answerTuto1, 0, answerTuto1.length(), bounds);

                        canvas.drawText(answerTuto1, centerXColorBtn - bounds.width()/2, centerYColorBtn - bounds.height()/2, paintAnswerTuto);

                        String answerTuto11 = getResources().getString(R.string.answer_tuto_1_1);
                        bounds = new Rect();
                        paintAnswerTuto = new Paint();
                        paintAnswerTuto.setColor(Color.WHITE);
                        paintAnswerTuto.setTextSize(Constance.getDpSize(18, getContext()));
                        paintAnswerTuto.getTextBounds(answerTuto11, 0, answerTuto11.length(), bounds);
                        canvas.drawText(answerTuto11, centerXColorBtn - bounds.width()/2, centerYColorBtn + bounds.height(), paintAnswerTuto);

                        // ANSWER BUTTON 2
                        centerXColorBtn = listColorBtn.get(1).getCenterX();
                        centerYColorBtn = listColorBtn.get(1).getCenterY();

                        String answerTuto2 = getResources().getString(R.string.answer_tuto_2);

                        bounds = new Rect();
                        paintAnswerTuto.setColor(Color.WHITE);
                        paintAnswerTuto.setTextSize(Constance.getDpSize(30, getContext()));
                        paintAnswerTuto.setTypeface(Typeface.create("Segoe UI Black", Typeface.BOLD));
                        paintAnswerTuto.getTextBounds(answerTuto2, 0, answerTuto2.length(), bounds);
                        canvas.drawText(answerTuto2, centerXColorBtn - bounds.width()/2, centerYColorBtn - bounds.height()/2, paintAnswerTuto);
                    }

                    break;
                case Constance.STEP_2:
                    if(vehicTuto.getY() > HAUTEUR_ECRAN/3){
                        etatFirstUse = Constance.STEP_3;
                    }
                    else{
                        canvas.drawBitmap(vehicTuto.getImg(), vehicTuto.getX(), vehicTuto.getY(), null);

                        vehicTuto.setY(vehicTuto.getY() + HAUTEUR_ECRAN / 200);
                    }

                    break;
                case Constance.STEP_3:
                    canvas.drawBitmap(vehicTuto.getImg(), vehicTuto.getX(), vehicTuto.getY(), null);

                    paintBlackRect.setAlpha(230);
                    // Affichage Fond
                    canvas.drawRect(0, HAUTEUR_ECRAN/2, LARGEUR_ECRAN, HAUTEUR_ECRAN/4, paintBlackRect);
                    // Affichage Border
                    canvas.drawRect(0, HAUTEUR_ECRAN/2, LARGEUR_ECRAN, HAUTEUR_ECRAN/4, paintBorderRect);

                    /**
                     * Affiche Question Tutoriel
                     */
                    Paint paintchooseColorTuto = new Paint();
                    bounds = new Rect();

                    String chooseColorTuto1 = getResources().getString(R.string.choose_color_tuto_1);
                    String chooseColorTuto2 = getResources().getString(R.string.choose_color_tuto_2);
                    paintchooseColorTuto.setColor(Color.WHITE);
                    paintchooseColorTuto.setTextAlign(Paint.Align.CENTER);
                    paintchooseColorTuto.setTextSize(Constance.getDpSize(30, getContext()));
                    paintchooseColorTuto.getTextBounds(chooseColorTuto1, 0, chooseColorTuto1.length(), bounds);
                    canvas.drawText(chooseColorTuto1, LARGEUR_ECRAN/2, HAUTEUR_ECRAN/2 - HAUTEUR_ECRAN/7, paintchooseColorTuto);
                    paintchooseColorTuto.getTextBounds(chooseColorTuto2, 0, chooseColorTuto1.length(), bounds);
                    canvas.drawText(chooseColorTuto2, LARGEUR_ECRAN/2, HAUTEUR_ECRAN/2 - HAUTEUR_ECRAN/7 + bounds.height()*2, paintchooseColorTuto);

                    try {
                        if(taskAnimIsStarted == false){
                            if(taskAnimY != null) {
                                taskAnimY.execute();
                                taskAnimIsStarted = true;
                            }
                        }

                    }
                    catch (Exception e){
                        Log.i("STARTING TASK ANIM", "Exception e : " + e);
                    }

                    // Affichage Hand Tutoriel First Use
                    canvas.drawBitmap(hand.getImg(), hand.getX(), hand.getY(), null);
                    break;
                case Constance.STEP_4:
                    if(vehicTuto.getY() + vehicTuto.getImg().getHeight()/3 > laneColor.getY()){
                        Bitmap imgAnimDisparaitre = Constance.getResizedBitmap(BitmapFactory.decodeResource(getResources(), intDisparition), vehicTuto.getImg().getWidth() * 1.2, 0);
                        canvas.drawBitmap(imgAnimDisparaitre, vehicTuto.getX() + vehicTuto.getImg().getWidth() / 2 - imgAnimDisparaitre.getWidth() / 2, laneColor.getY() - imgAnimDisparaitre.getHeight(), null);

                        vehicTuto.setImg(Constance.getResizedBitmap(vehiculeColorMap.get(listColorBtn.get(0).getColor()), LARGEUR_ECRAN / nbrColumn, 0));
                        vehicTuto.setY(HAUTEUR_ECRAN/15);
                        vehicTuto.setX(LARGEUR_ECRAN/3);

                        etatFirstUse = Constance.STEP_5;
                    }
                    else{
                        canvas.drawBitmap(vehicTuto.getImg(), vehicTuto.getX(), vehicTuto.getY(), null);

                        vehicTuto.setY(vehicTuto.getY() + HAUTEUR_ECRAN / 200);
                    }

                    break;
                case Constance.STEP_5:
                    if(vehicTuto.getY() > HAUTEUR_ECRAN/3){
                        hand.setX(listColorBtn.get(0).getX() + listColorBtn.get(0).getImg().getWidth()/2 - hand.getImg().getWidth()/2);
                        hand.setYBase(listColorBtn.get(0).getY() + listColorBtn.get(0).getImg().getHeight()/2 - hand.getImg().getHeight()/2);

                        taskAnimIsStarted = false;
                        etatFirstUse = Constance.STEP_6;
                    }
                    else{
                        canvas.drawBitmap(vehicTuto.getImg(), vehicTuto.getX(), vehicTuto.getY(), null);

                        vehicTuto.setY(vehicTuto.getY() + HAUTEUR_ECRAN / 200);
                    }

                    break;
                case Constance.STEP_6:
                    canvas.drawBitmap(vehicTuto.getImg(), vehicTuto.getX(), vehicTuto.getY(), null);

                    try {
                        if(taskAnimIsStarted == false){

                            taskAnimY.execute();
                            taskAnimIsStarted = true;
                        }

                    }
                    catch (Exception e){
                        Log.i("STARTING TASK ANIM", "Exception e : " + e);
                    }

                    // Affichage Hand Tutoriel First Use
                    canvas.drawBitmap(hand.getImg(), hand.getX(), hand.getY(), null);
                    break;
                case Constance.STEP_7:
                    if(vehicTuto.getY() + vehicTuto.getImg().getHeight()/3 > laneColor.getY()) {
                        Bitmap imgAnimDisparaitre = Constance.getResizedBitmap(BitmapFactory.decodeResource(getResources(), intDisparition), vehicTuto.getImg().getWidth() * 1.2, 0);
                        canvas.drawBitmap(imgAnimDisparaitre, vehicTuto.getX() + vehicTuto.getImg().getWidth() / 2 - imgAnimDisparaitre.getWidth() / 2, laneColor.getY() - imgAnimDisparaitre.getHeight(), null);

                        etatFirstUse = Constance.STEP_8;
                    }
                    else{
                        canvas.drawBitmap(vehicTuto.getImg(), vehicTuto.getX(), vehicTuto.getY(), null);

                        vehicTuto.setY(vehicTuto.getY() + HAUTEUR_ECRAN / 200);
                    }
                    break;
                case Constance.STEP_8:
                    paintBlackRect.setAlpha(230);
                    // Affichage Fond
                    canvas.drawRect(0, HAUTEUR_ECRAN/2, LARGEUR_ECRAN, HAUTEUR_ECRAN/5, paintBlackRect);
                    // Affichage Border
                    canvas.drawRect(0, HAUTEUR_ECRAN/2, LARGEUR_ECRAN, HAUTEUR_ECRAN/5, paintBorderRect);

                    /**
                     * Affiche END Tutoriel
                     */
                    Paint paintEndTuto = new Paint();
                    bounds = new Rect();

                    String endTuto1 = getResources().getString(R.string.end_tuto_1);
                    String endTuto2 = getResources().getString(R.string.end_tuto_2);
                    String endTuto3 = getResources().getString(R.string.end_tuto_3);
                    paintEndTuto.setColor(Color.WHITE);
                    paintEndTuto.setTextAlign(Paint.Align.CENTER);
                    paintEndTuto.setTypeface(Typeface.create("Segoe UI Black", Typeface.BOLD));
                    paintEndTuto.setTextSize(Constance.getDpSize(30, getContext()));
                    paintEndTuto.getTextBounds(endTuto1, 0, endTuto1.length(), bounds);
                    canvas.drawText(endTuto1, LARGEUR_ECRAN/2, HAUTEUR_ECRAN/2 - HAUTEUR_ECRAN/5, paintEndTuto);

                    paintEndTuto.getTextBounds(endTuto2, 0, endTuto2.length(), bounds);
                    canvas.drawText(endTuto2, LARGEUR_ECRAN/2, HAUTEUR_ECRAN/2 - HAUTEUR_ECRAN/5 + bounds.height() + bounds.height()/3, paintEndTuto);

                    paintEndTuto = new Paint();
                    paintEndTuto.setColor(Color.WHITE);
                    paintEndTuto.setTextAlign(Paint.Align.CENTER);
                    paintEndTuto.setTextSize(Constance.getDpSize(16, getContext()));
                    canvas.drawText(endTuto3, LARGEUR_ECRAN/2, HAUTEUR_ECRAN/2 - HAUTEUR_ECRAN/5 + bounds.height()*3, paintEndTuto);
                    break;
            }
        }

        /**
         * AFFICHAGE DE LA LANE COLOR
         */
        canvas.drawBitmap(Constance.getResizedBitmap(laneColor.getImg(), LARGEUR_ECRAN, HAUTEUR_ECRAN / 12), laneColor.getX(), laneColor.getY(), null);


        /**
         * Affichage du menu END
         */
        if(etatGame.getEtat() == Constance.END){
            paintBlackRect.setAlpha(235);
            canvas.drawRect(0, 0, LARGEUR_ECRAN, HAUTEUR_ECRAN, paintBlackRect);

            // Affichage END txt
            Paint paintEndTxt = new Paint();
            String txtLvlTutoriel = getResources().getString(R.string.end_txt);
            paintEndTxt.setColor(Color.WHITE);
            paintEndTxt.setTypeface(Typeface.create("Segoe UI Black", Typeface.BOLD));
            paintEndTxt.setTextAlign(Paint.Align.CENTER);
            paintEndTxt.setTextSize(Constance.getDpSize(50, getContext()));
            canvas.drawText(txtLvlTutoriel, LARGEUR_ECRAN/2, HAUTEUR_ECRAN/6, paintEndTxt);

            Bitmap star_white = Constance.getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.star_white_fond_black), LARGEUR_ECRAN/5, 0);
            Bitmap star_yellow = Constance.getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.star), LARGEUR_ECRAN/5, 0);

            // Affichage 3 Etoiles
            if(score >= levelActu.getScoreStar3()){

                canvas.drawBitmap(star_yellow, LARGEUR_ECRAN/5 - star_white.getWidth()/2, HAUTEUR_ECRAN/5, null);
                canvas.drawBitmap(star_yellow, LARGEUR_ECRAN/5 *2, HAUTEUR_ECRAN/5, null);
                canvas.drawBitmap(star_yellow, LARGEUR_ECRAN/5 * 3 + star_white.getWidth()/2, HAUTEUR_ECRAN/5, null);

            }
            else if(score >= levelActu.getScoreStar2()){
                canvas.drawBitmap(star_yellow, LARGEUR_ECRAN/5 - star_white.getWidth()/2, HAUTEUR_ECRAN/5, null);
                canvas.drawBitmap(star_yellow, LARGEUR_ECRAN/5 *2, HAUTEUR_ECRAN/5, null);
                canvas.drawBitmap(star_white, LARGEUR_ECRAN/5 * 3 + star_white.getWidth()/2, HAUTEUR_ECRAN/5, null);
            }
            else if(score >= levelActu.getScoreStar1()){
                canvas.drawBitmap(star_yellow, LARGEUR_ECRAN/5 - star_white.getWidth()/2, HAUTEUR_ECRAN/5, null);
                canvas.drawBitmap(star_white, LARGEUR_ECRAN/5 *2, HAUTEUR_ECRAN/5, null);
                canvas.drawBitmap(star_white, LARGEUR_ECRAN/5 * 3 + star_white.getWidth()/2, HAUTEUR_ECRAN/5, null);
            }
            else{
                canvas.drawBitmap(star_white, LARGEUR_ECRAN/5 - star_white.getWidth()/2, HAUTEUR_ECRAN/5, null);
                canvas.drawBitmap(star_white, LARGEUR_ECRAN/5 *2, HAUTEUR_ECRAN/5, null);
                canvas.drawBitmap(star_white, LARGEUR_ECRAN/5 * 3 + star_white.getWidth()/2, HAUTEUR_ECRAN/5, null);
            }

            // Affichage Score
            String scoreLibelle = getResources().getString(R.string.libelle_score);
            paintEndTxt.setTextSize(Constance.getDpSize(40, getContext()));
            canvas.drawText(scoreLibelle + " : " + score, LARGEUR_ECRAN/2, HAUTEUR_ECRAN/5 + HAUTEUR_ECRAN/10 + star_white.getHeight(), paintEndTxt);

            // Affichage Best Score
            String bestScoreLibelle = getResources().getString(R.string.libelle_best_score);
            paintEndTxt.setTextSize(Constance.getDpSize(16, getContext()));
            canvas.drawText(bestScoreLibelle + " : " + bestScore, LARGEUR_ECRAN/2, HAUTEUR_ECRAN/5 + HAUTEUR_ECRAN/6 + star_white.getHeight(), paintEndTxt);

            /**
             * AFFICHAGE BUTTON REVIVE
             */
            btnXRevive = LARGEUR_ECRAN/6;
            btnYRevive = HAUTEUR_ECRAN/5 + HAUTEUR_ECRAN/8 + star_white.getHeight() + HAUTEUR_ECRAN/15;
            btnWidth = LARGEUR_ECRAN - LARGEUR_ECRAN/6 - btnXAgain;
            btnHeight = HAUTEUR_ECRAN/5 + HAUTEUR_ECRAN/8 + star_white.getHeight() + HAUTEUR_ECRAN/15 + HAUTEUR_ECRAN/10 - btnYRevive;

            createButtonEnd(btnXRevive, btnYRevive, LARGEUR_ECRAN - LARGEUR_ECRAN/6, HAUTEUR_ECRAN/5 + HAUTEUR_ECRAN/8 + star_white.getHeight() + HAUTEUR_ECRAN/15 + HAUTEUR_ECRAN/10, R.color.red, canvas, R.drawable.application_ad, "Revive");

            if(!mRewardedVideoAd.isLoaded() || !etatGame.hasRevive()){
                Paint paintFondTransparent = new Paint();
                paintFondTransparent.setStyle(Paint.Style.FILL);
                paintFondTransparent.setColor(Color.BLACK);
                paintFondTransparent.setAlpha(150);
                canvas.drawRect(btnXRevive, btnYRevive, LARGEUR_ECRAN - LARGEUR_ECRAN/6, HAUTEUR_ECRAN/5 + HAUTEUR_ECRAN/8 + star_white.getHeight() + HAUTEUR_ECRAN/15 + HAUTEUR_ECRAN/10, paintFondTransparent);

                paintFondTransparent.setStyle(Paint.Style.STROKE);
                paintFondTransparent.setColor(getResources().getColor(R.color.black));
                paintFondTransparent.setStrokeWidth(Constance.getDpSize(2, getContext()));
                canvas.drawRoundRect(btnXRevive, btnYRevive, LARGEUR_ECRAN - LARGEUR_ECRAN/6, HAUTEUR_ECRAN/5 + HAUTEUR_ECRAN/8 + star_white.getHeight() + HAUTEUR_ECRAN/15 + HAUTEUR_ECRAN/10, Constance.getDpSize(10, getContext()), Constance.getDpSize(10, getContext()), paintFondTransparent);
            }
            if(etatGame.hasRevive()){
                //listVehicules.clear();
                listVehicules = new ArrayList<Vehicule>();
            }



            /**
             * AFFICHAGE BUTTON AGAIN
             */

            btnXAgain = LARGEUR_ECRAN/6;
            btnYAgain = HAUTEUR_ECRAN/5 + HAUTEUR_ECRAN/8 + star_white.getHeight() + (HAUTEUR_ECRAN/15)*3;

            createButtonEnd(btnXAgain, btnYAgain, LARGEUR_ECRAN - LARGEUR_ECRAN/6, HAUTEUR_ECRAN/5 + HAUTEUR_ECRAN/8 + star_white.getHeight() + (HAUTEUR_ECRAN/15)*3 + HAUTEUR_ECRAN/10, R.color.yellow, canvas, R.drawable.replay, getResources().getString(R.string.libelle_again));

            /**
             * Affichage Button 'Menu'
              */

            btnXMenu = LARGEUR_ECRAN/6;
            btnYMenu = HAUTEUR_ECRAN/5 + HAUTEUR_ECRAN/8 + star_white.getHeight() + (HAUTEUR_ECRAN/15)*5;

            btnMenuWidth = (LARGEUR_ECRAN - LARGEUR_ECRAN/6) - 2*((LARGEUR_ECRAN - (LARGEUR_ECRAN/6)*2)/3) - ((LARGEUR_ECRAN - (LARGEUR_ECRAN/6)*2)/12) - btnXMenu;
            createButtonEnd(btnXMenu,btnYMenu, (LARGEUR_ECRAN - LARGEUR_ECRAN/6) - 2*((LARGEUR_ECRAN - (LARGEUR_ECRAN/6)*2)/3) - ((LARGEUR_ECRAN - (LARGEUR_ECRAN/6)*2)/12), HAUTEUR_ECRAN/5 + HAUTEUR_ECRAN/8 + star_white.getHeight() + (HAUTEUR_ECRAN/15)*5 + HAUTEUR_ECRAN/10,  R.color.dark_grey, canvas, R.drawable.ico_menu, "");

            /**
             * Affichage Button 'Next'
              */
            btnXNext = (LARGEUR_ECRAN - LARGEUR_ECRAN/6) - 2*((LARGEUR_ECRAN - (LARGEUR_ECRAN/6)*2)/3);
            btnYNext = HAUTEUR_ECRAN/5 + HAUTEUR_ECRAN/8 + star_white.getHeight() + (HAUTEUR_ECRAN/15)*5;
            btnNextWidth = LARGEUR_ECRAN - LARGEUR_ECRAN/6 - btnXNext;

            if(levelActu.getNum() != worldActu.getlevels().size()){
                createButtonEnd(btnXNext,btnYNext, LARGEUR_ECRAN - LARGEUR_ECRAN/6, HAUTEUR_ECRAN/5 + HAUTEUR_ECRAN/8 + star_white.getHeight() + (HAUTEUR_ECRAN/15)*5 + HAUTEUR_ECRAN/10, R.color.grey_claire, canvas, 0, getResources().getString(R.string.end_next));
            }

            if(taskSpeedVehic != null){
                taskSpeedVehic.cancel(true);

                if(taskSpeedVehic.isCancelled()){
                    taskSpeedVehic = null;
                }
            }

            if(task != null){
                task.cancel(true);

                if(task.isCancelled()){
                    task = new ManagerDoor(getContext(), levelActu.getCouleurs(), listVehicules, LARGEUR_ECRAN, HAUTEUR_ECRAN, vehiculeColorMap, nbrColumn, levelActu.getDistance());
                }
            }

            if (interAd.isLoaded()) {
                if(etatGame.getEtat() == Constance.END) {
                    if(countADInterstitel >= 4){
                        try{
                            interAd.show();
                            countADInterstitel = 0;

                            dao.open();
                                dao.setCountAD(0);
                            dao.close();
                        }
                        catch (Exception e){

                        }
                    }
                }
            }
        }

        if(popup.isDisplay()){
            popup.display(canvas, LARGEUR_ECRAN, HAUTEUR_ECRAN, getContext());
        }


        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(etatGame.getEtat() == Constance.FIRST_USE){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                playSounds(clickBtn);
                switch(etatFirstUse){
                    case Constance.STEP_1:
                        if(listColorBtn.get(0) != null){
                            // WHEN USER CLICK ON "YES"
                            if(listColorBtn.get(0).isClicked(event.getX(), event.getY())){
                                etatFirstUse = Constance.STEP_2;
                            }
                        }

                        if(listColorBtn.get(1) != null){
                            // WHEN USER CLICK ON "NO"
                            if(listColorBtn.get(1).isClicked(event.getX(), event.getY())){
                                etatGame.setEtat(Constance.TUTORIEL);
                            }
                        }
                    break;
                    case Constance.STEP_2:

                        break;
                    case Constance.STEP_3:
                        if (listColorBtn.get(1).isClicked(event.getX(), event.getY())) {

                            laneColor.setColor(listColorBtn.get(1).getColor());
                            laneColor.setImg(doorColorMap.get(listColorBtn.get(1).getColor()));

                            etatFirstUse = Constance.STEP_4;

                            if(taskAnimY != null){
                                taskAnimY.cancel(true);
                            }

                            taskAnimY = new AnimImageY(hand, LARGEUR_ECRAN, HAUTEUR_ECRAN);
                        }
                        break;
                    case Constance.STEP_4:
                        break;
                    case Constance.STEP_5:
                        break;
                    case Constance.STEP_6:
                        if (listColorBtn.get(0).isClicked(event.getX(), event.getY())) {

                            laneColor.setColor(listColorBtn.get(0).getColor());
                            laneColor.setImg(doorColorMap.get(listColorBtn.get(0).getColor()));

                            etatFirstUse = Constance.STEP_7;

                            if(taskAnimY != null) {
                                taskAnimY.cancel(true);
                            }
                        }
                        break;
                    case Constance.STEP_7:
                        break;
                    case Constance.STEP_8:
                        etatGame.setEtat(Constance.TUTORIEL);
                        break;
                }
            }
        }

        else if(etatGame.getEtat() == Constance.TUTORIEL) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                playSounds(clickBtn);
                firstUse = Constance.FALSE;

                dao.open();

                dao.setFirstUtilisation(firstUse);

                dao.close();

                task = new ManagerDoor(getContext(), levelActu.getCouleurs(), listVehicules, LARGEUR_ECRAN, HAUTEUR_ECRAN, vehiculeColorMap, nbrColumn, levelActu.getDistance());
                taskSpeedVehic = new ManageSpeedVehic(levelActu, varGlobal);

                try{
                    Log.i("TASK MANAGER DOOR", "TASK ETAT : " + listVehicules.size());
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 10);
                }
                catch(Exception e){
                    Log.i("TASK MANAGER DOOR", "Impossible de l'executer, e : " + e);
                }

                try{
                    taskSpeedVehic.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 10);
                }
                catch (Exception e){
                    Log.i("TASK MANAGE SPEED VEHIC", "Impossible de l'executer, e : " + e);
                }

                etatGame.setEtat(Constance.EN_JEU);

                AdRequest adRequest = new AdRequest.Builder().build();
                interAd.loadAd(adRequest);

                countADInterstitel = countADInterstitel + 1;

                dao.open();

                dao.setCountAD(dao.getCountAD() + 1);

                dao.close();

                if(user.getNbrLife() > 0){
                    user.setNbrLife(user.getNbrLife() - 1);

                    dao.open();

                    if(dao.getTimeLastLife().equals("")){
                        dao.saveTimeLastLife(String.valueOf(System.currentTimeMillis()));
                    }

                    dao.setNbrLife(dao.getNbrLife() - 1);

                    dao.close();
                }
            }
        }
        else if(etatGame.getEtat() == Constance.EN_JEU){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                // Click on "Pause" button
                /*if(btnPause.isClicked(event.getX(), event.getY())){
                    if(task.getPause()){
                        task.wakeUp();
                    }
                    else{
                        task.pauseMyTask();
                    }
                } */

                //task.cancel(true);
                for(int i = 0; i < listColorBtn.size(); i++){
                    if (listColorBtn.get(i).isClicked(event.getX(), event.getY())) {
                        playSounds(clickBtn);
                        // Modifier la lane de couleur
                        laneColor.setColor(listColorBtn.get(i).getColor());
                        laneColor.setImg(doorColorMap.get(listColorBtn.get(i).getColor()));
                    }
                }
            }
        }
        else if(etatGame.getEtat() == Constance.END){
            if(event.getAction() == MotionEvent.ACTION_UP){
                // Si l'utilisateur clique sur 'Revive'
                if(event.getX() >= btnXRevive && event.getX() <= btnXRevive + btnWidth && event.getY() >= btnYRevive && event.getY() <= btnYRevive + btnHeight){
                    playSounds(clickBtn);
                    if(etatGame.hasRevive()){
                        // Lancer la pub vidéo
                        if (mRewardedVideoAd.isLoaded()) {
                            try{
                                mRewardedVideoAd.show();
                            }
                            catch (Exception e){
                                Log.i("Load RewardedVideoAd", "Exception : " + e);
                            }
                        }
                        else{
                        }
                    }
                }

                // Si l'utilisateur clique sur 'Again'
                if(event.getX() >= btnXAgain && event.getX() <= btnXAgain + btnWidth && event.getY() >= btnYAgain && event.getY() <= btnYAgain + btnHeight){
                    playSounds(clickBtn);
                    if(user.getNbrLife() > 0) {
                        etatGame.setHasRevive(true);
                        etatGame.setEtat(Constance.TUTORIEL);
                        listVehicules = new ArrayList<Vehicule>();
                        score = 0;
                        varGlobal.setVitesse(levelActu.getSpeedStart());
                    }
                    else{
                        etatGame.setEtat(Constance.POPUP_OPEN);
                        // Création popup plus de vie
                        float width = LARGEUR_ECRAN - LARGEUR_ECRAN/10;
                        float height = HAUTEUR_ECRAN - HAUTEUR_ECRAN/3 - HAUTEUR_ECRAN/10;
                        float x = LARGEUR_ECRAN/10;
                        float y = HAUTEUR_ECRAN/4;
                        TextInView title = new TextInView(getContext().getResources().getString(R.string.title_popin_no_life));
                        TextInView content = new TextInView(getContext().getResources().getString(R.string.content_popin_no_life));
                        btn1 = new TextInView(getContext().getResources().getString(R.string.btn1_popin_no_life));
                        btn2 = new TextInView(getContext().getResources().getString(R.string.btn2_popin_no_life));

                        popup.setX(x);
                        popup.setY(y);
                        popup.setWidth(width);
                        popup.setHeight(height);
                        popup.setDisplay(true);
                        popup.setTitle(title);
                        popup.setContent(content);
                        popup.setBtn1(btn1);
                        popup.setBtn2(btn2);
                    }
                }

                // Si l'utilisateur clique sur 'Menu'
                if(event.getX() >= btnXMenu && event.getX() <= btnXMenu + btnMenuWidth && event.getY() >= btnYMenu && event.getY() <= btnYMenu + btnHeight){
                    playSounds(clickBtn);
                    Bundle b = new Bundle();

                    // On re check en base le nbr de vie, il est possible que l'user en est gagné une en regardant une video de récompense
                    dao.open();

                    user.setNbrLife(dao.getNbrLife());

                    dao.close();

                    b.putSerializable("worldClicked", worldActu);
                    b.putSerializable("listWorld", (Serializable) listWorld);
                    b.putSerializable("user", user);

                    Intent intent = new Intent(getContext(), ListLevelActivity.class);

                    intent.putExtras(b);
                    getContext().startActivity(intent);
                }

                // Si l'utilisateur clique sur 'Next'
                if(event.getX() >= btnXNext && event.getX() <= btnXNext + btnNextWidth && event.getY() >= btnYNext && event.getY() <= btnYNext + btnHeight){
                    playSounds(clickBtn);
                    if(user.getNbrLife() > 0) {
                        if (levelActu.getNum() != worldActu.getlevels().size()) {
                            Bundle b = new Bundle();

                            b.putSerializable("levelClicked", worldActu.getLevel(levelActu.getNum()));
                            b.putSerializable("worldActu", worldActu);
                            b.putSerializable("listWorld", (Serializable) listWorld);
                            b.putSerializable("user", user);

                            Intent intent = new Intent(getContext(), TransitionActivityView.class);

                            intent.putExtras(b);
                            getContext().startActivity(intent);
                        }
                    }
                    else{
                        etatGame.setEtat(Constance.POPUP_OPEN);
                        // Création popup plus de vie
                        float width = LARGEUR_ECRAN - LARGEUR_ECRAN/10;
                        float height = HAUTEUR_ECRAN - HAUTEUR_ECRAN/3 - HAUTEUR_ECRAN/10;
                        float x = LARGEUR_ECRAN/10;
                        float y = HAUTEUR_ECRAN/4;
                        TextInView title = new TextInView(getContext().getResources().getString(R.string.title_popin_no_life));
                        TextInView content = new TextInView(getContext().getResources().getString(R.string.content_popin_no_life));
                        btn1 = new TextInView(getContext().getResources().getString(R.string.btn1_popin_no_life));
                        btn2 = new TextInView(getContext().getResources().getString(R.string.btn2_popin_no_life));

                        popup.setX(x);
                        popup.setY(y);
                        popup.setWidth(width);
                        popup.setHeight(height);
                        popup.setDisplay(true);
                        popup.setTitle(title);
                        popup.setContent(content);
                        popup.setBtn1(btn1);
                        popup.setBtn2(btn2);
                    }
                }
            }
        }
        else if(etatGame.getEtat() == Constance.POPUP_OPEN){
            if(event.getAction() == MotionEvent.ACTION_UP) {
                playSounds(clickBtn);
                // Button SHOP - Popup
                if (popup.getBtn1().isClicked(event.getX(), event.getY(), getContext())) {
                    Intent intent = new Intent(getContext(), ShopActivity.class);
                    getContext().startActivity(intent);
                }

                // Button OK - Popup
                if (popup.getBtn2().isClicked(event.getX(), event.getY(), getContext())) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(intent);
                }
            }
        }

        return true;
    }

    public void createButtonEnd(int left, int top, int right, int bottom, int color, Canvas canvas, int drawable, String txt){
        int buttonWidth = right - left;
        int buttonHeight = bottom - top;
        int buttonX = left;
        int buttonY = top;
        Paint paintTxtButton = new Paint();
        paintTxtButton.setColor(Color.WHITE);
        paintTxtButton.setTextSize(Constance.getDpSize(30, getContext()));

        // FOND
        Paint paintFondReviveBtn = new Paint();
        paintFondReviveBtn.setStyle(Paint.Style.FILL);
        paintFondReviveBtn.setColor(getResources().getColor(color));
        canvas.drawRoundRect(left, top, right, bottom, Constance.getDpSize(10, getContext()), Constance.getDpSize(10, getContext()), paintFondReviveBtn);

        // BORDER
        paintFondReviveBtn.setStyle(Paint.Style.STROKE);
        paintFondReviveBtn.setColor(getResources().getColor(R.color.white));
        paintFondReviveBtn.setStrokeWidth(Constance.getDpSize(2, getContext()));
        canvas.drawRoundRect(left, top, right, bottom, Constance.getDpSize(10, getContext()), Constance.getDpSize(10, getContext()), paintFondReviveBtn);

        // Si button contient image + txt
        if(drawable != 0 && !txt.equals("")){
            Rect bounds = new Rect();
            paintFondReviveBtn.getTextBounds(txt, 0, txt.length(), bounds);

            Bitmap icon = Constance.getResizedBitmap(BitmapFactory.decodeResource(getResources(), drawable), buttonWidth/5, buttonHeight - buttonHeight/5);
            canvas.drawBitmap(icon, buttonX + icon.getWidth()/2, buttonY + buttonHeight/2 - icon.getHeight()/2, null);

            canvas.drawText(txt, buttonX + icon.getWidth()*2, buttonY + buttonHeight - buttonHeight/3, paintTxtButton);
        }
        // Si button contient uniquement un txt
        else if(drawable == 0 && !txt.equals("")){
            Rect bounds = new Rect();
            paintFondReviveBtn.getTextBounds(txt, 0, txt.length(), bounds);
            canvas.drawText(txt, buttonX + buttonWidth/4 - bounds.width()/2 + bounds.width()/2, buttonY + buttonHeight - buttonHeight/3, paintTxtButton);
        }
        // Si button contient uniquement une image
        else if(drawable != 0 && txt.equals("")){
            Bitmap ico_menu = Constance.getResizedBitmap(BitmapFactory.decodeResource(getResources(), drawable), 0, buttonHeight - buttonHeight/3);
            canvas.drawBitmap(ico_menu, buttonX + buttonWidth/2 - ico_menu.getWidth()/2, buttonY + buttonHeight/2 - ico_menu.getHeight()/2, null);
        }
        else{

        }

    }

    public int getStarByTypeScore(int score){
        int nbrStar = 0;

        if(score >= levelActu.getScoreStar3()){
            nbrStar = 3;
        }
        else if(score >= levelActu.getScoreStar2()){
            nbrStar = 2;
        }
        else if(score >= levelActu.getScoreStar1()){
            nbrStar = 1;
        }

        return nbrStar;
    }

    public void initStaticData(){
        popup = new Popup(getContext());

        // COLOR Button
        btnColorMap.put(R.color.blue, BitmapFactory.decodeResource(getResources(), R.drawable.btn_blue));
        btnColorMap.put(R.color.yellow, BitmapFactory.decodeResource(getResources(), R.drawable.btn_yellow));
        btnColorMap.put(R.color.red, BitmapFactory.decodeResource(getResources(), R.drawable.btn_red));
        btnColorMap.put(R.color.dark_grey, BitmapFactory.decodeResource(getResources(), R.drawable.btn_dark_grey));
        btnColorMap.put(R.color.light_grey, BitmapFactory.decodeResource(getResources(), R.drawable.btn_light_grey));
        btnColorMap.put(R.color.green, BitmapFactory.decodeResource(getResources(), R.drawable.btn_green));

        // COLOR Vehicule & door
        switch (worldActu.getNum()){
            case Constance.ID_WORLD_FOOTBALL_AMERICAIN:
                vehiculeColorMap.put(R.color.blue, BitmapFactory.decodeResource(getResources(), R.drawable.american_football_blue));
                vehiculeColorMap.put(R.color.yellow, BitmapFactory.decodeResource(getResources(), R.drawable.american_football_yellow));
                vehiculeColorMap.put(R.color.red, BitmapFactory.decodeResource(getResources(), R.drawable.american_football_red));
                vehiculeColorMap.put(R.color.dark_grey, BitmapFactory.decodeResource(getResources(), R.drawable.american_football_dark_grey));
                vehiculeColorMap.put(R.color.light_grey, BitmapFactory.decodeResource(getResources(), R.drawable.american_football_light_grey));
                vehiculeColorMap.put(R.color.green, BitmapFactory.decodeResource(getResources(), R.drawable.american_football_green));

                doorColorMap.put(R.color.blue, BitmapFactory.decodeResource(getResources(), R.drawable.door_blue));
                doorColorMap.put(R.color.yellow, BitmapFactory.decodeResource(getResources(), R.drawable.door_yellow));
                doorColorMap.put(R.color.red, BitmapFactory.decodeResource(getResources(), R.drawable.door_red));
                doorColorMap.put(R.color.dark_grey, BitmapFactory.decodeResource(getResources(), R.drawable.door_dark_grey));
                doorColorMap.put(R.color.light_grey, BitmapFactory.decodeResource(getResources(), R.drawable.door_light_grey));
                doorColorMap.put(R.color.green, BitmapFactory.decodeResource(getResources(), R.drawable.door_green));
                break;
            case Constance.ID_WORLD_RACING:
                // Color Vehicule
                vehiculeColorMap.put(R.color.blue, BitmapFactory.decodeResource(getResources(), R.drawable.racing_blue));
                vehiculeColorMap.put(R.color.yellow, BitmapFactory.decodeResource(getResources(), R.drawable.racing_yellow));
                vehiculeColorMap.put(R.color.red, BitmapFactory.decodeResource(getResources(), R.drawable.racing_red));
                vehiculeColorMap.put(R.color.dark_grey, BitmapFactory.decodeResource(getResources(), R.drawable.racing_dark_grey));
                vehiculeColorMap.put(R.color.light_grey, BitmapFactory.decodeResource(getResources(), R.drawable.racing_light_grey));
                vehiculeColorMap.put(R.color.green, BitmapFactory.decodeResource(getResources(), R.drawable.racing_green));

                doorColorMap.put(R.color.blue, BitmapFactory.decodeResource(getResources(), R.drawable.door_blue));
                doorColorMap.put(R.color.yellow, BitmapFactory.decodeResource(getResources(), R.drawable.door_yellow));
                doorColorMap.put(R.color.red, BitmapFactory.decodeResource(getResources(), R.drawable.door_red));
                doorColorMap.put(R.color.dark_grey, BitmapFactory.decodeResource(getResources(), R.drawable.door_dark_grey));
                doorColorMap.put(R.color.light_grey, BitmapFactory.decodeResource(getResources(), R.drawable.door_light_grey));
                doorColorMap.put(R.color.green, BitmapFactory.decodeResource(getResources(), R.drawable.door_green));
                break;
            case Constance.ID_WORLD_AIRPLANE:
                // Color Vehicule
                vehiculeColorMap.put(R.color.blue, BitmapFactory.decodeResource(getResources(), R.drawable.airplane_blue_2));
                vehiculeColorMap.put(R.color.yellow, BitmapFactory.decodeResource(getResources(), R.drawable.airplane_yellow_2));
                vehiculeColorMap.put(R.color.red, BitmapFactory.decodeResource(getResources(), R.drawable.airplane_red_2));
                vehiculeColorMap.put(R.color.dark_grey, BitmapFactory.decodeResource(getResources(), R.drawable.airplane_dark_grey_2));
                vehiculeColorMap.put(R.color.light_grey, BitmapFactory.decodeResource(getResources(), R.drawable.airplane_light_grey_2));
                vehiculeColorMap.put(R.color.green, BitmapFactory.decodeResource(getResources(), R.drawable.airplane_green_2));

                doorColorMap.put(R.color.blue, BitmapFactory.decodeResource(getResources(), R.drawable.door_blue));
                doorColorMap.put(R.color.yellow, BitmapFactory.decodeResource(getResources(), R.drawable.door_yellow));
                doorColorMap.put(R.color.red, BitmapFactory.decodeResource(getResources(), R.drawable.door_red));
                doorColorMap.put(R.color.dark_grey, BitmapFactory.decodeResource(getResources(), R.drawable.door_dark_grey));
                doorColorMap.put(R.color.light_grey, BitmapFactory.decodeResource(getResources(), R.drawable.door_light_grey));
                doorColorMap.put(R.color.green, BitmapFactory.decodeResource(getResources(), R.drawable.door_green));
                break;
            case Constance.ID_WORLD_KAYAK:
                vehiculeColorMap.put(R.color.blue, BitmapFactory.decodeResource(getResources(), R.drawable.kayak_blue));
                vehiculeColorMap.put(R.color.yellow, BitmapFactory.decodeResource(getResources(), R.drawable.kayak_yellow));
                vehiculeColorMap.put(R.color.red, BitmapFactory.decodeResource(getResources(), R.drawable.kayak_red));
                vehiculeColorMap.put(R.color.dark_grey, BitmapFactory.decodeResource(getResources(), R.drawable.kayak_dark_grey));
                vehiculeColorMap.put(R.color.light_grey, BitmapFactory.decodeResource(getResources(), R.drawable.kayak_light_grey));
                vehiculeColorMap.put(R.color.green, BitmapFactory.decodeResource(getResources(), R.drawable.kayak_green));

                doorColorMap.put(R.color.blue, BitmapFactory.decodeResource(getResources(), R.drawable.door_blue));
                doorColorMap.put(R.color.yellow, BitmapFactory.decodeResource(getResources(), R.drawable.door_yellow));
                doorColorMap.put(R.color.red, BitmapFactory.decodeResource(getResources(), R.drawable.door_red));
                doorColorMap.put(R.color.dark_grey, BitmapFactory.decodeResource(getResources(), R.drawable.door_dark_grey));
                doorColorMap.put(R.color.light_grey, BitmapFactory.decodeResource(getResources(), R.drawable.door_light_grey));
                doorColorMap.put(R.color.green, BitmapFactory.decodeResource(getResources(), R.drawable.door_green));
                break;
        }
    }

    public void initColorButton(){
        int nbrCouleur = levelActu.getCouleurs().size();

        for(int i = 0; i < nbrCouleur; i++) {
            if (nbrCouleur > 3) { /* SI LE LEVEL COMPORTE PLUS DE TROIS LEVELS */
                int nbrCouleurSecondLine = nbrCouleur - 3;

                if(btnColorMap.containsKey(levelActu.getCouleurs().get(i))){
                    if(i<= 2){
                        ColorButton colBtn = new ColorButton(Constance.getResizedBitmap(btnColorMap.get(levelActu.getCouleurs().get(i)), LARGEUR_ECRAN / 3, HAUTEUR_ECRAN / 8), i * (LARGEUR_ECRAN / 3), HAUTEUR_ECRAN - HAUTEUR_ECRAN / 4 - HAUTEUR_ECRAN/25, levelActu.getCouleurs().get(i));
                        listColorBtn.add(colBtn);
                    }
                    else{
                        ColorButton colBtn = new ColorButton(Constance.getResizedBitmap(btnColorMap.get(levelActu.getCouleurs().get(i)), LARGEUR_ECRAN / nbrCouleurSecondLine, HAUTEUR_ECRAN / 8), (i - 3) * LARGEUR_ECRAN / nbrCouleurSecondLine, HAUTEUR_ECRAN - HAUTEUR_ECRAN / 8 - HAUTEUR_ECRAN/25, levelActu.getCouleurs().get(i));
                        listColorBtn.add(colBtn);
                    }
                }
            } else { /* SI LE LEVEL COMPORTE MOINS DE 3 COULEURS */
                if (btnColorMap.containsKey(levelActu.getCouleurs().get(i))) {
                    ColorButton colBtn = new ColorButton(Constance.getResizedBitmap(btnColorMap.get(levelActu.getCouleurs().get(i)), LARGEUR_ECRAN / nbrCouleur, HAUTEUR_ECRAN / 4), i * LARGEUR_ECRAN / nbrCouleur, HAUTEUR_ECRAN - HAUTEUR_ECRAN / 4, levelActu.getCouleurs().get(i));
                    listColorBtn.add(colBtn);
                }
            }
        }

        // About TUTORIEL FIRST USE
        hand = new ImageAnim();
        hand.setImg(Constance.getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.hand), LARGEUR_ECRAN/5, 0));
        hand.setX(listColorBtn.get(1).getX() + listColorBtn.get(1).getImg().getWidth()/2 - hand.getImg().getWidth()/2);
        hand.setImg(Constance.rotateBitmap(hand.getImg(), 45 * 7));

        hand.setY(listColorBtn.get(1).getY() + listColorBtn.get(1).getImg().getHeight()/2 - hand.getImg().getHeight()/2);
        hand.setYBase(listColorBtn.get(1).getY() + listColorBtn.get(1).getImg().getHeight()/2 - hand.getImg().getHeight()/2);

        taskAnimY = new AnimImageY(hand, LARGEUR_ECRAN, HAUTEUR_ECRAN);

        vehicTuto = new Vehicule(Constance.getResizedBitmap(vehiculeColorMap.get(listColorBtn.get(1).getColor()), LARGEUR_ECRAN / nbrColumn, 0), HAUTEUR_ECRAN / 15, LARGEUR_ECRAN, listColorBtn.get(0).getColor());
        vehicTuto.setX(LARGEUR_ECRAN - LARGEUR_ECRAN/3);
    }

    public void playSounds(MediaPlayer md){
        try{
            dao.open();

            if(dao.getStateSound() == Constance.SOUND_ENABLE){
                md.setVolume(1.0f, 1.0f);
                md.start();
            }


            dao.close();
        }
        catch (Exception e){
            Log.i("List Level Activity", "Sounds play : " + e);
        }
    }
}
