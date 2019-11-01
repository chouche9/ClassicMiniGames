package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.GuessNum.GuessGameStat;
import com.example.myapplication.Hangman.HangmanGameStat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GameManager {
    /** the sharedPreferences that to store game info*/
    SharedPreferences sharedPreferences;
    /** editor of this sharedPreferences*/
    SharedPreferences.Editor editor;

    /**
     *initiate GameManager
     * @param activity: the activity that call GameManger;
     * @param name: the file name which store your information;
     */
    public GameManager(Activity activity, String name) {
        this.sharedPreferences = activity.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
        editor.apply();
    };

    /**
     * get the GameStatus for a particular game for a user
     * @param username: name of this user
     * @return the GameStatus
     */
    public GameStatus getGameStatus(String username) {
        GameStatDeserializer gameStatDeserializer = new GameStatDeserializer("type");
        loadSub(gameStatDeserializer);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(GameStatus.class, gameStatDeserializer)
                .create();
        String json = sharedPreferences.getString(username, null);
        return gson.fromJson(json, GameStatus.class);
    }

    /**
     * save the GameStatus for the particular user.
     * @param gameStatus the new GameStatus want to get saved.
     */
    public void saveGame(GameStatus gameStatus) {
        Gson gson = new Gson();
        String json = gson.toJson(gameStatus);
        editor.putString(gameStatus.getName(), json);
        editor.apply();
    }

    /**
     * add GameTypes into gameStatDerializer
     * @param gameStatDeserializer the Derserializer used transfer json string into custom object
     */
    private void loadSub(GameStatDeserializer gameStatDeserializer){
        gameStatDeserializer.registerGameType("GuessGameStat", GuessGameStat.class);
        gameStatDeserializer.registerGameType("HangmanGameStat", HangmanGameStat.class);

    }
}
