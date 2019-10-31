package com.example.myapplication.GuessNum;

import android.os.Parcel;
import android.os.Parcelable;
public class GuessGameStat implements Parcelable{
    private String name;
    private String color;
    private int currentTries = 0;
    private int bestTries = -1;
    private int played = 0;
    private int lastNum;

    public GuessGameStat(String name){
        this.name = name;
    }

    protected GuessGameStat(Parcel in) {
        name = in.readString();
        color = in.readString();
        currentTries = in.readInt();
        bestTries = in.readInt();
        played = in.readInt();
        lastNum = in.readInt();
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

    public String getName() {
        return name;
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
        parcel.writeString(name);
        parcel.writeString(color);
        parcel.writeInt(currentTries);
        parcel.writeInt(bestTries);
        parcel.writeInt(played);
        parcel.writeInt(lastNum);
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