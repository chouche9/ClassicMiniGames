package com.example.myapplication.flappyfish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.flappyfish.FlappyGameStatus.FlappyGameStatusFacade;
import com.example.myapplication.R;

/**
 * The setting page of this flappy fish game in which the user gets to choose the level of
 * difficulty.
 */
public class FlappySetting extends AppCompatActivity implements View.OnClickListener {

  /** The status of this game which belongs to the current user. */
  private FlappyGameStatusFacade gameStatus;

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

    // The intent that took the previous activity to this setting page.
    Intent settingIntent = getIntent();
    gameStatus = settingIntent.getParcelableExtra("gameStatus");
    setUpLightBgBtn();
    setUpDarkBgBtn();
  }

  /** Initializes the easy game button. */
  private void setUpLightBgBtn() {
    // The button that sets the game to an easy level when clicked.
    Button lightBtn = findViewById(R.id.lightBtn);
    lightBtn.setOnClickListener(this);
  }

  /** Initializes the hard game button. */
  private void setUpDarkBgBtn() {
    // The button that sets the game to a hard level when clicked.
    Button darkBtn = findViewById(R.id.darkBtn);
    darkBtn.setOnClickListener(this);
  }

  /** Takes the user from the current setting page to the actual game. */
  private void startGame() {
    Intent startGame = new Intent(getApplicationContext(), FlappyMainActivity.class);
    startGame.putExtra("gameStatus", gameStatus);
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
      case R.id.lightBtn:
        gameStatus.setBgLight();
        startGame();
        break;

      case R.id.darkBtn:
        gameStatus.setBgDark();
        startGame();
        break;
    }
  }
}
