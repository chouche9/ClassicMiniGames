package com.example.myapplication.domain;

import android.app.Activity;

import com.example.myapplication.databaseconnector.GameStatusDaoImpl;

/** A game manager. */
public abstract class GameManager {

  /** The activity that called this GameManager. */
  private Activity activity;

  /**
   * Construct this GameManger.
   *
   * @param activity the activity that called this GameManager.
   */
  public GameManager(Activity activity) {
    this.activity = activity;
  }

  /**
   * Get the activity that called this GameManager.
   *
   * @return the activity that called this GameManager.
   */
  public Activity getActivity() {
    return activity;
  }

  /**
   * Get the GameStatus for a particular game for a particular user.
   *
   * @param username name of this user
   * @return the GameStatus
   */
  public abstract GameStatus getGameStatus(String username);
  /**
   * Save the GameStatus for a particular user.
   *
   * @param gameStatus the new GameStatus want to get saved.
   */
  public void saveGame(GameStatus gameStatus) {
    GameStatusDaoImpl.getInstance(activity).saveGameStatus(gameStatus, gameStatus.getGameType());
  }
}
