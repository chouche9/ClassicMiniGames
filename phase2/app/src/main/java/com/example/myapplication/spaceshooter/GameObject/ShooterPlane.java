package com.example.myapplication.spaceshooter.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.R;

public class ShooterPlane extends ShooterGameObject implements Parcelable  {
    public int life = 10;
    int planeStyle = 1;
    public ShooterPlane(Context context, int planeStyle){
        int dWidth = 1080;
        int dHeight = 2028;
        this.planeStyle = planeStyle;
        if (planeStyle == 1){
            object = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplane1);}
        else if (planeStyle == 2){
            object = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplane2);}
        else if(planeStyle == 3){
            object = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplane3);
        }
        object = Bitmap.createScaledBitmap(
                object, 300, 200, false);
        setX(dWidth/2 - 150);
        setY(dHeight - 300);
        setWidthHeight();

    }

    ShooterPlane(Parcel in) {
        super(in);
        life = in.readInt();
        planeStyle = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(life);
        dest.writeInt(planeStyle);
    }

    @Override
    public void setUpBitmap(Context context) {
        if (planeStyle == 1){
            object = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplane1);}
        else if (planeStyle == 2){
            object = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplane2);}
        else if(planeStyle == 3){
            object = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplane3);
        }
        object = Bitmap.createScaledBitmap(
                object, 300, 200, false);
    }

    @Override
    public int describeContents() {
        return 0;
    }


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


    public void resetPosition(){
        setX(1080/2 - 150);
        setY(2028 - 300);
    }

}
