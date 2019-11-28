package com.example.myapplication.FlappyFish;

import android.app.Activity;

import com.example.myapplication.databaseconnector.GameEnum;
import com.example.myapplication.Domain.GameManager;
import com.example.myapplication.Domain.GameStatus;
import com.example.myapplication.databaseconnector.GameStatusDaoImpl;

/** The game manager for this flappy fish game. */
class FlappyGameManager extends GameManager {

  /** The singleton FlappyGameManager. */
  private static FlappyGameManager gameManager;

  private Activity activity;

  /** Constructs a FlappyGameManager. */
  private FlappyGameManager(Activity activity) {
    this.activity = activity;
  }

  /**
   * Getter for this FlappyGameManager instance.
   *
   * @return FlappyGameManager the instance of this FlappyGameManager.
   */
  static FlappyGameManager getInstance(Activity activity) {
    if (gameManager == null) {
      gameManager = new FlappyGameManager(activity);
    }
    return gameManager;
  }

  /**
   * Save the GameStatus for a particular user.
   *
   * @param gameStatus the new GameStatus want to get saved.
   */
  public void saveGame(GameStatus gameStatus) {
    GameStatusDaoImpl.getInstance(activity).saveGameStatus(gameStatus, GameEnum.FLAPPYFISH);
  }

  /**
   * Returns the gameStatus instance that this FlappyGameManager is managing.
   *
   * @param username the username of the user playing this game.
   * @return FlappyGameStat the HangmanGameStat instance of this user.
   */
  public FlappyGameStatus getGameStatus(String username) {
    FlappyGameStatus flappyGameStatus =
        (FlappyGameStatus)
            GameStatusDaoImpl.getInstance(activity).getGameStatus(username, GameEnum.FLAPPYFISH);
    if (flappyGameStatus == null) {
      flappyGameStatus = new FlappyGameStatus(username);
    }
    return flappyGameStatus;
  }
}
