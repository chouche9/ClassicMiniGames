package com.example.myapplication.spaceshooter.ShooterGameStatus;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.spaceshooter.GameObject.ShooterBonus;
import com.example.myapplication.spaceshooter.GameObject.ShooterBullet1;
import com.example.myapplication.spaceshooter.GameObject.ShooterBullet2;
import com.example.myapplication.spaceshooter.GameObject.ShooterEnemy1;
import com.example.myapplication.spaceshooter.GameObject.ShooterEnemyExplosion;
import com.example.myapplication.spaceshooter.GameObject.ShooterHealthAid;
import com.example.myapplication.spaceshooter.GameObject.ShooterPlane;
import com.example.myapplication.spaceshooter.GameObject.ShooterPlaneExplosion;
import com.example.myapplication.spaceshooter.GameObject.ShooterPointBuff;

import java.util.ArrayList;
import java.util.List;

public class ShooterGameLevelManager implements Parcelable {
    private List<ShooterEnemy1> enemy1s = new ArrayList<>();
    private List<ShooterBullet1> bullet1s = new ArrayList<>();
    private List<ShooterBullet2> bullet2s = new ArrayList<>();
    private List<ShooterBonus> shooterBonuses = new ArrayList<>();
    private List<ShooterHealthAid> healthAids = new ArrayList<>();
    private List<ShooterPointBuff> pointBuffs = new ArrayList<>();
    private List<ShooterEnemyExplosion> enemyExplosions = new ArrayList<>();
    private List<ShooterPlaneExplosion> planeExplosions = new ArrayList<>();
    private ShooterPlane plane;
    private int millsecondLeft;

    public static int initaltime = 30000;


    public ShooterGameLevelManager(){
        this.millsecondLeft = initaltime;
    }

    protected ShooterGameLevelManager(Parcel in) {
        enemy1s = in.createTypedArrayList(ShooterEnemy1.CREATOR);
        bullet1s = in.createTypedArrayList(ShooterBullet1.CREATOR);
        bullet2s = in.createTypedArrayList(ShooterBullet2.CREATOR);
        shooterBonuses = in.createTypedArrayList(ShooterBonus.CREATOR);
        healthAids = in.createTypedArrayList(ShooterHealthAid.CREATOR);
        pointBuffs = in.createTypedArrayList(ShooterPointBuff.CREATOR);
        enemyExplosions = in.createTypedArrayList(ShooterEnemyExplosion.CREATOR);
        planeExplosions = in.createTypedArrayList(ShooterPlaneExplosion.CREATOR);
        plane = in.readParcelable(ShooterPlane.class.getClassLoader());
        millsecondLeft = in.readInt();
    }

    public static final Creator<ShooterGameLevelManager> CREATOR = new Creator<ShooterGameLevelManager>() {
        @Override
        public ShooterGameLevelManager createFromParcel(Parcel in) {
            return new ShooterGameLevelManager(in);
        }

        @Override
        public ShooterGameLevelManager[] newArray(int size) {
            return new ShooterGameLevelManager[size];
        }
    };

    void resetLevel(){
        if (plane != null){
            plane.resetPosition();}
        millsecondLeft = initaltime;
        shooterBonuses = new ArrayList<>();
        bullet1s = new ArrayList<>();
        bullet2s = new ArrayList<>();
        enemy1s = new ArrayList<>();
        healthAids = new ArrayList<>();
        pointBuffs = new ArrayList<>();
        enemyExplosions = new ArrayList<>();
        planeExplosions = new ArrayList<>();
    }
    void resetGame(){
        resetLevel();
        plane = null;
    }
    public void setPlane(int planeNum, Context context){
        plane = new ShooterPlane(context, planeNum);
    }

    public List<ShooterEnemy1> getEnemy1s() {
        return enemy1s;
    }

    public List<ShooterBullet1> getBullet1s() {
        return bullet1s;
    }

    public List<ShooterBullet2> getBullet2s() {
        return bullet2s;
    }

    public List<ShooterBonus> getShooterBonuses() {
        return shooterBonuses;
    }

    public List<ShooterHealthAid> getHealthAids() {
        return healthAids;
    }

    public List<ShooterPointBuff> getPointBuffs() {
        return pointBuffs;
    }

    public List<ShooterEnemyExplosion> getEnemyExplosions() {
        return enemyExplosions;
    }

    public List<ShooterPlaneExplosion> getPlaneExplosions() {
        return planeExplosions;
    }

    public ShooterPlane getPlane() {
        return plane;
    }

    public int getMillsecondLeft() {
        return millsecondLeft;
    }

    public void setMillsecondLeft(int millsecondLeft) {
        this.millsecondLeft = millsecondLeft;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(enemy1s);
        parcel.writeTypedList(bullet1s);
        parcel.writeTypedList(bullet2s);
        parcel.writeTypedList(shooterBonuses);
        parcel.writeTypedList(healthAids);
        parcel.writeTypedList(pointBuffs);
        parcel.writeTypedList(enemyExplosions);
        parcel.writeTypedList(planeExplosions);
        parcel.writeParcelable(plane, i);
        parcel.writeInt(millsecondLeft);
    }
}
