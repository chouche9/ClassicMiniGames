package com.example.myapplication.SpaceShooter.shootergameview;

import android.content.Context;
import android.media.SoundPool;

import com.example.myapplication.SpaceShooter.GameObject.ShooterBullet1;
import com.example.myapplication.SpaceShooter.GameObject.ShooterBullet2;
import com.example.myapplication.SpaceShooter.GameObject.ShooterEnemy1;
import com.example.myapplication.SpaceShooter.GameObject.ShooterEnemyExplosion;
import com.example.myapplication.SpaceShooter.GameObject.ShooterPlane;
import com.example.myapplication.SpaceShooter.GameObject.ShooterPlaneExplosion;
import com.example.myapplication.SpaceShooter.GameObject.ShooterSpecialItem;
import com.example.myapplication.SpaceShooter.ShooterGameStatus;

import java.util.ArrayList;
import java.util.List;

public class ShooterColisionManager {
    ShooterGameStatus shooterGameStatus;
    List<ShooterBullet1> bullet1s;
    List<ShooterSpecialItem> specialItems;
    List<ShooterBullet2> bullet2s;
    List<ShooterEnemyExplosion> enemyExplosions;
    List<ShooterPlaneExplosion> planeExplosions;
    List<ShooterEnemy1> enemy1s;
    Context context;
    int level;
    ShooterPlane plane;
    SoundPool sp;

    ShooterColisionManager(ShooterGameStatus shooterGameStatus, Context context, SoundPool sp) {
        this.shooterGameStatus = shooterGameStatus;
        this.context = context;
        setUpManager();
        this.sp = sp;
    }

    private void setUpManager() {
        level = shooterGameStatus.level;
        plane = shooterGameStatus.plane;
        bullet1s = shooterGameStatus.bullet1s;
        bullet2s = shooterGameStatus.bullet2s;
        enemyExplosions = shooterGameStatus.enemyExplosions;
        planeExplosions = shooterGameStatus.planeExplosions;
        specialItems = shooterGameStatus.specialItems;
        enemy1s = shooterGameStatus.enemy1s;
    }

    void handleColision() {
        planeEnemyColide();
        bullet1EnemyColide();
        planeSpecialItemColision();
        if (level == 2){
            bullet2PlaneColide();}

    }

    private void planeEnemyColide() {
        List<ShooterEnemy1> remove = new ArrayList<>();
        for (ShooterEnemy1 enemy1 : enemy1s) {
            if (checkEnemyPlaneColide(enemy1)){
                plane.life -= 5;
                planeExplosions.add(new ShooterPlaneExplosion(context,
                        (enemy1.getX() + enemy1.getWidth()/2),
                        enemy1.getY() + enemy1.getHeight()/2));
                remove.add(enemy1);
            }
        }
        for (ShooterEnemy1 enemy1: remove){
            enemy1s.remove(enemy1);
        }
    }
    private boolean checkEnemyPlaneColide(ShooterEnemy1 enemy1 ){
        int count = 0;
        if ((plane.getX() <= enemy1.getX() && enemy1.getX()<= (plane.getX() + plane.getWidth()))
                || (plane.getX() <= enemy1.getX() + enemy1.getWidth() &&
                enemy1.getX() + enemy1.getWidth() <= (plane.getX() + plane.getWidth()))) {
            count++;
        }
        if ((plane.getY() <= enemy1.getY() && enemy1.getY() <= (plane.getY() + plane.getHeight()))
                || (plane.getY() <= enemy1.getY() +  enemy1.getHeight()&&
                enemy1.getY() + enemy1.getHeight() <= (plane.getY() + plane.getHeight()))) {
            count++;
        }
        return count == 2;
    }

    private void bullet1EnemyColide(){
        List<ShooterBullet1> removebullet = new ArrayList<>();
        for (int i = 0; i < bullet1s.size(); i++){
            ShooterBullet1 bullet1 = bullet1s.get(i);

            if(hitEnemy(bullet1) != -1) {
                ShooterEnemy1 enemy1 =  enemy1s.get(hitEnemy(bullet1));
                enemy1s.remove(enemy1);
                removebullet.add(bullet1s.get(i));
            }
        }
        for (ShooterBullet1 bullet1:removebullet){
            bullet1s.remove(bullet1);
        }
    }
    private int hitEnemy(ShooterBullet1 bullet1) {

        for (int i = 0; i < enemy1s.size(); i++) {
            ShooterEnemy1 enemy1 = enemy1s.get(i);
            if ((bullet1.getX()>= enemy1.getX()) && bullet1.getX() <= (enemy1.getX() + enemy1.getWidth())
                    && bullet1.getY()>= enemy1.getY() && bullet1.getY() <= (enemy1.getY() + enemy1.getHeight())){

                sp.play(ShooterGameView.enemyDown, 1, 1, 0, 0, 1);
                ShooterEnemyExplosion enemyExplosion = new ShooterEnemyExplosion(context,enemy1.getX(), enemy1.getY() );
                enemyExplosions.add(enemyExplosion);
                shooterGameStatus.point+= 10;
                return i;
            }
        }
        return -1;
    }
    private void planeSpecialItemColision(){
        List<ShooterSpecialItem> remove = new ArrayList<>();
        for (ShooterSpecialItem specialItem: specialItems){
            boolean colide = planeSpecialItemCheck(specialItem);
            if (colide){
                specialItem.getBuff(shooterGameStatus);
                remove.add(specialItem);
            }
        }
        for (ShooterSpecialItem specialItem: remove){
            specialItems.remove(specialItem);
        }
    }


    private boolean planeSpecialItemCheck(ShooterSpecialItem specialItem){
        int count = 0;
        if ((plane.getX()<= specialItem.getX() && specialItem.getX() <= (plane.getX() + plane.getWidth()))
                ||(plane.getX()<= specialItem.getX() + specialItem.getWidth() &&
                specialItem.getX() + specialItem.getWidth() <= (plane.getX() + plane.getWidth()))){
            count++;
        }
        if ((plane.getY()<= specialItem.getY() && specialItem.getY() <= (plane.getY() + plane.getHeight()))
                ||(plane.getY()<= specialItem.getY() + specialItem.getHeight() &&
                specialItem.getY() + specialItem.getHeight() <= (plane.getY() + plane.getHeight()))){
            count++;
        }
        return count==2;
    }

    private void bullet2PlaneColide(){
        List<ShooterBullet2> removeBullet = new ArrayList<>();
        for(int i = 0; i< bullet2s.size(); i++){
            ShooterBullet2 bullet2 = bullet2s.get(i);
            if (bullet2.getX() >= plane.getX() && bullet2.getX() <= plane.getX() + plane.getWidth()
                    && bullet2.getY()>= plane.getY() && bullet2.getY() <= plane.getY() + plane.getHeight()
            ){
                removeBullet.add(bullet2);
                planeExplosions.add(new ShooterPlaneExplosion(context, bullet2.getX(), bullet2.getY()));
                plane.life --;
            }
        }
        for (ShooterBullet2 bullet2: removeBullet){
            bullet2s.remove(bullet2);
        }
    }
}

