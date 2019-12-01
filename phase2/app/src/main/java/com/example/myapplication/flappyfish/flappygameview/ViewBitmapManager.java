package com.example.myapplication.flappyfish.flappygameview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.myapplication.flappyfish.flappygamestatus.FlappyGameStatusFacade;
import com.example.myapplication.flappyfish.flappygameobjects.FlappyGameBonus;
import com.example.myapplication.flappyfish.flappygameobjects.FlappyGameFish;
import com.example.myapplication.flappyfish.flappygameobjects.FlappyGameObject;
import com.example.myapplication.flappyfish.flappygameobjects.FlappyGameShark;
import com.example.myapplication.flappyfish.flappygameobjects.FlappyGameShrimp;
import com.example.myapplication.R;
import android.content.res.Resources;
import android.graphics.Canvas;

/**
 * The manager object that is responsible of tracking all bitmap representations of game objects in
 * this game view.
 */
public class ViewBitmapManager {

  /** The game status that utilizes this bitmap manager. */
  private FlappyGameStatusFacade gameStatus;

  /** The canvas of which all bitmaps are drawn on. */
  private Canvas canvas;

  /** The background of this game view. */
  private Bitmap bg;

  /** The fish object. */
  private Bitmap fish;

  /** The shrimp object. */
  private Bitmap shrimp;

  /** The shark object. */
  private Bitmap shark;

  /** The bonus object. */
  private Bitmap bonus;

  /** The life the user has. */
  private Bitmap[] life = new Bitmap[2];

  /**
   * Constructs a bitmap manager with the specified game status.
   *
   * @param gameStatus the game status object that utilizes this bitmap manager.
   */
  public ViewBitmapManager(FlappyGameStatusFacade gameStatus) {
    this.gameStatus = gameStatus;
  }

  /**
   * Initializes all the bitmap representations tracked by this bitmap manager.
   *
   * @param resources the resources used to draw the bitmaps.
   */
  void setUpGame(Resources resources) {
    setUpBackground(resources);
    setUpSprite(resources);
    setUpLife(resources);
    setUpObjects();
  }

  /**
   * Set the canvas tracked by the bitmap manager to canvas.
   *
   * @param canvas the canvas of which all the bitmaps are drawn.
   */
  void setCanvas(Canvas canvas) {
    this.canvas = canvas;
  }

  /**
   * Set the bitmap representations of background based on the background in game status.
   *
   * @param resources the resources used to draw the bitmaps.
   */
  private void setUpBackground(Resources resources) {
    if (gameStatus.getBg()) {
      bg = BitmapFactory.decodeResource(resources, R.drawable.darkocean);
    } else {
      bg = BitmapFactory.decodeResource(resources, R.drawable.lightocean);
    }
  }

  /** Set up the sprites for the moving objects. */
  private void setUpSprite(Resources resources) {
    fish = BitmapFactory.decodeResource(resources, R.drawable.fish);
    shrimp = BitmapFactory.decodeResource(resources, R.drawable.shrimp);
    shark = BitmapFactory.decodeResource(resources, R.drawable.shark);
    bonus = BitmapFactory.decodeResource(resources, R.drawable.treasure_chest);
  }

  /** Set up the life that will show on the game view. */
  private void setUpLife(Resources resources) {
    life[0] = BitmapFactory.decodeResource(resources, R.drawable.heart);
    life[1] = BitmapFactory.decodeResource(resources, R.drawable.heart_empty);
  }

  /** Draw the bitmap representation of the background. */
  void drawBackground() {
    canvas.drawBitmap(bg, 0, 0, null);
  }

  /** Draw the bitmap representation of the lives of the player. */
  void drawLife() {
    int canvasWidth = canvas.getWidth();
    for (int i = 0; i < 3; i++) {
      double default_x = canvasWidth - 50 - life[0].getWidth() * 1.5 * 3;
      int x = (int) (default_x + life[0].getWidth() * 1.5 * i);
      int y = 30;

      if (i < gameStatus.getLifeCount()) {
        canvas.drawBitmap(life[0], x, y, null);
      } else {
        canvas.drawBitmap(life[1], x, y, null);
      }
    }
  }

  /** Set up the width and height of all the game objects with their bitmap representation. */
  private void setUpObjects() {
    FlappyGameFish fishObj = gameStatus.getFish();
    FlappyGameShrimp shrimpObj = gameStatus.getShrimp();
    FlappyGameShark sharkObj = gameStatus.getShark();
    FlappyGameBonus bonusObj = gameStatus.getBonus();
    fishObj.setWidth(fish.getWidth());
    fishObj.setHeight(fish.getHeight());
    shrimpObj.setWidth(shrimp.getWidth());
    shrimpObj.setHeight(shrimp.getHeight());
    sharkObj.setWidth(shark.getWidth());
    sharkObj.setHeight(shark.getHeight());
    bonusObj.setWidth(bonus.getWidth());
    bonusObj.setHeight(bonus.getHeight());
  }

  /**
   * Draw all the bitmaps tracked by this bitmap manager.
   *
   * @return whether the player has finished the current stage or ran out of lives.
   */
  boolean drawBitmaps() {
    int canvasWidth = canvas.getWidth();
    int canvasHeight = canvas.getHeight();
    int minY = fish.getHeight();
    int maxY = canvasHeight - minY * 4;
    FlappyGameObject fishObj = gameStatus.getFish();
    FlappyGameObject shrimpObj = gameStatus.getShrimp();
    FlappyGameObject sharkObj = gameStatus.getShark();

    fishObj.move();
    fishObj.update(gameStatus, canvasWidth, minY, maxY);
    canvas.drawBitmap(fish, fishObj.getX(), fishObj.getY(), null);

    shrimpObj.move();
    boolean stageFinished = shrimpObj.update(gameStatus, canvasWidth, minY, maxY);
    canvas.drawBitmap(shrimp, shrimpObj.getX(), shrimpObj.getY(), null);

    sharkObj.move();
    boolean playerDied = sharkObj.update(gameStatus, canvasWidth, minY, maxY);
    canvas.drawBitmap(shark, sharkObj.getX(), sharkObj.getY(), null);
    if (stageFinished) {
      gameStatus.increaseGameStage();
    }
    return (stageFinished || playerDied);
  }

  /**
   * Draw the bitmap representation of the bonus game item.
   *
   * @return whether the player has activated the bonus game.
   */
  boolean drawBonusGameBitmap() {
    int canvasWidth = canvas.getWidth();
    int canvasHeight = canvas.getHeight();
    int minY = fish.getHeight();
    int maxY = canvasHeight - minY * 4;
    FlappyGameObject bonusObj = gameStatus.getBonus();
    bonusObj.move();
    boolean isActivated = bonusObj.update(gameStatus, canvasWidth, minY, maxY);
    canvas.drawBitmap(bonus, bonusObj.getX(), bonusObj.getY(), null);
    return isActivated;
  }
}
