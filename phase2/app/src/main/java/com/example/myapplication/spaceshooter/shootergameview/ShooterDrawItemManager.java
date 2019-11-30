package com.example.myapplication.spaceshooter.shootergameview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.myapplication.spaceshooter.GameObject.ShooterBonus;
import com.example.myapplication.spaceshooter.GameObject.ShooterBullet1;
import com.example.myapplication.spaceshooter.GameObject.ShooterBullet2;
import com.example.myapplication.spaceshooter.GameObject.ShooterEnemy1;
import com.example.myapplication.spaceshooter.GameObject.ShooterEnemyExplosion;
import com.example.myapplication.spaceshooter.GameObject.ShooterHealthAid;
import com.example.myapplication.spaceshooter.GameObject.ShooterPlane;
import com.example.myapplication.spaceshooter.GameObject.ShooterPlaneExplosion;
import com.example.myapplication.spaceshooter.GameObject.ShooterPointBuff;
import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterGameStatusFacade;

import java.util.ArrayList;
import java.util.List;

public class ShooterDrawItemManager {
    Canvas canvas;
    final static int Textsize = 50;

    ShooterGameStatusFacade shooterGameStatus;
    List<ShooterBullet1> bullet1s;
    List<ShooterBonus> shooterBonuses;
    public List<ShooterHealthAid> healthAids;
    public List<ShooterPointBuff> pointBuffs;
    List<ShooterBullet2> bullet2s;
    List<ShooterEnemyExplosion> enemyExplosions;
    List<ShooterPlaneExplosion> planeExplosions;
    List<ShooterEnemy1> enemy1s;
    int level;
    ShooterPlane plane;
    Paint scorePaint, levelPaint, healthPaint;
    ShooterDrawItemManager(ShooterGameStatusFacade shooterGameStatus){

        this.shooterGameStatus = shooterGameStatus;
        setUpManager();
    }
    void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }
    private void setUpManager() {
        level = shooterGameStatus.getShooterCrossLevelManager().getLevel();
        plane = shooterGameStatus.getShooterGameLevelManager().getPlane();
        bullet1s = shooterGameStatus.getShooterGameLevelManager().getBullet1s();
        bullet2s = shooterGameStatus.getShooterGameLevelManager().getBullet2s();
        shooterBonuses = shooterGameStatus.getShooterGameLevelManager().getShooterBonuses();
        healthAids = shooterGameStatus.getShooterGameLevelManager().getHealthAids();
        pointBuffs = shooterGameStatus.getShooterGameLevelManager().getPointBuffs();
        enemyExplosions = shooterGameStatus.getShooterGameLevelManager().getEnemyExplosions();
        planeExplosions = shooterGameStatus.getShooterGameLevelManager().getPlaneExplosions();
        enemy1s = shooterGameStatus.getShooterGameLevelManager().getEnemy1s();
    }
    void draw(){
        drawText();
        drawGameObject();
        drawExplosion();
    }

    private void setPaint(){
        scorePaint = new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(Textsize);
        scorePaint.setTextAlign(Paint.Align.LEFT);
        healthPaint = new Paint();
        healthPaint.setColor(Color.GREEN);
        healthPaint.setTextSize(Textsize);
        healthPaint.setTextAlign(Paint.Align.RIGHT);
        levelPaint = new Paint();
        levelPaint.setColor(Color.BLUE);
        levelPaint.setTextSize(Textsize);
        levelPaint.setTextAlign(Paint.Align.CENTER);
    }
    private void drawText(){
        if (scorePaint == null){
            setPaint();}
        canvas.drawText("Life", ShooterGameView.dWidth -120, Textsize, healthPaint);
        canvas.drawRect(ShooterGameView.dWidth - 110, 10, ShooterGameView.dWidth - 110 +
                10 * plane.getLife(), Textsize, healthPaint);
        canvas.drawText("Pts: " + shooterGameStatus.getShooterCrossLevelManager().getPoint(),
                20, Textsize, scorePaint);
        canvas.drawText("Level: " + shooterGameStatus.getShooterCrossLevelManager().getLevel()
                , ShooterGameView.dWidth/2, Textsize, levelPaint);

    }
    private void drawGameObject(){
        if (plane.getLife() <= 0){
            shooterGameStatus.setGameSuccess(false);
        }
        plane.onDraw(canvas);
        for(ShooterBullet1 bullet1: bullet1s){
            bullet1.onDraw(canvas);
        }
        for(ShooterBullet2 bullet2: bullet2s){
            bullet2.onDraw(canvas);
        }
        for (ShooterEnemy1 enemy1: enemy1s){
            enemy1.onDraw(canvas);
        }
        for (ShooterPointBuff specialItem: pointBuffs){
            specialItem.onDraw(canvas);
        }
        for (ShooterHealthAid specialItem: healthAids){
            specialItem.onDraw(canvas);
        }
        for (ShooterBonus shooterBonus: shooterBonuses){
            shooterBonus.onDraw(canvas);
        }
    }
    private void drawExplosion(){
        List<ShooterPlaneExplosion> remove1 = new ArrayList<>();
        for (ShooterPlaneExplosion planeExplosion: planeExplosions){
            if(planeExplosion.checkFrameValid()){
                planeExplosion.onDraw(canvas);}
            else {
                remove1.add(planeExplosion);
            }
        }
        for(ShooterPlaneExplosion planeExplosion: remove1){
            planeExplosions.remove( planeExplosion);
        }

        List<ShooterEnemyExplosion> remove2 = new ArrayList<>();
        for (ShooterEnemyExplosion enemyExplosion: enemyExplosions){
            if(enemyExplosion.checkFrameValid()){
                enemyExplosion.onDraw(canvas);}
            else {
                remove2.add(enemyExplosion);
            }
        }
        for(ShooterEnemyExplosion enemyExplosion: remove2){
            enemyExplosions.remove(enemyExplosion);
        }
    }
}

