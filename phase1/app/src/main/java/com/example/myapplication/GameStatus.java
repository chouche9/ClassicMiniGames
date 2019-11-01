package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class GameStatus implements Parcelable {
    private String name;
    public GameStatus(String name){
        this.name = name;
    }

    protected GameStatus(Parcel in) {
        name = in.readString();
    }

    public static final Creator<GameStatus> CREATOR = new Creator<GameStatus>() {
        @Override
        public GameStatus createFromParcel(Parcel in) {
            return new GameStatus(in);
        }

        @Override
        public GameStatus[] newArray(int size) {
            return new GameStatus[size];
        }
    };

    public String getName(){
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }
}
