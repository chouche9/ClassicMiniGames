package com.example.myapplication.spaceshooter.shootergamestatus;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.spaceshooter.gameobject.ShooterBonus;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlaneBullet;
import com.example.myapplication.spaceshooter.gameobject.ShooterEnemyBullet;
import com.example.myapplication.spaceshooter.gameobject.ShooterEnemy;
import com.example.myapplication.spaceshooter.gameobject.ShooterEnemyExplosion;
import com.example.myapplication.spaceshooter.gameobject.ShooterHealthAid;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlane;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlaneExplosion;
import com.example.myapplication.spaceshooter.gameobject.ShooterPointBuff;

import java.util.ArrayList;
import java.util.List;

/** The type Shooter game level manager. */
public class ShooterGameLevelManager implements Parcelable {
  /** list of shooter enemy */
  private List<ShooterEnemy> enemies = new ArrayList<>();
  /** list of plane bullets */
  private List<ShooterPlaneBullet> planeBullets = new ArrayList<>();
  /** list of enemy bullets */
  private List<ShooterEnemyBullet> enemyBullets = new ArrayList<>();
  /** list ofs bonuse items */
  private List<ShooterBonus> shooterBonuses = new ArrayList<>();
  /** list of health aids */
  private List<ShooterHealthAid> healthAids = new ArrayList<>();
  /** list of point buff */
  private List<ShooterPointBuff> pointBuffs = new ArrayList<>();
  /** list of enemyExplosions */
  private List<ShooterEnemyExplosion> enemyExplosions = new ArrayList<>();
  /** list of planeExplosions */
  private List<ShooterPlaneExplosion> planeExplosions = new ArrayList<>();
  /** the plane object */
  private ShooterPlane plane;
  /** number of millSecond left in this game */
  private int millsecondLeft;

  /** The constant initaltime. */
  public static int initaltime = 30000;

  /** Instantiates a new Shooter game level manager. */
  public ShooterGameLevelManager() {
    this.millsecondLeft = initaltime;
  }

  /**
   * Instantiates a new Shooter game level manager.
   *
   * @param in the in
   */
  private ShooterGameLevelManager(Parcel in) {
    enemies = in.createTypedArrayList(ShooterEnemy.CREATOR);
    planeBullets = in.createTypedArrayList(ShooterPlaneBullet.CREATOR);
    enemyBullets = in.createTypedArrayList(ShooterEnemyBullet.CREATOR);
    shooterBonuses = in.createTypedArrayList(ShooterBonus.CREATOR);
    healthAids = in.createTypedArrayList(ShooterHealthAid.CREATOR);
    pointBuffs = in.createTypedArrayList(ShooterPointBuff.CREATOR);
    enemyExplosions = in.createTypedArrayList(ShooterEnemyExplosion.CREATOR);
    planeExplosions = in.createTypedArrayList(ShooterPlaneExplosion.CREATOR);
    plane = in.readParcelable(ShooterPlane.class.getClassLoader());
    millsecondLeft = in.readInt();
  }

  /** The constant CREATOR. */
  public static final Creator<ShooterGameLevelManager> CREATOR =
      new Creator<ShooterGameLevelManager>() {
        @Override
        public ShooterGameLevelManager createFromParcel(Parcel in) {
          return new ShooterGameLevelManager(in);
        }

        @Override
        public ShooterGameLevelManager[] newArray(int size) {
          return new ShooterGameLevelManager[size];
        }
      };

  /** Reset game level when starting new level. */
  void resetLevel() {
    if (plane != null) {
      plane.resetPosition();
    }
    millsecondLeft = initaltime;
    shooterBonuses = new ArrayList<>();
    planeBullets = new ArrayList<>();
    enemyBullets = new ArrayList<>();
    enemies = new ArrayList<>();
    healthAids = new ArrayList<>();
    pointBuffs = new ArrayList<>();
    enemyExplosions = new ArrayList<>();
    planeExplosions = new ArrayList<>();
  }

  /** Reset game when starting new game. */
  void resetGame() {
    resetLevel();
    plane = null;
  }

  /**
   * creating new plane.
   *
   * @param planeNum the plane num
   * @param context the context
   */
  void setPlane(int planeNum, Context context) {
    plane = new ShooterPlane(context, planeNum);
  }

  /**
   * Gets enemies.
   *
   * @return the enemies
   */
  public List<ShooterEnemy> getEnemies() {
    return enemies;
  }

  /**
   * Gets plane bullets.
   *
   * @return the plane bullets list
   */
  public List<ShooterPlaneBullet> getPlaneBullets() {
    return planeBullets;
  }

  /**
   * Gets enemy bullets.
   *
   * @return the enemy bullets list
   */
  public List<ShooterEnemyBullet> getEnemyBullets() {
    return enemyBullets;
  }

  /**
   * Gets shooter bonuses.
   *
   * @return the shooter bonuses
   */
  public List<ShooterBonus> getShooterBonuses() {
    return shooterBonuses;
  }

  /**
   * Gets health aids.
   *
   * @return the health aids
   */
  public List<ShooterHealthAid> getHealthAids() {
    return healthAids;
  }

  /**
   * Gets point buffs.
   *
   * @return the point buffs
   */
  public List<ShooterPointBuff> getPointBuffs() {
    return pointBuffs;
  }

  /**
   * Gets enemy explosions.
   *
   * @return the enemy explosions
   */
  public List<ShooterEnemyExplosion> getEnemyExplosions() {
    return enemyExplosions;
  }

  /**
   * Gets plane explosions.
   *
   * @return the plane explosions
   */
  public List<ShooterPlaneExplosion> getPlaneExplosions() {
    return planeExplosions;
  }

  /**
   * Gets plane.
   *
   * @return the plane
   */
  public ShooterPlane getPlane() {
    return plane;
  }

  /**
   * Gets millsecond left.
   *
   * @return the millsecond left
   */
  public int getMillsecondLeft() {
    return millsecondLeft;
  }

  /**
   * Sets mill second left.
   *
   * @param millSecondLeft the mill second left
   */
  public void setMillisecondLeft(int millSecondLeft) {
    this.millsecondLeft = millSecondLeft;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeTypedList(enemies);
    parcel.writeTypedList(planeBullets);
    parcel.writeTypedList(enemyBullets);
    parcel.writeTypedList(shooterBonuses);
    parcel.writeTypedList(healthAids);
    parcel.writeTypedList(pointBuffs);
    parcel.writeTypedList(enemyExplosions);
    parcel.writeTypedList(planeExplosions);
    parcel.writeParcelable(plane, i);
    parcel.writeInt(millsecondLeft);
  }
}
