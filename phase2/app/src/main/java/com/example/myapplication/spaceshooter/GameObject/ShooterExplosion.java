package com.example.myapplication.spaceshooter.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;

public abstract class ShooterExplosion extends ShooterItem implements Parcelable {
    Bitmap[] explosions;
    int explosionFrame = 0;
    ShooterExplosion(int x, int y) {
        super(x, y);
    }

    ShooterExplosion(Parcel in){
        super(in);
        explosionFrame = in.readInt();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(explosions[explosionFrame], getX(), getY(), null);
        explosionFrame ++;
    }
    public boolean checkFrameValid(){
        return explosionFrame < explosions.length;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(explosionFrame);
    }
    public abstract void setUpBitmap(Context context);

    @Override
    void setWidthHeight(){
        setWidth(explosions[0].getWidth());
        setHeight(explosions[1].getHeight());
    };
}