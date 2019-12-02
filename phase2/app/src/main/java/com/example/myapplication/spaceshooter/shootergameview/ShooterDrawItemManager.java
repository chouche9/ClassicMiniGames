package com.example.myapplication.spaceshooter.shootergameview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.myapplication.spaceshooter.gameobject.ShooterBonus;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlaneBullet;
import com.example.myapplication.spaceshooter.gameobject.ShooterEnemyBullet;
import com.example.myapplication.spaceshooter.gameobject.ShooterEnemy;
import com.example.myapplication.spaceshooter.gameobject.ShooterEnemyExplosion;
import com.example.myapplication.spaceshooter.gameobject.ShooterHealthAid;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlane;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlaneExplosion;
import com.example.myapplication.spaceshooter.gameobject.ShooterPointBuff;
import com.example.myapplication.spaceshooter.shootergamestatus.ShooterGameStatusFacade;

import java.util.ArrayList;
import java.util.List;

/** The type Shooter draw item manager. */
class ShooterDrawItemManager {
  /** The Canvas. */
  private Canvas canvas;
  /** The constant Textsize. */
  private static final int Textsize = 50;

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

  /** The Plane. */
  private ShooterPlane plane;
  /** The Score paint. */
  private Paint scorePaint,
      /** The Level paint. */
      levelPaint,
      /** The Health paint. */
      healthPaint;

  /**
   * Instantiates a new Shooter draw item manager.
   *
   * @param shooterGameStatus the shooter game status
   */
  ShooterDrawItemManager(ShooterGameStatusFacade shooterGameStatus) {

    this.shooterGameStatus = shooterGameStatus;
    setUpManager();
  }

  /**
   * Set canvas.
   *
   * @param canvas the canvas
   */
  void setCanvas(Canvas canvas) {
    this.canvas = canvas;
  }

  /** load class from shooterGameStatus */
  private void setUpManager() {
    plane = shooterGameStatus.getShooterGameLevelManager().getPlane();
    shooterPlaneBullets = shooterGameStatus.getShooterGameLevelManager().getPlaneBullets();
    shooterEnemyBullets = shooterGameStatus.getShooterGameLevelManager().getEnemyBullets();
    shooterBonuses = shooterGameStatus.getShooterGameLevelManager().getShooterBonuses();
    healthAids = shooterGameStatus.getShooterGameLevelManager().getHealthAids();
    pointBuffs = shooterGameStatus.getShooterGameLevelManager().getPointBuffs();
    enemyExplosions = shooterGameStatus.getShooterGameLevelManager().getEnemyExplosions();
    planeExplosions = shooterGameStatus.getShooterGameLevelManager().getPlaneExplosions();
    shooterEnemies = shooterGameStatus.getShooterGameLevelManager().getEnemies();
  }

  /** Draw all items on canvas */
  void draw() {
    drawText();
    drawGameObject();
    drawExplosion();
  }

  /** set up all paints for the canvas */
  private void setPaint() {
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

  /** draw the message text on canvas */
  private void drawText() {
    if (scorePaint == null) {
      setPaint();
    }
    canvas.drawText("Life", ShooterGameView.dWidth - 120, Textsize, healthPaint);
    canvas.drawRect(
        ShooterGameView.dWidth - 110,
        10,
        ShooterGameView.dWidth - 110 + 10 * plane.getLife(),
        Textsize,
        healthPaint);
    canvas.drawText(
        "Pts: " + shooterGameStatus.getShooterCrossLevelManager().getPoint(),
        20,
        Textsize,
        scorePaint);
    canvas.drawText(
        "Level: " + shooterGameStatus.getShooterCrossLevelManager().getLevel(),
        ShooterGameView.dWidth / 2,
        Textsize,
        levelPaint);
  }

  /** draw all game objects */
  private void drawGameObject() {
    if (plane.getLife() <= 0) {
      shooterGameStatus.setGameSuccess(false);
    }
    plane.onDraw(canvas);
    for (ShooterPlaneBullet bullet1 : shooterPlaneBullets) {
      bullet1.onDraw(canvas);
    }
    for (ShooterEnemyBullet bullet2 : shooterEnemyBullets) {
      bullet2.onDraw(canvas);
    }
    for (ShooterEnemy enemy1 : shooterEnemies) {
      enemy1.onDraw(canvas);
    }
    for (ShooterPointBuff specialItem : pointBuffs) {
      specialItem.onDraw(canvas);
    }
    for (ShooterHealthAid specialItem : healthAids) {
      specialItem.onDraw(canvas);
    }
    for (ShooterBonus shooterBonus : shooterBonuses) {
      shooterBonus.onDraw(canvas);
    }
  }

  /** update and draw explosion effect */
  private void drawExplosion() {
    List<ShooterPlaneExplosion> remove1 = new ArrayList<>();
    for (ShooterPlaneExplosion planeExplosion : planeExplosions) {
      if (planeExplosion.checkFrameValid()) {
        planeExplosion.onDraw(canvas);
      } else {
        remove1.add(planeExplosion);
      }
    }
    for (ShooterPlaneExplosion planeExplosion : remove1) {
      planeExplosions.remove(planeExplosion);
    }

    List<ShooterEnemyExplosion> remove2 = new ArrayList<>();
    for (ShooterEnemyExplosion enemyExplosion : enemyExplosions) {
      if (enemyExplosion.checkFrameValid()) {
        enemyExplosion.onDraw(canvas);
      } else {
        remove2.add(enemyExplosion);
      }
    }
    for (ShooterEnemyExplosion enemyExplosion : remove2) {
      enemyExplosions.remove(enemyExplosion);
    }
  }
}
