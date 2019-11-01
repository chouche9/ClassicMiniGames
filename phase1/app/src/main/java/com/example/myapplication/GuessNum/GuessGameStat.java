package com.example.myapplication.GuessNum;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.GameStatus;

public class GuessGameStat extends GameStatus implements Parcelable {
    private String color;
    private int currentTries = 0;
    private int bestTries = -1;
    private int played = 0;
    private int lastNum;
    private String type;

    public GuessGameStat(String name){
        super(name);
        this.type = "GuessGameStat";
    }

    protected GuessGameStat(Parcel in) {
        super(in);
        color = in.readString();
        currentTries = in.readInt();
        bestTries = in.readInt();
        played = in.readInt();
        lastNum = in.readInt();
        type = in.readString();
    }

    public void setLastNum(int lastNum) {
        this.lastNum = lastNum;
    }

    public int getLastNum() {
        return lastNum;
    }

    public static final Creator<GuessGameStat> CREATOR = new Creator<GuessGameStat>() {
        @Override
        public GuessGameStat createFromParcel(Parcel in) {
            return new GuessGameStat(in);
        }

        @Override
        public GuessGameStat[] newArray(int size) {
            return new GuessGameStat[size];
        }
    };

    public void setCurrentTries() {
        this.currentTries ++;
    }
    public void setCurrentTries(int cur) {
        this.currentTries  = cur;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public String getColor() {
        return color;
    }

    public int getBestTries() {
        return bestTries;
    }
    public boolean played(){
        if (played == 0){
            return false;
        }
        else{
            return true;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(color);
        parcel.writeInt(currentTries);
        parcel.writeInt(bestTries);
        parcel.writeInt(played);
        parcel.writeInt(lastNum);
        parcel.writeString(type);
    }
    public void finishUpdate(int finalTries){
        if (bestTries == -1 || bestTries > finalTries){
            bestTries = finalTries;
        }
        currentTries = 0;
        lastNum = 0;
        played = 0;
    }
    public void startUpdate(){
        this.setPlayed(true);
        this.setCurrentTries(0);
        this.setLastNum(0);
    }
    public int getCurrentTries(){
        return currentTries;
    }

    public void setPlayed(boolean played) {
        if(played){
            this.played = 1 ;}
        else{
            this.played = 0;
        }
    }
}