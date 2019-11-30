package com.example.myapplication.spaceshooter.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;

import com.example.myapplication.R;
import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterGameStatusFacade;

public class ShooterHealthAid extends ShooterSpecialItem {
    public ShooterHealthAid(Context context){
        super();
        setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.pshealth));
        setObject(Bitmap.createScaledBitmap(
                getObject(), 150, 150, false));
    }
    ShooterHealthAid(Parcel in){
        super(in);
    }

    @Override
    public void getBuff(ShooterGameStatusFacade shooterGameStatus) {
        ShooterPlane plane = shooterGameStatus.getPlane();
        plane.setLife(plane.getLife() + 5);
        plane.setLife(Math.min(plane.getLife(), 10));
    }
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
    }



    public static final Creator<ShooterHealthAid> CREATOR = new Creator<ShooterHealthAid>() {
        @Override
        public ShooterHealthAid createFromParcel(Parcel in) {
            return new ShooterHealthAid(in);
        }

        @Override
        public ShooterHealthAid[] newArray(int size) {
            return new ShooterHealthAid[size];
        }
    };

    @Override
    public void setUpBitmap(Context context) {
        setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.pshealth));
        setObject(Bitmap.createScaledBitmap(
                getObject(), 150, 150, false));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

