package com.example.myapplication.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.backgroundmusic.BackgroundMusic;

/** Hangman Game Settings Page */
public class HangmanSetting extends AppCompatActivity implements View.OnClickListener {

  /** A variable to store the hangmanGameStat */
  private HangmanGameStatFacade hangmanGameStat;

  /**
   * Initializes this HangmanMain activity.
   *
   * @param savedInstanceState a bundle of the resources in this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hangman_setting);

    Intent intent = getIntent();
    hangmanGameStat = intent.getParcelableExtra(HangmanMain.getGamestatusMsg());

    // get which gender the player chose to play with
    Button btnMale = findViewById(R.id.btnMale);
    Button btnFemale = findViewById(R.id.btnFemale);

    btnMale.setVisibility(View.VISIBLE);
    btnFemale.setVisibility(View.VISIBLE);
    btnMale.setBackgroundColor(Color.TRANSPARENT);
    btnFemale.setBackgroundColor(Color.TRANSPARENT);

    btnMale.setOnClickListener(this);
    btnFemale.setOnClickListener(this);
  }

  /**
   * Events that happen when each of the buttons in this activity is clicked.
   *
   * @param view view responsible for event handling.
   */
  @Override
  public void onClick(View view) {
    Intent intent = new Intent(getApplicationContext(), HangmanMain.class);

    if (view.getId() == R.id.btnFemale) {
      hangmanGameStat.setGender("FEMALE");
      intent.putExtra(HangmanMain.getGamestatusMsg(), hangmanGameStat);
    } else {
      hangmanGameStat.setGender("MALE");
      intent.putExtra(HangmanMain.getGamestatusMsg(), hangmanGameStat);
    }
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
    finish();
  }

  /** Resumes this activity and checks whether the background music should be played or not. */
  @Override
  protected void onResume() {
    super.onResume();
    if (HangmanMain.isPlaying) {
      startService(new Intent(this, HangmanBackgroundMusic.class));
    }
  }

  /** Pauses this activity and stops the background music if the home key is pressed. */
  @Override
  protected void onPause() {
    super.onPause();
    if (BackgroundMusic.isApplicationSentToBackground(this)) {
      stopService(new Intent(this, HangmanBackgroundMusic.class));
    }
  }
}
