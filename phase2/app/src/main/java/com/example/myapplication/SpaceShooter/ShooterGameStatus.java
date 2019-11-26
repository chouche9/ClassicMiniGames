package com.example.myapplication.SpaceShooter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.GameStatus;
import com.example.myapplication.SpaceShooter.GameObject.ShooterBullet1;
import com.example.myapplication.SpaceShooter.GameObject.ShooterBullet2;
import com.example.myapplication.SpaceShooter.GameObject.ShooterEnemy1;
import com.example.myapplication.SpaceShooter.GameObject.ShooterEnemyExplosion;
import com.example.myapplication.SpaceShooter.GameObject.ShooterPlane;
import com.example.myapplication.SpaceShooter.GameObject.ShooterPlaneExplosion;
import com.example.myapplication.SpaceShooter.GameObject.ShooterSpecialItem;

import java.util.ArrayList;
import java.util.List;


public class ShooterGameStatus extends GameStatus implements Parcelable {
    static int initaltime = 10000;
    public int level;
    public int point;
    int numCoin;
    public ShooterPlane plane;
    public int millsecondLeft;
    public boolean levelFinish;
    public boolean gameSuccess;
    // number of enemy hit
    public List<ShooterEnemy1> enemy1s = new ArrayList<>();
    public List<ShooterBullet1> bullet1s = new ArrayList<>();
    public List<ShooterBullet2> bullet2s = new ArrayList<>();
    public List<ShooterSpecialItem> specialItems = new ArrayList<>();
    public List<ShooterEnemyExplosion> enemyExplosions = new ArrayList<>();
    public List<ShooterPlaneExplosion> planeExplosions = new ArrayList<>();
    ShooterGameStatus(String name){
        super(name);
        point = 0;
        gameSuccess = true;
        numCoin = 0;
        level = 1;
        levelFinish = false;
        millsecondLeft = initaltime;

    }

    protected ShooterGameStatus(Parcel in) {
        super(in);
        level = in.readInt();
        point = in.readInt();
        numCoin = in.readInt();
        plane = in.readParcelable(ShooterPlane.class.getClassLoader());
        millsecondLeft = in.readInt();
        levelFinish = in.readByte() != 0;
        gameSuccess = in.readByte() != 0;
        enemy1s = in.createTypedArrayList(ShooterEnemy1.CREATOR);
        bullet1s = in.createTypedArrayList(ShooterBullet1.CREATOR);
        bullet2s = in.createTypedArrayList(ShooterBullet2.CREATOR);
        specialItems = in.createTypedArrayList(ShooterSpecialItem.CREATOR);
        enemyExplosions = in.createTypedArrayList(ShooterEnemyExplosion.CREATOR);
        planeExplosions = in.createTypedArrayList(ShooterPlaneExplosion.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(level);
        dest.writeInt(point);
        dest.writeInt(numCoin);
        dest.writeParcelable(plane, flags);
        dest.writeInt(millsecondLeft);
        dest.writeByte((byte) (levelFinish ? 1 : 0));
        dest.writeByte((byte) (gameSuccess ? 1 : 0));
        dest.writeTypedList(enemy1s);
        dest.writeTypedList(bullet1s);
        dest.writeTypedList(bullet2s);
        dest.writeTypedList(specialItems);
        dest.writeTypedList(enemyExplosions);
        dest.writeTypedList(planeExplosions);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShooterGameStatus> CREATOR = new Creator<ShooterGameStatus>() {
        @Override
        public ShooterGameStatus createFromParcel(Parcel in) {
            return new ShooterGameStatus(in);
        }

        @Override
        public ShooterGameStatus[] newArray(int size) {
            return new ShooterGameStatus[size];
        }
    };

    public void resetGameStatus(){
        plane.resetPosition();
        millsecondLeft = initaltime;
        bullet1s = new ArrayList<>();
        bullet2s = new ArrayList<>();
        enemy1s = new ArrayList<>();
        levelFinish = false;
        specialItems = new ArrayList<>();
        enemyExplosions = new ArrayList<>();
        planeExplosions = new ArrayList<>();
    }
    public void eraseGameStatus(){
        resetGameStatus();
        plane = null;
        updateLevel(1);
        levelFinish = false;
        gameSuccess = true;
        numCoin =0;
        point = 0;
    }
    public void updateLevel(int level){
        this.level = level;
        ShooterEnemy1.level = level;
    }
    void setPlane(int planeNum, Context context){
        plane = new ShooterPlane(context, planeNum);
    }
}

