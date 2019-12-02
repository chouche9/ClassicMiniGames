package com.example.myapplication.spaceshooter.shootergameover;

/** The interface Shooter game over view. */
interface ShooterGameOverView {
  /** set the next level button appear */
  void nextLevelAppear();

  /** set the next level button disappear */
  void nextLevelGone();

  /** start background music */
  void startMusic();

  /** stop background music */
  void stopMusic();

  /**
   * set gametext message
   *
   * @param message message that want to display
   */
  void setGameText(String message);

  /** activate start the next level intent */
  void startNewLevel();

  /** activate go back to game menu intent */
  void backToMenu();
}
