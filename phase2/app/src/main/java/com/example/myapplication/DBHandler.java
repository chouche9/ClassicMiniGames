package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.FlappyFish.FlappyGameStatus;
import com.example.myapplication.GuessNum.GuessGameStat;
import com.example.myapplication.Hangman.HangmanGameStatFacade;
import com.example.myapplication.SpaceShooter.ShooterGameStatus;
import com.google.gson.Gson;

import java.util.HashMap;

/** A database handler that executes SQL requests and manages a local database. */
public class DBHandler extends SQLiteOpenHelper {

  /** The singleton instance of this DBHandler. */
  private static DBHandler dbHandler;
  /** The version of the database. */
  private static final int DATABASE_VERSION = 1;
  /** The name of the database. */
  private static final String DATABASE_NAME = "gameDatabase.db";

  /** The name of the table that stores the users. */
  private static final String TABLE_USER = "User";

  // Columns
  /** The name of the column that stores the usernames. */
  private static final String COLUMN_USERNAME = "Username";
  /** The name of the column that stores the passwords. */
  private static final String COLUMN_USER_INSTANCE = "UserInstance";
  /** The name of the column that stores the game status. */
  private static final String COLUMN_STATUS = "GameStatus";
  /** The name of the column that stores the highest score. */
  private static final String COLUMN_HIGHEST_SCORE = "HighestScore";

  /** Gson used to convert objects into json and vice versa. */
  private Gson gson = new Gson();

  /** An enum that contains all the game types. */
  public enum Game {
    FLAPPYFISH,
    SPACESHOOTER,
    HANGMAN,
    GUESSNUM
  }

  /** Maps each type of game to its corresponding table name in the database. */
  private HashMap<Game, String> tables = new HashMap<>();

  /** Maps each type of game to its actual class type. */
  private HashMap<Game, Class<? extends GameStatus>> gameTypeClass = new HashMap<>();

  /** Constructs this DBHandler for creating, opening and managing the database. */
  private DBHandler(Activity activity) {
    super(activity, DATABASE_NAME, null, DATABASE_VERSION);
    putGameTypeClass();
    putTables();
  }

  /** Puts a key-value pair with key being the game type and value being its actual class type. */
  private void putGameTypeClass() {
    gameTypeClass.put(Game.FLAPPYFISH, FlappyGameStatus.class);
    gameTypeClass.put(Game.SPACESHOOTER, ShooterGameStatus.class);
    gameTypeClass.put(Game.HANGMAN, HangmanGameStatFacade.class);
    gameTypeClass.put(Game.GUESSNUM, GuessGameStat.class);
  }

  /** Puts a key-value pair with key being the game type and value being the name of the table. */
  private void putTables() {
    tables.put(Game.FLAPPYFISH, "FlappyFish");
    tables.put(Game.SPACESHOOTER, "PlaneShooter");
    tables.put(Game.HANGMAN, "Hangman");
    tables.put(Game.GUESSNUM, "Guessnum");
  }

  /**
   * Getter for this DBHandler instance.
   *
   * @return the instance of this DBHandler.
   */
  public static DBHandler getInstance(Activity activity) {
    if (dbHandler == null) {
      dbHandler = new DBHandler(activity);
    }
    return dbHandler;
  }

  /**
   * Creates the tables and the initial population of the tables.
   *
   * @param sqLiteDatabase the database.
   */
  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(
            "CREATE TABLE "
                    + TABLE_USER
                    + " ("
                    + COLUMN_USERNAME
                    + " TEXT,"
                    + COLUMN_USER_INSTANCE
                    + " TEXT"
                    + ")");

