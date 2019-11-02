package com.example.myapplication.GuessNum;

import android.app.Activity;

import com.example.myapplication.GameManager;

/** the class that manager all GuessGameStat. */
public class GuessGameManager extends GameManager {

  /** the singleton guessGameManager */
  private static GuessGameManager guessGameManager;

  /** the name of the file want to get called in sharedPreferences */
  private static final String gameName = "guess game";

  /**
   * initiate GuessGameManager Instance
   *
   * @param activity: the activity that call GameManger;
   * @param name: the file name which store your information;
   */
  private GuessGameManager(Activity activity, String name) {
    super(activity, name);
  }

  /**
   * get the singleton of GuessGameManager
   *
   * @param activity the activity that call getInstance;
   * @return the GuessGameManager Singleton
   */
  static GuessGameManager getInstance(Activity activity) {
    if (guessGameManager == null) {
      guessGameManager = new GuessGameManager(activity, gameName);
    }
    return guessGameManager;
  }

  /**
   * get the GuessGameStat for user: username
   *
   * @param username: name of this user
   * @return GuessGameStat for user: username
   */
  public GuessGameStat getGameStatus(String username) {
    GuessGameStat guessGameStat = (GuessGameStat) super.getGameStatus(username);
    if (guessGameStat == null) {
      guessGameStat = new GuessGameStat(username);
    }
    return guessGameStat;
  }
}
