package com.example.myapplication.spaceshooter.shootergameview;

import android.content.Context;
import android.media.SoundPool;

import com.example.myapplication.spaceshooter.gameobject.ShooterBonus;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlaneBullet;
import com.example.myapplication.spaceshooter.gameobject.ShooterEnemyBullet;
import com.example.myapplication.spaceshooter.gameobject.ShooterEnemy;
import com.example.myapplication.spaceshooter.gameobject.ShooterHealthAid;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlane;
import com.example.myapplication.spaceshooter.gameobject.ShooterPointBuff;
import com.example.myapplication.spaceshooter.shootergamestatus.ShooterGameStatusFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Shooter load item manager.
 */
class ShooterLoadItemManager {
    /**
     * The Shooter game status.
     */
    private ShooterGameStatusFacade shooterGameStatus;
    /**
     * The Shooter plane bullets.
     */
    private List<ShooterPlaneBullet> shooterPlaneBullets;
    /**
     * The Shooter bonuses.
     */
    private List<ShooterBonus> shooterBonuses;
    /**
     * The Health aids.
     */
    private List<ShooterHealthAid> healthAids;
    /**
     * The Point buffs.
     */
    private List<ShooterPointBuff> pointBuffs;
    /**
     * The Shooter enemy bullets.
     */
    private List<ShooterEnemyBullet> shooterEnemyBullets;
    /**
     * The Plane bullet count.
     */
    private int planeBulletCount = 0;
    /**
     * The Enemy bullet count.
     */
    private int enemyBulletCount = 0;
    /**
     * The Enemy count.
     */
    private int enemyCount = 0;
    /**
     * The Bonus count.
     */
    private int bonusCount = 0;
    /**
     * The Enemies.
     */
    private List<ShooterEnemy> enemies;
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Level of game.
     */
    private int level;
    /**
     * The Plane.
     */
    private ShooterPlane plane;
    /**
     * The Sound pool of all sound effect.
     */
    private SoundPool sp;

    /**
     * Instantiates a new Shooter load item manager.
     *
     * @param shooterGameStatus the shooter game status
     * @param context           the context
     * @param sp                the sp
     */
    ShooterLoadItemManager(ShooterGameStatusFacade shooterGameStatus, Context context, SoundPool sp) {
        this.shooterGameStatus = shooterGameStatus;
        this.context = context;
        setUpManager();
        this.sp = sp;
    }
    /**
     * load class from shooterGameStatus
     */
    private void setUpManager() {
        level = shooterGameStatus.getShooterCrossLevelManager().getLevel();
        plane = shooterGameStatus.getShooterGameLevelManager().getPlane();
        shooterBonuses = shooterGameStatus.getShooterGameLevelManager().getShooterBonuses();
        healthAids = shooterGameStatus.getShooterGameLevelManager().getHealthAids();
        pointBuffs = shooterGameStatus.getShooterGameLevelManager().getPointBuffs();
        shooterPlaneBullets = shooterGameStatus.getShooterGameLevelManager().getPlaneBullets();
        shooterEnemyBullets = shooterGameStatus.getShooterGameLevelManager().getEnemyBullets();
        enemies = shooterGameStatus.getShooterGameLevelManager().getEnemies();
    }

    /**
     * Load all items in a fixed interval
     */
    void loadItem() {
        updateSpacialItems();
        updateEnemy();
        if (level == 2){
            updateEnemybullet();}
        updatePlanebullet();
        updateBonuses();
    }

    /**
     * create new Spacial Item and delete Special item if it's out of screen
     */
    private void updateSpacialItems() {
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
            specialItem.setY(specialItem.getY() + specialItem.getVelocity());
            if (specialItem.getY() >= ShooterGameView.dHeight) {
                remove1.add(specialItem);
            }
        }
        for (ShooterHealthAid specialItem : remove1) {
            healthAids.remove(specialItem);
        }

        for (ShooterPointBuff specialItem : pointBuffs) {
            specialItem.setY(specialItem.getY() + specialItem.getVelocity());
            if (specialItem.getY() >= ShooterGameView.dHeight) {
                remove2.add(specialItem);
            }
        }
        for (ShooterPointBuff specialItem : remove2) {
            pointBuffs.remove(specialItem);
        }
    }
    /**
     * create new enemy and delete enemy if it's out of screen
     */
    private void updateEnemy(){
        List<ShooterEnemy> remove = new ArrayList<>();
        enemyCount++;
        if(enemyCount == 10){
            enemies.add(new ShooterEnemy(context));
            enemyCount = 0;
        }
        for (ShooterEnemy enemy1: enemies){
            enemy1.setY(
                    enemy1.getY() +
                            enemy1.getVelocity());

            if (enemy1.getY() > ShooterGameView.dHeight){
                remove.add(enemy1);
            }
        }
        for (ShooterEnemy enemy1: remove){
            enemies.remove(enemy1);
        }
    }
    /**
     * create new enemy bullet  and delete enemy bullet if it's out of screen
     */
    private void updateEnemybullet(){
        List<ShooterEnemyBullet> remove = new ArrayList<>();
        enemyBulletCount++;
        if(enemyBulletCount == 14){
            for(ShooterEnemy enemy1: enemies){
                if(enemy1.getY() > 0){
                    shooterEnemyBullets.add(new ShooterEnemyBullet(context,
                            enemy1.getX()+ enemy1.getWidth()/2,
                            enemy1.getY() + enemy1.getHeight()));
                }
            }
            enemyBulletCount = 0;
        }
        for (ShooterEnemyBullet bullet2: shooterEnemyBullets){
            bullet2.setY(bullet2.getY() + bullet2.getVelocity());
            if (bullet2.getY() > ShooterGameView.dHeight){
                remove.add(bullet2);
            }
        }
        for (ShooterEnemyBullet bullet2: remove){
            shooterEnemyBullets.remove(bullet2);
        }
    }
    /**
     * create new plane bullet and delete plane bullet if it's out of screen
     */
    private void updatePlanebullet(){
        List<ShooterPlaneBullet> remove = new ArrayList<>();
        planeBulletCount++;
        if(planeBulletCount == 5){
            shooterPlaneBullets.add(new ShooterPlaneBullet(context, plane.getX() + plane.getWidth()/2, plane.getY()));
            sp.play(ShooterGameView.bulletLoad, 1, 1, 0, 0, 1);
            planeBulletCount = 0;
        }
        for (ShooterPlaneBullet bullet1: shooterPlaneBullets){
            bullet1.setY(bullet1.getY() - bullet1.getVelocity());
            if (bullet1.getY() < 0){
                remove.add(bullet1);
            }
        }
        for (ShooterPlaneBullet bullet1: remove){
            shooterPlaneBullets.remove(bullet1);
        }

    }
    /**
     * create new bonus and delete bonus if it's out of screen
     */
    private void updateBonuses(){
        bonusCount++;
        List<ShooterBonus> remove = new ArrayList<>();
        if (bonusCount == 100){
            shooterBonuses.add(new ShooterBonus(context));
            bonusCount = 0;
        }
        for (ShooterBonus shooterBonus: shooterBonuses){
            shooterBonus.setY(shooterBonus.getY() + shooterBonus.getVelocity());
            if (shooterBonus.getY()> ShooterGameView.dHeight){
                remove.add(shooterBonus);
            }
        }
        for (ShooterBonus shooterBonus: remove){
            shooterBonuses.remove(shooterBonus);
        }
    }


}
