package com.example.myapplication.spaceshooter.shooterplanegame;

import android.app.Activity;
import android.content.Context;

import com.example.myapplication.spaceshooter.ShooterGameManager;
import com.example.myapplication.spaceshooter.ShooterGameStatus;

public class ShooterPlaneGameLogic {
ShooterGameStatus shooterGameStatus;
Context context;

    ShooterPlaneGameLogic(ShooterGameStatus shooterGameStatus, Context context){
        this.shooterGameStatus = shooterGameStatus;
        this.context = context;
    }
    boolean shouldMusicStop(){
        if (shooterGameStatus.levelFinish || !shooterGameStatus.gameSuccess){
            return false;
        }
        else {return true;}

    }
    void saveGameState(){
        ShooterGameManager shooterGameManager = ShooterGameManager.getInstance((Activity)context);
        shooterGameManager.saveGame(shooterGameStatus);
    }
    void addBonusPoint(int bonus){
        shooterGameStatus.point += bonus;
    }
}
