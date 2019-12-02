package com.example.myapplication.spaceshooter.shootergamestart;

interface ShooterStartView {
  /** start the music */
  void startMusic();

  /** pause the music */
  void stopMusic();

  /** make resumeButton appear */
  void resumeAppear();

  /** make resumeButton disappear */
  void resumeGone();

  /** start gameOver intent */
  void startFinishPage();

  /** start shooter game view intent */
  void startGamePage();

  /** start shooter setting activity */
  void startSettingPage();
}
