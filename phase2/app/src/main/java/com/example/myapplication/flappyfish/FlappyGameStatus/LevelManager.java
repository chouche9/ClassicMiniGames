package com.example.myapplication.flappyfish.FlappyGameStatus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The manager object responsible for tracking all attributes related to the game.
 */
public class LevelManager implements Parcelable {

    /** The default score when game starts. */
    private static final int DEFAULT_SCORE = 10;

    /**
     * The background tracked by this level manager.
     */
    public boolean background;

    /** Indicate whether the user has played the game. */
    private int played = 0;

    /** The stage the user is currently playing at. */
    private int stage = 1;

    /** The score the user has. */
    private int score;

    /** The number of lives the user has. */
    private int life_count;

    /**
     * Constructs a level manager instance.
     */
    public LevelManager() {}

    /**
     * Build a level manager from parcel
     * @param in the parcel that stores the level manager.
     */
    private LevelManager (Parcel in) {
        score = in.readInt();
        life_count = in.readInt();
        played = in.readInt();
        stage = in.readInt();
        background = in.readByte() != 0;
    }

    /**
     * Set the stage tracked by this level manager to 1.
     */
    void setStageDefault() {
        this.stage = 1;
    }

    /**
     * Increase the stage tracked by this level manger by 1.
     */
    void increaseStage() {
        this.stage += 1;
    }

    /**
     * Set the background tracked by this level manager to the light theme.
     */
    void setBgLight() {
        this.background = false;
    }

    /**
     * Set the background tracked by this level manager to the dark theme.
     */
    void setBgDark() {
        this.background = true;
    }

    /**
     * Return the background tracked by this level manager.
     * @return the background tracked by this level manager.
     */
    boolean getBackground() {
        return background;
    }

    /**
     * Return the stage tracked by this level manager.
     * @return the stage tracked by this level manager.
     */
    int getStage() {
        return stage;
    }

    /**
     * Return the background tracked by this level manager.
     * @return the background tracked by this level manager.
     */
    int getScore() {
        return score;
    }

    /**
     * Set the score tracked by this level manger to 0.
     */
    private void setScore() {
        this.score = 0;
    }

    /**
     * Increase the score tracked by this level manager by DEFAULT_SCORE.
     */
    void updateScore() {
        score += DEFAULT_SCORE;
    }

    /**
     * Add bonusScore to score.
     *
     * @param bonusScore the value that is added to the current score.
     */
    void addBonusScore(int bonusScore) {
        this.score += bonusScore;
    }

    /**
     * Return the life tracked by this level manager.
     * @return the background tracked by this level manager.
     */
    int getLife_count() {
        return life_count;
    }

    /**
     * Set the life tracked by this level manger to 3.
     */
    private void setLife_count() {
        this.life_count = 3;
    }

    /**
     * Reduce the life tracked by this level manager by 1.
     */
    /** Reduce the life count. */
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
     * Begin updating this level manager instance.
     */
    void startUpdate() {
        setPlayed(true);
        setLife_count();
        setScore();
    }

    /**
     * Finish updating this level manager instance.
     */
    void finishUpdate() {
        setPlayed(false);
        setLife_count();
        setScore();
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
     * @param i flags.
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(score);
        parcel.writeInt(life_count);
        parcel.writeInt(played);
        parcel.writeInt(stage);
        parcel.writeByte((byte) (background ? 1 : 0));
    }

    /** Create FlappyGameStatatus by the super Creator object. */
    public static final Creator<LevelManager> CREATOR =
            new Creator<LevelManager>() {
                @Override
                public LevelManager createFromParcel(Parcel in) {
                    return new LevelManager(in);
                }

                @Override
                public LevelManager[] newArray(int size) {
                    return new LevelManager[size];
                }
            };
}
