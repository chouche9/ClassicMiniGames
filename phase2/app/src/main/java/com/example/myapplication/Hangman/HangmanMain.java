package com.example.myapplication.Hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.GameMain;
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

  /** The gender that was chosen by the user in settings. */
  private String settingsGender;

  /** Game state for this HangmanGame of the user that is currently playing. */
  private HangmanGameStatFacade hangmanGameStat;

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

    // from HangmanStageEnded or HangmanSetting
    if (intent.getParcelableExtra(getGamestatusMsg()) != null) {
      hangmanGameStat = intent.getParcelableExtra(getGamestatusMsg());
    }
    settingsGender = hangmanGameStat.getGender();

    btnStartGame.setOnClickListener(this);
    btnResumeGame.setOnClickListener(this);
    btnSettings.setOnClickListener(this);
    btnBackToMain.setOnClickListener(this);

    Log.e("HangmanMain", "ONCREATE");
  }

  /** Resumes this HangmanMain activity. */
  @Override
  protected void onResume() {
    super.onResume();
    HangmanGameManager hangmanGameManager = HangmanGameManager.getInstance(this);
    hangmanGameStat = hangmanGameManager.getGameStatus(hangmanGameStat.getName());
    // this line is used when the user chooses to go back to the settings and change the gender
    // during the game
    hangmanGameStat.setGender(settingsGender);

    if (hangmanGameStat.getPlayed()) {
      btnResumeGame.setVisibility(View.VISIBLE);
    } else {
      btnResumeGame.setVisibility(View.GONE);
    }
    startService(new Intent(this, HangmanBackgroundMusic.class));
    Log.e("HangmanMain", "ONRESUME");
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
        hangmanGameStat.resetGameStatus();
        hangmanGameStat.setGender(settingsGender);
        intent1 = new Intent(getApplicationContext(), HangmanGameActivity.class);
        break;

      case R.id.btnResumeGame:
        intent1 = new Intent(getApplicationContext(), HangmanGameActivity.class);
        break;

      case R.id.btnSettings:
        intent1 = new Intent(getApplicationContext(), HangmanSetting.class);
        break;

      case R.id.btnBackToHome:
        intent1 = new Intent(getApplicationContext(), GameMain.class);
        intent1.putExtra("user", hangmanGameStat.getName());
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        stopService(new Intent(this, HangmanBackgroundMusic.class));
        break;
    }
    intent1.putExtra(getGamestatusMsg(), hangmanGameStat);
    startActivity(intent1);
  }

  @Override
  protected void onPause() {
    super.onPause();
    HangmanGameManager hangmanGameManager = HangmanGameManager.getInstance(this);
    hangmanGameManager.saveGame(hangmanGameStat);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    stopService(new Intent(this, HangmanBackgroundMusic.class));
  }
}
