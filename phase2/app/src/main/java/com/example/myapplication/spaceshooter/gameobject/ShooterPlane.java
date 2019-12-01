package com.example.myapplication.spaceshooter.gameobject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.R;

/**
 * The type Shooter plane.
 */
public class ShooterPlane extends ShooterGameObject implements Parcelable {
    /**
     * the plane's life number
     */
    private int life = 10;
    /**
     * the plane number player choose
     */
    private int planeStyle = 1;

    /**
     * Instantiates a new Shooter plane.
     *
     * @param context    the context to get plane picture
     * @param planeStyle the plane style
     */
    public ShooterPlane(Context context, int planeStyle) {
        int dWidth = 1080;
        int dHeight = 2028;
        this.planeStyle = planeStyle;
        if (planeStyle == 1) {
            setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psplane1));
        } else if (planeStyle == 2) {
            setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psplane2));
        } else if (planeStyle == 3) {
            setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psplane3));
        }
        setObject(Bitmap.createScaledBitmap(
                getObject(), 300, 200, false));
        setX(dWidth / 2 - 150);
        setY(dHeight - 300);
        setWidthHeight();

    }

    /**
     * Instantiates a new Shooter plane from parcel
     *
     * @param in the parcel
     */
    private ShooterPlane(Parcel in) {
        super(in);
        life = in.readInt();
        planeStyle = in.readInt();
    }

    /**
     * write plane attribute to a parcel
     *
     * @param dest  parcel to write
     * @param flags the flag integer
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(life);
        dest.writeInt(planeStyle);
    }

    /**
     * set up bitmap for plane
     *
     * @param context the context to get plane picture
     */
    @Override
    public void setUpBitmap(Context context) {
        if (planeStyle == 1) {
            setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psplane1));
        } else if (planeStyle == 2) {
            setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psplane2));
        } else if (planeStyle == 3) {
            setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psplane3));
        }
        setObject(Bitmap.createScaledBitmap(
                getObject(), 300, 200, false));
    }

    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * The constant CREATOR.
     */
    public static final Creator<ShooterPlane> CREATOR = new Creator<ShooterPlane>() {
        @Override
        public ShooterPlane createFromParcel(Parcel in) {
            return new ShooterPlane(in);
        }

        @Override
        public ShooterPlane[] newArray(int size) {
            return new ShooterPlane[size];
        }
    };

    /**
     * where the planer's touch is inside plane's location range.
     *
     * @param touchX the touch x
     * @param touchY the touch y
     * @return the boolean
     */
    public boolean touchInRange(float touchX, float touchY) {
        return getX() <= touchX && touchX <= getX() + getWidth()
                && getY() <= touchY && touchY <= getY() + getHeight();
    }

    /**
     * Reset position of plane
     */
    public void resetPosition() {
        setX(1080 / 2 - 150);
        setY(2028 - 300);
    }

    /**
     * Gets life of the plane
     *
     * @return the life
     */
    public int getLife() {
        return life;
    }

    /**
     * Sets life of the plane
     *
     * @param life the life
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * Gets plane style.
     *
     * @return the plane style
     */
    public int getPlaneStyle() {
        return planeStyle;
    }

    /**
     * Sets plane style.
     *
     * @param planeStyle the plane style
     */
    public void setPlaneStyle(int planeStyle) {
        this.planeStyle = planeStyle;
    }
}
