package com.example.myapplication.spaceshooter.shootergameover;

import android.content.Intent;
import android.view.View;

import com.example.myapplication.spaceshooter.shooterplanegame.ShooterGame;

/** The type Shooter game over presenter that connect ShooterGameOverLogic and ShooterGameOver. */
public class ShooterGameOverPresenter {
  /** the shooterGameOverLogic instance */
  private ShooterGameOverLogic shooterGameOverLogic;
  /** the view that connect with this class */
  private ShooterGameOverView shooterGameOverView;

  /**
   * Instantiates a new Shooter game over presenter.
   *
   * @param shooterGameOverLogic the shooter game over logic
   * @param shooterGameOverView the shooter game over view
   */
  ShooterGameOverPresenter(
      ShooterGameOverLogic shooterGameOverLogic, ShooterGameOverView shooterGameOverView) {
    this.shooterGameOverLogic = shooterGameOverLogic;
    this.shooterGameOverView = shooterGameOverView;
  }

  /** Check should next level appear. */
  void checkNextLevelAppear() {
    if (shooterGameOverLogic.checkNextLevelAppear()) {
      shooterGameOverView.nextLevelAppear();
    } else {
      shooterGameOverView.nextLevelGone();
    }
  }

  /** Check should music start. */
  void checkMusicStart() {
    if (shooterGameOverLogic.getMusicFinish()) {
      shooterGameOverView.startMusic();
    }
    shooterGameOverLogic.setMusicFinish(true);
  }

  /** .transfer the message get from logic, pass in to shooterGameOver view. */
  void setUpGameMessage() {
    shooterGameOverView.setGameText(shooterGameOverLogic.getText());
  }

  /** Check should music stop. */
  void checkMusicStop() {
    if (shooterGameOverLogic.getMusicFinish()) {
      shooterGameOverView.stopMusic();
    }
  }

  /**
   * Handle next level case, change gameStatus in logic and start intent in ShooterGameOver activity
   * class .
   */
  void handleNextLevel() {
    shooterGameOverLogic.levelUpGamestate();
    shooterGameOverView.startNewLevel();
    shooterGameOverLogic.setMusicFinish(false);
  }

  /** Handle back to main case. */
  void handleBackToMain() {
    shooterGameOverView.backToMenu();
    checkMusicStop();
  }
}
