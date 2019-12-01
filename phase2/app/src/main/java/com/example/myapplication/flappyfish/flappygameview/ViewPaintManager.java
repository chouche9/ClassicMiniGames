package com.example.myapplication.flappyfish.flappygameview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.myapplication.flappyfish.flappygamestatus.FlappyGameStatusFacade;

/**
 * The manager object that is responsible of tracking all paint representations of game objects * in
 * this game view.
 */
public class ViewPaintManager {

  /** The game status that utilizes this paint manager. */
  private FlappyGameStatusFacade gameStatus;

  /** The canvas of which all paints are drawn on. */
  private Canvas canvas;

  /** The score the user gets. */
  private Paint score = new Paint();

  /** The game level the user plays. */
  private Paint level = new Paint();

  /**
   * Constructs a paint manager with the specified game status.
   *
   * @param gameStatus the game status object that utilizes this paint manager.
   */
  public ViewPaintManager(FlappyGameStatusFacade gameStatus) {
    this.gameStatus = gameStatus;
  }

  /** Initializes all the paint representations tracked by this paint manager. */
  void setUpGame() {
    setUpLevel();
    setUpScore();
  }

  /**
   * Set the canvas tracked by the bitmap manager to canvas.
   *
   * @param canvas the canvas of which all the bitmaps are drawn.
   */
  void setCanvas(Canvas canvas) {
    this.canvas = canvas;
  }

  /** Set up the level that will show on the game view. */
  private void setUpLevel() {
    level.setColor(Color.DKGRAY);
    level.setTextSize(45);
    level.setTypeface(Typeface.DEFAULT_BOLD);
    level.setTextAlign(Paint.Align.CENTER);
    level.setAntiAlias(true);
  }

  /** Set up the score that will show on the game view. */
  private void setUpScore() {
    score.setColor(Color.BLACK);
    score.setTextSize(45);
    score.setTypeface(Typeface.DEFAULT_BOLD);
    score.setAntiAlias(true);
  }

  /** Draw the paint representation of the score on the canvas. */
  void drawScore() {
    canvas.drawText("Score : " + gameStatus.getScore(), 50, 80, score);
  }

  /** Draw the paint representation of the score on the canvas. */
  void drawLevel() {
    int canvasWidth = canvas.getWidth();
    canvas.drawText("Stage: " + gameStatus.getStage(), canvasWidth / 2, 80, level);
  }
}
