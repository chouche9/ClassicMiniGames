package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.GuessNum.GuessGameStat;
import com.example.myapplication.Hangman.HangmanGameStat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GameManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public GameManager(Activity activity, String name) {
        this.sharedPreferences = activity.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
        editor.apply();
    };


    public GameStatus getGameStatus(String username) {
        GameStatDeserializer gameStatDeserializer = new GameStatDeserializer("type");
        loadSub(gameStatDeserializer);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(GameStatus.class, gameStatDeserializer)
                .create();
        String json = sharedPreferences.getString(username, null);
        return gson.fromJson(json, GameStatus.class);
    }

    public void saveGame(GameStatus gameStatus) {
        Gson gson = new Gson();
        String json = gson.toJson(gameStatus);
        editor.putString(gameStatus.getName(), json);
        editor.apply();
    }
    private void loadSub(GameStatDeserializer gameStatDeserializer){
        gameStatDeserializer.registerGameType("GuessGameStat", GuessGameStat.class);

    }
}
