package com.example.myapplication.Hangman;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.GameManager;
import com.google.gson.Gson;

/**
 * Game manager for this Hangman game.
 */
class HangmanGameManager extends GameManager {

    /**
     * Name used globally to send/retrieve this HangmanGameManager to/from an intent.
     */
    private static HangmanGameManager hangmanGameManager;

    /**
     * Name indicating which game this game manager is responsible for.
     */
    private static final String gameName = "hangman game";

    /**
     * Constructs this HangmanGameManager.
     *
     * @param activity the activity that called this HangmanGameManager.
     * @param name the name of this game, gameName.
     *
     */
    private HangmanGameManager(Activity activity, String name) {
        super(activity, name);
    }

    /**
     * Getter for this HangmanGameManager instance.
     *
     * @param activity the activity that called this HangmanGameManager.
     * @return HangmanGameManager the instance of this HangmanGameManager.
     */
    static HangmanGameManager getInstance(Activity activity) {
        if (hangmanGameManager == null) {
            hangmanGameManager = new HangmanGameManager(activity, gameName);
        }
        return hangmanGameManager;
    }

    /**
     * Returns the gameStatus instance that this HangmanGameManager is managing.
     *
     * @param username the username of the user playing this game.
     * @return HangmanGameStat the HangmanGameStat instance of this user.
     */
    public HangmanGameStat getGameStatus(String username) {
        HangmanGameStat hangmanGameStat = (HangmanGameStat) super.getGameStatus(username);

        if (hangmanGameStat == null) {
            hangmanGameStat = new HangmanGameStat(username);
        }
        return hangmanGameStat;
    }
}
