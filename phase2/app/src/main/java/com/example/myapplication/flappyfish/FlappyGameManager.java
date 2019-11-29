package com.example.myapplication.flappyfish;

import android.app.Activity;

import com.example.myapplication.flappyfish.FlappyGameStatus.FlappyGameStatusFacade;
import com.example.myapplication.databaseconnector.GameEnum;
import com.example.myapplication.domain.GameManager;
import com.example.myapplication.domain.GameStatus;
import com.example.myapplication.databaseconnector.GameStatusDaoImpl;

/** The game manager for this flappy fish game. */
class FlappyGameManager extends GameManager {

  /** The singleton FlappyGameManager. */
  private static FlappyGameManager gameManager;

  /** Constructs a FlappyGameManager. */
  private FlappyGameManager(Activity activity) {
    super(activity);
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
   * Returns the gameStatus instance that this FlappyGameManager is managing.
   *
   * @param username the username of the user playing this game.
   * @return FlappyGameStat the HangmanGameStat instance of this user.
   */
  public FlappyGameStatusFacade getGameStatus(String username) {
    FlappyGameStatusFacade flappyGameStatusFacade =
            (FlappyGameStatusFacade)
                    GameStatusDaoImpl.getInstance(getActivity()).getGameStatus(username, GameEnum.FLAPPYFISH);
    if (flappyGameStatusFacade == null) {
      flappyGameStatusFacade = new FlappyGameStatusFacade(username);
    }
    return flappyGameStatusFacade;
  }
}
