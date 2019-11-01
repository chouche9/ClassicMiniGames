package com.example.myapplication.Hangman;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.GameManager;
import com.google.gson.Gson;

class HangmanGameManager extends GameManager {

    static private HangmanGameManager hangmanGameManager;
    private static final String gameName = "hangman game";

    private HangmanGameManager(Activity activity, String name) {
        super(activity, name);
    }

    static HangmanGameManager getInstance(Activity activity) {
        if (hangmanGameManager == null) {
            hangmanGameManager = new HangmanGameManager(activity, gameName);
        }
        return hangmanGameManager;
    }

    public HangmanGameStat getGameStatus(String username) {
        HangmanGameStat hangmanGameStat = (HangmanGameStat) super.getGameStatus(username);

        if (hangmanGameStat == null) {
            hangmanGameStat = new HangmanGameStat(username);
        }
        return hangmanGameStat;
    }
}
