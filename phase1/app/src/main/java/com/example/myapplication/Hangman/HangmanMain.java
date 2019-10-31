package com.example.myapplication.Hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class HangmanMain extends AppCompatActivity implements View.OnClickListener {

    Intent intent1;
    private Button btnResumeGame;
    private HangmanGameStat hangmanGameStat;

    public static String getGamestatusMsg() {
        return "hangmanGameStat";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_main);

        Button btnStartGame = findViewById(R.id.btnNewGame);
        btnResumeGame = findViewById(R.id.btnResumeGame);
        Button btnSettings = findViewById(R.id.btnSettings);

        HangmanGameManager hangmanGameManager = HangmanGameManager.getInstance(this);
        Intent intent = getIntent();

        // from log in page
        String name = intent.getStringExtra("name");
        hangmanGameStat = hangmanGameManager.getGameStatus(name);

        // from playAgain
        if (intent.getParcelableExtra(getGamestatusMsg()) != null) {
            hangmanGameStat = intent.getParcelableExtra(getGamestatusMsg());
        }

        boolean clearGame = intent.getBooleanExtra("clear game", false);

        if (clearGame) {
            hangmanGameManager.saveGame(hangmanGameStat);
            btnResumeGame.setVisibility(View.GONE);
        } else {
            btnResumeGame.setVisibility(View.GONE);

            if (hangmanGameStat.played) {
                btnResumeGame.setVisibility(View.VISIBLE);
            }
        }

        btnStartGame.setOnClickListener(this);
        btnResumeGame.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HangmanGameManager hangmanGameManager = HangmanGameManager.getInstance(this);
        hangmanGameStat = hangmanGameManager.getGameStatus(hangmanGameStat.getUsername());

        if (hangmanGameStat.played) {
            btnResumeGame.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNewGame:
                String originalGender = hangmanGameStat.getGender();
                hangmanGameStat.resetGameStatus();
                if (originalGender == null) {
                    originalGender = "MALE"; // if user never played game, set "MALE" by default
                }
                hangmanGameStat.setGender(originalGender);
                intent1 = new Intent(getApplicationContext(), HangmanGame.class);
                break;
            case R.id.btnResumeGame:
                intent1 = new Intent(getApplicationContext(), HangmanGame.class);
                break;
            case R.id.btnSettings:
                hangmanGameStat.resetGameStatus();
                intent1 = new Intent(getApplicationContext(), HangmanSetting.class);
                break;
        }
        intent1.putExtra(getGamestatusMsg(), hangmanGameStat);
        startActivity(intent1);
    }

}
