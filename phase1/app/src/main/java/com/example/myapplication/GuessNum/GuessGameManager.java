package com.example.myapplication.GuessNum;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.GameManager;
import com.example.myapplication.GameStatus;

public class GuessGameManager extends GameManager {
    static private GuessGameManager guessGameManager;
    private static final String gameName = "guess game";


    private GuessGameManager(Activity activity, String name)  {
        super(activity, name);
    }

    static GuessGameManager getInstance(Activity activity){
        if (guessGameManager == null){
            guessGameManager = new GuessGameManager(activity, gameName);
        }
        return guessGameManager;
    }
    public GuessGameStat getGameStatus(String username) {
        GuessGameStat guessGameStat = (GuessGameStat) super.getGameStatus(username);
        if(guessGameStat == null){
            guessGameStat = new GuessGameStat(username);
        }
        return guessGameStat;
    }
}

