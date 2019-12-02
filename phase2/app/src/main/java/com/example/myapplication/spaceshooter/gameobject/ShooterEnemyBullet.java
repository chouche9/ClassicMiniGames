package com.example.myapplication.spaceshooter.gameobject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.R;

/** The type Shooter bullet 2. */
public class ShooterEnemyBullet extends ShooterGameObject implements Parcelable {
  /**
   * Instantiates a new Shooter bullet 2.
   *
   * @param context the context
   * @param pX the p x coordinate
   * @param pY the p y coordinate
   */
  public ShooterEnemyBullet(Context context, int pX, int pY) {
    setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psbullet2));
    setObject(Bitmap.createScaledBitmap(getObject(), 50, 50, false));
    setWidthHeight();
    setX(pX - getWidth() / 2);
    setY(pY);
    setVelocity(80);
  }

  /**
   * Instantiates a new Shooter bullet 2.
   *
   * @param in the in
   */
  private ShooterEnemyBullet(Parcel in) {
    super(in);
  }

  /** The constant CREATOR. */
  public static final Creator<ShooterEnemyBullet> CREATOR =
      new Creator<ShooterEnemyBullet>() {
        @Override
        public ShooterEnemyBullet createFromParcel(Parcel in) {
          return new ShooterEnemyBullet(in);
        }

        @Override
        public ShooterEnemyBullet[] newArray(int size) {
          return new ShooterEnemyBullet[size];
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
    setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psbullet2));
    setObject(Bitmap.createScaledBitmap(getObject(), 50, 50, false));
  }
}
