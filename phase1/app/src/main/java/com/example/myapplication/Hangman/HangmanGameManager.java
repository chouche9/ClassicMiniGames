package com.example.myapplication.Hangman;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

class HangmanGameManager {

    private SharedPreferences pref;
    private Gson gson = new Gson();
    static private HangmanGameManager hangmanGameManager;

    private HangmanGameManager(Activity activity) {
        this.pref = activity.getApplicationContext().getSharedPreferences("Hangman", Context.MODE_PRIVATE);
    }

    static HangmanGameManager getInstance(Activity activity) {
        if (hangmanGameManager == null) {
            hangmanGameManager = new HangmanGameManager(activity);
        }
        return hangmanGameManager;
    }

    HangmanGameStat getGameStatus(String username) {
        String json = pref.getString(username, null);
        HangmanGameStat hangmanGameStat = gson.fromJson(json, HangmanGameStat.class);

        if (hangmanGameStat == null) {
            hangmanGameStat = new HangmanGameStat(username);
        }

        return hangmanGameStat;
    }

    void saveGame(HangmanGameStat userHangmanGameStat) {
        SharedPreferences.Editor editor = pref.edit();
        String json = gson.toJson(userHangmanGameStat);
        editor.putString(userHangmanGameStat.getUsername(), json);
        editor.apply();
    }
}
