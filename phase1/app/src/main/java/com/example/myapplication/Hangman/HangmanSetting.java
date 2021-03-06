package com.example.myapplication.Hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

/** Hangman Game Settings Page */
public class HangmanSetting extends AppCompatActivity implements View.OnClickListener {

  /** A variable to store the hangmanGameStat */
  private HangmanGameStat hangmanGameStat;

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
    Intent intent = new Intent(getApplicationContext(), HangmanGame.class);

    if (view.getId() == R.id.btnFemale) {
      hangmanGameStat.setGender("FEMALE");
      intent.putExtra(HangmanMain.getGamestatusMsg(), hangmanGameStat);
    } else {
      hangmanGameStat.setGender("MALE");
      intent.putExtra(HangmanMain.getGamestatusMsg(), hangmanGameStat);
    }
    startActivity(intent);
  }
}
