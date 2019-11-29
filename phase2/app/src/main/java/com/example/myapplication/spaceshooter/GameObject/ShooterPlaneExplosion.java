package com.example.myapplication.spaceshooter.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.R;

public class ShooterPlaneExplosion extends ShooterExplosion implements Parcelable {

    public ShooterPlaneExplosion(Context context, int explosionX, int explosionY){
        super(explosionX, explosionY);
        explosions = new Bitmap[5];
        explosions[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion1);
        explosions[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion2);
        explosions[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion3);
        explosions[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion4);
        explosions[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion5);
        setWidthHeight();

    }

    protected ShooterPlaneExplosion(Parcel in) {
        super(in);
    }

    public static final Creator<ShooterPlaneExplosion> CREATOR = new Creator<ShooterPlaneExplosion>() {
        @Override
        public ShooterPlaneExplosion createFromParcel(Parcel in) {
            return new ShooterPlaneExplosion(in);
        }

        @Override
        public ShooterPlaneExplosion[] newArray(int size) {
            return new ShooterPlaneExplosion[size];
        }
    };

    Bitmap getExplosion(int frame){
        return explosions[frame];
    }

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
        explosions = new Bitmap[5];
        explosions[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion1);
        explosions[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion2);
        explosions[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion3);
        explosions[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion4);
        explosions[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion5);
    }


}
