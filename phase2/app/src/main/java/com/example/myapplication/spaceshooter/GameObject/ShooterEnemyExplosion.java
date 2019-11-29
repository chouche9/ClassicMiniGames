package com.example.myapplication.spaceshooter.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.R;

public class ShooterEnemyExplosion extends ShooterExplosion implements Parcelable {

    public ShooterEnemyExplosion(Context context, int explosionX, int explosionY) {
        super(explosionX, explosionY);
        explosions = new Bitmap[9];
        explosions[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion0);
        explosions[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion1);
        explosions[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion2);
        explosions[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion3);
        explosions[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion4);
        explosions[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion5);
        explosions[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion6);
        explosions[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion7);
        explosions[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion8);
        setWidthHeight();
    }

    protected ShooterEnemyExplosion(Parcel in) {
        super(in);
    }

    public static final Creator<ShooterEnemyExplosion> CREATOR = new Creator<ShooterEnemyExplosion>() {
        @Override
        public ShooterEnemyExplosion createFromParcel(Parcel in) {
            return new ShooterEnemyExplosion(in);
        }

        @Override
        public ShooterEnemyExplosion[] newArray(int size) {
            return new ShooterEnemyExplosion[size];
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
        explosions = new Bitmap[9];
        explosions[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion0);
        explosions[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion1);
        explosions[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion2);
        explosions[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion3);
        explosions[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion4);
        explosions[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion5);
        explosions[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion6);
        explosions[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion7);
        explosions[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psexplosion8);
    }

}
