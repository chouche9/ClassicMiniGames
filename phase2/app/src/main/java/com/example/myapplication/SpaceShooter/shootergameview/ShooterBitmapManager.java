package com.example.myapplication.SpaceShooter.shootergameview;

import android.content.Context;

import com.example.myapplication.SpaceShooter.GameObject.ShooterBullet1;
import com.example.myapplication.SpaceShooter.GameObject.ShooterBullet2;
import com.example.myapplication.SpaceShooter.GameObject.ShooterEnemy1;
import com.example.myapplication.SpaceShooter.GameObject.ShooterEnemyExplosion;
import com.example.myapplication.SpaceShooter.GameObject.ShooterExplosion;
import com.example.myapplication.SpaceShooter.GameObject.ShooterHealthAid;
import com.example.myapplication.SpaceShooter.GameObject.ShooterPlane;
import com.example.myapplication.SpaceShooter.GameObject.ShooterPlaneExplosion;
import com.example.myapplication.SpaceShooter.GameObject.ShooterPointBuff;
import com.example.myapplication.SpaceShooter.ShooterGameStatus;

import java.util.List;

public class ShooterBitmapManager {
    Context context;
    ShooterGameStatus shooterGameStatus;
    List<ShooterBullet1> bullet1s;
    public List<ShooterHealthAid> healthAids;
    public List<ShooterPointBuff> pointBuffs;
    List<ShooterBullet2> bullet2s;
    List<ShooterEnemyExplosion> enemyExplosions;
    List<ShooterPlaneExplosion> planeExplosions;
    List<ShooterEnemy1> enemy1s;
    ShooterPlane plane;
    ShooterBitmapManager(Context context, ShooterGameStatus shooterGameStatus){
        this.context = context;
        this.shooterGameStatus = shooterGameStatus;
        loadManager();

    }
    private void loadManager(){
        plane = shooterGameStatus.plane;
        bullet1s = shooterGameStatus.bullet1s;
        bullet2s = shooterGameStatus.bullet2s;
        enemyExplosions = shooterGameStatus.enemyExplosions;
        planeExplosions = shooterGameStatus.planeExplosions;
        healthAids = shooterGameStatus.healthAids;
        pointBuffs = shooterGameStatus.pointBuffs;
        enemy1s = shooterGameStatus.enemy1s;
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
    }

}
