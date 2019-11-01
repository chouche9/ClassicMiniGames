package com.example.myapplication;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

public class Manager {

    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();
    static private Manager manager;
    private SharedPreferences.Editor editor;

    Manager(Activity activity, String name) {
        this.sharedPreferences = activity.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
        editor.apply();
    };

    static Manager getInstance(Activity activity, String name){
        if (manager == null) {
            manager = new Manager(activity, name);
        }

        return manager;
    };

    GameStatus getGameStatus(String username) {
        String json = sharedPreferences.getString(username, null);
        GameStatus gameStatus = gson.fromJson(json, GameStatus.class);

        if (gameStatus == null) {
            gameStatus = new GameStatus();
        }

        return gameStatus;
    }

    void saveGame(GameStatus gameStatus) {
        String json = gson.toJson(gameStatus);
        editor.putString(gameStatus.getUsername(), json);
        editor.apply();
    };

}
