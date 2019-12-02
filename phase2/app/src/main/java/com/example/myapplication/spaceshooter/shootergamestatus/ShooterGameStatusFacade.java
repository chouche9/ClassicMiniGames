package com.example.myapplication.spaceshooter.shootergamestatus;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.gameenum.GameEnum;
import com.example.myapplication.domain.GameStatus;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlane;

/** The type Shooter game status facade. */
public class ShooterGameStatusFacade extends GameStatus implements Parcelable {

  /**
   * Sets shooter cross level manager.
   *
   * @param shooterCrossLevelManager the shooter cross level manager
   */
  public void setShooterCrossLevelManager(ShooterCrossLevelManager shooterCrossLevelManager) {
    this.shooterCrossLevelManager = shooterCrossLevelManager;
  }

  /**
   * Sets shooter game level manager.
   *
   * @param shooterGameLevelManager the shooter game level manager
   */
  public void setShooterGameLevelManager(ShooterGameLevelManager shooterGameLevelManager) {
    this.shooterGameLevelManager = shooterGameLevelManager;
  }

  /** the shooterCrossLevelManager that manager cross level fields */
  private ShooterCrossLevelManager shooterCrossLevelManager;
  /** the shooterGameLevelManager that manger each level fields */
  private ShooterGameLevelManager shooterGameLevelManager;

  /**
   * Instantiates a new Shooter game status facade.
   *
   * @param name the name
   */
  public ShooterGameStatusFacade(String name) {
    super(name, GameEnum.SPACESHOOTER);
  }

  /**
   * Instantiates a new Shooter game status facade.
   *
   * @param in the in
   */
  protected ShooterGameStatusFacade(Parcel in) {
    super(in);
    setGameType(GameEnum.valueOf(in.readString()));
    shooterGameLevelManager = in.readParcelable(ShooterGameLevelManager.class.getClassLoader());
    shooterCrossLevelManager = in.readParcelable(ShooterCrossLevelManager.class.getClassLoader());
  }

  /**
   * write the object to parcel
   *
   * @param dest the parcel to write
   * @param flags the flag integer
   */
  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(getGameType().toString());
    dest.writeParcelable(shooterGameLevelManager, flags);
    dest.writeParcelable(shooterCrossLevelManager, flags);
  }

  /**
   * Get plane shooter plane.
   *
   * @return the shooter plane
   */
  public ShooterPlane getPlane() {
    return shooterGameLevelManager.getPlane();
  }

  /**
   * Add point.
   *
   * @param point the point
   */
  public void addPoint(int point) {
    shooterCrossLevelManager.setPoint(shooterCrossLevelManager.getPoint() + point);
  }

  /** Reset game status. */
  public void resetGameStatus() {
    shooterGameLevelManager.resetLevel();
    shooterCrossLevelManager.resetLevel();
  }

  /** Erase game status. */
  public void eraseGameStatus() {
    shooterCrossLevelManager.resetGame();
    shooterGameLevelManager.resetGame();
  }

  /**
   * Set if game success.
   *
   * @param success the success
   */
  public void setGameSuccess(boolean success) {
    shooterCrossLevelManager.setGameSuccess(success);
  }

  /**
   * Set plane.
   *
   * @param planeNum the plane num
   * @param context the context
   */
  public void setPlane(int planeNum, Context context) {
    shooterGameLevelManager.setPlane(planeNum, context);
  }

  /** The constant CREATOR. */
  public static final Creator<ShooterGameStatusFacade> CREATOR =
      new Creator<ShooterGameStatusFacade>() {
        @Override
        public ShooterGameStatusFacade createFromParcel(Parcel in) {
          return new ShooterGameStatusFacade(in);
        }

        @Override
        public ShooterGameStatusFacade[] newArray(int size) {
          return new ShooterGameStatusFacade[size];
        }
      };

  /**
   * Gets shooter cross level manager.
   *
   * @return the shooter cross level manager
   */
  public ShooterCrossLevelManager getShooterCrossLevelManager() {
    return shooterCrossLevelManager;
  }

  /**
   * Gets shooter game level manager.
   *
   * @return the shooter game level manager
   */
  public ShooterGameLevelManager getShooterGameLevelManager() {
    return shooterGameLevelManager;
  }
}
