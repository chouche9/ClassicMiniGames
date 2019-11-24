package com.example.myapplication.Hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

/** Main page of this Hangman Game. */
public class HangmanMain extends AppCompatActivity implements View.OnClickListener {

  /** Intent that sends this activity to the next activity. */
  private Intent intent1;

  /** Button for starting the game. */
  private Button btnStartGame;

  /** Button for resuming the game. */
  private Button btnResumeGame;

  /** Button for settings */
  private Button btnSettings;

  /** Button for Back To Main Menu */
  private Button btnBackToMain;

  /** Game state for this HangmanGame of the user that is currently playing. */
  private HangmanGameStatInteractor hangmanGameStat;

  /**
   * Name used globally to send/retrieve the HangmanGameStat instance to/from an intent.
   *
   * @return String the name for hangmanGameStat.
   */
  public static String getGamestatusMsg() {
    return "hangmanGameStat";
  }

  /**
   * Initializes this HangmanMain activity.
   *
   * @param savedInstanceState a bundle of the resources in this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hangman_main);

    btnStartGame = findViewById(R.id.btnNewGame);
    btnResumeGame = findViewById(R.id.btnResumeGame);
    btnSettings = findViewById(R.id.btnSettings);
    btnBackToMain = findViewById(R.id.btnBackToHome);

    HangmanGameManager hangmanGameManager = HangmanGameManager.getInstance(this);

    Intent intent = getIntent();

    // from log in page
    String name = intent.getStringExtra("user");
    hangmanGameStat = hangmanGameManager.getGameStatus(name);

    // from playAgain
    if (intent.getParcelableExtra(getGamestatusMsg()) != null) {
      hangmanGameStat = intent.getParcelableExtra(getGamestatusMsg());
    }

    btnStartGame.setOnClickListener(this);
    btnResumeGame.setOnClickListener(this);
    btnSettings.setOnClickListener(this);
    btnBackToMain.setOnClickListener(this);
  }

  /** Resumes this HangmanMain activity. */
  @Override
  protected void onResume() {
    super.onResume();
    HangmanGameManager hangmanGameManager = HangmanGameManager.getInstance(this);
    hangmanGameStat = hangmanGameManager.getGameStatus(hangmanGameStat.getName());

    if (hangmanGameStat.getPlayed()) {
      btnResumeGame.setVisibility(View.VISIBLE);
    } else {
      btnResumeGame.setVisibility(View.GONE);
    }
  }

  /**
   * Events that happen when each of the buttons in this activity is clicked.
   *
   * @param view view responsible for event handling.
   */
  @Override
  public void onClick(View view) {

    switch (view.getId()) {
      case R.id.btnNewGame:
        String originalGender = hangmanGameStat.getGender();
        hangmanGameStat.resetGameStatus();
        if (originalGender == null) {
          // if user never played Hangman game before, set "MALE" by default
          originalGender = "MALE";
        }
        hangmanGameStat.setGender(originalGender);
        intent1 = new Intent(getApplicationContext(), HangmanGameActivity.class);
        break;

      case R.id.btnResumeGame:
        intent1 = new Intent(getApplicationContext(), HangmanGameActivity.class);
        break;

      case R.id.btnSettings:
        hangmanGameStat.resetGameStatus();
        intent1 = new Intent(getApplicationContext(), HangmanSetting.class);
        break;

      case R.id.btnBackToHome:
        intent1 = new Intent(getApplicationContext(), MainActivity.class);
        break;

    }

    intent1.putExtra(getGamestatusMsg(), hangmanGameStat);
    startActivity(intent1);

  }
}
