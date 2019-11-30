package com.example.myapplication.databaseconnector;

import com.example.myapplication.domain.GameStatus;

/** The DAO for handling game status. */
interface GameStatusDao {

  /**
   * Save the game status according to its game type.
   *
   * @param gameStatus the game status to be saved into the database.
   * @param type the type of the game.
   */
  void saveGameStatus(GameStatus gameStatus, GameEnum type);

  /**
   * Get the game status according to the the username and game type.
   *
   * @param username the username of the user.
   * @param type the type of the game.
   * @return the game status with specified game type which belongs to a user with username.
   */
  GameStatus getGameStatus(String username, GameEnum type);
}
