package com.example.myapplication.spaceshooter.GameObject;

import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;

public abstract class ShooterItem implements Parcelable {
    private int x;
    private int y;
    private int width, height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int weight) {
        width = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    ShooterItem(int x, int y){
        this.x = x;
        this.y = y;
    }
    ShooterItem(){

    }

    protected ShooterItem(Parcel in) {
        x = in.readInt();
        y = in.readInt();
        width = in.readInt();
        height = in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(x);
        dest.writeInt(y);
        dest.writeInt(width);
        dest.writeInt(height);
    }
    public abstract void onDraw(Canvas canvas);
    abstract void setWidthHeight();
}