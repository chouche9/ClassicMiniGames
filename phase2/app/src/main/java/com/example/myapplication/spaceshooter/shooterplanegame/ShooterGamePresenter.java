package com.example.myapplication.spaceshooter.shooterplanegame;

import android.widget.Toast;

import com.example.myapplication.spaceshooter.shootergameover.ShooterGameOver;
import com.example.myapplication.spaceshooter.shootergameover.ShooterGameOverPresenter;

public class ShooterGamePresenter {
    private ShooterGameInterface shooterGameInterface;
    private ShooterPlaneGameLogic shooterPlaneGameLogic;

    ShooterGamePresenter(ShooterGameInterface shooterGameInterface,
                         ShooterPlaneGameLogic shooterPlaneGameLogic){
        this.shooterGameInterface = shooterGameInterface;
        this.shooterPlaneGameLogic = shooterPlaneGameLogic;
    }

    void HandleOnResume(){
        shooterPlaneGameLogic.handleOnResume();
        shooterGameInterface.startMusic();
    }
    void HandleOnPause(){
        shooterPlaneGameLogic.handleOnPause();
        if(shooterPlaneGameLogic.shouldMusicStop()){
            shooterGameInterface.stopMusic();
        }
        if (shooterPlaneGameLogic.isBonusOpen()){
            shooterGameInterface.dismissDialog();
        }
    }
    void activateBonusGame(){
        shooterPlaneGameLogic.handleActivateBonusGame();
        shooterGameInterface.openDialog();
    }
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
    void cancelBonus(){
        shooterPlaneGameLogic.handleCancelBonus();
    }
}
