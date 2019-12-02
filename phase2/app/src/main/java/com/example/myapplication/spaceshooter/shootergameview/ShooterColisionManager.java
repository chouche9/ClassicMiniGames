package com.example.myapplication.spaceshooter.shootergameview;

import android.content.Context;
import android.media.SoundPool;

import com.example.myapplication.spaceshooter.gameobject.ShooterBonus;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlaneBullet;
import com.example.myapplication.spaceshooter.gameobject.ShooterEnemyBullet;
import com.example.myapplication.spaceshooter.gameobject.ShooterEnemy;
import com.example.myapplication.spaceshooter.gameobject.ShooterEnemyExplosion;
import com.example.myapplication.spaceshooter.gameobject.ShooterGameObject;
import com.example.myapplication.spaceshooter.gameobject.ShooterHealthAid;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlane;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlaneExplosion;
import com.example.myapplication.spaceshooter.gameobject.ShooterPointBuff;
import com.example.myapplication.spaceshooter.shootergamestatus.ShooterGameStatusFacade;
import com.example.myapplication.spaceshooter.shooterplanegame.ShooterGame;

import java.util.ArrayList;
import java.util.List;

/** The type Shooter colision manager. */
public class ShooterColisionManager {
  /** The Shooter game status. */
  private ShooterGameStatusFacade shooterGameStatus;
  /** The Shooter plane bullets. */
  private List<ShooterPlaneBullet> shooterPlaneBullets;
  /** The Shooter bonuses. */
  private List<ShooterBonus> shooterBonuses;
  /** The Health aids. */
  private List<ShooterHealthAid> healthAids;
  /** The Point buffs. */
  private List<ShooterPointBuff> pointBuffs;
  /** The Shooter enemy bullets. */
  private List<ShooterEnemyBullet> shooterEnemyBullets;
  /** The Enemy explosions. */
  private List<ShooterEnemyExplosion> enemyExplosions;
  /** The Plane explosions. */
  private List<ShooterPlaneExplosion> planeExplosions;
  /** The Shooter enemies. */
  private List<ShooterEnemy> shooterEnemies;
  /** The Context. */
  private Context context;
  /** The Level. */
  private int level;
  /** The Plane. */
  private ShooterPlane plane;
  /** The Sp. */
  private SoundPool sp;

  /**
   * Instantiates a new Shooter colision manager.
   *
   * @param shooterGameStatus the shooter game status
   * @param context the context
   * @param sp the sp
   */
  ShooterColisionManager(ShooterGameStatusFacade shooterGameStatus, Context context, SoundPool sp) {
    this.shooterGameStatus = shooterGameStatus;
    this.context = context;
    setUpManager();
    this.sp = sp;
  }

  /** load class from shooterGameStatus */
  private void setUpManager() {
    level = shooterGameStatus.getShooterCrossLevelManager().getLevel();
    plane = shooterGameStatus.getShooterGameLevelManager().getPlane();
    shooterPlaneBullets = shooterGameStatus.getShooterGameLevelManager().getPlaneBullets();
    shooterBonuses = shooterGameStatus.getShooterGameLevelManager().getShooterBonuses();
    shooterEnemyBullets = shooterGameStatus.getShooterGameLevelManager().getEnemyBullets();
    enemyExplosions = shooterGameStatus.getShooterGameLevelManager().getEnemyExplosions();
    planeExplosions = shooterGameStatus.getShooterGameLevelManager().getPlaneExplosions();
    healthAids = shooterGameStatus.getShooterGameLevelManager().getHealthAids();
    pointBuffs = shooterGameStatus.getShooterGameLevelManager().getPointBuffs();
    shooterEnemies = shooterGameStatus.getShooterGameLevelManager().getEnemies();
  }

  /** Handle collision. */
  void handleCollision() {
    planeEnemyCollide();
    bullet1EnemyCollide();
    planeSpecialItemCollision();
    if (level == 2) {
      bullet2PlaneColide();
    }
    bonusPlaneCollision();
  }

  /** handle the plane enemies collision case */
  private void planeEnemyCollide() {
    List<ShooterEnemy> remove = new ArrayList<>();
    for (ShooterEnemy enemy1 : shooterEnemies) {
      if (checkEnemyPlaneColide(enemy1)) {
        plane.setLife(plane.getLife() - 5);
        planeExplosions.add(
            new ShooterPlaneExplosion(
                context,
                (enemy1.getX() + enemy1.getWidth() / 2),
                enemy1.getY() + enemy1.getHeight() / 2));
        remove.add(enemy1);
      }
    }
    for (ShooterEnemy enemy1 : remove) {
      shooterEnemies.remove(enemy1);
    }
  }

  /**
   * check whether plane and enemy1 collide
   *
   * @param enemy1 the enemy to check
   * @return true/false they collide
   */
  private boolean checkEnemyPlaneColide(ShooterEnemy enemy1) {
    int count = 0;
    if ((plane.getX() <= enemy1.getX() && enemy1.getX() <= (plane.getX() + plane.getWidth()))
        || (plane.getX() <= enemy1.getX() + enemy1.getWidth()
            && enemy1.getX() + enemy1.getWidth() <= (plane.getX() + plane.getWidth()))) {
      count++;
    }
    if ((plane.getY() <= enemy1.getY() && enemy1.getY() <= (plane.getY() + plane.getHeight()))
        || (plane.getY() <= enemy1.getY() + enemy1.getHeight()
            && enemy1.getY() + enemy1.getHeight() <= (plane.getY() + plane.getHeight()))) {
      count++;
    }
    return count == 2;
  }

