package com.example.myapplication.FlappyFish.GameObjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.FlappyFish.FlappyGameStatus;

public class FlappyGameFish extends FlappyGameObjects implements Parcelable {

    /** The default x coordinate of the fish. */
    private static final int FISH_X = 10;

    /** The default y coordinate of the fish. */
    private static final int FISH_Y = 500;

    /** The default speed of the fish when falling down. */
    private static final int DROP_SPEED = 2;

    /** The default speed of the fish when jumping up. */
    private static final int JUMP_SPEED = -35;

    /**
     * Construct a new fish object at the default starting coordinates with velocity of 0.
     */
    public FlappyGameFish() {
        super(FISH_X, FISH_Y, 0);
    }

    /**
     * Build the fish object from Parcel.
     * @param in the parcel that stores the previously saved fish object.
     */
    public FlappyGameFish(Parcel in) {
        super(in);
    }

    @Override
    public void setGameEasy() {
    }

    @Override
    public void setGameHard() {
    }

    /**
     * Move the fish according to its current velocity.
     */
    public void move() {
        setY(getY() + getVelocity());
    }

    /**
     * Update the game object according to the specified game status
     * and ensure the game object does not move out of the screen
     * using minY and maxY.
     * @param gameStatus the game status object that tracks this fish.
     * @param canvasWidth the width of the canvas this fish is drawn on.
     * @param minY the minimum value for this fish's y coordinate.
     * @param maxY the maximum value for this fish's coordinate.
     */
    public boolean update(FlappyGameStatus gameStatus, int canvasWidth, int minY, int maxY) {
        validCheck(minY, maxY);
        setFishFallSpeed();
        return false;
    }

    /**
     * Ensure the fish does not move out of the screen.
     * @param minY the minimum value for this fish's y coordinate.
     * @param maxY the maximum value for this fish's coordinate.
     */
    private void validCheck(int minY, int maxY) {
        if (getY() < minY) {
            setY(minY);
        }
        if (getY() > maxY) {
            setY(maxY);
        }
    }

    /** Set the jump speed for fish. */
    public void setFishJumpSpeed() {
        setVelocity(JUMP_SPEED);
    }

    /** Set the fall speed for fish. */
    private void setFishFallSpeed() {
        setVelocity(DROP_SPEED);
    }

    /**
     * Binds the FlappyGameFish object.
     */
    public static final Creator<FlappyGameFish> CREATOR = new Parcelable.Creator<FlappyGameFish>() {
        public FlappyGameFish createFromParcel(Parcel in){
            return new FlappyGameFish(in);
        }

        public FlappyGameFish[] newArray(int size) {
            return new FlappyGameFish[size];
        }
    };
}
