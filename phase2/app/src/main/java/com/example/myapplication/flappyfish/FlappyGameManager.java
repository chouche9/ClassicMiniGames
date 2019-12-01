package com.example.myapplication.flappyfish;

import android.app.Activity;

import com.example.myapplication.flappyfish.flappygamestatus.FlappyGameStatusFacade;
import com.example.myapplication.databaseconnector.GameEnum;
import com.example.myapplication.domain.GameManager;
import com.example.myapplication.databaseconnector.GameStatusDaoImpl;
import com.example.myapplication.flappyfish.flappygamestatus.LevelManager;
import com.example.myapplication.flappyfish.flappygamestatus.ObjectManager;

/** The game manager for this flappy fish game. */
class FlappyGameManager extends GameManager {

  /** The singleton FlappyGameManager. */
  private static FlappyGameManager gameManager;

  /**
   * Constructs a FlappyGameManager.
   *
   * @param activity the activity that called this FlappyGameManager.
   */
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
   * Returns the FlappyGameManager instance that belongs to a user with specified username.
   *
   * @param username the username of the user playing this game.
   * @return FlappyGameStatusFacade the FlappyGameStatusFacade instance of this user.
   */
  protected FlappyGameStatusFacade getGameStatus(String username) {
    FlappyGameStatusFacade flappyGameStatusFacade =
        (FlappyGameStatusFacade)
            GameStatusDaoImpl.getInstance(getActivity())
                .getGameStatus(username, GameEnum.FLAPPYFISH);
    if (flappyGameStatusFacade == null) {
      flappyGameStatusFacade = new FlappyGameStatusFacade(username);
      flappyGameStatusFacade.setLevelManager(new LevelManager());
      flappyGameStatusFacade.setObjectManager(new ObjectManager());
    }
    return flappyGameStatusFacade;
  }
}
