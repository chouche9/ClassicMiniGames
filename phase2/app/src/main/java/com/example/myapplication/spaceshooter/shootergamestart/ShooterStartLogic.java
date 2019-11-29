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
    private boolean musicFinish;
    ShooterStartLogic(Context context, String user){
        this.context = context;
        this.user = user;
        shooterGameManager = ShooterGameManager.getInstance((Activity)context);
        shooterGameStatus = shooterGameManager.getGameStatus(user);
        musicFinish = true;
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
        else return shooterGameStatus.level != 2 || !shooterGameStatus.levelFinish;
    }
    void eraseGameStat(){
        shooterGameStatus.eraseGameStatus();
    }
    void setMusicFinish(boolean musicFinish){
        this.musicFinish = musicFinish;
    }

    boolean checkAtLevel1Finish(){
        return shooterGameStatus.levelFinish;
    }
    ShooterGameStatus getShooterGameStatus(){
        return shooterGameStatus;
    }
    boolean getMusicFinish(){
        return musicFinish;
    }

}
