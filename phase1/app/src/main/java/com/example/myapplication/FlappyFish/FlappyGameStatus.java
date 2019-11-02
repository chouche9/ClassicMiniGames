package com.example.myapplication.FlappyFish;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.GameStatus;

import java.io.Serializable;

/**
 * The flappy fish game status.
 */
public class FlappyGameStatus extends GameStatus implements Parcelable {

    /**
     * The default x coordinate of the fish.
     */
    private static final int FISH_X = 10;

    /**
     * The default y coordinate of the fish.
     */
    private static final int FISH_Y = 500;

    /**
     * The default speed of the shrimp for easy mode.
     */
    private static final int SHRIMP_SPEED_EASY = 15;

    /**
     * The default speed of the shrimp for hard mode.
     */
    private static final int SHRIMP_SPEED_HARD = 25;

    /**
     * The default speed of the shark for easy mode.
     */
    private static final int SHARK_SPEED_EASY = 20;

    /**
     * The dafault speed of the shark for hard mode.
     */
    private static final int SHARK_SPEED_HARD = 30;

    /**
     * The default speed of the fish when falling down.
     */
    private static final int DROP_SPEED = 2;

    /**
     * The default speed of the fish when jumping up.
     */
    private static final int JUMP_SPEED = -35;

    /**
     * The default score when game starts.
     */
    private static final int DEFAULT_SCORE = 0;

    /**
     * The default x coordinate of an object when it collides with the fish.
     */
    private static final int DEAD_POS = -100;

    /**
     * Indicate whether the user has played the game.
     */
    private int played;

    /**
     * The difficulty the user chose.
     */
    private String difficulty;

    /**
     * The type of the game the user plays.
     */
    private String type;

    /**
     * The x coordinate of the fish.
     */
    private int fishX;

    /**
     * The y coordinate of the fish.
     */
    private int fishY;

    /**
     * The speed of the fish.
     */
    private int fishSpeed;

    /**
     * The x coordinate of the shrimp.
     */
    private int shrimpX;

    /**
     * The y coordinate of the shrimp.
     */
    private int shrimpY;

    /**
     * The speed of the shrimp.
     */
    private int shrimpSpeed;

    /**
     * The x coordinate of the shark.
     */
    private int sharkX;

    /**
     * The y coordinate of the shark.
     */
    private int sharkY;

    /**
     * The speed of the shark.
     */
    private int sharkSpeed;

    /**
     * The score the user has.
     */
    private int score;

    /**
     * The number of lives the user has.
     */
    private int life_count;

    /**
     * Construct a new game status for the user with name name.
     *
     * @param name the name of the user.
     */
    FlappyGameStatus(String name) {
        super(name);
        this.type = "FlappyGameStatus";
    }

    /**
     * Build FlappyGameStatus from Parcel.
     *
     * @param in the Parcel that store FlappyGameStatus.
     */
    private FlappyGameStatus(Parcel in) {
        super(in);
        type = in.readString();
        fishX = in.readInt();
        fishY = in.readInt();
        fishSpeed = in.readInt();
        shrimpX = in.readInt();
        shrimpY = in.readInt();
        shrimpSpeed = in.readInt();
        sharkX = in.readInt();
        sharkY = in.readInt();
        sharkSpeed = in.readInt();
        score = in.readInt();
        life_count = in.readInt();
        played = in.readInt();
        difficulty = in.readString();
    }

    /**
     * Set the game difficulty level to easy.
     */
    void setGameEasy() {
        shrimpSpeed = SHRIMP_SPEED_EASY;
        sharkSpeed = SHARK_SPEED_EASY;
        this.difficulty = "EASY";
    }

    /**
     * Set the game difficulty level to hard.
     */
    void setGameHard() {
        shrimpSpeed = SHRIMP_SPEED_HARD;
        sharkSpeed = SHARK_SPEED_HARD;
        this.difficulty = "HARD";
    }

    /**
     * Create FlappyGameStatatus by the super Creator object.
     */
    public static final Creator<FlappyGameStatus> CREATOR = new Creator<FlappyGameStatus>() {
        @Override
        public FlappyGameStatus createFromParcel(Parcel in) {
            return new FlappyGameStatus(in);
        }

        @Override
        public FlappyGameStatus[] newArray(int size) {
            return new FlappyGameStatus[size];
        }
    };

    /**
     * Get the x coordinate of the fish.
     *
     * @return Return the x coordinate of the fish.
     */
    int getFishX() {
        return fishX;
    }

    /**
     * Get the y coordinate of the fish.
     *
     * @return Return the y coordinate of the fish.
     */
    int getFishY() {
        return fishY;
    }

