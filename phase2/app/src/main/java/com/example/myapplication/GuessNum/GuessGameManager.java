package com.example.myapplication.GuessNum;

import android.app.Activity;

import com.example.myapplication.databaseconnector.GameEnum;
import com.example.myapplication.GameManager;
import com.example.myapplication.GameStatus;
import com.example.myapplication.databaseconnector.GameStatusDaoImpl;

/** the class that manager all GuessGameStat. */
class GuessGameManager extends GameManager {

  /** the singleton guessGameManager */
  private static GuessGameManager guessGameManager;

  /** Constructs a GuessGameManager. */
  private GuessGameManager(Activity activity) {
    this.activity = activity;
  }

  private Activity activity;

  /**
   * get the singleton of GuessGameManager
   *
   * @return the GuessGameManager Singleton
   */
  static GuessGameManager getInstance(Activity activity) {
    if (guessGameManager == null) {
      guessGameManager = new GuessGameManager(activity);
    }
    return guessGameManager;
  }

  /**
   * Save the GameStatus for a particular user.
   *
   * @param gameStatus the new GameStatus want to get saved.
   */
  public void saveGame(GameStatus gameStatus) {
    GameStatusDaoImpl.getInstance(activity).saveGameStatus(gameStatus, GameEnum.GUESSNUM);
  }

  /**
   * get the GuessGameStat for user: username
   *
   * @param username: name of this user
   * @return GuessGameStat for user: username
   */
  public GuessGameStat getGameStatus(String username) {
    GuessGameStat guessGameStat =
        (GuessGameStat)
            GameStatusDaoImpl.getInstance(activity).getGameStatus(username, GameEnum.GUESSNUM);
    if (guessGameStat == null) {
      guessGameStat = new GuessGameStat(username);
    }
    return guessGameStat;
  }
}
