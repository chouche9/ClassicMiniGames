package com.example.myapplication.flappyfish.FlappyGameStatus;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.databaseconnector.GameEnum;
import com.example.myapplication.flappyfish.GameObjects.FlappyGameBonus;
import com.example.myapplication.flappyfish.GameObjects.FlappyGameFish;
import com.example.myapplication.flappyfish.GameObjects.FlappyGameShark;
import com.example.myapplication.flappyfish.GameObjects.FlappyGameShrimp;
import com.example.myapplication.domain.GameStatus;

/** The flappy fish game status. */
public class FlappyGameStatusFacade extends GameStatus implements Parcelable {

  private ObjectManager objectManager = new ObjectManager();

  private LevelManager levelManager = new LevelManager();

  /**
   * Construct a new game status for the user with name name.
   *
   * @param name the name of the user.
   */
  public FlappyGameStatusFacade(String name) {
    super(name, GameEnum.FLAPPYFISH);
  }

  /**
   * Build FlappyGameStatusFacade from Parcel.
   *
   * @param in the Parcel that store FlappyGameStatusFacade.
   */
  private FlappyGameStatusFacade(Parcel in) {
    super(in);
    setGameType(GameEnum.valueOf(in.readString()));
    levelManager = in.readParcelable(LevelManager.class.getClassLoader());
    objectManager = in.readParcelable(ObjectManager.class.getClassLoader());
  }

  public void setLevelManager(LevelManager levelManager) {
    this.levelManager = levelManager;
  }

  public void setObjectManager(ObjectManager objectManager) {
    this.objectManager = objectManager;
  }

  /** Set the game difficulty to the initial stage. */
  public void setGameDefault() {
    objectManager.setObjectDefault();
    levelManager.setStageDefault();
  }

  /** Set the game difficulty level to hard. */
  public void increaseGameStage() {
    objectManager.increaseObjectStage();
    levelManager.increaseStage();
  }

  public void setBgLight() {
    levelManager.setBgLight();
  }

  public void setBgDark() {
    levelManager.setBgDark();
  }

  public boolean getBg() {
    return levelManager.getBackground();
  }

  /**
   * Get the difficulty of the game.
   *
   * @return Return the difficulty of the game.
   */
  public int getStage() {
    return levelManager.getStage();
  }

  /**
   * Get the score of the game.
   *
   * @return Return the score of the game.
   */
  public int getScore() {
    return levelManager.getScore();
  }

  /** Update the score. */
  public void updateScore() {
    levelManager.updateScore();
  }

  public void addBonusScore(int bonusScore) {
    levelManager.addBonusScore(bonusScore);
  }

  /**
   * Get the life count.
   *
   * @return Return the life count.
   */
  public int getLife_count() {
    return levelManager.getLife_count();
  }

  /** Reduce the life count. */
  public void reduceLife_count() {
    levelManager.reduceLife_count();
  }

  /**
   * Check whether the user has played the game.
   *
   * @return Return true if the user has played the game, false otherwise.
   */
  public boolean getPlayed() {
    return levelManager.getPlayed();
  }

  /** Start updating the game status. */
  public void startUpdate() {
    objectManager.restartFish();
    levelManager.startUpdate();
  }

  /** Stop updating the game status. */
  public void finishUpdate() {
    objectManager.restartFish();
    levelManager.finishUpdate();
  }

  public void setFishJumpSpeed() {
    objectManager.setFishJumpSpeed();
  }

  public FlappyGameFish getFish() {
    return objectManager.getFish();
  }

  public FlappyGameShrimp getShrimp() {
    return objectManager.getShrimp();
  }

  public FlappyGameShark getShark() {
    return objectManager.getShark();
  }
  public FlappyGameBonus getBonus() {
    return objectManager.getBonus();
  }


  /**
   * Default method from the super class.
   *
   * @return 0.
   */
  @Override
  public int describeContents() {
    return 0;
  }

  /**
   * Default method from the super class.
   *
   * @param parcel parcel to write the attributes of this GameStatus.
   * @param i flags.
   */
  @Override
  public void writeToParcel(Parcel parcel, int i) {
    super.writeToParcel(parcel, i);
    parcel.writeString(getGameType().toString());
    parcel.writeParcelable(levelManager, i);
    parcel.writeParcelable(objectManager, i);
  }

  /** Create FlappyGameStatatus by the super Creator object. */
  public static final Creator<FlappyGameStatusFacade> CREATOR =
          new Creator<FlappyGameStatusFacade>() {
            @Override
            public FlappyGameStatusFacade createFromParcel(Parcel in) {
              return new FlappyGameStatusFacade(in);
            }

            @Override
            public FlappyGameStatusFacade[] newArray(int size) {
              return new FlappyGameStatusFacade[size];
            }
          };
}
