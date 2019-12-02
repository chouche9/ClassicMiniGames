package com.example.myapplication.spaceshooter.shootergameview;

import android.content.Context;

import com.example.myapplication.spaceshooter.gameobject.ShooterBonus;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlaneBullet;
import com.example.myapplication.spaceshooter.gameobject.ShooterEnemyBullet;
import com.example.myapplication.spaceshooter.gameobject.ShooterEnemy;
import com.example.myapplication.spaceshooter.gameobject.ShooterEnemyExplosion;
import com.example.myapplication.spaceshooter.gameobject.ShooterExplosion;
import com.example.myapplication.spaceshooter.gameobject.ShooterHealthAid;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlane;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlaneExplosion;
import com.example.myapplication.spaceshooter.gameobject.ShooterPointBuff;
import com.example.myapplication.spaceshooter.shootergamestatus.ShooterGameStatusFacade;

import java.util.List;

/** The type Shooter bitmap manager. */
public class ShooterBitmapManager {
  /** The Context. */
  private Context context;
  /** The Shooter game status. */
  private ShooterGameStatusFacade shooterGameStatus;
  /** The shooterPlaneBullets. */
  private List<ShooterPlaneBullet> shooterPlaneBullets;
  /** The Shooter bonuses. */
  private List<ShooterBonus> shooterBonuses;
  /** The Health aids. */
  private List<ShooterHealthAid> healthAids;
  /** The Point buffs. */
  private List<ShooterPointBuff> pointBuffs;
  /** The shooterEnemyBullets. */
  private List<ShooterEnemyBullet> shooterEnemyBullets;
  /** The Enemy explosions. */
  private List<ShooterEnemyExplosion> enemyExplosions;
  /** The Plane explosions. */
  private List<ShooterPlaneExplosion> planeExplosions;
  /** The Enemies. */
  private List<ShooterEnemy> shooterEnemies;
  /** The Plane. */
  private ShooterPlane plane;

  /**
   * Instantiates a new Shooter bitmap manager.
   *
   * @param context the context
   * @param shooterGameStatus the shooter game status
   */
  ShooterBitmapManager(Context context, ShooterGameStatusFacade shooterGameStatus) {
    this.context = context;
    this.shooterGameStatus = shooterGameStatus;
    loadManager();
  }

  /** load class from shooterGameStatus */
  private void loadManager() {
    plane = shooterGameStatus.getShooterGameLevelManager().getPlane();
    shooterPlaneBullets = shooterGameStatus.getShooterGameLevelManager().getPlaneBullets();
    shooterEnemyBullets = shooterGameStatus.getShooterGameLevelManager().getEnemyBullets();
    shooterBonuses = shooterGameStatus.getShooterGameLevelManager().getShooterBonuses();
    enemyExplosions = shooterGameStatus.getShooterGameLevelManager().getEnemyExplosions();
    planeExplosions = shooterGameStatus.getShooterGameLevelManager().getPlaneExplosions();
    healthAids = shooterGameStatus.getShooterGameLevelManager().getHealthAids();
    pointBuffs = shooterGameStatus.getShooterGameLevelManager().getPointBuffs();
    shooterEnemies = shooterGameStatus.getShooterGameLevelManager().getEnemies();
  }

  /** Load bitmap. */
  void loadBitmap() {
    plane.setUpBitmap(context);
    loadExplosion();
    loadListGameObject();
  }

  /** load both plane/enemy explosions */
  private void loadExplosion() {
    for (ShooterExplosion explosion : enemyExplosions) {
      explosion.setUpBitmap(context);
    }
    for (ShooterExplosion explosion : planeExplosions) {
      explosion.setUpBitmap(context);
    }
  }

  /** load all the game objects list's bitmap */
  private void loadListGameObject() {
    for (ShooterEnemy enemy1 : shooterEnemies) {
      enemy1.setUpBitmap(context);
    }
    for (ShooterEnemyBullet bullet2 : shooterEnemyBullets) {
      bullet2.setUpBitmap(context);
    }
    for (ShooterPlaneBullet bullet1 : shooterPlaneBullets) {
      bullet1.setUpBitmap(context);
    }
    for (ShooterHealthAid shooterHealthAid : healthAids) {
      shooterHealthAid.setUpBitmap(context);
    }
    for (ShooterPointBuff shooterPointBuff : pointBuffs) {
      shooterPointBuff.setUpBitmap(context);
    }
    for (ShooterBonus shooterBonus : shooterBonuses) {
      shooterBonus.setUpBitmap(context);
    }
  }
}
