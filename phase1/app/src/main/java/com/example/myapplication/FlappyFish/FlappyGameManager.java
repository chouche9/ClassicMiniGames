package com.example.myapplication.FlappyFish;

import android.app.Activity;

import com.example.myapplication.GameManager;

/**
 * The game manager for this flappy fish game.
 */
class FlappyGameManager extends GameManager {

    /**
     * Name used globally to send/retrieve this FlappyGameManager to/from an intent.
     */
    static private FlappyGameManager gameManager;

    /**
     * Name indicating which game this game manager is responsible;
     */
    private static final String gameName = "flappy game";

    /**
     * Constructs this FlappyGameManager.
     * @param activity the activity that called this FlappyGameManager.
     * @param name the name of this game, gameName.
     */
    private FlappyGameManager(Activity activity, String name) {
        super(activity, name);
    }

    /**
     * Getter for this FlappyGameManager instance.
     *
     * @param activity the activity that called this FlappyGameManager.
     * @return HangmanGameManager the instance of this FlappyGameManager.
     */
    static FlappyGameManager getInstance(Activity activity) {
        if (gameManager == null) {
            gameManager = new FlappyGameManager(activity, gameName);
        }
        return gameManager;
    }

    /**
     * Returns the gameStatus instance that this FlappyGameManager is managing.
     *
     * @param username the username of the user playing this game.
     * @return FlappyGameStat the HangmanGameStat instance of this user.
     */
    public FlappyGameStatus getGameStatus(String username) {
        FlappyGameStatus flappyGameStatus = (FlappyGameStatus) super.getGameStatus(username);
        if(flappyGameStatus == null){
            flappyGameStatus = new FlappyGameStatus(username);
        }
        return flappyGameStatus;
    }
}