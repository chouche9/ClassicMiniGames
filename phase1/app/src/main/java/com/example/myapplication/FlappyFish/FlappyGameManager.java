package com.example.myapplication.FlappyFish;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

class FlappyGameManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();
    static private FlappyGameManager gameManager;

    private FlappyGameManager(Activity activity, String name) {
        this.sharedPreferences = activity.getApplicationContext().getSharedPreferences(name ,
                Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
        editor.apply(); // do we need this ?
    }

    static FlappyGameManager getInstance(Activity activity) {
        if (gameManager == null) {
            gameManager = new FlappyGameManager(activity, "Flappy");
        }
        return gameManager;
    }


    FlappyGameStatus getGameStatus(String name) {
        String json = sharedPreferences.getString(name, null);
        FlappyGameStatus gameStatus = gson.fromJson(json, FlappyGameStatus.class);

        if(gameStatus == null) {
            gameStatus = new FlappyGameStatus(name);
        }
        return gameStatus;
    }

    void saveGame(FlappyGameStatus userStatus) {
        String json = gson.toJson(userStatus);
        editor.putString(userStatus.getName(), json);
        editor.apply();
    }
}