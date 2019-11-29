package com.example.myapplication.spaceshooter.shootergamestart;

import android.app.Activity;
import android.content.Context;

import com.example.myapplication.spaceshooter.ShooterGameManager;
import com.example.myapplication.spaceshooter.ShooterGameStatus;

public class ShooterStartLogic {
    Context context;
    ShooterGameManager shooterGameManager;
    private ShooterGameStatus shooterGameStatus;
    String user;
    ShooterStartLogic(Context context, String user){
        this.context = context;
        this.user = user;
        shooterGameManager = ShooterGameManager.getInstance((Activity)context);
        shooterGameStatus = shooterGameManager.getGameStatus(user);
    }
    boolean ResumeBtnAppear(){
        if (!shooterGameStatus.gameSuccess){
            return false;
        }
        if (shooterGameStatus.millsecondLeft == ShooterGameStatus.initaltime){
            return false;
        }
        else if(shooterGameStatus.level == 1 && shooterGameStatus.levelFinish){
            return true;
        }
        else if(shooterGameStatus.level == 2 && shooterGameStatus.levelFinish){
            return false;
        }
        return true;
    }
    void eraseGameStat(){
        shooterGameStatus.eraseGameStatus();
    }
    boolean checkAtLevel1Finish(){
        return shooterGameStatus.levelFinish;
    }
    ShooterGameStatus getShooterGameStatus(){
        return shooterGameStatus;
    }

}
