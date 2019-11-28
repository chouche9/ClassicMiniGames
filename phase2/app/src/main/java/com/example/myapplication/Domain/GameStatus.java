package com.example.myapplication.Domain;

import android.os.Parcel;
import android.os.Parcelable;

/** GameStatus of a game. */
public class GameStatus implements Parcelable {

  /** The name of the user that this GameStatus is responsible for. */
  private String name;

  /**
   * Constructs this GameStatus.
   *
   * @param name the name of the user that this GameStatus is responsible for.
   */
  public GameStatus(String name) {
    this.name = name;
  }

  /**
   * Constructs this GameStatus using the values stored in parcel in.
   *
   * @param in the parcel that stores values of a GameStatus object.
   */
  protected GameStatus(Parcel in) {
    name = in.readString();
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
  }
}
