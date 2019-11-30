package com.example.myapplication.spaceshooter.shootergameview;

import android.content.Context;

import com.example.myapplication.spaceshooter.GameObject.ShooterBonus;
import com.example.myapplication.spaceshooter.GameObject.ShooterBullet1;
import com.example.myapplication.spaceshooter.GameObject.ShooterBullet2;
import com.example.myapplication.spaceshooter.GameObject.ShooterEnemy1;
import com.example.myapplication.spaceshooter.GameObject.ShooterEnemyExplosion;
import com.example.myapplication.spaceshooter.GameObject.ShooterExplosion;
import com.example.myapplication.spaceshooter.GameObject.ShooterHealthAid;
import com.example.myapplication.spaceshooter.GameObject.ShooterPlane;
import com.example.myapplication.spaceshooter.GameObject.ShooterPlaneExplosion;
import com.example.myapplication.spaceshooter.GameObject.ShooterPointBuff;
import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterGameStatusFacade;

import java.util.List;

public class ShooterBitmapManager {
    Context context;
    ShooterGameStatusFacade shooterGameStatus;
    List<ShooterBullet1> bullet1s;
    List<ShooterBonus> shooterBonuses;
    public List<ShooterHealthAid> healthAids;
    public List<ShooterPointBuff> pointBuffs;
    List<ShooterBullet2> bullet2s;
    List<ShooterEnemyExplosion> enemyExplosions;
    List<ShooterPlaneExplosion> planeExplosions;
    List<ShooterEnemy1> enemy1s;
    ShooterPlane plane;
    ShooterBitmapManager(Context context, ShooterGameStatusFacade shooterGameStatus){
        this.context = context;
        this.shooterGameStatus = shooterGameStatus;
        loadManager();

    }
    private void loadManager(){
        plane = shooterGameStatus.getShooterGameLevelManager().getPlane();
        bullet1s = shooterGameStatus.getShooterGameLevelManager().getBullet1s();
        bullet2s = shooterGameStatus.getShooterGameLevelManager().getBullet2s();
        shooterBonuses = shooterGameStatus.getShooterGameLevelManager().getShooterBonuses();
        enemyExplosions = shooterGameStatus.getShooterGameLevelManager().getEnemyExplosions();
        planeExplosions = shooterGameStatus.getShooterGameLevelManager().getPlaneExplosions();
        healthAids = shooterGameStatus.getShooterGameLevelManager().getHealthAids();
        pointBuffs = shooterGameStatus.getShooterGameLevelManager().getPointBuffs();
        enemy1s = shooterGameStatus.getShooterGameLevelManager().getEnemy1s();
    }

    void  loadBitmap(){
        plane.setUpBitmap(context);
        loadExplosion();
        loadEnemyAndBullet();
    }
    private void loadExplosion(){
        for (ShooterExplosion explosion: enemyExplosions){
            explosion.setUpBitmap(context);
        }
        for (ShooterExplosion explosion: planeExplosions){
            explosion.setUpBitmap(context);
        }
    }
    private void loadEnemyAndBullet(){
        for (ShooterEnemy1 enemy1: enemy1s){
            enemy1.setUpBitmap(context);
        }
        for (ShooterBullet2 bullet2: bullet2s){
            bullet2.setUpBitmap(context);
        }
        for (ShooterBullet1 bullet1: bullet1s){
            bullet1.setUpBitmap(context);
        }
        for (ShooterHealthAid shooterHealthAid: healthAids){
            shooterHealthAid.setUpBitmap(context);
        }
        for (ShooterPointBuff shooterPointBuff: pointBuffs){
            shooterPointBuff.setUpBitmap(context);
        }
        for (ShooterBonus shooterBonus: shooterBonuses){
            shooterBonus.setUpBitmap(context);
        }
    }

}
