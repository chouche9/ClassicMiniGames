package com.example.myapplication.spaceshooter.ShooterGameStatus;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.spaceshooter.GameObject.ShooterEnemy1;

public class ShooterCrossLevelManager implements Parcelable {
    private int level;
    private int point;
    private boolean levelFinish;
    private boolean gameSuccess;

    public ShooterCrossLevelManager(){
        point = 0;
        gameSuccess = true;
        level = 1;
        levelFinish = false;
    }

    protected ShooterCrossLevelManager(Parcel in) {
        level = in.readInt();
        point = in.readInt();
        levelFinish = in.readByte() != 0;
        gameSuccess = in.readByte() != 0;
    }

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

    void resetLevel(){
        levelFinish = false;
    }
    void resetGame(){
        updateLevel(1);
        levelFinish = false;
        gameSuccess = true;
        point = 0;
    }
    public void updateLevel(int level){
        this.level = level;
        ShooterEnemy1.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isLevelFinish() {
        return levelFinish;
    }

    public void setLevelFinish(boolean levelFinish) {
        this.levelFinish = levelFinish;
    }

    public boolean isGameSuccess() {
        return gameSuccess;
    }

    public void setGameSuccess(boolean gameSuccess) {
        this.gameSuccess = gameSuccess;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(level);
        parcel.writeInt(point);
        parcel.writeByte((byte) (levelFinish ? 1 : 0));
        parcel.writeByte((byte) (gameSuccess ? 1 : 0));
    }
}
