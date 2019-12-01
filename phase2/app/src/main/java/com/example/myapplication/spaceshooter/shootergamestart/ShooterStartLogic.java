package com.example.myapplication.spaceshooter.shootergamestart;

import android.app.Activity;
import android.content.Context;

import com.example.myapplication.domain.GameStatus;
import com.example.myapplication.spaceshooter.ShooterGameManager;
import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterGameLevelManager;
import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterGameStatusFacade;

/**
 * The model Shooter start logic.
 */
class ShooterStartLogic {
    /**
     * the shooterGameStatus class that store game status
     */
    private ShooterGameStatusFacade shooterGameStatus;
    /**
     * boolean value that indicate if the music is finished;
     */
    private boolean musicFinish;

    /**
     * Instantiates a new Shooter start logic.
     *
     * @param context the context
     * @param user    the user
     */
    ShooterStartLogic(Context context, String user){
        ShooterGameManager shooterGameManager = ShooterGameManager.getInstance((Activity) context);
        GameStatus gameStatus = shooterGameManager.getGameStatus(user);
        shooterGameStatus = shooterGameManager.getGameStatus(user);
        musicFinish = true;
    }

    /**
     * check whether the resumeButton should appear
     *
     * @return the boolean
     */
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

    /**
     * Erase game stat.
     */
    void eraseGameStat(){
        shooterGameStatus.eraseGameStatus();
    }

    /**
     * Set music finish.
     */
    void setMusicFinish(){
        this.musicFinish = false;
    }

    /**
     * Check if the on resume game is at level 1 finish .
     *
     * @return the boolean that is if it's at level 1 finish or not
     */
    boolean checkAtLevel1Finish(){
        return shooterGameStatus.getShooterCrossLevelManager().isLevelFinish();
    }

    /**
     * Get shooter game status shooter game status facade.
     *
     * @return the shooter game status facade
     */
    ShooterGameStatusFacade getShooterGameStatus(){
        return shooterGameStatus;
    }

    /**
     * Get music finish boolean.
     *
     * @return the boolean
     */
    boolean getMusicFinish(){
        return musicFinish;
    }

}
