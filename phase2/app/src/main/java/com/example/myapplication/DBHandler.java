package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.FlappyFish.FlappyGameStatus;
import com.example.myapplication.GuessNum.GuessGameStat;
import com.example.myapplication.Hangman.HangmanGameStat;
import com.google.gson.Gson;

public class DBHandler extends SQLiteOpenHelper {

  private static DBHandler dbHandler;
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "gameDB.db";

  // Table Names
  private static final String TABLE_USER = "User";
  private static final String TABLE_FLAPPYFISH = "FlappyFish";
  private static final String TABLE_GUESSNUM = "GuessNum";
  private static final String TABLE_HANGMAN = "Hangman";

  // User Table Columns
  private static final String COLUMN_USERNAME = "Username";
  private static final String COLUMN_PASSWORD = "Password";

  // FlappyFish Table Columns
  private static final String COLUMN_FLAPPYFISH_USERNAME = "Username";
  private static final String COLUMN_FLAPPYFISH_HIGHEST_SCORE = "FlappyHighScore";
  private static final String COLUMN_FLAPPYFISH_STATUS = "FlappyStatus";

  // GuessNum Table Columns
  private static final String COLUMN_GUESSNUM_USERNAME = "Username";
  private static final String COLUMN_GUESSNUM_HIGHEST_SCORE = "GuessNumHighScore";
  private static final String COLUMN_GUESSNUM_STATUS = "GuessNumStatus";

  // Hangman Table Columns
  private static final String COLUMN_HANGMAN_USERNAME = "Username";
  private static final String COLUMN_HANGMAN_HIGHEST_SCORE = "HangmanHighScore";
  private static final String COLUMN_HANGMAN_STATUS = "HangmanStatus";

  private static final String CREATE_USER_TABLE =
      "CREATE TABLE "
          + TABLE_USER
          + " ("
          + COLUMN_USERNAME
          + " TEXT,"
          + COLUMN_PASSWORD
          + " TEXT"
          + ")";
  private static final String CREATE_FLAPPYFISH_TABLE =
      "CREATE TABLE "
          + TABLE_FLAPPYFISH
          + " ("
          + COLUMN_FLAPPYFISH_USERNAME
          + " TEXT,"
          + COLUMN_FLAPPYFISH_HIGHEST_SCORE
          + " TEXT,"
          + COLUMN_FLAPPYFISH_STATUS
          + " TEXT"
          + ")";
  private static final String CREATE_GUESSNUM_TABLE =
      "CREATE TABLE "
          + TABLE_GUESSNUM
          + " ("
          + COLUMN_GUESSNUM_USERNAME
          + " TEXT,"
          + COLUMN_GUESSNUM_HIGHEST_SCORE
          + " TEXT,"
          + COLUMN_GUESSNUM_STATUS
          + " TEXT"
          + ")";
  private static final String CREATE_HANGMAN_TABLE =
      "CREATE TABLE "
          + TABLE_HANGMAN
          + " ("
          + COLUMN_HANGMAN_USERNAME
          + " TEXT,"
          + COLUMN_HANGMAN_HIGHEST_SCORE
          + " TEXT,"
          + COLUMN_HANGMAN_STATUS
          + " TEXT"
          + ")";

  enum Game {
    FLAPPYGAMESTATUS,
    GUESSGAMESTATUS,
    HANGMANGAMESTATUS
  }

  private DBHandler() {
    super(null, DATABASE_NAME, null, DATABASE_VERSION);
  }

