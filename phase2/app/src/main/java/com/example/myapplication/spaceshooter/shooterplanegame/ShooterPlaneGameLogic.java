package com.example.myapplication.spaceshooter.shooterplanegame;

import android.app.Activity;
import android.content.Context;

import com.example.myapplication.spaceshooter.ShooterGameManager;
import com.example.myapplication.spaceshooter.ShooterGameStatus;
import com.example.myapplication.spaceshooter.shootergameview.ShooterGameView;

class ShooterPlaneGameLogic {
private ShooterGameStatus shooterGameStatus;
private Context context;
private ShooterGameView shooterGameView;
private boolean bonusOpen;
private int start;

    ShooterPlaneGameLogic(ShooterGameStatus shooterGameStatus, Context context,
                          ShooterGameView shooterGameView){
        this.shooterGameStatus = shooterGameStatus;
        this.context = context;
        this.shooterGameView = shooterGameView;
        start = 1;
        bonusOpen = false;
    }
    boolean shouldMusicStop(){
        return !shooterGameStatus.levelFinish && shooterGameStatus.gameSuccess;

    }
    private void saveGameState(){
        ShooterGameManager shooterGameManager = ShooterGameManager.getInstance((Activity)context);
        shooterGameManager.saveGame(shooterGameStatus);
    }
    void addBonusPoint(int bonus){
        shooterGameStatus.point += bonus;
    }
    void handleOnResume(){
        shooterGameView.setActivityFinish(false);
        shooterGameView.startTimer();
        if(start<1){
            shooterGameView.invalidate();}
        start--;
    }
    void handleOnPause(){
        shooterGameView.setActivityFinish(false);
        saveGameState();
    }

    boolean isBonusOpen() {
        return bonusOpen;
    }

    private void setBonusOpen(boolean bonusOpen) {
        this.bonusOpen = bonusOpen;
    }
    void handleActivateBonusGame(){
        setBonusOpen(true);
        shooterGameView.setActivityFinish(true);
    }
    void handleCancelBonus(){
        setBonusOpen(false);
        shooterGameView.setActivityFinish(false);
        shooterGameView.startTimer();
        shooterGameView.invalidate();
    }
}