    /**
     * Move the fish.
     *
     * @param minY The minimum value for fish's y coordinate.
     * @param maxY The maximum value for fish's y coordinate.
     */
    void fishMove(int minY, int maxY) {
        fishY += fishSpeed;
        if (fishY < minY) {
            fishY = minY;
        }
        if (fishY > maxY) {
            fishY = maxY;
        }
    }

    /**
     * Set the jump speed for fish.
     */
    void setFishJumpSpeed() {
        fishSpeed = JUMP_SPEED;
    }

    /**
     * Set the fall speed for fish.
     */
    void setFishFallSpeed() {
        fishSpeed += DROP_SPEED;
    }

    /**
     * Get the x coordinate of the shrimp.
     *
     * @return Return the x coordinate of the shrimp.
     */
    int getShrimpX() {
        return shrimpX;
    }

    /**
     * Get the y coordinate of the shrimp.
     *
     * @return
     */
    int getShrimpY() {
        return shrimpY;
    }

    /**
     * Set the x coordinate of the shrimp to dead position.
     */
    void shrimpGone() {
        this.shrimpX = DEAD_POS;
    }

    /**
     * Move the shrimp.
     */
    void shrimpMove() {
        shrimpX -= shrimpSpeed;
    }

    /**
     * Set the shrimp to a new starting point.
     *
     * @param canvasWidth the width of the canvas.
     * @param minY        the minimum value for fish's y coordinate.
     * @param maxY        the maximum value for fish's y coordinate.
     */
    void shrimpValidCheck(int canvasWidth, int minY, int maxY) {
        if (shrimpX < 0) {
            shrimpX = canvasWidth + 10;
            shrimpY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }
    }

    /**
     * Get the x coordinate of the shark.
     *
     * @return
     */
    int getSharkX() {
        return sharkX;
    }

    /**
     * Get the y coordinate of the shark.
     *
     * @return
     */
    int getSharkY() {
        return sharkY;
    }

    /**
     * Set the x coordinate of the shark to dead position.
     */
    void sharkGone() {
        this.sharkX = DEAD_POS;
    }

    /**
     * Move the shark.
     */
    void sharkMove() {
        sharkX -= sharkSpeed;
    }

    /**
     * Set the shark to a new starting point.
     *
     * @param canvasWidth the width of the canvas.
     * @param minY        the minimum value for fish's y coordinate.
     * @param maxY        the maximum value for fish's y coordinate.
     */
    void sharkValidCheck(int canvasWidth, int minY, int maxY) {
        if (sharkX < 0) {
            sharkX = canvasWidth + 10;
            sharkY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }
    }

    /**
     * Get the difficulty of the game.
     *
     * @return
     */
    String getDifficulty() {
        return this.difficulty;
    }

    /**
     * Get the score of the game.
     *
     * @return
     */
    int getScore() {
        return score;
    }

    /**
     * Update the score.
     */
    void updateScore() {
        this.score += DEFAULT_SCORE;
    }

    /**
     * Get the life count.
     *
     * @return
     */
    int getLife_count() {
        return life_count;
    }

    /**
     * Reduce the life count.
     */
    void reduceLife_count() {
        life_count--;
    }

    /**
     * Check whether the user has played the game.
     *
     * @return Return true if the user has played the game, false otherwise.
     */
    boolean getPlayed() {
        return played != 0;
    }

    /**
     * Set played to 1 if played is true, 0 otherwise.
     *
     * @param played whether the game status has been updated.
     */
    private void setPlayed(boolean played) {
        if (played) {
            this.played = 1;
        } else {
            this.played = 0;
        }
    }

    /**
     * Start updating the game status.
     */
    void startUpdate() {
        setPlayed(true);
        fishX = FISH_X;
        fishY = FISH_Y;
        life_count = 3;
        score = 0;
    }

    /**
     * Stop updating the game status.
     */
    void finishUpdate() {
        this.played = 0;
        fishX = FISH_X;
        fishY = FISH_Y;
        life_count = 3;
        score = 0;
    }

    /**
     * Default method from the super class.
     *
     * @return 0.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Default method from the super class.
     *
     * @param parcel parcel to write the attributes of this GameStatus.
     * @param i      flags.
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(type);
        parcel.writeInt(fishX);
        parcel.writeInt(fishY);
        parcel.writeInt(fishSpeed);
        parcel.writeInt(shrimpX);
        parcel.writeInt(shrimpY);
        parcel.writeInt(shrimpSpeed);
        parcel.writeInt(sharkX);
        parcel.writeInt(sharkY);
        parcel.writeInt(sharkSpeed);
        parcel.writeInt(score);
        parcel.writeInt(life_count);
        parcel.writeInt(played);
        parcel.writeString(difficulty);
    }
}
