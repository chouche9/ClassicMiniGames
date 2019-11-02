package com.example.myapplication.FlappyFish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.R;

/**
 * The Flappy fish game view.
 */
class FlappyGameView extends View {

    /***
     * The key that is responsible for the score.
     */
    public static final String EXTRA_MESSAGE = "SCORE";

    /**
     * The game status object of this user.
     */
    private FlappyGameStatus gameStatus;

    /**
     * The activity that the game view is on.
     */
    private Context context;

    /**
     * The background of this game view.
     */
    private Bitmap bg;

    /**
     * The fish object.
     */
    private Bitmap fish;

    /**
     * The shrimp object.
     */
    private Bitmap shrimp;

    /**
     * The shark object.
     */
    private Bitmap shark;

    /**
     * The score the user gets.
     */
    private Paint score = new Paint();

    /**
     * The game level the user plays.
     */
    private Paint level = new Paint();

    /**
     * The life the user has.
     */
    private Bitmap[] life = new Bitmap[2];


    /**
     * Construct a new flappy fish game in the context environment.
     *
     * @param context the environment.
     */
    public FlappyGameView(Context context) {
        super(context);
        this.context = context;

        setUpSprite();
        setUpScore();
        setUpLevel();
        setUpLife();
    }

    /**
     * Set up the background and the sprites for the moving objects.
     */
    private void setUpSprite() {
        bg = BitmapFactory.decodeResource(getResources(), R.drawable.ocean);
        fish = BitmapFactory.decodeResource(getResources(), R.drawable.fish);
        shrimp = BitmapFactory.decodeResource(getResources(), R.drawable.shrimp);
        shark = BitmapFactory.decodeResource(getResources(), R.drawable.shark);
    }

    /**
     * Set up the life that will show on the game view.
     */
    private void setUpLife() {
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_empty);
    }

    /**
     * Set up the level that will show on the game view.
     */
    private void setUpLevel() {
        level.setColor(Color.DKGRAY);
        level.setTextSize(45);
        level.setTypeface(Typeface.DEFAULT_BOLD);
        level.setTextAlign(Paint.Align.CENTER);
        level.setAntiAlias(true);
    }

    /**
     * Set up the score that will show on the game view.
     */
    private void setUpScore() {
        score.setColor(Color.BLACK);
        score.setTextSize(45);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        score.setAntiAlias(true);
    }

    /**
     * Draw all the elements that made up the flappy fish game on the canvas.
     *
     * @param canvas the canvas that the view uses to draw.
     */
    @Override
    protected void onDraw(Canvas canvas) {

        // Canvas
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();

        // Background
        canvas.drawBitmap(bg, 0, 0, null);

        int minFishY = fish.getHeight();
        int maxFishY = canvasHeight - fish.getHeight() * 4;

        // Fish
        gameStatus.fishMove(minFishY, maxFishY);
        gameStatus.setFishFallSpeed();
        canvas.drawBitmap(fish, gameStatus.getFishX(), gameStatus.getFishY(), null);

        // Shrimp
        gameStatus.shrimpMove();
        if (collideCheck(gameStatus.getShrimpX(), gameStatus.getShrimpY(), shrimp)) {
            gameStatus.updateScore();
            gameStatus.shrimpGone();
        }
        gameStatus.shrimpValidCheck(canvasWidth, minFishY, maxFishY);
        canvas.drawBitmap(shrimp, gameStatus.getShrimpX(), gameStatus.getShrimpY(), null);

        // Shark
        gameStatus.sharkMove();
        if (collideCheck(gameStatus.getSharkX(), gameStatus.getSharkY(), shark)) {
            gameStatus.sharkGone();
            gameStatus.reduceLife_count();
            if (gameStatus.getLife_count() == 0) {
                // Game Over
                Intent intent = new Intent(context, FlappyResultActivity.class);
                intent.putExtra(EXTRA_MESSAGE, gameStatus);
                context.startActivity(intent);
            }
        }
        gameStatus.sharkValidCheck(canvasWidth, minFishY, maxFishY);
        canvas.drawBitmap(shark, gameStatus.getSharkX(), gameStatus.getSharkY(), null);

        // Score
        canvas.drawText("Score : " + gameStatus.getScore(), 50, 80, score);

        // Level
        canvas.drawText("" + gameStatus.getDifficulty(), canvasWidth / 2, 80, level);

        // Life
        for (int i = 0; i < 3; i++) {
            double default_x = canvasWidth - 50 - life[0].getWidth() * 1.5 * 3;
            int x = (int) (default_x + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < gameStatus.getLife_count()) {
                canvas.drawBitmap(life[0], x, y, null);
            } else {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }
    }

    /**
     * Check whether the fish object collides with the obj object.
     *
     * @param x   the x coordinate of the obj.
     * @param y   the y coordinate of the obj.
     * @param obj the object that is being checked.
     * @return Return true if obj collides with the fish object; Otherwise, return false.
     */
    boolean collideCheck(int x, int y, Bitmap obj) {
        int fishX = gameStatus.getFishX();
        int fishY = gameStatus.getFishY();
        return fishX < x && x < (fishX + fish.getWidth()) && (fishY - obj.getHeight()) < y &&
                y < (fishY + fish.getHeight());
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
            gameStatus.setFishJumpSpeed();
        }
        return true;
    }

    /**
     * Set the game status.
     *
     * @param gameStatus the game status that is being used.
     */
    void setGameStatus(FlappyGameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