  static DBHandler getInstance() {
    if (dbHandler == null) {
      dbHandler = new DBHandler();
    }
    return dbHandler;
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    sqLiteDatabase.execSQL(CREATE_FLAPPYFISH_TABLE);
    sqLiteDatabase.execSQL(CREATE_GUESSNUM_TABLE);
    sqLiteDatabase.execSQL(CREATE_HANGMAN_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    // Drop older table if existed
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FLAPPYFISH);
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GUESSNUM);
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_HANGMAN);

    // Create tables again
    onCreate(sqLiteDatabase);
  }

  void insertUser(User user) {

    // Gets the data repository in write mode
    SQLiteDatabase database = this.getWritableDatabase();

    // Create a new map of values, where column names are the keys
    ContentValues values = new ContentValues();
    values.put(COLUMN_USERNAME, user.getName());
    values.put(COLUMN_PASSWORD, user.getPassword());
    database.insert(TABLE_USER, null, values);

    insertUserHelper(database, COLUMN_FLAPPYFISH_USERNAME, TABLE_FLAPPYFISH, user.getName());
    insertUserHelper(database, COLUMN_GUESSNUM_USERNAME, TABLE_GUESSNUM, user.getName());
    insertUserHelper(database, COLUMN_HANGMAN_USERNAME, TABLE_HANGMAN, user.getName());
  }

  private void insertUserHelper(
      SQLiteDatabase database, String column, String table, String value) {
    ContentValues values = new ContentValues();
    values.put(column, value);
    database.insert(table, null, values);
  }

  void saveGameStatus(GameStatus gameStatus, Game type) {
    int score;

    Gson gson = new Gson();
    String json = gson.toJson(gameStatus);

    // Gets the data repository in write mode
    SQLiteDatabase database = this.getWritableDatabase();

    // Create a new map of values, where column names are the keys
    ContentValues values = new ContentValues();

    switch (type) {
      case FLAPPYGAMESTATUS:
        score =
            compareScore(
                ((FlappyGameStatus) gameStatus).getScore(),
                getHighestScore(gameStatus.getName(), type));
        values.put(COLUMN_FLAPPYFISH_HIGHEST_SCORE, score);
        values.put(COLUMN_FLAPPYFISH_STATUS, json);
        database.update(
            TABLE_FLAPPYFISH,
            values,
            COLUMN_FLAPPYFISH_USERNAME + "=" + gameStatus.getName(),
            null);
        break;
      case GUESSGAMESTATUS:
        score =
            compareScore(
                ((GuessGameStat) gameStatus).getCurrentTries(),
                getHighestScore(gameStatus.getName(), type));
        values.put(COLUMN_GUESSNUM_HIGHEST_SCORE, score);
        values.put(COLUMN_GUESSNUM_STATUS, json);
        database.update(
            TABLE_GUESSNUM, values, COLUMN_GUESSNUM_USERNAME + "=" + gameStatus.getName(), null);
        break;
      case HANGMANGAMESTATUS:
        score =
            compareScore(
                ((HangmanGameStat) gameStatus).getScore(),
                getHighestScore(gameStatus.getName(), type));
        values.put(COLUMN_HANGMAN_HIGHEST_SCORE, score);
        values.put(COLUMN_HANGMAN_STATUS, json);
        database.update(
            TABLE_HANGMAN, values, COLUMN_HANGMAN_USERNAME + "=" + gameStatus.getName(), null);
        break;
    }
  }

  private int compareScore(int gameStatusScore, int databaseHighestScore) {
    if (gameStatusScore > databaseHighestScore) {
      return gameStatusScore;
    } else {
      return databaseHighestScore;
    }
  }

  public int getHighestScore(String username, Game type) {
    SQLiteDatabase database = this.getWritableDatabase();
    String query = "";

    switch (type) {
      case FLAPPYGAMESTATUS:
        query =
            "Select COLUMN_FLAPPYFISH_HIGHEST_SCORE FROM "
                + TABLE_FLAPPYFISH
                + "WHERE"
                + COLUMN_FLAPPYFISH_USERNAME
                + " = "
                + "'"
                + username
                + "'";
        break;
      case GUESSGAMESTATUS:
        query =
            "Select COLUMN_GUESSNUM_HIGHEST_SCORE FROM "
                + TABLE_GUESSNUM
                + "WHERE"
                + COLUMN_GUESSNUM_USERNAME
                + " = "
                + "'"
                + username
                + "'";
        break;
      case HANGMANGAMESTATUS:
        query =
            "Select COLUMN_HANGMAN_HIGHEST_SCORE FROM "
                + TABLE_HANGMAN
                + "WHERE"
                + COLUMN_HANGMAN_USERNAME
                + " = "
                + "'"
                + username
                + "'";
        break;
    }
    Cursor cursor = database.rawQuery(query, null);
    cursor.close();
    return cursor.getInt(0);
  }

  public String getPassword(String username) {
    SQLiteDatabase database = this.getWritableDatabase();
    String query =
        "Select COLUMN_PASSWORD FROM "
            + TABLE_USER
            + "WHERE"
            + COLUMN_USERNAME
            + " = "
            + "'"
            + username
            + "'";
    Cursor cursor = database.rawQuery(query, null);
    cursor.close();
    return cursor.getString(0);
  }

  public GameStatus getGameStatus(String username, Game type) {
    SQLiteDatabase database = this.getWritableDatabase();
    String query = "";

    switch (type) {
      case FLAPPYGAMESTATUS:
        query =
            "Select COLUMN_FLAPPYFISH_STATUS FROM "
                + TABLE_FLAPPYFISH
                + "WHERE"
                + COLUMN_FLAPPYFISH_USERNAME
                + " = "
                + "'"
                + username
                + "'";
        break;
      case GUESSGAMESTATUS:
        query =
            "Select COLUMN_GUESSNUM_STATUS FROM "
                + TABLE_GUESSNUM
                + "WHERE"
                + COLUMN_GUESSNUM_USERNAME
                + " = "
                + "'"
                + username
                + "'";
        break;
      case HANGMANGAMESTATUS:
        query =
            "Select COLUMN_HANGMAN_STATUS FROM "
                + TABLE_HANGMAN
                + "WHERE"
                + COLUMN_HANGMAN_USERNAME
                + " = "
                + "'"
                + username
                + "'";
        break;
    }
    Cursor cursor = database.rawQuery(query, null);
    cursor.close();
    Gson gson = new Gson();
    String json = cursor.getString(0);
    return gson.fromJson(json, GameStatus.class);
  }
}
