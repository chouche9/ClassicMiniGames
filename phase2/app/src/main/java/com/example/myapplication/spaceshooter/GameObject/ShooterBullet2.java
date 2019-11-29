package com.example.myapplication.spaceshooter.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.R;


public class ShooterBullet2 extends ShooterGameObject implements Parcelable {
    public ShooterBullet2(Context context, int pX, int pY){
        object = BitmapFactory.decodeResource(context.getResources(), R.drawable.psbullet2);
        object = Bitmap.createScaledBitmap(
                object, 50, 50, false);
        setWidthHeight();
        setX(pX - getWidth()/2);
        setY(pY);
        velocity = 80;
    }

    ShooterBullet2(Parcel in) {
        super(in);
    }

    public static final Creator<ShooterBullet2> CREATOR = new Creator<ShooterBullet2>() {
        @Override
        public ShooterBullet2 createFromParcel(Parcel in) {
            return new ShooterBullet2(in);
        }

        @Override
        public ShooterBullet2[] newArray(int size) {
            return new ShooterBullet2[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public void setUpBitmap(Context context) {
        object = BitmapFactory.decodeResource(context.getResources(), R.drawable.psbullet2);
        object = Bitmap.createScaledBitmap(
                object, 50, 50, false);
    }

}
