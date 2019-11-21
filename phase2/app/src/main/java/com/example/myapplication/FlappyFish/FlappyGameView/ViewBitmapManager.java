package com.example.myapplication.FlappyFish.FlappyGameView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.myapplication.FlappyFish.FlappyGameStatus;
import com.example.myapplication.FlappyFish.GameObjects.FlappyGameFish;
import com.example.myapplication.FlappyFish.GameObjects.FlappyGameShark;
import com.example.myapplication.FlappyFish.GameObjects.FlappyGameShrimp;
import com.example.myapplication.R;
import android.content.res.Resources;
import android.graphics.Canvas;

public class ViewBitmapManager {

    FlappyGameStatus gameStatus;

    Canvas canvas;

    /** The background of this game view. */
    private Bitmap bg;

    /** The fish object. */
    private Bitmap fish;

    /** The shrimp object. */
    private Bitmap shrimp;

    /** The shark object. */
    private Bitmap shark;

    /** The life the user has. */
    private Bitmap[] life = new Bitmap[2];

    public ViewBitmapManager(FlappyGameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    void setUpGame(Resources resources) {
        setUpSprite(resources);
        setUpLife(resources);
        setUpObjects();
    }

    void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    /** Set up the background and the sprites for the moving objects. */
    private void setUpSprite(Resources resources) {
        bg = BitmapFactory.decodeResource(resources, R.drawable.ocean);
        fish = BitmapFactory.decodeResource(resources, R.drawable.fish);
        shrimp = BitmapFactory.decodeResource(resources, R.drawable.shrimp);
        shark = BitmapFactory.decodeResource(resources, R.drawable.shark);
    }

    /** Set up the life that will show on the game view. */
    private void setUpLife(Resources resources) {
        life[0] = BitmapFactory.decodeResource(resources, R.drawable.heart);
        life[1] = BitmapFactory.decodeResource(resources, R.drawable.heart_empty);
    }

    void drawBackground() {
        canvas.drawBitmap(bg, 0, 0, null);
    }

    void drawLife() {
        int canvasWidth = canvas.getWidth();
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

    private void setUpObjects(){
        gameStatus.fish.setWidth(fish.getWidth());
        gameStatus.fish.setHeight(fish.getHeight());
        gameStatus.shrimp.setWidth(shrimp.getWidth());
        gameStatus.shrimp.setHeight(shrimp.getHeight());
        gameStatus.shark.setWidth(shark.getWidth());
        gameStatus.shark.setHeight(shark.getHeight());
    }

    boolean drawBitmaps() {
        int canvasWidth = canvas.getWidth();
        int minY = fish.getHeight();
        int maxY = canvasWidth - minY * 4;
        FlappyGameFish fishObj = gameStatus.fish;
        FlappyGameShrimp shrimpObj = gameStatus.shrimp;
        FlappyGameShark sharkObj = gameStatus.shark;

        fishObj.move();
        fishObj.update(gameStatus, canvasWidth, minY, maxY);
        canvas.drawBitmap(fish, fishObj.getX(), fishObj.getY(), null);

        shrimpObj.move();
        shrimpObj.update(gameStatus, canvasWidth, minY, maxY);
        canvas.drawBitmap(shrimp, shrimpObj.getX(), shrimpObj.getY(), null);

        sharkObj.move();
        boolean gameOver = sharkObj.update(gameStatus, canvasWidth, minY, maxY);
        canvas.drawBitmap(shark, sharkObj.getX(), sharkObj.getY(), null);
        return gameOver;
    }
}
