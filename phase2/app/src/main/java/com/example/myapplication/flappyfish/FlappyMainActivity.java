package com.example.myapplication.flappyfish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.myapplication.bonuslevel.BonusLevelDialog;
import com.example.myapplication.flappyfish.FlappyGameStatus.FlappyGameStatusFacade;
import com.example.myapplication.flappyfish.FlappyGameView.FlappyGameViewFacade;
import com.example.myapplication.flappyfish.FlappyGameView.ViewBitmapManager;
import com.example.myapplication.flappyfish.FlappyGameView.ViewPaintManager;

import java.util.Timer;
import java.util.TimerTask;

/** The user interface of the flappy fish game. */
public class FlappyMainActivity extends AppCompatActivity
    implements BonusLevelDialog.BonusLevelDialogListener {

  /** The game view the game uses. */
  private FlappyGameViewFacade gameView;

  /** The game status the game uses. */
  private FlappyGameStatusFacade gameStatus;

  /** The timer used by the game view. */
  private Timer timer = null;

  /** The handler that handles code execution over a specific thread. */
  private Handler handler = new Handler();

  /** The time interval for the timer. */
  private static final long TIMER_INTERVAL = 30;

  /** The activity code to start another activity. */
  private static final int REQUEST_CODE3 = 3;

  /** The bonus level dialog that is displayed on the screen. */
  private BonusLevelDialog dialog;

  /**
   * Create FlappyMainActivity activity.
   *
   * @param savedInstanceState bundle of the resource in this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    gameStatus = getIntent().getParcelableExtra("gameStatus");
    gameView = new FlappyGameViewFacade(this, this);
    gameView.setBitmapManager(new ViewBitmapManager(gameStatus));
    gameView.setPaintManager(new ViewPaintManager(gameStatus));
    setContentView(gameView);
    gameView.setUpView();
  }

  /** Start a timer for displaying the game view. */
  @Override
  protected void onResume() {
    super.onResume();
    gameView.setGameStatus(gameStatus);

    if (timer == null) {
      startTimer();
    }
  }

  /** Create a timer that is used to display the game view. */
  private void startTimer() {
    timer = new Timer();
    timer.schedule(
        new TimerTask() {
          @Override
          public void run() {
            handler.post(
                new Runnable() {
                  @Override
                  public void run() {
                    gameView.invalidate();
                  }
                });
          }
        },
        0,
        TIMER_INTERVAL);
  }

  /** Cancel the timer that is currently running. */
  private void pauseTimer() {
    timer.cancel();
    timer = null;
  }

  /** Pause this game activity. */
  @Override
  protected void onPause() {
    super.onPause();
    if (timer != null) {
      pauseTimer();
    }
    FlappyGameManager gameManager = FlappyGameManager.getInstance(this);
    gameManager.saveGame(gameStatus);
  }

  /**
   * Decide whether FlappyMainActivity activity should close or not but the data passed in.
   *
   * @param requestCode the request code of the started activity.
   * @param resultCode whether the result get returned is okay or not.
   * @param data intent that store the closed info.
   */
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE3) {
      setResult(RESULT_OK, data);
      finish();
    }
  }

  /** Stop the timer and activate the bonus level dialog. */
  public void activateBonusGame() {
    pauseTimer();
    openDialog();
  }

  /**
   * Create a new BonusLevelDialog and show the dialog on the screen. The user cannot dismiss the
   * dialog by clicking outside the dialog once the dialog appears.
   */
  private void openDialog() {
    dialog = new BonusLevelDialog();
    dialog.setCancelable(false);
    dialog.show(getSupportFragmentManager(), "Bonus Level Dialog");
  }

  /**
   * Add bonusScore to the current score in the game, inform the user if he/she won the bonus game,
   * dismiss the bonus level dialog, and resume the game.
   *
   * @param isWon indicate whether the user has won the bonus level or not.
   * @param bonusSore the score that the user has won from the bonus level.
   */
  @Override
  public void bonusLevelResult(boolean isWon, int bonusSore) {
    if (isWon) {
      gameStatus.addBonusScore(bonusSore);
      Toast.makeText(this, "You guessed the correct number!\nPlus 100 points!", Toast.LENGTH_SHORT)
          .show();
    } else {
      Toast.makeText(this, "Try Again Next Time!", Toast.LENGTH_SHORT).show();
    }
    dialog.dismiss();
    startTimer();
  }

  /** Start the timer again to resume the game when the cancel button on the dialog is clicked. */
  @Override
  public void onCancel() {
    startTimer();
  }
}
