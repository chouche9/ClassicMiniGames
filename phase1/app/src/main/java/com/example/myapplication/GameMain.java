package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.myapplication.FlappyFish.FlappyGameMenu;
import com.example.myapplication.GuessNum.GuessMain;
import com.example.myapplication.Hangman.HangmanMain;

import java.util.HashMap;

public class GameMain extends AppCompatActivity implements View.OnClickListener {
    /** Button for going to guess number game*/
    Button guessNum;
    /** Button for going to hangman game*/
    Button hangman;
    /** Button for going to flappy Fish game*/
    Button flappyFish;
    /** Button for logging out*/
    Button logOut;
    /** the username*/
    String user;
    /** method to create this activity*/
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
    /** method when player click a button, controlling which game he/she goes to*/
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
            case R.id.flappyFish:
                Intent intent2 = new Intent(this, FlappyGameMenu.class);
                intent2.putExtra("user", user);
                startActivity(intent2);
                break;
            case R.id.logout:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}