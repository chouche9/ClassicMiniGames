package com.example.myapplication.spaceshooter.shooterplanegame;

import android.widget.Toast;

import com.example.myapplication.spaceshooter.shootergameover.ShooterGameOver;
import com.example.myapplication.spaceshooter.shootergameover.ShooterGameOverPresenter;

/**
 * The type Shooter game presenter.
 */
class ShooterGamePresenter {
    /**
     * The Shooter game interface.
     */
    private ShooterGameInterface shooterGameInterface;
    /**
     * The Shooter plane game logic.
     */
    private ShooterPlaneGameLogic shooterPlaneGameLogic;

    /**
     * Instantiates a new Shooter game presenter.
     *
     * @param shooterGameInterface  the shooter game interface
     * @param shooterPlaneGameLogic the shooter plane game logic
     */
    ShooterGamePresenter(ShooterGameInterface shooterGameInterface,
                         ShooterPlaneGameLogic shooterPlaneGameLogic){
        this.shooterGameInterface = shooterGameInterface;
        this.shooterPlaneGameLogic = shooterPlaneGameLogic;
    }

    /**
     * Handle on resume.
     */
    void HandleOnResume(){
        shooterPlaneGameLogic.handleOnResume();
        shooterGameInterface.startMusic();
    }

    /**
     * Handle on pause.
     */
    void HandleOnPause(){
        shooterPlaneGameLogic.handleOnPause();
        if(shooterPlaneGameLogic.shouldMusicStop()){
            shooterGameInterface.stopMusic();
        }
        if (shooterPlaneGameLogic.isBonusOpen()){
            shooterGameInterface.dismissDialog();
        }
    }

    /**
     * Activate bonus game.
     */
    void activateBonusGame(){
        shooterPlaneGameLogic.handleActivateBonusGame();
        shooterGameInterface.openDialog();
    }

    /**
     * Handle bonus game result.
     *
     * @param isWon     the is won
     * @param bonusSore the bonus sore
     */
    void handleBonusGameResult(boolean isWon, int bonusSore){
        if (isWon) {
            shooterPlaneGameLogic.addBonusPoint(bonusSore);
            shooterGameInterface.makeBonusWinToast();
    }
        else {
        shooterGameInterface.makeBonusLoseToast();
        }
        shooterGameInterface.dismissDialog();
    }

    /**
     * Cancel bonus.
     */
    void cancelBonus(){
        shooterPlaneGameLogic.handleCancelBonus();
    }
}
