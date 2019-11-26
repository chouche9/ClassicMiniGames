package com.example.myapplication.SpaceShooter.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;

import com.example.myapplication.R;
import com.example.myapplication.SpaceShooter.ShooterGameStatus;

public class ShooterHealthAid extends ShooterSpecialItem {
    public ShooterHealthAid(Context context){
        super();
        object = BitmapFactory.decodeResource(context.getResources(), R.drawable.pshealth);
        object = Bitmap.createScaledBitmap(
                object, 150, 150, false);
    }
    ShooterHealthAid(Parcel in){
        super(in);
    }

    @Override
    public void getBuff(ShooterGameStatus shooterGameStatus) {
        shooterGameStatus.plane.life += 5;
        shooterGameStatus.plane.life = Math.min(shooterGameStatus.plane.life, 10);
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(CLASS_TYPE_ONE);
        super.writeToParcel(out, flags);
    }

    @Override
    public void setUpBitmap(Context context) {
        object = BitmapFactory.decodeResource(context.getResources(), R.drawable.pshealth);
        object = Bitmap.createScaledBitmap(
                object, 150, 150, false);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

