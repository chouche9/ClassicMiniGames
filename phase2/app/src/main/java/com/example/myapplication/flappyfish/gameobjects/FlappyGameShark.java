package com.example.myapplication.flappyfish.gameobjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.flappyfish.flappygamestatus.FlappyGameStatusFacade;

public class FlappyGameShark extends FlappyGameObjects implements Parcelable {

  /** The default speed of the shark. */
  private static final int SHARK_SPEED_DEFAULT = 20;

  /** The default increment of speed of the shark. */
  private static final int SHARK_SPEED_INCREASE = 5;

  /** The default x coordinate of an object when it collides with the fish. */
  private static final int DEAD_POS = -100;

  /**
   * Construct a new shark object at the default starting coordinates with the easy mode velocity as
   * default.
   */
  public FlappyGameShark() {
    super(0, 0, SHARK_SPEED_DEFAULT);
  }

  /**
   * Build the shark object from Parcel.
   *
   * @param in the parcel that stores the previously saved shark object.
   */
  private FlappyGameShark(Parcel in) {
    super(in);
  }

  /** Set the velocity of this shrimp object to the default value. */
  @Override
  public void setGameDefault() {
    setVelocity(SHARK_SPEED_DEFAULT);
  }

  /** Increase the velocity of this shrimp object by the defined increment. */
  @Override
  public void increaseGameStage() {
    setVelocity(getVelocity() + SHARK_SPEED_INCREASE);
  }

  /** Move the shark according to its current velocity. */
  public void move() {
    setX(getX() - getVelocity());
  }

  /**
   * Update the shark object according to the specified game status and ensure the shark does not
   * move out of the screen using canvasWidth, minY and maxY.
   *
   * @param gameStatus the game status object that tracks this fish.
   * @param canvasWidth the width of the canvas this fish is drawn on.
   * @param minY the minimum value for this fish's y coordinate.
   * @param maxY the maximum value for this fish's coordinate.
   */
  public boolean update(FlappyGameStatusFacade gameStatus, int canvasWidth, int minY, int maxY) {
    if (collideCheck(gameStatus)) {
      gameStatus.reduceLifeCount();
      kill();
      if (gameStatus.getLifeCount() == 0) {
        return true;
      }
    }
    validCheck(canvasWidth, minY, maxY);
    return false;
  }

  /**
   * Ensures the shark object does not move outside of the screen.
   *
   * @param canvasWidth the width of the displayed canvas.
   * @param minY the minimum value for shark's y coordinate.
   * @param maxY the maximum value for shark's y coordinate.
   */
  void validCheck(int canvasWidth, int minY, int maxY) {
    if (getX() < 0) {
      setX(canvasWidth + 10);
      setY((int) Math.floor(Math.random() * (maxY - minY)) + minY);
    }
  }

  /** Set the x coordinate of the shark to dead position. */
  private void kill() {
    setX(DEAD_POS);
  }

  /**
   * Check whether the specified fish object collides with this shark object.
   *
   * @param gameStatus the gameStatus object that tracks the two game objects.
   * @return Return true if shark collides with the fish object; Otherwise, return false.
   */
  private boolean collideCheck(FlappyGameStatusFacade gameStatus) {
    FlappyGameObjects fish = gameStatus.getFish();
    FlappyGameObjects shark = gameStatus.getShark();
    int fishX = fish.getX();
    int fishY = fish.getY();
    int sharkX = shark.getX();
    int sharkY = shark.getY();
    return fishX < sharkX
        && sharkX < (fishX + fish.getWidth())
        && (fishY - shark.getHeight()) < sharkY
        && sharkY < (fishY + fish.getHeight());
  }

  /** Binds the FlappyGameShark object. */
  public static final Creator<FlappyGameShark> CREATOR =
      new Parcelable.Creator<FlappyGameShark>() {
        public FlappyGameShark createFromParcel(Parcel in) {
          return new FlappyGameShark(in);
        }

        public FlappyGameShark[] newArray(int size) {
          return new FlappyGameShark[size];
        }
      };
}
