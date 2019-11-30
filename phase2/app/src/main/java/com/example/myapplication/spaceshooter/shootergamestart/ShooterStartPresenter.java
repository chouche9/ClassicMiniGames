package com.example.myapplication.spaceshooter.shootergamestart;

import com.example.myapplication.domain.GameStatus;

public class ShooterStartPresenter {
    private ShooterStartLogic shooterStartLogic;
    private ShooterStartView shooterStartView;
    ShooterStartPresenter(ShooterStartLogic shooterStartLogic, ShooterStartView shooterStartView){
        this.shooterStartLogic = shooterStartLogic;
        this.shooterStartView = shooterStartView;
    }
    void checkResumeAppear(){
        if(shooterStartLogic.ResumeBtnAppear()){
            shooterStartView.resumeAppear();
        }
        else {
            shooterStartView.resumeGone();
        }
    }
    void pauseMusic(){
        if (shooterStartLogic.getMusicFinish()){
            shooterStartView.stopMusic();
        }
    }
    private void setMusicFinishFalse(){
        shooterStartLogic.setMusicFinish();
    }
    GameStatus getGameStatus(){
        return shooterStartLogic.getShooterGameStatus();
    }
    void startMusic(){
        shooterStartView.startMusic();
    }

    void startNewGame(){
        shooterStartLogic.eraseGameStat();
        setMusicFinishFalse();
        shooterStartView.startSettingPage();
    }
    void resumeGame(){
        if (shooterStartLogic.checkAtLevel1Finish()){
            setMusicFinishFalse();
            shooterStartView.startFinishPage();
        }
        else {
            setMusicFinishFalse();
            shooterStartView.startGamePage();
        }
    }
}
