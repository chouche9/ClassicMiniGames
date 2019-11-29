package com.example.myapplication.spaceshooter.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;

import com.example.myapplication.R;
import com.example.myapplication.spaceshooter.shootergameview.ShooterGameView;

import java.util.Random;

public class ShooterBonus extends ShooterGameObject {

    public ShooterBonus(Context context){
        Random random = new Random();
        setX(random.nextInt(ShooterGameView.dWidth - 150));
        setY(-60);
        velocity = 30;
        object =  BitmapFactory.decodeResource(context.getResources(), R.drawable.treasure_chest);
        object = Bitmap.createScaledBitmap(
                object, 150, 150, false);
        setWidthHeight();
    }

    ShooterBonus(Parcel in){
        super(in);
    }
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Creator<ShooterBonus> CREATOR = new Creator<ShooterBonus>() {
        @Override
        public ShooterBonus createFromParcel(Parcel in) {
            return new ShooterBonus(in);
        }

        @Override
        public ShooterBonus[] newArray(int size) {
            return new ShooterBonus[size];
        }
    };

    @Override
    public void setUpBitmap(Context context) {
       object =  BitmapFactory.decodeResource(context.getResources(), R.drawable.treasure_chest);
       object = Bitmap.createScaledBitmap(
                object, 150, 150, false);
    }


}
