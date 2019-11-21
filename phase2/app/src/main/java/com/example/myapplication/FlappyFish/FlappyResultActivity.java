package com.example.myapplication.FlappyFish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.FlappyFish.FlappyGameView.FlappyGameViewFacade;
import com.example.myapplication.GameMain;
import com.example.myapplication.R;

/** The result page that is shown when the game finishes. */
public class FlappyResultActivity extends AppCompatActivity implements View.OnClickListener {

  /** The text the displays the player's final score. */
  private TextView resultText;

  /** The button the allows users to play the same game again when clicked. */
  private Button playAgainBtn;

  /** The button that takes the user to the menu of different games when clicked. */
  private Button backToMainBtn;

  /** The status of this game which belongs to the current user. */
  private FlappyGameStatus gameStatus;

  /** The intent that took the previous activity to this result page. */
  private Intent resultIntent;

  /**
   * Initializes this result page.
   *
   * @param savedInstanceState a bundle of the resources in this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_flappy_result);

    resultIntent = getIntent();
    gameStatus = resultIntent.getParcelableExtra(FlappyGameViewFacade.EXTRA_MESSAGE);
    int finalScore = gameStatus.getScore();
    String result = "Your Score : " + finalScore;
    setResultText();
    resultText.setText(result);
    setPlayAgainBtn();
    setBackToMainBtn();
    //        setFinish();
  }

  /** Initializes the textview object that displays the player's final score. */
  private void setResultText() {
    resultText = findViewById(R.id.result);
    resultText.setOnClickListener(this);
  }

  /** Initializes the button that plays the game again. */
  private void setPlayAgainBtn() {
    playAgainBtn = findViewById(R.id.playAgainBtn);
    playAgainBtn.setOnClickListener(this);
  }

  /** Initializes the button that returns to the menu of different games. */
  private void setBackToMainBtn() {
    backToMainBtn = findViewById(R.id.backToMainBtn);
    backToMainBtn.setOnClickListener(this);
  }

  /** Pauses this activity. */
  @Override
  protected void onPause() {
    super.onPause();
    FlappyGameManager gameManager = FlappyGameManager.getInstance(this);
    gameManager.saveGame(gameStatus);
  }

  /**
   * Events that takes place when any of the buttons are clicked.
   *
   * @param view the review that is responsible for event handling.
   */
  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.playAgainBtn:
        Intent playAgainIntent = new Intent(this, FlappyGameMenu.class);
        playAgainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        playAgainIntent.putExtra("user", gameStatus.getName());
        gameStatus.finishUpdate();
        startActivity(playAgainIntent);
        finish();
        break;
      case R.id.backToMainBtn:
        Intent backToMainIntent = new Intent(this, GameMain.class);
        backToMainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        backToMainIntent.putExtra("user", gameStatus.getName());
        gameStatus.finishUpdate();
        startActivity(backToMainIntent);
        finish();
        break;
    }
  }
}
