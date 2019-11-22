package com.example.myapplication.FlappyFish.GameObjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.FlappyFish.FlappyGameStatus;

public class FlappyGameShark extends FlappyGameObjects implements Parcelable{

    /** The default speed of the shark for easy mode. */
    private static final int SHARK_SPEED_EASY = 20;

    /** The default speed of the shark for hard mode. */
    private static final int SHARK_SPEED_HARD = 30;

    /** The default x coordinate of an object when it collides with the fish. */
    private static final int DEAD_POS = -100;

    /**
     * Construct a new shark object at the default starting coordinates with
     * the easy mode velocity as default.
     */
    public FlappyGameShark(){
        super(0, 0, SHARK_SPEED_EASY);
    }

    /**
     * Build the shark object from Parcel.
     * @param in the parcel that stores the previously saved shark object.
     */
    public FlappyGameShark(Parcel in) {
        super(in);
    }

    /** Set the game difficulty level to easy by adjusting the shark's velocity. */
    public void setGameEasy() {
        setVelocity(SHARK_SPEED_EASY);
    }

    /** Set the game difficulty level to hard by adjusting the shark's velocity. */
    public void setGameHard() {
        setVelocity(SHARK_SPEED_HARD);
    }

    /**
     * Move the shark according to its current velocity.
     */
    public void move() {
        setX(getX() - getVelocity());
    }

    /**
     * Update the shark object according to the specified game status
     * and ensure the shark does not move out of the screen
     * using canvasWidth, minY and maxY.
     * @param gameStatus the game status object that tracks this fish.
     * @param canvasWidth the width of the canvas this fish is drawn on.
     * @param minY the minimum value for this fish's y coordinate.
     * @param maxY the maximum value for this fish's coordinate.
     */
    public boolean update(FlappyGameStatus gameStatus, int canvasWidth, int minY, int maxY) {
        if (collideCheck(gameStatus)) {
            gameStatus.reduceLife_count();
            kill();
            if (gameStatus.getLife_count() == 0) {
                return true;
            }
        }
        validCheck(canvasWidth, minY, maxY);
        return false;
    }

    /**
     * Ensures the shark object does not move outside of the screen.
     * @param canvasWidth the width of the displayed canvas.
     * @param minY the minimum value for shark's y coordinate.
     * @param maxY the maximum value for shark's y coordinate.
     */
    private void validCheck(int canvasWidth, int minY, int maxY) {
        if (getX() < 0) {
            setX(canvasWidth + 10);
            setY((int) Math.floor(Math.random() * (maxY - minY)) + minY);
        }
    }

    /** Set the x coordinate of the shark to dead position. */
    private void kill() {
        setX(DEAD_POS);
    }

    /**
     * Check whether the specified fish object collides with this shark object.
     *
     * @param gameStatus the gameStatus object that tracks the two game objects.
     * @return Return true if shark collides with the fish object; Otherwise, return false.
     */
    private boolean collideCheck(FlappyGameStatus gameStatus) {
        FlappyGameFish fish = gameStatus.fish;
        FlappyGameShark shark = gameStatus.shark;
        int fishX = fish.getX();
        int fishY = fish.getY();
        int sharkX = shark.getX();
        int sharkY = shark.getY();
        return fishX < sharkX
                && sharkX < (fishX + fish.getWidth())
                && (fishY - shark.getHeight()) < sharkY
                && sharkY < (fishY + fish.getHeight());
    }

    /**
     * Binds the FlappyGameShark object.
     */
    public static final Creator<FlappyGameShark> CREATOR = new Parcelable.Creator<FlappyGameShark>() {
        public FlappyGameShark createFromParcel(Parcel in){
            return new FlappyGameShark(in);
        }

        public FlappyGameShark[] newArray(int size) {
            return new FlappyGameShark[size];
        }
    };

}
