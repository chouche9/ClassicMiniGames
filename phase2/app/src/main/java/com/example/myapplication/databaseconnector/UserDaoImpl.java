package com.example.myapplication.databaseconnector;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Domain.User;
import com.google.gson.Gson;

import java.util.HashMap;

public class UserDaoImpl implements UserDao {

  private static UserDaoImpl userDao;

  private DBHandler dbHandler;

  private HashMap<GameEnum, String> tables;

  private Gson gson = new Gson();

  private UserDaoImpl(Activity activity) {
    this.dbHandler = DBHandler.getInstance(activity);
    this.tables = dbHandler.getTables();
  }

  public static UserDaoImpl getInstance(Activity activity) {
    if (userDao == null) {
      userDao = new UserDaoImpl(activity);
    }
    return userDao;
  }

  @Override
  public void saveUser(User user) {

    // Gets the data repository in write mode
    SQLiteDatabase database = dbHandler.getWritableDatabase();

    String json = gson.toJson(user);

    // Create a new map of values, where column names are the keys
    ContentValues userValues = new ContentValues();
    userValues.put(DBHandler.COLUMN_USERNAME, user.getName());
    userValues.put(DBHandler.COLUMN_USER_INSTANCE, json);
    database.insert(DBHandler.TABLE_USER, null, userValues);

    // Store the username into every game tables
    ContentValues usernameValue = new ContentValues();
    for (GameEnum type : GameEnum.values()) {
      usernameValue.put(DBHandler.COLUMN_USERNAME, user.getName());
      database.insert(tables.get(type), null, usernameValue);
    }
  }

  @Override
  public User getUser(String username) {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    String query =
        " SELECT "
            + DBHandler.COLUMN_USER_INSTANCE
            + " FROM "
            + DBHandler.TABLE_USER
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
      return gson.fromJson(json, User.class);
    } else {
      return null;
    }
  }
}
