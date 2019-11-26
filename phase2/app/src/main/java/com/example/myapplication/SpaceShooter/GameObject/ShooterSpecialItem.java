package com.example.myapplication.SpaceShooter.GameObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.SpaceShooter.ShooterGameStatus;
import com.example.myapplication.SpaceShooter.shootergameview.ShooterGameView;

import java.util.Random;

public abstract class ShooterSpecialItem extends ShooterGameObject implements Parcelable{
     static final int CLASS_TYPE_ONE = 1;
     static final int CLASS_TYPE_TWO = 2;

    ShooterSpecialItem(){
        super();
        Random random = new Random();
        setX(random.nextInt(ShooterGameView.dWidth - 90));
        setY(-60);
        velocity = 30;
    }
    ShooterSpecialItem(Parcel in){
        super(in);
    }

    public static final Creator<ShooterSpecialItem> CREATOR = new Creator<ShooterSpecialItem>() {
        @Override
        public ShooterSpecialItem createFromParcel(Parcel in) {
            switch (in.readInt()){
                case CLASS_TYPE_ONE:
                    return new ShooterHealthAid(in);
                case CLASS_TYPE_TWO:
                    return new ShooterPointBuff(in);
                default:
                    return null;
            }


        }
        @Override
        public ShooterSpecialItem[] newArray(int size) {
            return new ShooterSpecialItem[size];
        }
    };
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
    }
    public abstract void getBuff(ShooterGameStatus shooterGameStatus);
}

