package com.example.myapplication.FlappyFish.FlappyGameView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.myapplication.FlappyFish.FlappyGameStatus;

public class ViewPaintManager {

    private FlappyGameStatus gameStatus;

    private Canvas canvas;

    /** The score the user gets. */
    private Paint score = new Paint();

    /** The game level the user plays. */
    private Paint level = new Paint();

    public ViewPaintManager(FlappyGameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    void setUpGame() {
        setUpLevel();
        setUpScore();
    }

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

    void drawScore() {
        canvas.drawText("Score : " + gameStatus.getScore(), 50, 80, score);
    }

    void drawLevel() {
        int canvasWidth = canvas.getWidth();
        canvas.drawText("Stage: " + gameStatus.getStage(), canvasWidth / 2, 80, level);
    }


}
