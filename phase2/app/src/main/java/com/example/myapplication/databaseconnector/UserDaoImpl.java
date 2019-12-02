package com.example.myapplication.databaseconnector;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.domain.User;
import com.example.myapplication.gameenum.GameEnum;
import com.google.gson.Gson;

/** The implementation of the UserDao. */
public class UserDaoImpl implements UserDao {

  /** The singleton userDao. */
  private static UserDaoImpl userDao;

  /** DBHandler object that manages the database. */
  private DBHandler dbHandler;

  /** Gson that handles json. */
  private Gson gson = new Gson();

  /**
   * Construct this UserDaoImpl.
   *
   * @param activity the activity that called this object.
   */
  private UserDaoImpl(Activity activity) {
    this.dbHandler = DBHandler.getInstance(activity);
  }

  /**
   * Get the singleton instance of this UserDaoImpl.
   *
   * @param activity the activity that called this object.
   * @return the singleton instance of this UserDaoImpl.
   */
  public static UserDaoImpl getInstance(Activity activity) {
    if (userDao == null) {
      userDao = new UserDaoImpl(activity);
    }
    return userDao;
  }

  /**
   * Save the user into the database.
   *
   * @param user the user to be saved into the database.
   */
  @Override
  public void saveUser(User user) {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    String json = gson.toJson(user);

    ContentValues userValues = new ContentValues();
    userValues.put(DBHandler.COLUMN_USERNAME, user.getName());
    userValues.put(DBHandler.COLUMN_USER_INSTANCE, json);
    database.insert(DBHandler.TABLE_USER, null, userValues);

    ContentValues usernameValue = new ContentValues();
    for (GameEnum type : GameEnum.values()) {
      usernameValue.put(DBHandler.COLUMN_USERNAME, user.getName());
      database.insert(dbHandler.getTables().get(type), null, usernameValue);
    }
  }

  /**
   * Get the user object with the specified username from the database.
   *
   * @param username the username of the user.
   * @return the user object with the specified username.
   */
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
