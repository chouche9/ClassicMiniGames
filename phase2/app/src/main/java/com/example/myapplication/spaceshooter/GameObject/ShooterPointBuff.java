package com.example.myapplication.spaceshooter.GameObject;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;

import com.example.myapplication.R;
import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterGameStatusFacade;

public class ShooterPointBuff extends ShooterSpecialItem {
    public ShooterPointBuff(Context context){
        super();
        setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psgold));
        setObject(Bitmap.createScaledBitmap(
                getObject(), 150, 150, false));
    }

    ShooterPointBuff(Parcel in){
        super(in);
    }
    @Override
    public void getBuff(ShooterGameStatusFacade shooterGameStatus) {
        shooterGameStatus.addPoint(50);
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
    }
    public static final Creator<ShooterPointBuff> CREATOR = new Creator<ShooterPointBuff>() {
        @Override
        public ShooterPointBuff createFromParcel(Parcel in) {
            return new ShooterPointBuff(in);
        }

        @Override
        public ShooterPointBuff[] newArray(int size) {
            return new ShooterPointBuff[size];
        }
    };

    @Override
    public void setUpBitmap(Context context) {
        setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psgold));
        setObject(Bitmap.createScaledBitmap(
                getObject(), 150, 150, false));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