  /** handle plane's bullets enemy collision */
  private void bullet1EnemyCollide() {
    List<ShooterPlaneBullet> removeBullet = new ArrayList<>();
    for (int i = 0; i < shooterPlaneBullets.size(); i++) {
      ShooterPlaneBullet bullet1 = shooterPlaneBullets.get(i);
      int enemyHit = hitEnemy(bullet1);
      if (enemyHit != -1) {
        ShooterEnemy enemy1 = shooterEnemies.get(enemyHit);
        shooterEnemies.remove(enemy1);
        removeBullet.add(shooterPlaneBullets.get(i));
      }
    }
    for (ShooterPlaneBullet bullet1 : removeBullet) {
      shooterPlaneBullets.remove(bullet1);
    }
  }

  /**
   * return the hitEnemy's index if enemy get hit
   *
   * @param bullet1 the plane bullet
   * @return the hit enemy's index or -1
   */
  private int hitEnemy(ShooterPlaneBullet bullet1) {

    for (int i = 0; i < shooterEnemies.size(); i++) {
      ShooterEnemy enemy1 = shooterEnemies.get(i);
      if ((bullet1.getX() >= enemy1.getX())
          && bullet1.getX() <= (enemy1.getX() + enemy1.getWidth())
          && bullet1.getY() >= enemy1.getY()
          && bullet1.getY() <= (enemy1.getY() + enemy1.getHeight())) {

        sp.play(ShooterGameView.enemyDown, 1, 1, 0, 0, 1);
        ShooterEnemyExplosion enemyExplosion =
            new ShooterEnemyExplosion(context, enemy1.getX(), enemy1.getY());
        enemyExplosions.add(enemyExplosion);
        shooterGameStatus.addPoint(10);
        return i;
      }
    }
    return -1;
  }

  /** handle plane and special items' collision */
  private void planeSpecialItemCollision() {
    List<ShooterHealthAid> remove = new ArrayList<>();
    for (ShooterHealthAid specialItem : healthAids) {
      boolean collide = planeSpecialItemCheck(specialItem);
      if (collide) {
        specialItem.getBuff(shooterGameStatus);
        remove.add(specialItem);
      }
    }
    for (ShooterHealthAid specialItem : remove) {
      healthAids.remove(specialItem);
    }
    List<ShooterPointBuff> remove2 = new ArrayList<>();
    for (ShooterPointBuff specialItem : pointBuffs) {
      boolean colide = planeSpecialItemCheck(specialItem);
      if (colide) {
        specialItem.getBuff(shooterGameStatus);
        remove2.add(specialItem);
      }
    }
    for (ShooterPointBuff specialItem : remove2) {
      pointBuffs.remove(specialItem);
    }
  }

  /**
   * check if specialItem collide with plane
   *
   * @param specialItem the specialItem to check
   * @return boolean whether they collide
   */
  private boolean planeSpecialItemCheck(ShooterGameObject specialItem) {
    int count = 0;
    if ((plane.getX() <= specialItem.getX()
            && specialItem.getX() <= (plane.getX() + plane.getWidth()))
        || (plane.getX() <= specialItem.getX() + specialItem.getWidth()
            && specialItem.getX() + specialItem.getWidth() <= (plane.getX() + plane.getWidth()))) {
      count++;
    }
    if ((plane.getY() <= specialItem.getY()
            && specialItem.getY() <= (plane.getY() + plane.getHeight()))
        || (plane.getY() <= specialItem.getY() + specialItem.getHeight()
            && specialItem.getY() + specialItem.getHeight()
                <= (plane.getY() + plane.getHeight()))) {
      count++;
    }
    return count == 2;
  }

  /** check if enemy bullet collide with plane */
  private void bullet2PlaneColide() {
    List<ShooterEnemyBullet> removeBullet = new ArrayList<>();
    for (int i = 0; i < shooterEnemyBullets.size(); i++) {
      ShooterEnemyBullet bullet2 = shooterEnemyBullets.get(i);
      if (bullet2.getX() >= plane.getX()
          && bullet2.getX() <= plane.getX() + plane.getWidth()
          && bullet2.getY() >= plane.getY()
          && bullet2.getY() <= plane.getY() + plane.getHeight()) {
        removeBullet.add(bullet2);
        planeExplosions.add(new ShooterPlaneExplosion(context, bullet2.getX(), bullet2.getY()));
        plane.setLife(plane.getLife() - 1);
      }
    }
    for (ShooterEnemyBullet bullet2 : removeBullet) {
      shooterEnemyBullets.remove(bullet2);
    }
  }

  /** check if bonus box collide with plane */
  private void bonusPlaneCollision() {
    List<ShooterBonus> remove = new ArrayList<>();
    for (ShooterBonus shooterBonus : shooterBonuses) {
      if (planeSpecialItemCheck(shooterBonus)) {
        ((ShooterGame) context).activateBonusGame();
        remove.add(shooterBonus);
      }
    }
    for (ShooterBonus shooterBonus : remove) {
      shooterBonuses.remove(shooterBonus);
    }
  }
}
