package com.example.myapplication.hangman;

import android.app.Activity;

import com.example.myapplication.gameenum.GameEnum;
import com.example.myapplication.domain.GameManager;
import com.example.myapplication.databaseconnector.GameStatusDaoImpl;

/** The game manager for this Hangman game. */
class HangmanGameManager extends GameManager {

  /** The singleton HangmanGameManager. */
  private static HangmanGameManager hangmanGameManager;

  /** Constructs a HangmanGameManager. */
  private HangmanGameManager(Activity activity) {
    super(activity);
  }

  /**
   * Getter for this HangmanGameManager instance.
   *
   * @return HangmanGameManager the instance of this HangmanGameManager.
   */
  static HangmanGameManager getInstance(Activity activity) {
    if (hangmanGameManager == null) {
      hangmanGameManager = new HangmanGameManager(activity);
    }
    return hangmanGameManager;
  }

  /**
   * Returns the HangmanGameManager instance that belongs to a user with specified username.
   *
   * @param username the username of the user playing this game.
   * @return HangmanGameStatus the HangmanGameStatus instance of this user.
   */
  protected HangmanGameStatus getGameStatus(String username) {
    HangmanGameStatus hangmanGameStat =
            (HangmanGameStatus)
                    GameStatusDaoImpl.getInstance(getActivity()).getGameStatus(username, GameEnum.HANGMAN);

    if (hangmanGameStat == null) {
      hangmanGameStat = new HangmanGameStatus(username);
    }

    return hangmanGameStat;
  }
}
