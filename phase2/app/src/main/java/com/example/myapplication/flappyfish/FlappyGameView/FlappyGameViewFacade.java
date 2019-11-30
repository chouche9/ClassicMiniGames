package com.example.myapplication.flappyfish.FlappyGameView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.flappyfish.FlappyGameStatus.FlappyGameStatusFacade;
import com.example.myapplication.flappyfish.FlappyMainActivity;
import com.example.myapplication.flappyfish.FlappyResultActivity;


/** The Flappy fish game view. */
public class FlappyGameViewFacade extends View {

  /** The key that is responsible for the score. */
  public static final String EXTRA_MESSAGE = "SCORE";

  /** The game status object of this user. */
  private FlappyGameStatusFacade gameStatus;

  /** The activity that the game view is on. */
  private Context context;

  /** The activity that calls the game view. */
  private Activity activity;

  /** The object that manages all the bitmap representations of objects drawn on the screen. */
  private ViewBitmapManager bitmapManager;

  /** The object that manages all the paint representations of objects drawn on the screen. */
  private ViewPaintManager paintManager;

  /**
   * Construct a new flappy fish game in the context environment.
   *
   * @param context the environment.
   */
  public FlappyGameViewFacade(Context context, Activity activity) {
    super(context);
    this.context = context;
    this.activity = activity;
  }

  /**
   * Set the bitmapManager of this game view to bitmapManager.
   * @param bitmapManager the new bitmapManager of this game view.
   */
  public void setBitmapManager(ViewBitmapManager bitmapManager) {
    this.bitmapManager = bitmapManager;
  }

  /**
   * Set the paintManager of this game view to bitmapManager.
   * @param paintManager the new bitmapManager of this game view.
   */
  public void setPaintManager(ViewPaintManager paintManager) {
    this.paintManager = paintManager;
  }

  /**
   * Set up this game view by initializing the attributes tracked by the two managers.
   */
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

    // Fish, Shrimp and Shark
    if (bitmapManager.drawBitmaps()) {
      gameOver();
    }

    // Bonus item
    if (bitmapManager.drawBonusGameBitmap()) {
      activateBonusGame();
    }

    // Score
    paintManager.drawScore();

    // Level
    paintManager.drawLevel();

    // Life
    bitmapManager.drawLife();
  }

  /**
   * Start the result activity page.
   */
  private void gameOver() {
    Intent intent = new Intent(context, FlappyResultActivity.class);
    intent.putExtra(EXTRA_MESSAGE, gameStatus);
    context.startActivity(intent);
  }

  /** Activate the bonus level dialog. */
  private void activateBonusGame() {
    ((FlappyMainActivity) activity).activateBonusGame();
  }

  /**
   * Change the speed of the fish object when a touch screen motion event occurs.
   *
   * @param event the event that reports input details from the touch screen.
   * @return Return true if the event was handled, false otherwise.
   */
  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      gameStatus.getFish().setFishJumpSpeed();
    }
    return true;
  }

  /**
   * Set the game status.
   *
   * @param gameStatus the game status that is being used.
   */
  public void setGameStatus(FlappyGameStatusFacade gameStatus) {
    this.gameStatus = gameStatus;
  }
}
