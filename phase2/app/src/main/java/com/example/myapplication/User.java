package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/** A user. */
public class User implements Parcelable {

  /** The username of this user. */
  private String name;

  /** The password of this user. */
  private String password;


  /**
   * Constructs this user.
   *
   * @param name the username of this user.
   * @param password the password of this user.
   */
  public User(String name, String password) {
    this.name = name;
    this.password = password;
  }

  /**
   * Getter for this user's username.
   *
   * @return String the username of this user.
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for this user's password.
   *
   * @return String this user's password.
   */
  String getPassword() {
    return password;
  }

  /**
   * Constructs this User using the values stored in parcel in.
   *
   * @param in the parcel that stores values of a User object.
   */
  private User(Parcel in) {
    name = in.readString();
    password = in.readString();
  }

  /** Binds the User object. */
  public static final Creator<User> CREATOR =
          new Creator<User>() {
            @Override
            public User createFromParcel(Parcel in) {
              return new User(in);
            }

            @Override
            public User[] newArray(int size) {
              return new User[size];
            }
          };

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
   * Write the attributes of this User to parcel.
   *
   * @param parcel parcel to write the attributes of this User.
   * @param i flags.
   */
  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(name);
    parcel.writeString(password);
  }

  /**
   * Return the string representation of this user.
   *
   * @return String the string representation of this user.
   */
  @NonNull
  @Override
  public String toString() {
    return "User: " + name + " password: " + password + " \n";
  }
}
