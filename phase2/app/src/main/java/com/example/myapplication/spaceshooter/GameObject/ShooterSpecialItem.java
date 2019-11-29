package com.example.myapplication.spaceshooter.GameObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.spaceshooter.ShooterGameStatus;
import com.example.myapplication.spaceshooter.shootergameview.ShooterGameView;

import java.util.Random;

public abstract class ShooterSpecialItem extends ShooterGameObject implements Parcelable{

    ShooterSpecialItem(){
        super();
        Random random = new Random();
        setX(random.nextInt(ShooterGameView.dWidth - 150));
        setY(-60);
        velocity = 30;
    }
    ShooterSpecialItem(Parcel in){
        super(in);
    }


    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
    }
    public abstract void getBuff(ShooterGameStatus shooterGameStatus);
}

