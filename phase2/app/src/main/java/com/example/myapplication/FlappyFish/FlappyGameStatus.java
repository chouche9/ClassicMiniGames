package com.example.myapplication.FlappyFish;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.FlappyFish.GameObjects.FlappyGameBonus;
import com.example.myapplication.FlappyFish.GameObjects.FlappyGameFish;
import com.example.myapplication.FlappyFish.GameObjects.FlappyGameObjects;
import com.example.myapplication.FlappyFish.GameObjects.FlappyGameShark;
import com.example.myapplication.FlappyFish.GameObjects.FlappyGameShrimp;
import com.example.myapplication.Domain.GameStatus;

/** The flappy fish game status. */
public class FlappyGameStatus extends GameStatus implements Parcelable {

  /** The default score when game starts. */
  private static final int DEFAULT_SCORE = 10;

  /** The fish object that is displayed on the screen. */
  public FlappyGameFish fish = new FlappyGameFish();

  /** The shrimp object that is displayed on the screen. */
  public FlappyGameShrimp shrimp = new FlappyGameShrimp();

  /** The shark object that is displayed on the screen. */
  public FlappyGameShark shark = new FlappyGameShark();

  /** The bonus object that is displayed on the screen. */
  public FlappyGameBonus bonus = new FlappyGameBonus();

  public boolean background;

  /** Indicate whether the user has played the game. */
  private int played;

  /** The stage the user is currently playing at. */
  private int stage = 1;

  /** The type of the game the user plays. */
  private String type;

  /** The score the user has. */
  private int score;

  /** The number of lives the user has. */
  private int life_count;

  /**
   * Construct a new game status for the user with name name.
   *
   * @param name the name of the user.
   */
  FlappyGameStatus(String name) {
    super(name);
    this.type = "FlappyGameStatus";
  }

  /**
   * Build FlappyGameStatus from Parcel.
   *
   * @param in the Parcel that store FlappyGameStatus.
   */
  private FlappyGameStatus(Parcel in) {
    super(in);
    type = in.readString();
    fish = in.readParcelable(FlappyGameObjects.class.getClassLoader());
    shrimp = in.readParcelable(FlappyGameObjects.class.getClassLoader());
    shark = in.readParcelable(FlappyGameObjects.class.getClassLoader());
    bonus = in.readParcelable(FlappyGameObjects.class.getClassLoader());
    score = in.readInt();
    life_count = in.readInt();
    played = in.readInt();
    stage = in.readInt();
    background = in.readByte() != 0;
  }

  /** Set the game difficulty to the initial stage. */
  void setGameDefault() {
    fish.setGameDefault();
    shrimp.setGameDefault();
    shark.setGameDefault();
    bonus.setGameDefault();
    this.stage = 1;
  }

  /** Set the game difficulty level to hard. */
  public void increaseGameStage() {
    fish.increaseGameStage();
    shrimp.increaseGameStage();
    shark.increaseGameStage();
    bonus.increaseGameStage();
    this.stage += 1;
  }

  void setBgLight() {
    this.background = false;
  }

  void setBgDark() {
    this.background = true;
  }

  /**
   * Get the difficulty of the game.
   *
   * @return Return the difficulty of the game.
   */
  public int getStage() {
    return this.stage;
  }

  /**
   * Get the score of the game.
   *
   * @return Return the score of the game.
   */
  public int getScore() {
    return score;
  }

  /** Update the score. */
  public void updateScore() {
    this.score += DEFAULT_SCORE;
  }

  /**
   * Add bonusScore to score.
   *
   * @param bonusScore the value that is added to the current score.
   */
  void addBonusScore(int bonusScore) {
    this.score += bonusScore;
  }

  /**
   * Get the life count.
   *
   * @return Return the life count.
   */
  public int getLife_count() {
    return life_count;
  }

  /** Reduce the life count. */
  public void reduceLife_count() {
    life_count--;
  }

  /**
   * Check whether the user has played the game.
   *
   * @return Return true if the user has played the game, false otherwise.
   */
  boolean getPlayed() {
    return played != 0;
  }

  /**
   * Set played to 1 if played is true, 0 otherwise.
   *
   * @param played whether the game status has been updated.
   */
  private void setPlayed(boolean played) {
    if (played) {
      this.played = 1;
    } else {
      this.played = 0;
    }
  }

  /** Start updating the game status. */
  void startUpdate() {
    setPlayed(true);
    fish = new FlappyGameFish();
    life_count = 3;
    score = 0;
  }

  /** Stop updating the game status. */
  void finishUpdate() {
    setPlayed(false);
    fish = new FlappyGameFish();
    life_count = 3;
    score = 0;
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
    parcel.writeString(type);
    parcel.writeParcelable(fish, i);
    parcel.writeParcelable(shrimp, i);
    parcel.writeParcelable(shark, i);
    parcel.writeParcelable(bonus, i);
    parcel.writeInt(score);
    parcel.writeInt(life_count);
    parcel.writeInt(played);
    parcel.writeInt(stage);
    parcel.writeByte((byte) (background ? 1 : 0));
  }

  /** Create FlappyGameStatatus by the super Creator object. */
  public static final Creator<FlappyGameStatus> CREATOR =
          new Creator<FlappyGameStatus>() {
            @Override
            public FlappyGameStatus createFromParcel(Parcel in) {
              return new FlappyGameStatus(in);
            }

            @Override
            public FlappyGameStatus[] newArray(int size) {
              return new FlappyGameStatus[size];
            }
          };
}
