package com.example.myapplication.Hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.GameMain;
import com.example.myapplication.R;

/**
 * Hangman play again Activity
 */
public class HangmanPlayAgain extends AppCompatActivity {

    /**
     * A HangmanGameStat that will store a hangmanGameState
     */
    private HangmanGameStat hangmanGameStat;

    /**
     * A String to store the original Gender
     */
    private String originalGender;

    /**
     * Initializes this HangmanMain activity.
     *
     * @param savedInstanceState a bundle of the resources in this activity.
     */
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
        Button backToHome = findViewById(R.id.btnBackToHome);

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

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                hangmanGameStat.resetGameStatus();
                hangmanGameStat.setGender(originalGender);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Pause the Game
     */
    protected void onPause() {
        super.onPause();
        HangmanGameManager hangmanGameManager = HangmanGameManager.getInstance(this);
        hangmanGameManager.saveGame(hangmanGameStat);
    }
}
