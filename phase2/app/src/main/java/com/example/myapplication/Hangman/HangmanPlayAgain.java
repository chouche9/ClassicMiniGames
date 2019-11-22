package com.example.myapplication.Hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.GameMain;
import com.example.myapplication.R;

/** Hangman play again Activity */
public class HangmanPlayAgain extends AppCompatActivity {

  /** A HangmanGameStatInteractor that stores the hangman game status. */
  private HangmanGameStatInteractor hangmanGameStat;

  /** A String to store the original gender */
  private String originalGender;

  /** Button that user clicks to play again. */
  private Button playAgain;

  /** Button that user clicks to go back to the main menu of the Hangman game. */
  private Button mainMenu;

  /** Button that the user clicks to go back to the home page of the app. */
  private Button backToHome;

  /**
   * Initializes this HangmanMain activity.
   *
   * @param savedInstanceState a bundle of the resources in this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hangman_play_again);

    Intent received_intent = getIntent();
    String score = received_intent.getStringExtra(HangmanGameActivity.getScoreMessage());
    String message = received_intent.getStringExtra(HangmanGameActivity.getMessage());
    hangmanGameStat = received_intent.getParcelableExtra(HangmanMain.getGamestatusMsg());
    assert hangmanGameStat != null;
    originalGender = hangmanGameStat.getGender();

    TextView txtScore = findViewById(R.id.txtScore);
    txtScore.setText(score);

    TextView txtMessage = findViewById(R.id.txtMessage);
    txtMessage.setText(message);

    playAgain = findViewById(R.id.btnPlayAgain);
    mainMenu = findViewById(R.id.btnMainMenu);
    backToHome = findViewById(R.id.btnBackToHome);

    hangmanGameStat.resetGameStatus();

    setPlayAgainButton();
    setMainMenuButton();
    setBackToHome();
  }

  /** Event that happens after playAgain button is clicked. */
  private void setPlayAgainButton() {
    playAgain.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), HangmanGameActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            hangmanGameStat.resetGameStatus();
            hangmanGameStat.setGender(originalGender);
            intent.putExtra(HangmanMain.getGamestatusMsg(), hangmanGameStat);
            startActivity(intent);
            finish();
          }
        });
  }

  /** Event that happens after mainMenu button is clicked. */
  private void setMainMenuButton() {
    mainMenu.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), HangmanMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            hangmanGameStat.resetGameStatus();
            hangmanGameStat.setGender(originalGender);
            intent.putExtra(HangmanMain.getGamestatusMsg(), hangmanGameStat);
            intent.putExtra("clear game", true);
            intent.putExtra("user", hangmanGameStat.getName());
            startActivity(intent);
            finish();
          }
        });
  }

  /** Event that happens after backToHome button is clicked. */
  private void setBackToHome() {
    backToHome.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), GameMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            hangmanGameStat.resetGameStatus();
            hangmanGameStat.setGender(originalGender);
            intent.putExtra(HangmanMain.getGamestatusMsg(), hangmanGameStat);
            intent.putExtra("user", hangmanGameStat.getName());
            startActivity(intent);
            finish();
          }
        });
  }

  /** Pause the Game */
  protected void onPause() {
    super.onPause();
    HangmanGameManager hangmanGameManager = HangmanGameManager.getInstance(this);
    hangmanGameManager.saveGame(hangmanGameStat);
  }
}
