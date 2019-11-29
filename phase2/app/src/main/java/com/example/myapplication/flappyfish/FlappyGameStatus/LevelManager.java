package com.example.myapplication.flappyfish.FlappyGameStatus;

import android.os.Parcel;
import android.os.Parcelable;

public class LevelManager implements Parcelable {

    /** The default score when game starts. */
    private static final int DEFAULT_SCORE = 10;

    public boolean background;

    /** Indicate whether the user has played the game. */
    private int played = 0;

    /** The stage the user is currently playing at. */
    private int stage = 1;

    /** The score the user has. */
    private int score;

    /** The number of lives the user has. */
    private int life_count;

    public LevelManager() {}

    private LevelManager (Parcel in) {
        score = in.readInt();
        life_count = in.readInt();
        played = in.readInt();
        stage = in.readInt();
        background = in.readByte() != 0;
    }

    void setStageDefault() {
        this.stage = 1;
    }

    void increaseStage() {
        this.stage += 1;
    }

    void setBgLight() {
        this.background = false;
    }

    void setBgDark() {
        this.background = true;
    }

    boolean getBackground() {
        return background;
    }

    int getStage() {
        return stage;
    }

    int getScore() {
        return score;
    }

    private void setScore() {
        this.score = 0;
    }

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

    int getLife_count() {
        return life_count;
    }

    private void setLife_count() {
        this.life_count = 3;
    }

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

    void startUpdate() {
        setPlayed(true);
        setLife_count();
        setScore();
    }

    void finishUpdate() {
        setPlayed(false);
        setLife_count();
        setScore();
    }


    @Override
    public int describeContents() {
        return 0;
    }

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
