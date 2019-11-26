package com.example.myapplication.SpaceShooter.shootergameview;

import android.content.Context;
import android.media.SoundPool;

import com.example.myapplication.SpaceShooter.GameObject.ShooterBullet1;
import com.example.myapplication.SpaceShooter.GameObject.ShooterBullet2;
import com.example.myapplication.SpaceShooter.GameObject.ShooterEnemy1;
import com.example.myapplication.SpaceShooter.GameObject.ShooterHealthAid;
import com.example.myapplication.SpaceShooter.GameObject.ShooterPlane;
import com.example.myapplication.SpaceShooter.GameObject.ShooterPointBuff;
import com.example.myapplication.SpaceShooter.GameObject.ShooterSpecialItem;
import com.example.myapplication.SpaceShooter.ShooterGameStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShooterLoadItemManager {
    ShooterGameStatus shooterGameStatus;
    List<ShooterBullet1> bullet1s;
    public List<ShooterHealthAid> healthAids;
    public List<ShooterPointBuff> pointBuffs;
    List<ShooterBullet2> bullet2s;
    int bullet1count = 0;
    int bullet2count = 0;
    int enemyCount = 0;
    List<ShooterEnemy1> enemy1s;
    Context context;
    int level;
    ShooterPlane plane;
    SoundPool sp;

    ShooterLoadItemManager(ShooterGameStatus shooterGameStatus, Context context, SoundPool sp) {
        this.shooterGameStatus = shooterGameStatus;
        this.context = context;
        setUpManager();
        this.sp = sp;
    }

    private void setUpManager() {
        level = shooterGameStatus.level;
        plane = shooterGameStatus.plane;
        healthAids = shooterGameStatus.healthAids;
        pointBuffs = shooterGameStatus.pointBuffs;
        bullet1s = shooterGameStatus.bullet1s;
        bullet2s = shooterGameStatus.bullet2s;
        enemy1s = shooterGameStatus.enemy1s;
    }

    void loadItem() {
        updateSpeacialItems();
        updateEnemy();
        if (level == 2){
            updateEnemybullet();}
        updatePlanebullet();
    }

    private void updateSpeacialItems() {
        List<ShooterHealthAid> remove1 = new ArrayList<>();
        List<ShooterPointBuff> remove2 = new ArrayList<>();
        Random random = new Random();
        int target = random.nextInt(100);
        if (target == 20) {
            healthAids.add(new ShooterHealthAid(context));
        }
        if (target == 10) {
            pointBuffs.add(new ShooterPointBuff(context));
        }
        for (ShooterHealthAid specialItem : healthAids) {
            specialItem.setY(specialItem.getY() + specialItem.velocity);
            if (specialItem.getY() >= ShooterGameView.dHeight) {
                remove1.add(specialItem);
            }
        }
        for (ShooterHealthAid specialItem : remove1) {
            healthAids.remove(specialItem);
        }

        for (ShooterPointBuff specialItem : pointBuffs) {
            specialItem.setY(specialItem.getY() + specialItem.velocity);
            if (specialItem.getY() >= ShooterGameView.dHeight) {
                remove2.add(specialItem);
            }
        }
        for (ShooterPointBuff specialItem : remove2) {
            pointBuffs.remove(specialItem);
        }
    }
    private void updateEnemy(){
        ShooterEnemy1.level = shooterGameStatus.level;
        List<ShooterEnemy1> remove = new ArrayList<>();
        enemyCount++;
        if(enemyCount == 10){
            enemy1s.add(new ShooterEnemy1(context));
            enemyCount = 0;
        }
        for (ShooterEnemy1 enemy1: enemy1s){
            enemy1.setY(
                    enemy1.getY() +
                            enemy1.velocity);

            if (enemy1.getY() > ShooterGameView.dHeight){
                remove.add(enemy1);
            }
        }
        for (ShooterEnemy1 enemy1: remove){
            enemy1s.remove(enemy1);
        }
    }
    private void updateEnemybullet(){
        List<ShooterBullet2> remove = new ArrayList<>();
        bullet2count ++;
        if(bullet2count == 8){
            for(ShooterEnemy1 enemy1:enemy1s){
                if(enemy1.getY() > 0){
                    bullet2s.add(new ShooterBullet2(context,
                            enemy1.getX()+ enemy1.getWidth()/2,
                            enemy1.getY() + enemy1.getHeight()));
                }
            }
            bullet2count = 0;
        }
        for (ShooterBullet2 bullet2: bullet2s){
            bullet2.setY(bullet2.getY() + bullet2.velocity);
            if (bullet2.getY() > ShooterGameView.dHeight){
                remove.add(bullet2);
            }
        }
        for (ShooterBullet2 bullet2: remove){
            bullet2s.remove(bullet2);
        }
    }

    private void updatePlanebullet(){
        List<ShooterBullet1> remove = new ArrayList<>();
        bullet1count++;
        if(bullet1count == 5){
            bullet1s.add(new ShooterBullet1(context, plane.getX() + plane.getWidth()/2, plane.getY()));
            sp.play(ShooterGameView.bulletLoad, 1, 1, 0, 0, 1);
            bullet1count = 0;
        }
        for (ShooterBullet1 bullet1: bullet1s){
            bullet1.setY(bullet1.getY() - bullet1.velocity);
            if (bullet1.getY() < 0){
                remove.add(bullet1);
            }
        }
        for (ShooterBullet1 bullet1: remove){
            bullet1s.remove(bullet1);
        }

    }


}
