package com.example.myapplication.spaceshooter.GameObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterGameStatusFacade;
import com.example.myapplication.spaceshooter.shootergameview.ShooterGameView;

import java.util.Random;

/**
 * The type Shooter special item.
 */
public abstract class ShooterSpecialItem extends ShooterGameObject implements Parcelable{

    /**
     * Instantiates a new Shooter special item.
     */
    ShooterSpecialItem(){
        super();
        Random random = new Random();
        setX(random.nextInt(ShooterGameView.dWidth - 150));
        setY(-60);
        setVelocity(30);
    }

    /**
     * Instantiates a new Shooter special item.
     *
     * @param in the in
     */
    ShooterSpecialItem(Parcel in){
        super(in);
    }


    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
    }

    /**
     * Gets buff method that change game status according to the buff
     *
     * @param shooterGameStatus the shooter game status
     */
    public abstract void getBuff(ShooterGameStatusFacade shooterGameStatus);
}

