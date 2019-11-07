package com.example.myapplication.FlappyFish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

/**
 * The starting menu of the flappy fish game when this game is chosen from main menu of the game.
 */
public class FlappyGameMenu extends AppCompatActivity implements View.OnClickListener {

  /** The button on screen that starts a new flappyfish game when clicked. */
  private Button newGameBtn;

  /** The button the continues the game from where the user last played this game when clicked. */
  private Button resumeGameBtn;

  /** The button that goes back to the menu of different games when clicked. */
  private Button quitGameBtn;

  /** The status of this game which belongs to the current user. */
  private FlappyGameStatus gameStatus;

  /** The request code for resuming the game from its previous state. */
  private final int REQUEST_CODE1 = 1;

  /** The request code for entering the setting page. */
  private final int REQUEST_CODE2 = 2;

  /** The intent that took the previous activity to this page. */
  private Intent menuIntent;

  /**
   * Initializes this game menu activity.
   *
   * @param savedInstanceState a bundle of the resources in this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_flappy_game_menu);

    menuIntent = getIntent();
    setNewGameBtn();
    setResumeGameBtn();
    setQuitBtn();
  }

  /**
   * Resumes this game menu activity and set the appearance of the resume button depending on
   * whether the user had previously left this game midway.
   */
  @Override
  protected void onResume() {
    super.onResume();
    FlappyGameManager gameManager = FlappyGameManager.getInstance(this);
    String name = menuIntent.getStringExtra("user");
    gameStatus = gameManager.getGameStatus(name);
    if (!gameStatus.getPlayed()) {
      newGameBtn.setVisibility(View.VISIBLE);
      resumeGameBtn.setVisibility(View.GONE);
    } else {
      newGameBtn.setVisibility(View.VISIBLE);
      resumeGameBtn.setVisibility(View.VISIBLE);
    }
  }

  /**
   * Dispatch incoming result to the correct fragment.
   *
   * @param requestCode the request code of the starting activity.
   * @param resultCode whether the returned result is okay or not.
   * @param data an intent that stores and passes the information.
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE1 || requestCode == REQUEST_CODE2) {
      if (data != null) {
        boolean closed = data.getBooleanExtra("closed", false);
        if (closed) {
          finish();
        }
      }
    }
  }

  /** Initializes the new game button. */
  private void setNewGameBtn() {
    newGameBtn = findViewById(R.id.newGameBtn);
    newGameBtn.setOnClickListener(this);
  }

  /** Initializes the resume game button. */
  private void setResumeGameBtn() {
    resumeGameBtn = findViewById(R.id.resumeGameBtn);
    resumeGameBtn.setOnClickListener(this);
  }

  /** Initializes the exit game button. */
  private void setQuitBtn() {
    quitGameBtn = findViewById(R.id.quitGameBtn);
    quitGameBtn.setOnClickListener(this);
  }

  /**
   * Events that takes place when any of the buttons are clicked.
   *
   * @param view the review that is responsible for event handling.
   */
  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.newGameBtn:
        Intent startSettingIntent = new Intent(this, FlappySetting.class);
        gameStatus.startUpdate();
        startSettingIntent.putExtra("gamer", gameStatus);
        startActivityForResult(startSettingIntent, REQUEST_CODE2);
        break;

      case R.id.resumeGameBtn:
        Intent startGameIntent = new Intent(this, FlappyMainActivity.class);
        startGameIntent.putExtra("gamer", gameStatus);
        startActivityForResult(startGameIntent, REQUEST_CODE1);
        break;

      case R.id.quitGameBtn:
        finish();
    }
  }
}
