package com.example.myapplication.spaceshooter.shootergameover;

interface ShooterGameOverView {
    void nextLevelAppear();
    void nextLevelGone();
    void startMusic();
    void stopMusic();
    void setGameText(String message);
    void startNewLevel();
    void backToMenu();
}
