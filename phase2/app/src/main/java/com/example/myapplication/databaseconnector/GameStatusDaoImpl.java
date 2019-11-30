package com.example.myapplication.databaseconnector;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.domain.GameStatus;
import com.google.gson.Gson;

/** The implementation of the GameStatusDao. */
public class GameStatusDaoImpl implements GameStatusDao {

  /** The singleton gameStatusDao. */
  private static GameStatusDaoImpl gameStatusDao;

  /** DBHandler object that manages the database. */
  private DBHandler dbHandler;

  /** Gson that handles json. */
  private Gson gson = new Gson();

  /**
   * Construct this GameStatusDaoImpl.
   *
   * @param activity the activity that called this object.
   */
  private GameStatusDaoImpl(Activity activity) {
    this.dbHandler = DBHandler.getInstance(activity);
  }

  /**
   * Get the singleton instance of this GameStatusDaoImpl.
   *
   * @param activity the activity that called this object.
   * @return the singleton instance of this GameStatusDaoImpl.
   */
  public static GameStatusDaoImpl getInstance(Activity activity) {
    if (gameStatusDao == null) {
      gameStatusDao = new GameStatusDaoImpl(activity);
    }
    return gameStatusDao;
  }

  /**
   * Save the game status according to its game type.
   *
   * @param gameStatus the game status to be saved into the database.
   * @param type the type of the game.
   */
  @Override
  public void saveGameStatus(GameStatus gameStatus, GameEnum type) {
    String json = gson.toJson(gameStatus);
    SQLiteDatabase database = dbHandler.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(DBHandler.COLUMN_STATUS, json);
    database.update(
            dbHandler.getTables().get(type),
            values,
            DBHandler.COLUMN_USERNAME + " = " + "'" + gameStatus.getName() + "'",
            null);
  }

  /**
   * Get the game status according to the the username and game type.
   *
   * @param username the username of the user.
   * @param type the type of the game.
   * @return the game status with specified game type which belongs to a user with username.
   */
  @Override
  public GameStatus getGameStatus(String username, GameEnum type) {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    String query =
            "SELECT "
                    + DBHandler.COLUMN_STATUS
                    + " FROM "
                    + dbHandler.getTables().get(type)
                    + " WHERE "
                    + DBHandler.COLUMN_USERNAME
                    + " = "
                    + "'"
                    + username
                    + "'";

    Cursor cursor = database.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      String json = cursor.getString(0);
      cursor.close();

      return gson.fromJson(json, dbHandler.getGameTypeClass().get(type));
    }
    return null;
  }
}
