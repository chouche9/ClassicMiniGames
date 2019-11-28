package com.example.myapplication.databaseconnector;

import com.example.myapplication.Domain.GameStatus;

interface GameStatusDao {

  void saveGameStatus(GameStatus gameStatus, GameEnum type);

  GameStatus getGameStatus(String username, GameEnum type);
}
