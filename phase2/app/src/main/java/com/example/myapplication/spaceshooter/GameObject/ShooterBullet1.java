package com.example.myapplication.spaceshooter.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.R;

public class ShooterBullet1 extends ShooterGameObject implements Parcelable {
    public ShooterBullet1(Context context, int pX, int pY){
        object = BitmapFactory.decodeResource(context.getResources(), R.drawable.psbullet);
        object = Bitmap.createScaledBitmap(
                object, 50, 50, false);
        setWidthHeight();
        setX(pX - getWidth()/2);
        setY(pY);
        velocity = 80;

    }

    protected ShooterBullet1(Parcel in) {
        super(in);
    }

    public static final Creator<ShooterBullet1> CREATOR = new Creator<ShooterBullet1>() {
        @Override
        public ShooterBullet1 createFromParcel(Parcel in) {
            return new ShooterBullet1(in);
        }

        @Override
        public ShooterBullet1[] newArray(int size) {
            return new ShooterBullet1[size];
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
        object = BitmapFactory.decodeResource(context.getResources(), R.drawable.psbullet);
        object = Bitmap.createScaledBitmap(
                object, 50, 50, false);
    }
}

