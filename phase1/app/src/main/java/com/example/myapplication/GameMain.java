package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.myapplication.GuessNum.GuessMain;
import com.example.myapplication.Hangman.HangmanMain;

import java.util.HashMap;

public class GameMain extends AppCompatActivity implements View.OnClickListener {
    Button guessNum;
    Button hangman;
    Button flappyFish;
    Button logOut;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        guessNum = findViewById(R.id.guessNum);
        guessNum.setOnClickListener(this);
        hangman = findViewById(R.id.hangMan);
        hangman.setOnClickListener(this);
        flappyFish = findViewById(R.id.flappyFish);
        flappyFish.setOnClickListener(this);
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        logOut = findViewById(R.id.logout);
        logOut.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.guessNum:
                Intent intent = new Intent(this, GuessMain.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
            case R.id.hangMan:
                Intent intent1 = new Intent(this, HangmanMain.class);
                intent1.putExtra("user", user);
                startActivity(intent1);
                break;
            case R.id.logout:
                setResult(RESULT_OK);
                finish();
        }
    }
}