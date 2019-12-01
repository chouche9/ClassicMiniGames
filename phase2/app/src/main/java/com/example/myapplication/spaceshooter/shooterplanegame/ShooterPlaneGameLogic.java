package com.example.myapplication.spaceshooter.shooterplanegame;

import android.app.Activity;
import android.content.Context;

import com.example.myapplication.spaceshooter.ShooterGameManager;
import com.example.myapplication.spaceshooter.shootergamestatus.ShooterGameStatusFacade;
import com.example.myapplication.spaceshooter.shootergameview.ShooterGameView;

/**
 * The type Shooter plane game logic.
 */
class ShooterPlaneGameLogic {
    /**
     * The Shooter game status.
     */
    private ShooterGameStatusFacade shooterGameStatus;
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Shooter game view.
     */
    private ShooterGameView shooterGameView;
    /**
     * The Bonus is open.
     */
    private boolean bonusOpen;
    /**
     * The start indicate how many times on resume get called.
     */
    private int start;

    /**
     * Instantiates a new Shooter plane game logic.
     *
     * @param shooterGameStatus the shooter game status
     * @param context           the context
     * @param shooterGameView   the shooter game view
     */
    ShooterPlaneGameLogic(ShooterGameStatusFacade shooterGameStatus, Context context,
                          ShooterGameView shooterGameView){
        this.shooterGameStatus = shooterGameStatus;
        this.context = context;
        this.shooterGameView = shooterGameView;
        start = 1;
        bonusOpen = false;
    }

    /**
     * Should music stop boolean.
     * indicate whether music should stop
     * @return the boolean
     */
    boolean shouldMusicStop(){
        return !shooterGameStatus.getShooterCrossLevelManager().isLevelFinish() &&
                shooterGameStatus.getShooterCrossLevelManager().isGameSuccess();

    }

    /**
     *  save game status
     */
    private void saveGameState(){
        ShooterGameManager shooterGameManager = ShooterGameManager.getInstance((Activity)context);
        shooterGameManager.saveGame(shooterGameStatus);
    }

    /**
     * Add bonus point.
     *
     * @param bonus the bonus
     */
    void addBonusPoint(int bonus){
        shooterGameStatus.addPoint(bonus);
    }

    /**
     * Handle on resume.
     */
    void handleOnResume(){
        shooterGameView.setActivityFinish(false);
        shooterGameView.startTimer();
        if(start<1){
            shooterGameView.invalidate();}
        start--;
    }

    /**
     * Handle on pause.
     */
    void handleOnPause(){
        shooterGameView.setActivityFinish(true);
        saveGameState();
    }

    /**
     * Is bonus open boolean.
     *
     * @return the boolean
     */
    boolean isBonusOpen() {
        return bonusOpen;
    }

    /**
     * set if bnous is open
     * @param bonusOpen bonus open indicator
     */
    private void setBonusOpen(boolean bonusOpen) {
        this.bonusOpen = bonusOpen;
    }

    /**
     * Handle activate bonus game.
     */
    void handleActivateBonusGame(){
        setBonusOpen(true);
        shooterGameView.setActivityFinish(true);
    }

    /**
     * Handle cancel bonus.
     */
    void handleCancelBonus(){
        setBonusOpen(false);
        shooterGameView.setActivityFinish(false);
        shooterGameView.startTimer();
        shooterGameView.invalidate();
    }
}
