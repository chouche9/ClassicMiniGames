package com.example.myapplication.databaseconnector;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.domain.GameStatus;
import com.google.gson.Gson;

import java.util.HashMap;

public class GameStatusDaoImpl implements GameStatusDao {

  private static GameStatusDaoImpl gameStatusDao;

  private DBHandler dbHandler;

  private Gson gson = new Gson();

  private GameStatusDaoImpl(Activity activity) {
    this.dbHandler = DBHandler.getInstance(activity);
  }

  public static GameStatusDaoImpl getInstance(Activity activity) {
    if (gameStatusDao == null) {
      gameStatusDao = new GameStatusDaoImpl(activity);
    }
    return gameStatusDao;
  }

  @Override
  public void saveGameStatus(GameStatus gameStatus, GameEnum type) {
    String json = gson.toJson(gameStatus);

    // Gets the data repository in write mode
    SQLiteDatabase database = dbHandler.getWritableDatabase();

    // Create a new map of values, where column names are the keys
    ContentValues values = new ContentValues();

    values.put(DBHandler.COLUMN_STATUS, json);
    database.update(
        dbHandler.getTables().get(type),
        values,
        DBHandler.COLUMN_USERNAME + " = " + "'" + gameStatus.getName() + "'",
        null);
  }

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
