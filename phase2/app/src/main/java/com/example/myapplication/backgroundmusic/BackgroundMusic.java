package com.example.myapplication.backgroundmusic;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.util.List;

/** An abstract class that is responsible for playing background music. */
public abstract class BackgroundMusic extends Service {

  /** A media player that plays the music. */
  private MediaPlayer mediaPlayer;

  /** The music that is played by the media player. */
  private int music;

  /**
   * Construct a new background music.
   *
   * @param music The music that is played in the background.
   */
  public BackgroundMusic(int music) {
    this.music = music;
  }

  /**
   * Return the communication channel to the service.
   *
   * @param intent The Intent that was used to bind to this service, as given to Context.
   * @return Return an IBinder through which clients can call on to the service.
   */
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  /** Called by the system when the service is first created. */
  @Override
  public void onCreate() {
    super.onCreate();
    mediaPlayer = MediaPlayer.create(this, music);
    mediaPlayer.setLooping(true);
    mediaPlayer.setVolume(100, 100);
    mediaPlayer.start();
  }

  /**
   * Called by the system every time a client explicitly starts the service by calling
   * Context.startService(Intent), providing the arguments it supplied and a unique integer token
   * representing the start request.
   *
   * @param intent The Intent supplied to Context.startService(Intent), as given.
   * @param flags Additional data about this start request.
   * @param startId A unique integer representing this specific request to start.
   * @return The return value indicates what semantics the system should use for the service's
   *     current started state.
   */
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    return START_STICKY;
  }

  /**
   * Called by the system to notify a Service that it is no longer used and is being removed. The
   * service should clean up any resources it holds (threads, registered receivers, etc) at this
   * point. Upon return, there will be no more calls in to this Service object and it is effectively
   * dead.
   */
  @Override
  public void onDestroy() {
    super.onDestroy();
    mediaPlayer.release();
  }

  /**
   * Check whether this app is the app which has been most recently sent to the background.
   *
   * @param context The current activity.
   * @return Return true if the app is sent to the background; otherwise, return false.
   */
  public static boolean isApplicationSentToBackground(final Context context) {
    ActivityManager activityManager =
        (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
    if (!tasks.isEmpty()) {
      ComponentName topActivity = tasks.get(0).topActivity;
      return !topActivity.getPackageName().equals(context.getPackageName());
    }
    return false;
  }
}
