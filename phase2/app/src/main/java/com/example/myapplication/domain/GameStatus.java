package com.example.myapplication.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.gameenum.GameEnum;

/** GameStatus of a game. */
public class GameStatus implements Parcelable {

  /** The name of the user that this GameStatus is responsible for. */
  private String name;

  /** The game type of this GameStatus. */
  private GameEnum gameType;

  /**
   * Constructs this GameStatus.
   *
   * @param name the name of the user that this GameStatus is responsible for.
   */
  protected GameStatus(String name, GameEnum gameType) {
    this.name = name;
    this.gameType = gameType;
  }

  /**
   * Get the game type of this GameStatus.
   *
   * @return the game type of this GameStatus.
   */
  protected GameEnum getGameType() {
    return gameType;
  }

  /**
   * Set the game type of this GameStatus.
   *
   * @param gameType the game type of this GameStatus.
   */
  protected void setGameType(GameEnum gameType) {
    this.gameType = gameType;
  }

  /**
   * Constructs this GameStatus using the values stored in parcel in.
   *
   * @param in the parcel that stores values of a GameStatus object.
   */
  protected GameStatus(Parcel in) {
    name = in.readString();
    gameType = GameEnum.valueOf(in.readString());
  }

  /** Binds the GameStatus object. */
  public static final Creator<GameStatus> CREATOR =
      new Creator<GameStatus>() {
        @Override
        public GameStatus createFromParcel(Parcel in) {
          return new GameStatus(in);
        }

        @Override
        public GameStatus[] newArray(int size) {
          return new GameStatus[size];
        }
      };

  /**
   * Getter for the name attribute.
   *
   * @return String the name attribute.
   */
  public String getName() {
    return name;
  }

  /**
   * Default method from Parcelable interface.
   *
   * @return int 0
   */
  @Override
  public int describeContents() {
    return 0;
  }

  /**
   * Write the attribute of this GameStatus to parcel.
   *
   * @param parcel parcel to write the attributes of this GameStatus.
   * @param i flags.
   */
  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(name);
    parcel.writeString(gameType.name());
  }
}
