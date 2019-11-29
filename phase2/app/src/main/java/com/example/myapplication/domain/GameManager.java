package com.example.myapplication.domain;

/** A game manager. */
public abstract class GameManager {

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
  public abstract void saveGame(GameStatus gameStatus);

}
