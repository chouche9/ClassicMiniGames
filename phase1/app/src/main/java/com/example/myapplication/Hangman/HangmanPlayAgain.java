package com.example.myapplication.Hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HangmanPlayAgain extends AppCompatActivity {

    // Instatiate Variables
    private HangmanGameStat hangmanGameStat;
    private String originalGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_play_again);
    }
}
