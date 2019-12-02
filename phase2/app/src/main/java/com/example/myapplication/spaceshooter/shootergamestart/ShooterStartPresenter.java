package com.example.myapplication.spaceshooter.shootergamestart;

import com.example.myapplication.spaceshooter.shootergamestatus.ShooterGameStatusFacade;

/** The type Shooter start presenter that connect ShooterStart (activity class). */
public class ShooterStartPresenter {
  private ShooterStartLogic shooterStartLogic;
  private ShooterStartView shooterStartView;

  /**
   * Instantiates a new Shooter start presenter.
   *
   * @param shooterStartLogic the shooter start logic
   * @param shooterStartView the shooter start view
   */
  ShooterStartPresenter(ShooterStartLogic shooterStartLogic, ShooterStartView shooterStartView) {
    this.shooterStartLogic = shooterStartLogic;
    this.shooterStartView = shooterStartView;
  }

  /** Check resume button appear. */
  void checkResumeAppear() {
    if (shooterStartLogic.ResumeBtnAppear()) {
      shooterStartView.resumeAppear();
    } else {
      shooterStartView.resumeGone();
    }
  }

  /** Pause music. */
  void pauseMusic() {
    if (shooterStartLogic.getMusicFinish()) {
      shooterStartView.stopMusic();
    }
  }

  private void setMusicFinishFalse() {
    shooterStartLogic.setMusicFinish();
  }

  /**
   * Get game status shooter game status facade.
   *
   * @return the shooter game status facade
   */
  ShooterGameStatusFacade getGameStatus() {
    return shooterStartLogic.getShooterGameStatus();
  }

  /** Start music. */
  void startMusic() {
    shooterStartView.startMusic();
  }

  /** Start new game. */
  void startNewGame() {
    shooterStartLogic.eraseGameStat();
    setMusicFinishFalse();
    shooterStartView.startSettingPage();
  }

  /** Resume game. */
  void resumeGame() {
    if (shooterStartLogic.checkAtLevel1Finish()) {
      setMusicFinishFalse();
      shooterStartView.startFinishPage();
    } else {
      setMusicFinishFalse();
      shooterStartView.startGamePage();
    }
  }
}
