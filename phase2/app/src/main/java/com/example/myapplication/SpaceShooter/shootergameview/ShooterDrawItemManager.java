package com.example.myapplication.SpaceShooter.shootergameview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.myapplication.SpaceShooter.GameObject.ShooterBullet1;
import com.example.myapplication.SpaceShooter.GameObject.ShooterBullet2;
import com.example.myapplication.SpaceShooter.GameObject.ShooterEnemy1;
import com.example.myapplication.SpaceShooter.GameObject.ShooterEnemyExplosion;
import com.example.myapplication.SpaceShooter.GameObject.ShooterHealthAid;
import com.example.myapplication.SpaceShooter.GameObject.ShooterPlane;
import com.example.myapplication.SpaceShooter.GameObject.ShooterPlaneExplosion;
import com.example.myapplication.SpaceShooter.GameObject.ShooterPointBuff;
import com.example.myapplication.SpaceShooter.GameObject.ShooterSpecialItem;
import com.example.myapplication.SpaceShooter.ShooterGameStatus;

import java.util.ArrayList;
import java.util.List;

public class ShooterDrawItemManager {
    Canvas canvas;
    final static int Textsize = 50;

    ShooterGameStatus shooterGameStatus;
    List<ShooterBullet1> bullet1s;
    public List<ShooterHealthAid> healthAids;
    public List<ShooterPointBuff> pointBuffs;
    List<ShooterBullet2> bullet2s;
    List<ShooterEnemyExplosion> enemyExplosions;
    List<ShooterPlaneExplosion> planeExplosions;
    List<ShooterEnemy1> enemy1s;
    int level;
    ShooterPlane plane;
    Paint scorePaint, levelPaint, healthPaint;
    ShooterDrawItemManager(ShooterGameStatus shooterGameStatus){

        this.shooterGameStatus = shooterGameStatus;
        setUpManager();
    }
    void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }
    private void setUpManager() {
        level = shooterGameStatus.level;
        plane = shooterGameStatus.plane;
        bullet1s = shooterGameStatus.bullet1s;
        bullet2s = shooterGameStatus.bullet2s;
        healthAids = shooterGameStatus.healthAids;
        pointBuffs = shooterGameStatus.pointBuffs;
        enemyExplosions = shooterGameStatus.enemyExplosions;
        planeExplosions = shooterGameStatus.planeExplosions;
        enemy1s = shooterGameStatus.enemy1s;
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
                10 * plane.life, Textsize, healthPaint);
        canvas.drawText("Pts: " + shooterGameStatus.point, 20, Textsize, scorePaint);
        canvas.drawText("Level: " + shooterGameStatus.level, ShooterGameView.dWidth/2, Textsize, levelPaint);

    }
    private void drawGameObject(){
        if (plane.life <= 0){
            shooterGameStatus.gameSuccess = false;
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

