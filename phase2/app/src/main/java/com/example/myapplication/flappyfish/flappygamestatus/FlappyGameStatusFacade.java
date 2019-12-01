package com.example.myapplication.flappyfish.flappygamestatus;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.databaseconnector.GameEnum;
import com.example.myapplication.flappyfish.gameobjects.FlappyGameBonus;
import com.example.myapplication.flappyfish.gameobjects.FlappyGameFish;
import com.example.myapplication.flappyfish.gameobjects.FlappyGameShark;
import com.example.myapplication.flappyfish.gameobjects.FlappyGameShrimp;
import com.example.myapplication.domain.GameStatus;

/** The flappy fish game status. */
public class FlappyGameStatusFacade extends GameStatus implements Parcelable {

  /**
   * The object that is responsible for managing all the game objects displayed on the screen.
   */
  private ObjectManager objectManager;

  /**
   * The object that is responsible for managing all the attributes of the game.
   */
  private LevelManager levelManager;

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

  /**
   * Set the level manager of this game status to levelManager.
   * @param levelManager the new level manager of this game status object.
   */
  public void setLevelManager(LevelManager levelManager) {
    this.levelManager = levelManager;
  }

  /**
   * Set the object manager of this game status to levelManager.
   * @param objectManager the new object manager of this game status object.
   */
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

  /**
   * Set the background of this game status to the light theme.
   */
  public void setBgLight() {
    levelManager.setBgLight();
  }

  /**
   * Set the background of this game status to the dark theme.
   */
  public void setBgDark() {
    levelManager.setBgDark();
  }

  /**
   * Return the background of this game status object.
   * @return the background of this game status object.
   */
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

  /**
   * Increase the score of this game status by bonusScore.
   * @param bonusScore the score obtained by winning the bonus game.
   */
  public void addBonusScore(int bonusScore) {
    levelManager.addBonusScore(bonusScore);
  }

  /**
   * Get the life count.
   *
   * @return Return the life count.
   */
  public int getLifeCount() {
    return levelManager.getLifeCount();
  }

  /** Reduce the life count. */
  public void reduceLifeCount() {
    levelManager.reduceLifeCount();
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

  /**
   * Return the fish object tracked by this game status object.
   * @return the fish object tracker by this game status object.
   */
  public FlappyGameFish getFish() {
    return objectManager.getFish();
  }

  /**
   * Return the shrimp object tracked by this game status object.
   * @return the shrimp object tracker by this game status object.
   */
  public FlappyGameShrimp getShrimp() {
    return objectManager.getShrimp();
  }

  /**
   * Return the shark object tracked by this game status object.
   * @return the shark object tracker by this game status object.
   */
  public FlappyGameShark getShark() {
    return objectManager.getShark();
  }

  /**
   * Return the bonus game object tracked by this game status object.
   * @return the bonus game object tracker by this game status object.
   */
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
