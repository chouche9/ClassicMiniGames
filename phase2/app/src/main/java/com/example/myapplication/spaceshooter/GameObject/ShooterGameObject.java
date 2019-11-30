package com.example.myapplication.spaceshooter.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Parcel;

public abstract class ShooterGameObject extends ShooterItem {
    private Bitmap object;
    private int velocity;
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

    public Bitmap getObject() {
        return object;
    }

    public void setObject(Bitmap object) {
        this.object = object;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
}

