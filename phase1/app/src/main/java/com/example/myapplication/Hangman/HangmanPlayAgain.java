package com.example.myapplication.Hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HangmanPlayAgain extends AppCompatActivity {

    // Instatiate Variables
    private HangmanGameStat hangmanGameStat;
    private String originalGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_play_again);

        Intent intent = getIntent();
        String score = intent.getStringExtra(HangmanGame.getScoreMessage());
        String message = intent.getStringExtra(HangmanGame.getMessage());
        hangmanGameStat = intent.getParcelableExtra(HangmanMain.getGamestatusMsg());
        assert hangmanGameStat != null;
        originalGender = hangmanGameStat.getGender();

        TextView txtScore = findViewById(R.id.txtScore);
        txtScore.setText(score);

        TextView txtMessage = findViewById(R.id.txtMessage);
        txtMessage.setText(message);

        Button playAgain = findViewById(R.id.btnPlayAgain);
        Button mainMenu = findViewById(R.id.btnMainMenu);

        hangmanGameStat.resetGameStatus();

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HangmanGame.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                hangmanGameStat.resetGameStatus();
                hangmanGameStat.setGender(originalGender);
                intent.putExtra(HangmanMain.getGamestatusMsg(), hangmanGameStat);
                startActivity(intent);
                finish();
            }
        });

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HangmanMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                hangmanGameStat.resetGameStatus();
                hangmanGameStat.setGender(originalGender);
                intent.putExtra(HangmanMain.getGamestatusMsg(), hangmanGameStat);
                intent.putExtra("clear game", true);
                startActivity(intent);
                finish();
            }
        });
    }
}
