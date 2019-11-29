package com.example.myapplication.databaseconnector;

import com.example.myapplication.domain.GameStatus;

interface GameStatusDao {

  void saveGameStatus(GameStatus gameStatus, GameEnum type);

  GameStatus getGameStatus(String username, GameEnum type);
}
