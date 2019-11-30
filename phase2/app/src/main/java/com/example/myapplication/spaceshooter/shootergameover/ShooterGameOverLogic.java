package com.example.myapplication.spaceshooter.shootergameover;

import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterCrossLevelManager;
import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterGameStatusFacade;

public class ShooterGameOverLogic {
    ShooterGameStatusFacade shooterGameStatus;
    ShooterCrossLevelManager shooterCrossLevelManager;
    int level;
    private boolean musicFinish;
    boolean gameSuccess;
    int point;
    boolean levelfinish;

    ShooterGameOverLogic(ShooterGameStatusFacade shooterGameStatus){
        this.shooterGameStatus = shooterGameStatus;
        shooterCrossLevelManager = shooterGameStatus.getShooterCrossLevelManager();
        level = shooterCrossLevelManager.getLevel();
        gameSuccess = shooterCrossLevelManager.isGameSuccess();
        levelfinish = shooterCrossLevelManager.isLevelFinish();
        point = shooterCrossLevelManager.getPoint();
        musicFinish = false;
    }
    boolean checkNextLevelAppear(){
        if (!gameSuccess){
            return false;
        }
        return level == 1 && levelfinish;
    }
    String getText(){
        String string;
        if(gameSuccess && level == 1){
            string = "Congratulation, you finish level 1 with " + point + " points, Continue??";
        }
        else if(!gameSuccess){
            string = "Sorry, you did not finish the game, your score is " + point;
        }
        else {
            string = "Congratulation, you finish game with " + point +"points!!!";
        }
        return string;
    }
    void setMusicFinish(boolean finish){
        musicFinish = finish;
    }
    boolean getMusicFinish(){
        return musicFinish;
    }
    void levelUpGamestate(){
        shooterGameStatus.resetGameStatus();
        shooterCrossLevelManager.updateLevel(2);
    }
    void eraseGameState(){
        shooterGameStatus.eraseGameStatus();
    }
}
