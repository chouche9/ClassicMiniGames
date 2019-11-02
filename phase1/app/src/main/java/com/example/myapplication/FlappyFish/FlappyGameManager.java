package com.example.myapplication.FlappyFish;

import android.app.Activity;

import com.example.myapplication.GameManager;

class FlappyGameManager extends GameManager {

    static private FlappyGameManager gameManager;
    private static final String gameName = "flappy game";

    private FlappyGameManager(Activity activity, String name) {
        super(activity, name);
    }
    static FlappyGameManager getInstance(Activity activity) {
        if (gameManager == null) {
            gameManager = new FlappyGameManager(activity, gameName);
        }
        return gameManager;
    }


    public FlappyGameStatus getGameStatus(String username) {
        FlappyGameStatus flappyGameStatus = (FlappyGameStatus) super.getGameStatus(username);
        if(flappyGameStatus == null){
            flappyGameStatus = new FlappyGameStatus(username);
        }
        return flappyGameStatus;
    }
}