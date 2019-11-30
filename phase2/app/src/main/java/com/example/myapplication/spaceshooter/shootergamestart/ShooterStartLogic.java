package com.example.myapplication.spaceshooter.shootergamestart;

import android.app.Activity;
import android.content.Context;

import com.example.myapplication.domain.GameStatus;
import com.example.myapplication.spaceshooter.ShooterGameManager;
import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterGameLevelManager;
import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterGameStatusFacade;

class ShooterStartLogic {
    private ShooterGameStatusFacade shooterGameStatus;

    private boolean musicFinish;
    ShooterStartLogic(Context context, String user){
        ShooterGameManager shooterGameManager = ShooterGameManager.getInstance((Activity) context);
        GameStatus gameStatus = shooterGameManager.getGameStatus(user);
        shooterGameStatus = shooterGameManager.getGameStatus(user);
        musicFinish = true;
    }
    boolean ResumeBtnAppear(){
        if (!shooterGameStatus.getShooterCrossLevelManager().isGameSuccess()){
            return false;
        }
        if (shooterGameStatus.getShooterGameLevelManager().getMillsecondLeft() ==
                ShooterGameLevelManager.initaltime){
            return false;
        }
        else if(shooterGameStatus.getShooterCrossLevelManager().getLevel() == 1 &&
                shooterGameStatus.getShooterCrossLevelManager().isLevelFinish()){
            return true;
        }
        else return shooterGameStatus.getShooterCrossLevelManager().getLevel() != 2 ||
                    !shooterGameStatus.getShooterCrossLevelManager().isLevelFinish();
    }
    void eraseGameStat(){
        shooterGameStatus.eraseGameStatus();
    }
    void setMusicFinish(){
        this.musicFinish = false;
    }

    boolean checkAtLevel1Finish(){
        return shooterGameStatus.getShooterCrossLevelManager().isLevelFinish();
    }
    ShooterGameStatusFacade getShooterGameStatus(){
        return shooterGameStatus;
    }
    boolean getMusicFinish(){
        return musicFinish;
    }

}
