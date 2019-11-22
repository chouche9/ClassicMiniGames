package com.example.myapplication.FlappyFish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.FlappyFish.FlappyGameView.FlappyGameViewFacade;
import com.example.myapplication.FlappyFish.FlappyGameView.ViewBitmapManager;
import com.example.myapplication.FlappyFish.FlappyGameView.ViewPaintManager;

import java.util.Timer;
import java.util.TimerTask;

/** The user interface of the flappy fish game. */
public class FlappyMainActivity extends AppCompatActivity {

  /** The game view the game uses. */
  private FlappyGameViewFacade gameView;

  /** The game status the game uses. */
  private FlappyGameStatus gameStatus;

  /** The timer used by the game view. */
  private Timer timer = null;

  /** The handler that handles code execution over a specific thread. */
  private Handler handler = new Handler();

  /** The time interval for the timer. */
  private static final long TIMER_INTERVAL = 30;

  /** The activity code to start another activity. */
  private static final int REQUEST_CODE3 = 3;

  /**
   * Create FlappyMainActivity activity.
   *
   * @param savedInstanceState bundle of the resource in this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    gameStatus = getIntent().getParcelableExtra("gamer");
    gameView = new FlappyGameViewFacade(this);
    gameView.setBitmapManager(new ViewBitmapManager(gameStatus));
    gameView.setPaintManager(new ViewPaintManager(gameStatus));
    setContentView(gameView);
    gameView.setUpView();
  }

  /** Create a timer that is used to display the game view. */
  @Override
  protected void onResume() {
    super.onResume();
    gameView.setGameStatus(gameStatus);

    if (timer == null) {
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
  }

  /** Pause this game activity. */
  @Override
  protected void onPause() {
    super.onPause();
    timer.cancel();
    timer = null;
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
}
