package com.example.myapplication.databaseconnector;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.domain.GameStatus;
import com.example.myapplication.flappyfish.FlappyGameStatus.FlappyGameStatusFacade;
import com.example.myapplication.hangman.HangmanGameStatus;
import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterGameStatusFacade;

import java.util.HashMap;

/** A database handler that manages a local database. */
class DBHandler extends SQLiteOpenHelper {

  /** The singleton instance of this DBHandler. */
  private static DBHandler dbHandler;

  /** The version of the database. */
  private static final int DATABASE_VERSION = 1;

  /** The name of the database. */
  private static final String DATABASE_NAME = "gameDatabase.db";

  /** The name of the table that stores the users. */
  static final String TABLE_USER = "User";

  // Columns
  /** The name of the column that stores the usernames. */
  static final String COLUMN_USERNAME = "Username";
  /** The name of the column that stores the passwords. */
  static final String COLUMN_USER_INSTANCE = "UserInstance";
  /** The name of the column that stores the game status. */
  static final String COLUMN_STATUS = "GameStatus";

  /** Maps each type of game to its corresponding table name in the database. */
  private HashMap<GameEnum, String> tables = new HashMap<>();

  /** Maps each type of game to its actual class type. */
  private HashMap<GameEnum, Class<? extends GameStatus>> gameTypeClass = new HashMap<>();

  /** Constructs this DBHandler for creating, opening and managing the database. */
  private DBHandler(Activity activity) {
    super(activity, DATABASE_NAME, null, DATABASE_VERSION);
    putGameTypeClass();
    putTables();
  }

  /** Puts a key-value pair with key being the game type and value being its actual class type. */
  private void putGameTypeClass() {
    gameTypeClass.put(GameEnum.FLAPPYFISH, FlappyGameStatusFacade.class);
    gameTypeClass.put(GameEnum.SPACESHOOTER, ShooterGameStatusFacade.class);
    gameTypeClass.put(GameEnum.HANGMAN, HangmanGameStatus.class);
  }

  /** Puts a key-value pair with key being the game type and value being the name of the table. */
  private void putTables() {
    tables.put(GameEnum.FLAPPYFISH, "FlappyFish");
    tables.put(GameEnum.SPACESHOOTER, "PlaneShooter");
    tables.put(GameEnum.HANGMAN, "Hangman");
  }

  /**
   * Getter for this DBHandler instance.
   *
   * @return the instance of this DBHandler.
   */
  static DBHandler getInstance(Activity activity) {
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

    for (GameEnum type : GameEnum.values()) {
      sqLiteDatabase.execSQL(
          "CREATE TABLE "
              + tables.get(type)
              + " ("
              + COLUMN_USERNAME
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
    for (GameEnum type : GameEnum.values()) {
      sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tables.get(type));
    }
    // Create tables again
    onCreate(sqLiteDatabase);
  }

  /**
   * Get all the game tables in the database.
   *
   * @return Return a HashMap that stores the game tables in the database.
   */
  HashMap<GameEnum, String> getTables() {
    return tables;
  }

  /**
   * Get all the games' actual class type.
   *
   * @return Return a HashMap that stores all the games' actual class type.
   */
  HashMap<GameEnum, Class<? extends GameStatus>> getGameTypeClass() {
    return gameTypeClass;
  }
}
