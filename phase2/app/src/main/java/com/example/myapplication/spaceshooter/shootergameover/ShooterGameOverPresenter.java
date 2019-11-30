package com.example.myapplication.spaceshooter.shootergameover;

import android.content.Intent;
import android.view.View;

import com.example.myapplication.spaceshooter.shooterplanegame.ShooterGame;

public class ShooterGameOverPresenter {
    private ShooterGameOverLogic shooterGameOverLogic;
    private ShooterGameOverView shooterGameOverView;
    ShooterGameOverPresenter(ShooterGameOverLogic shooterGameOverLogic,
                             ShooterGameOverView shooterGameOverView){
        this.shooterGameOverLogic = shooterGameOverLogic;
        this.shooterGameOverView = shooterGameOverView;
    }
    void checkNextLevelAppear(){
        if (shooterGameOverLogic.checkNextLevelAppear()){
            shooterGameOverView.nextLevelAppear();
        }
        else {
            shooterGameOverView.nextLevelGone();
        }
    }
    void checkMusicStart(){
        if (shooterGameOverLogic.getMusicFinish()){
            shooterGameOverView.startMusic();
        }
        shooterGameOverLogic.setMusicFinish(true);
    }
    void setUpGameMessage(){
        shooterGameOverView.setGameText(shooterGameOverLogic.getText());
    }
    void checkMusicStop(){
        if(shooterGameOverLogic.getMusicFinish()){
            shooterGameOverView.stopMusic();
        }
    }
    void handleNextLevel(){
        shooterGameOverLogic.levelUpGamestate();
        shooterGameOverView.startNewLevel();
        shooterGameOverLogic.setMusicFinish(false);
    }
    void handleBackToMain(){
        shooterGameOverView.backToMenu();
        checkMusicStop();
    }
}
