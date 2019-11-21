package com.example.myapplication.FlappyFish.FlappyGameView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.FlappyFish.FlappyGameStatus;
import com.example.myapplication.FlappyFish.FlappyResultActivity;

/** The Flappy fish game view. */
public class FlappyGameViewFacade extends View {

  /** * The key that is responsible for the score. */
  public static final String EXTRA_MESSAGE = "SCORE";

  /** The game status object of this user. */
  private FlappyGameStatus gameStatus;

  /** The activity that the game view is on. */
  private Context context;

  /** The object that manages all the bitmap representations of
   * objects drawn on the screen.
   */
  private ViewBitmapManager bitmapManager;

  /** The object that manages all the paint representations of
   * objects drawn on the screen.
   */
  private ViewPaintManager paintManager;

  /**
   * Construct a new flappy fish game in the context environment.
   *
   * @param context the environment.
   */
  public FlappyGameViewFacade(Context context) {
    super(context);
    this.context = context;
  }

  public void setBitmapManager(ViewBitmapManager bitmapManager) {
    this.bitmapManager = bitmapManager;

  }

  public void setPaintManager(ViewPaintManager paintManager) {
    this.paintManager = paintManager;
  }

  public void setUpView() {
    Resources resources = getResources();
    bitmapManager.setUpGame(resources);
    paintManager.setUpGame();
  }

  /**
   * Draw all the elements that made up the flappy fish game on the canvas.
   *
   * @param canvas the canvas that the view uses to draw.
   */
  @Override
  protected void onDraw(Canvas canvas) {

    bitmapManager.setCanvas(canvas);
    paintManager.setCanvas(canvas);

    // Background
    bitmapManager.drawBackground();

    //Fish, Shrimp and Shark
    if (bitmapManager.drawBitmaps()) {
      gameOver();
    }

    // Score
    paintManager.drawScore();

    // Level
    paintManager.drawLevel();

    // Life
    bitmapManager.drawLife();
  }

  private void gameOver() {
    Intent intent = new Intent(context, FlappyResultActivity.class);
    intent.putExtra(EXTRA_MESSAGE, gameStatus);
    context.startActivity(intent);
  }

  /**
   * Change the speed of the fish object when a touch screen motion event occurs.
   *
   * @param event the event that reports input details from the touch screen.
   * @return Return true if the event  was handled, false otherwise.
   */
  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      gameStatus.fish.setFishJumpSpeed();
    }
    return true;
  }

  /**
   * Set the game status.
   *
   * @param gameStatus the game status that is being used.
   */
  public void setGameStatus(FlappyGameStatus gameStatus) {
    this.gameStatus = gameStatus;
  }
}
