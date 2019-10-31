package com.example.fishgame.FlappyGame;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

class FlappyGameStatus implements Parcelable {

    // TODO: (private or public) static final?
    private static final int FISH_X = 10;
    private static final int FISH_Y = 500;

    private static final int SHRIMP_SPEED_EASY = 15;
    private static final int SHRIMP_SPEED_HARD = 25;
    private static final int SHARK_SPEED_EASY = 20;
    private static final int SHARK_SPEED_HARD = 30;

    private static final int DROP_SPEED = 2;
    private static final int JUMP_SPEED = -35;

    private static final int DEFAULT_SCORE = 10;
    private static final int DEAD_POS = -100;

    //User
    private String name;
    private int played;
    private String difficulty;

    // Fish
    private int fishX;
    private int fishY;
    private int fishSpeed;

    // Shrimp
    private int shrimpX;
    private int shrimpY;
    private int shrimpSpeed;

    // Shark
    private int sharkX;
    private int sharkY;
    private int sharkSpeed;

    // Score
    private int score;

    // Life
    private int life_count;

    FlappyGameStatus(String name) {
        this.name = name;
    }

    private FlappyGameStatus(Parcel in){
        name = in.readString();
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

    void setGameEasy() {
        shrimpSpeed = SHRIMP_SPEED_EASY;
        sharkSpeed = SHARK_SPEED_EASY;
        this.difficulty = "EASY";
    }

    void setGameHard() {
        shrimpSpeed = SHRIMP_SPEED_HARD;
        sharkSpeed = SHARK_SPEED_HARD;
        this.difficulty = "HARD";
    }

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

    String getName() {
        return this.name;
    }

    int getFishX() {
        return fishX;
    }

    int getFishY() {
        return fishY;
    }

    void fishMove(int minY, int maxY) {
        fishY += fishSpeed;
        if (fishY < minY) {
            fishY = minY;
        }
        if (fishY > maxY) {
            fishY = maxY;
        }
    }

    void setFishJumpSpeed() {
        fishSpeed = JUMP_SPEED;
    }

    void setFishFallSpeed() {
        fishSpeed += DROP_SPEED;
    }


    int getShrimpX() {
        return shrimpX;
    }

    int getShrimpY() {
        return shrimpY;
    }

    void shrimpGone() {
        this.shrimpX = DEAD_POS;
    }

    void shrimpMove() {
        shrimpX -= shrimpSpeed;
    }

    void shrimpValidCheck(int canvasWidth, int minY, int maxY) {
        if (shrimpX < 0) {
            shrimpX = canvasWidth + 10;
            shrimpY = (int) Math.floor(Math.random() * (maxY - minY)) + minY; // TODO: feel like need to change this
        }
    }

    int getSharkX() {
        return sharkX;
    }

    int getSharkY() {
        return sharkY;
    }

    void sharkGone() {
        this.sharkX = DEAD_POS;
    }

    void sharkMove() {
        sharkX -= sharkSpeed;
    }

    void sharkValidCheck(int canvasWidth, int minY, int maxY) {
        if (sharkX < 0) {
            sharkX = canvasWidth + 10;
            sharkY = (int) Math.floor(Math.random() * (maxY - minY)) + minY; // TODO: feel like need to change this
        }
    }

    String getDifficulty(){
        return this.difficulty;
    }

    int getScore() {
        return score;
    }

    void updateScore() {
        this.score += DEFAULT_SCORE;
    }

    int getLife_count() {
        return life_count;
    }

    void reduceLife_count() {
        life_count--;
    }

    boolean getPlayed() {
        return played != 0;
    }
    private void setPlayed(boolean played) {
        if(played){
            this.played = 1 ;}
        else{
            this.played = 0;
        }
    }

    void startUpdate() {
        setPlayed(true);
        fishX = FISH_X;
        fishY = FISH_Y;
        life_count = 3;
        score = 0;
    }

    void finishUpdate() {
        played = 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
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
