package com.example.myapplication.spaceshooter.shootergameover;

import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterCrossLevelManager;
import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterGameStatusFacade;

/**
 * The type Shooter game over logic.
 */
class ShooterGameOverLogic {
    /**
     * The Shooter game status.
     */
    private ShooterGameStatusFacade shooterGameStatus;
    /**
     * The Shooter cross level manager that manager cross level statistic.
     */
    private ShooterCrossLevelManager shooterCrossLevelManager;
    /**
     * The Level of game.
     */
    private int level;
    /**
     * the music finish boolean
     */
    private boolean musicFinish;
    /**
     * The Game success that indicate whether the game succeeds.
     */
    private boolean gameSuccess;
    /**
     * The Point of the shooter game.
     */
    private int point;
    /**
     * The Levelfinish boolean that indicate if this level is finish.
     */
    private boolean levelfinish;

    /**
     * Instantiates a new Shooter game over logic.
     *
     * @param shooterGameStatus the shooter game status
     */
    ShooterGameOverLogic(ShooterGameStatusFacade shooterGameStatus){
        this.shooterGameStatus = shooterGameStatus;
        shooterCrossLevelManager = shooterGameStatus.getShooterCrossLevelManager();
        level = shooterCrossLevelManager.getLevel();
        gameSuccess = shooterCrossLevelManager.isGameSuccess();
        levelfinish = shooterCrossLevelManager.isLevelFinish();
        point = shooterCrossLevelManager.getPoint();
        musicFinish = false;
    }

    /**
     * Check if next level button should appear boolean.
     *
     * @return the boolean that indicate nextLevelButton appearance
     */
    boolean checkNextLevelAppear(){
        if (!gameSuccess){
            return false;
        }
        return level == 1 && levelfinish;
    }

    /**
     * decide the game text that want to display on activity
     *
     * @return the string that should display
     */
    String getText(){
        String string;
        if(gameSuccess && level == 1){
            string = "Congratulation, you finish level 1 with " + point + " points, Continue??";
        }
        else if(!gameSuccess){
            string = "Sorry, you did not finish the game, your score is " + point;
        }
        else {
            string = "Congratulation, you finish game with " + point +" points!!!";
        }
        return string;
    }

    /**
     * Set music finish.
     *
     * @param finish the finish
     */
    void setMusicFinish(boolean finish){
        musicFinish = finish;
    }

    /**
     * Get music finish boolean.
     *
     * @return the boolean
     */
    boolean getMusicFinish(){
        return musicFinish;
    }

    /**
     * Level up gamestate.
     */
    void levelUpGamestate(){
        shooterGameStatus.resetGameStatus();
        shooterCrossLevelManager.updateLevel(2);
    }

    /**
     * Erase game state.
     */
    void eraseGameState(){
        shooterGameStatus.eraseGameStatus();
    }
}
