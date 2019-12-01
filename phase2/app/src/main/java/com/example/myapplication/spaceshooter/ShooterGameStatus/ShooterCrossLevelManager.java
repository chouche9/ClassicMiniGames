package com.example.myapplication.spaceshooter.ShooterGameStatus;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.spaceshooter.GameObject.ShooterEnemy;

/**
 * The type Shooter cross level manager.
 */
public class ShooterCrossLevelManager implements Parcelable {
    /**
     * level player is at
     */
    private int level;
    /**
     * point player get
     */
    private int point;
    /**
     * represent boolean finish
     */
    private boolean levelFinish;
    private boolean gameSuccess;

    /**
     * Instantiates a new Shooter cross level manager.
     */
    public ShooterCrossLevelManager(){
        point = 0;
        gameSuccess = true;
        level = 1;
        levelFinish = false;
    }

    /**
     * Instantiates a new Shooter cross level manager.
     *
     * @param in the in
     */
    protected ShooterCrossLevelManager(Parcel in) {
        level = in.readInt();
        point = in.readInt();
        levelFinish = in.readByte() != 0;
        gameSuccess = in.readByte() != 0;
    }

    /**
     * The constant CREATOR.
     */
    public static final Creator<ShooterCrossLevelManager> CREATOR = new Creator<ShooterCrossLevelManager>() {
        @Override
        public ShooterCrossLevelManager createFromParcel(Parcel in) {
            return new ShooterCrossLevelManager(in);
        }

        @Override
        public ShooterCrossLevelManager[] newArray(int size) {
            return new ShooterCrossLevelManager[size];
        }
    };

    /**
     * Reset level.
     */
    void resetLevel(){
        levelFinish = false;
    }

    /**
     * Reset game.
     */
    void resetGame(){
        updateLevel(1);
        levelFinish = false;
        gameSuccess = true;
        point = 0;
    }

    /**
     * Update level.
     *
     * @param level the level
     */
    public void updateLevel(int level){
        this.level = level;
        ShooterEnemy.level = level;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets point.
     *
     * @return the point
     */
    public int getPoint() {
        return point;
    }

    /**
     * Sets point.
     *
     * @param point the point
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * Is level finish boolean.
     *
     * @return the boolean
     */
    public boolean isLevelFinish() {
        return levelFinish;
    }

    /**
     * Sets level finish.
     *
     * @param levelFinish the level finish
     */
    public void setLevelFinish(boolean levelFinish) {
        this.levelFinish = levelFinish;
    }

    /**
     * Is game success boolean.
     *
     * @return the boolean
     */
    public boolean isGameSuccess() {
        return gameSuccess;
    }

    /**
     * Sets game success.
     *
     * @param gameSuccess the game success
     */
    public void setGameSuccess(boolean gameSuccess) {
        this.gameSuccess = gameSuccess;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * write this class into a parcel
     * @param parcel the parcel get written to
     * @param i the flag integer
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(level);
        parcel.writeInt(point);
        parcel.writeByte((byte) (levelFinish ? 1 : 0));
        parcel.writeByte((byte) (gameSuccess ? 1 : 0));
    }
}
