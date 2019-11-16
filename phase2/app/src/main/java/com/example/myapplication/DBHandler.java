package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.FlappyFish.FlappyGameStatus;
import com.example.myapplication.GuessNum.GuessGameStat;
import com.example.myapplication.Hangman.HangmanGameStat;
import com.google.gson.Gson;

/** A database handler that executes SQL requests and manages a local database. */
public class DBHandler extends SQLiteOpenHelper {

  /** Name used globally to interact with the SQLite database. */
  private static DBHandler dbHandler;
  /** The version of the database. */
  private static final int DATABASE_VERSION = 1;
  /** The name of the database. */
  private static final String DATABASE_NAME = "gameDB.db";

  // Table Names
  /** The name of the table that stores the users' info. */
  private static final String TABLE_USER = "User";
  /** The name of the table that stores the FlappyFish's statistics. */
  private static final String TABLE_FLAPPYFISH = "FlappyFish";
  /** The name of the table that stores the GuessNum's statistics. */
  private static final String TABLE_GUESSNUM = "GuessNum";
  /** The name of the table that stores the Hangman's statistics. */
  private static final String TABLE_HANGMAN = "Hangman";

  // User Table Columns
  /** The name of the column that stores the usernames. */
  private static final String COLUMN_USERNAME = "Username";
  /** The name of the column that stores the passwords. */
  private static final String COLUMN_PASSWORD = "Password";

  // FlappyFish Table Columns
  /** The name of the column that stores the usernames. */
  private static final String COLUMN_FLAPPYFISH_USERNAME = "Username";
  /** The name of the column that stores the highest score in FlappyFish. */
  private static final String COLUMN_FLAPPYFISH_HIGHEST_SCORE = "FlappyHighScore";
  /** The name of the column that stores FlappyGameStatus. */
  private static final String COLUMN_FLAPPYFISH_STATUS = "FlappyStatus";

  // GuessNum Table Columns
  /** The name of the column that stores the usernames. */
  private static final String COLUMN_GUESSNUM_USERNAME = "Username";
  /** The name of the column that stores the highest score in GuessNum. */
  private static final String COLUMN_GUESSNUM_HIGHEST_SCORE = "GuessNumHighScore";
  /** The name of the column that stores GuessGameStat. */
  private static final String COLUMN_GUESSNUM_STATUS = "GuessNumStatus";

  // Hangman Table Columns
  /** The name of the column that stores the usernames. */
  private static final String COLUMN_HANGMAN_USERNAME = "Username";
  /** The name of the column that stores the highest score in Hangman. */
  private static final String COLUMN_HANGMAN_HIGHEST_SCORE = "HangmanHighScore";
  /** The name of the column that stores HangmanGameStat. */
  private static final String COLUMN_HANGMAN_STATUS = "HangmanStatus";

  /** The query that creates the TABLE_USER table in the database. */
  private static final String CREATE_USER_TABLE =
      "CREATE TABLE "
          + TABLE_USER
          + " ("
          + COLUMN_USERNAME
          + " TEXT,"
          + COLUMN_PASSWORD
          + " TEXT"
          + ")";

  /** The query that creates the TABLE_FLAPPYFISH table in the database. */
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

  /** The query that creates the TABLE_GUESSNUM table in the database. */
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

  /** The query that creates the TABLE_HANGMAN table in the database. */
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

  /** An enum that contains all the game types. */
  public enum Game {
    FLAPPYGAMESTATUS,
    GUESSGAMESTATUS,
    HANGMANGAMESTATUS
  }

  /** Constructs this DBHandler for creating, opening and managing the database. */
  private DBHandler() {
    super(null, DATABASE_NAME, null, DATABASE_VERSION);
  }

  /**
   * Getter for this DBHandler instance.
   *
   * @return the instance of this DBHandler.
   */
  static DBHandler getInstance() {
    if (dbHandler == null) {
      dbHandler = new DBHandler();
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
    sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    sqLiteDatabase.execSQL(CREATE_FLAPPYFISH_TABLE);
    sqLiteDatabase.execSQL(CREATE_GUESSNUM_TABLE);
    sqLiteDatabase.execSQL(CREATE_HANGMAN_TABLE);
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
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FLAPPYFISH);
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GUESSNUM);
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_HANGMAN);

    // Create tables again
    onCreate(sqLiteDatabase);
  }

  /**
   * Insert a new row in the tables for the new user.
   *
   * @param user a new user.
   */
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

  /**
   * A helper method that inserts the given value to the given column in the given table.
   *
   * @param database the database.
   * @param column the column that the value is inserted to.
   * @param table the table that the value is inserted to.
   * @param value the value that is inserted to the database.
   */
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
