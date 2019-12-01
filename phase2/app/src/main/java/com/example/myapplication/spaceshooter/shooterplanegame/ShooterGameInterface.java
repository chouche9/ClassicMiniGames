package com.example.myapplication.spaceshooter.shooterplanegame;

/**
 * The interface Shooter game interface.
 */
public interface ShooterGameInterface {
    /**
     * Start music.
     */
    void startMusic();

    /**
     * Stop music.
     */
    void stopMusic();

    /**
     * Dismiss dialog.
     */
    void dismissDialog();

    /**
     * Open dialog.
     */
    void openDialog();

    /**
     * Make bonus win toast.
     */
    void makeBonusWinToast();

    /**
     * Make bonus lose toast.
     */
    void makeBonusLoseToast();
}
