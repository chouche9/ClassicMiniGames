package com.example.myapplication.flappyfish;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class FlappyBackgroundMusic extends Service {

  private MediaPlayer player;

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  public void onCreate() {
    super.onCreate();
    player = MediaPlayer.create(this, R.raw.under_the_sea);
    player.setLooping(true);
    player.setVolume(100, 100);
    player.start();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    player.release();
  }
}
