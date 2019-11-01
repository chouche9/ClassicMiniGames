package com.example.myapplication.GuessNum;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class GuessGameManager {
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();
    private SharedPreferences.Editor editor;
    static private GuessGameManager guessGameManager;

    private GuessGameManager(Activity activity, String name) {
        this.sharedPreferences = activity.getApplicationContext().getSharedPreferences(name ,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    static GuessGameManager getInstance(Activity activity){
        if (guessGameManager == null){
            guessGameManager = new GuessGameManager(activity, "Guess the Number");
        }
        return guessGameManager;
    }

    void saveUsers(GuessGameStat user){
        String json = gson.toJson(user);
        editor.putString(user.getName(), json);
        editor.apply();
    }

    public GuessGameStat getGameStat(String name) {
        String json = sharedPreferences.getString(name, null);
        GuessGameStat gameStat;
        gameStat = gson.fromJson(json, GuessGameStat.class);

        if (gameStat == null) {
            gameStat = new GuessGameStat(name);
        }
        return gameStat;
    }
}

