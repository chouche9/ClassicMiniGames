package com.example.myapplication.spaceshooter.shootergameover;

import com.example.myapplication.spaceshooter.ShooterGameStatus;

public class ShooterGameOverLogic {
    ShooterGameStatus shooterGameStatus;
    int level;
    boolean gameSuccess;
    int point;
    boolean levelfinish;

    ShooterGameOverLogic(ShooterGameStatus shooterGameStatus){
        this.shooterGameStatus = shooterGameStatus;
        level = shooterGameStatus.level;
        gameSuccess = shooterGameStatus.gameSuccess;
        levelfinish = shooterGameStatus.levelFinish;
        point = shooterGameStatus.point;
    }
    boolean checkNextLevelAppear(){
        if (!gameSuccess){
            return false;
        }
        return level == 1 && levelfinish;
    }
    String setText(){
        String string;
        if(gameSuccess && level == 1){
            string = "Congratulation, you finish level 1 with " + point + " points, Continue??";
        }
        else if(!gameSuccess){
            string = "Sorry, you did not finish the game, your score is " + point;
        }
        else {
            string = "Congradulation, you finish game with " + point +"points!!!";
        }
        return string;
    }

    void levelUpGamestate(){
        shooterGameStatus.resetGameStatus();
        shooterGameStatus.updateLevel(2);
    }
    void eraseGameState(){
        shooterGameStatus.eraseGameStatus();
    }
}