    for (Game type : Game.values()) {
      sqLiteDatabase.execSQL(
              "CREATE TABLE "
                      + tables.get(type)
                      + " ("
                      + COLUMN_USERNAME
                      + " TEXT,"
                      + COLUMN_HIGHEST_SCORE
                      + " TEXT,"
                      + COLUMN_STATUS
                      + " TEXT"
                      + ")");
    }
  }

  /**
   * Drop tables, add tables, or do anything else the database needs to upgrade to the new schema
   * version.
   *
   * @param sqLiteDatabase the database.
   * @param oldVersion the old database version.
   * @param newVersion the new database version.
   */
  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    // Drop older table if existed
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
    for (Game type : Game.values()) {
      sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tables.get(type));
    }
    // Create tables again
    onCreate(sqLiteDatabase);
  }

  /**
   * Insert a new row in the tables for the new user.
   *
   * @param user a new user.
   */
  void saveUser(User user) {
    // Gets the data repository in write mode
    SQLiteDatabase database = this.getWritableDatabase();

    String json = gson.toJson(user);

    // Create a new map of values, where column names are the keys
    ContentValues userValues = new ContentValues();
    userValues.put(COLUMN_USERNAME, user.getName());
    userValues.put(COLUMN_USER_INSTANCE, json);
    database.insert(TABLE_USER, null, userValues);

    // Store the username into every game tables
    ContentValues usernameValue = new ContentValues();
    for (Game type : Game.values()) {
      usernameValue.put(COLUMN_USERNAME, user.getName());
      database.insert(tables.get(type), null, usernameValue);
    }
  }

  /**
   * Get the User with the specified username.
   *
   * @param username the username of the user.
   * @return User object of the user with username.
   */
  User getUser(String username) {
    SQLiteDatabase database = this.getWritableDatabase();
    String query =
            " SELECT "
                    + COLUMN_USER_INSTANCE
                    + " FROM "
                    + TABLE_USER
                    + " WHERE "
                    + COLUMN_USERNAME
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

  /**
   * Save the GameStatus gameStatus into the database.
   *
   * @param gameStatus the GameStatus.
   * @param type the game type of gameStatus.
   */
  public void saveGameStatus(GameStatus gameStatus, Game type) {
    String json = gson.toJson(gameStatus);

    // Gets the data repository in write mode
    SQLiteDatabase database = this.getWritableDatabase();

    // Create a new map of values, where column names are the keys
    ContentValues values = new ContentValues();

    //    int score =
    //            compareScore(
    //                    ((FlappyGameStatus) gameStatus).getScore(),
    //                    getHighestScore(gameStatus.getName(), type));
    //    values.put(COLUMN_FLAPPYFISH_HIGHEST_SCORE, score);

    values.put(COLUMN_STATUS, json);
    database.update(tables.get(type), values, COLUMN_USERNAME + " = " + "'" +
            gameStatus.getName() + "'", null);
  }

  //  /**
  //   * Returns the higher score of the gameStatusScore and databaseHighestScore.
  //   *
  //   * @param gameStatusScore the score currently stored in gameStatusScore.
  //   * @param databaseHighestScore the highest score stored in the database for a game.
  //   * @return the higher score of the gameStatusScore and databaseHighestScore.
  //   */
  //  private int compareScore(int gameStatusScore, int databaseHighestScore) {
  //    if (gameStatusScore > databaseHighestScore) {
  //      return gameStatusScore;
  //    } else {
  //      return databaseHighestScore;
  //    }
  //  }
  //
  //  /**
  //   * Returns the highest score of the user with username for a specified game.
  //   *
  //   * @param username the username of the user.
  //   * @param type the type of the game.
  //   * @return the highest score of the user for a specified game.
  //   */
  //  public int getHighestScore(String username, Game type) {
  //    SQLiteDatabase database = this.getWritableDatabase();
  //    String query = "";
  //
  //    switch (type) {
  //      case TestClass.Game.FLAPPYFISH:
  //        query =
  //                "SELECT "
  //                        + COLUMN_FLAPPYFISH_HIGHEST_SCORE
  //                        + " FROM "
  //                        + TABLE_FLAPPYFISH
  //                        + " WHERE "
  //                        + COLUMN_FLAPPYFISH_USERNAME
  //                        + " = "
  //                        + "'"
  //                        + username
  //                        + "'";
  //        break;
  //      case TestClass.Game.GUESSNUM:
  //        query =
  //                "SELECT "
  //                        + COLUMN_GUESSNUM_HIGHEST_SCORE
  //                        + " FROM "
  //                        + TABLE_GUESSNUM
  //                        + " WHERE "
  //                        + COLUMN_GUESSNUM_USERNAME
  //                        + " = "
  //                        + "'"
  //                        + username
  //                        + "'";
  //        break;
  //      case TestClass.Game.HANGMAN:
  //        query =
  //                "SELECT "
  //                        + COLUMN_HANGMAN_HIGHEST_SCORE
  //                        + " FROM "
  //                        + TABLE_HANGMAN
  //                        + " WHERE "
  //                        + COLUMN_HANGMAN_USERNAME
  //                        + " = "
  //                        + "'"
  //                        + username
  //                        + "'";
  //        break;
  //    }
  //    Cursor cursor = database.rawQuery(query, null);
  //
  //    // TODO: modify this later
  //    if (cursor.moveToFirst()) {
  //      int highestScore = cursor.getInt(0);
  //      cursor.close();
  //      return highestScore;
  //    } else {
  //      return 0;
  //    }
  //  }

  /**
   * Returns the gameStatus of a user with username for a specified game.
   *
   * @param username the username of the user.
   * @param type the type of the game.
   * @return the gameStatus of the user for a specified game.
   */
  public GameStatus getGameStatus(String username, Game type) {
    SQLiteDatabase database = this.getWritableDatabase();
    String query =
            "SELECT "
                    + COLUMN_STATUS
                    + " FROM "
                    + tables.get(type)
                    + " WHERE "
                    + COLUMN_USERNAME
                    + " = "
                    + "'"
                    + username
                    + "'";

    Cursor cursor = database.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      String json = cursor.getString(0);
      cursor.close();

      return gson.fromJson(json, gameTypeClass.get(type));
    }
    return null;
  }
}
