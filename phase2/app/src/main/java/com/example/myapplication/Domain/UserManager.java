package com.example.myapplication.Domain;

import android.app.Activity;

import com.example.myapplication.databaseconnector.UserDaoImpl;

/** A UserManager for this application. */
public class UserManager {

  /** The single static instance of UserManager. */
  private static UserManager userManager = null;

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
   * @param userName the username.
   * @param password the password.
   * @return true if userName and password are valid.
   */
  public boolean authenticate(String userName, String password) {
    User user = getUser(userName);
    if (user == null) {
      return false;
    }
    return (user.getPassword().equals(password));
  }

  /**
   * Return whether if a user with a username userName has an account already.
   *
   * @param userName the username.
   * @return true if a user with userName doesn't exist.
   */
  public boolean createAuthenticate(String userName) {
    User user = userManager.getUser(userName);
    return user == null;
  }
}
