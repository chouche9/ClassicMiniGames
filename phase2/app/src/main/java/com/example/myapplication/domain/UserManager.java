package com.example.myapplication.domain;

import android.app.Activity;

import com.example.myapplication.databaseconnector.UserDaoImpl;

/** A UserManager for this application. */
public class UserManager {

  /** The single static instance of UserManager. */
  private static UserManager userManager = null;

  /** The activity that called this user manager. */
  private Activity activity;

  /**
   * Construct this UserManager singleton instance.
   *
   * @param activity the activity that called this UserManager.
   */
  private UserManager(Activity activity) {
    this.activity = activity;
  }

  /**
   * Method that creates the only instance of UserManager.
   *
   * @param activity the activity that called this UserManager.
   * @return UserManager the only instance of UserManager.
   */
  public static UserManager getInstance(Activity activity) {
    if (userManager == null) userManager = new UserManager(activity);

    return userManager;
  }

  /**
   * Saves the User instance into sharedPreferences.
   *
   * @param user the user that is to be saved.
   */
  public void saveUser(User user) {
    UserDaoImpl.getInstance(activity).saveUser(user);
  }

  /**
   * Return the User instance with username userName.
   *
   * @param username the username of a user.
   * @return User the user instance with the username userName.
   */
  private User getUser(String username) {
    return UserDaoImpl.getInstance(activity).getUser(username);
  }

  /**
   * Return whether if userName and password are valid.
   *
   * @param username the username.
   * @param password the password.
   * @return true if userName and password are valid.
   */
  public boolean authenticate(String username, String password) {
    User user = getUser(username);
    if (user == null) {
      return false;
    }
    return (user.getPassword().equals(password));
  }

  /**
   * Return whether if a user with a specified username has an account already.
   *
   * @param username the username of a user.
   * @return true if a user with username doesn't exist, false otherwise.
   */
  public boolean createAuthenticate(String username) {
    User user = userManager.getUser(username);
    return user == null;
  }
}
