package com.example.myapplication.FlappyFish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

/**
 * The setting page of this flappy fish game in which the user gets to choose the level of
 * difficulty.
 */
public class FlappySetting extends AppCompatActivity implements View.OnClickListener {

  /** The button that sets the game to an easy level when clicked. */
  private Button easyBtn;

  /** The button that sets the game to a hard level when clicked. */
  private Button hardBtn;

  /** The intent that took the previous activity to this setting page. */
  private Intent settingIntent;

  /** The status of this game which belongs to the current user. */
  private FlappyGameStatus gameStatus;

  /** The request code for starting the next activity. */
  private static final int REQUEST_CODE = 5;

  /**
   * Initializes this setting activity.
   *
   * @param savedInstanceState bundle of the resource in this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_flappy_setting);

    settingIntent = getIntent();
    gameStatus = settingIntent.getParcelableExtra("gamer");
    setUpEasyBtn();
    setUpHardBtn();
  }

  /** Initializes the easy game button. */
  private void setUpEasyBtn() {
    easyBtn = findViewById(R.id.easyBtn);
    easyBtn.setOnClickListener(this);
  }

  /** Initializes the hard game button. */
  private void setUpHardBtn() {
    hardBtn = findViewById(R.id.hardBtn);
    hardBtn.setOnClickListener(this);
  }

  /** Takes the user from the current setting page to the actual game. */
  private void startGame() {
    Intent startGame = new Intent(getApplicationContext(), FlappyMainActivity.class);
    startGame.putExtra("gamer", gameStatus);
    startActivityForResult(startGame, REQUEST_CODE);
  }

  /**
   * Dispatch incoming result to the correct fragment.
   *
   * @param requestCode the request code of the started activity
   * @param resultCode whether the result get returned is okay or not
   * @param data intent that store the closed info
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE) {
      setResult(RESULT_OK, data);
      finish();
    }
  }

  /**
   * Events that takes place when any of the buttons are clicked.
   *
   * @param view the review that is responsible for event handling.
   */
  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.easyBtn:
        gameStatus.setGameEasy();
        startGame();
        break;

      case R.id.hardBtn:
        gameStatus.setGameHard();
        startGame();
        break;
    }
  }
}
