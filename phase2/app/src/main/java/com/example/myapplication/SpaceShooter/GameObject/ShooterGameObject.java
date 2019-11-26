package com.example.myapplication.SpaceShooter.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Parcel;

public abstract class ShooterGameObject extends ShooterItem {
    Bitmap object;
    public int velocity;
    ShooterGameObject(int x, int y) {
        super(x, y);
    }
    ShooterGameObject(){}

    ShooterGameObject(Parcel in){
        super(in);
        velocity = in.readInt();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(object, getX(), getY(), null);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(velocity);
    }

    public abstract void setUpBitmap(Context context);
    void cleanBitmap(){
        object = null;
    };

    @Override
    void setWidthHeight() {
        setWidth(object.getWidth());
        setHeight(object.getHeight());
    }
}

