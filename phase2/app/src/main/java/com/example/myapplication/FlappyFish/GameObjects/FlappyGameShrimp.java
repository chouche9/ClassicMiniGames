package com.example.myapplication.FlappyFish.GameObjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.FlappyFish.FlappyGameStatus;

public class FlappyGameShrimp extends FlappyGameObjects implements Parcelable{

    /** The default speed of the shrimp for easy mode. */
    private static final int SHRIMP_SPEED_EASY = 15;

    /** The default speed of the shrimp for hard mode. */
    private static final int SHRIMP_SPEED_HARD = 25;

    /** The default x coordinate of an object when it collides with the fish. */
    private static final int DEAD_POS = -100;

    /**
     * Construct a new shrimp object at the default starting coordinates with
     * the easy mode velocity as default.
     */
    public FlappyGameShrimp() {
        super(0, 0, SHRIMP_SPEED_EASY);
    }

    /**
     * Build the shrimp object from Parcel.
     * @param in the parcel that stores the previously saved shrimp object.
     */
    public FlappyGameShrimp(Parcel in) {
        super(in);
    }

    /** Set the game difficulty level to easy by adjusting the shrimp's velocity. */
    public void setGameEasy() {
        setVelocity(SHRIMP_SPEED_EASY);
    }

    /** Set the game difficulty level to hard by adjusting the shrimp's velocity. */
    public void setGameHard() {
        setVelocity(SHRIMP_SPEED_HARD);
    }

    /**
     * Move the shrimp according to its current velocity.
     */
    public void move() {
        setX(getX() - getVelocity());
    }

    /**
     * Update the shrimp according to the specified game status
     * and ensure the shrimp does not move out of the screen
     * using canvasWidth, minY and maxY.
     * @param gameStatus the game status object that tracks this fish.
     * @param canvasWidth the width of the canvas this fish is drawn on.
     * @param minY the minimum value for this shrimp's y coordinate.
     * @param maxY the maximum value for this shrimp's coordinate.
     */
    public boolean update(FlappyGameStatus gameStatus, int canvasWidth, int minY, int maxY) {
        if (collideCheck(gameStatus)) {
            gameStatus.updateScore();
            kill();
        }
        validCheck(canvasWidth, minY, maxY);
        return false;
    }

    /**
     * Ensure the shrimp does not move out of the screen.
     * @param minY the minimum value for this shrimp's y coordinate.
     * @param maxY the maximum value for this shrimp's coordinate.
     */
    private void validCheck(int canvasWidth, int minY, int maxY) {
        if (getX() < 0) {
            setX(canvasWidth + 10);
            setY((int) Math.floor(Math.random() * (maxY - minY)) + minY);
        }
    }

    /** Set the x coordinate of the shrimp to dead position. */
    private void kill() {
        setX(DEAD_POS);
    }

    /**
     * Check whether the specified fish object collides with this shrimp object.
     *
     * @param gameStatus the gameStatus object that tracks the two game objects.
     * @return Return true if obj collides with the fish object; Otherwise, return false.
     */
    private boolean collideCheck(FlappyGameStatus gameStatus) {
        FlappyGameFish fish = gameStatus.fish;
        FlappyGameShrimp shrimp = gameStatus.shrimp;
        int fishX = fish.getX();
        int fishY = fish.getY();
        int shrimpX = getX();
        int shrimpY = getY();
        return fishX < shrimpX
                && shrimpY < (fishX + fish.getWidth())
                && (fishY - shrimp.getHeight()) < shrimpY
                && shrimpY < (fishY + fish.getHeight());
    }

    /**
     * Binds the FlappyGameShrimp object.
     */
    public static final Creator<FlappyGameShrimp> CREATOR = new Parcelable.Creator<FlappyGameShrimp>() {
        public FlappyGameShrimp createFromParcel(Parcel in){
            return new FlappyGameShrimp(in);
        }

        public FlappyGameShrimp[] newArray(int size) {
            return new FlappyGameShrimp[size];
        }
    };
}