package com.example.myapplication.Hangman;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

class HangmanGameManager {

    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();
    static private HangmanGameManager hangmanGameManager;
    private SharedPreferences.Editor editor;

    private HangmanGameManager(Activity activity, String name) {
        this.sharedPreferences = activity.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
        editor.apply();
    }

    static HangmanGameManager getInstance(Activity activity, String name) {
        if (hangmanGameManager == null) {
            hangmanGameManager = new HangmanGameManager(activity, name);
        }
        return hangmanGameManager;
    }

    HangmanGameStat getGameStatus(String username) {
        String json = sharedPreferences.getString(username, null);
        HangmanGameStat hangmanGameStat = gson.fromJson(json, HangmanGameStat.class);

        if (hangmanGameStat == null) {
            hangmanGameStat = new HangmanGameStat(username);
        }

        return hangmanGameStat;
    }

    void saveGame(HangmanGameStat userHangmanGameStat) {
        String json = gson.toJson(userHangmanGameStat);
        editor.putString(userHangmanGameStat.getUsername(), json);
        editor.apply();
    }
}
