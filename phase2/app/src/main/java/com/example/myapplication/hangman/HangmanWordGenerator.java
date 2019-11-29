package com.example.myapplication.hangman;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class HangmanWordGenerator {

  /** ArrayList that stores all of the possible words that can be generated in this Hangman game. */
  private ArrayList<String> listOfWords = new ArrayList<>();

  /** Context the activity that called this HangmanWordGenerator. */
  private Context context;

  /**
   * Constructs this HangmanWordGenerator.
   *
   * @param context the activity that called this HangmanWordGenerator.
   */
  HangmanWordGenerator(Context context) {
    this.context = context;
    readFile();
  }

  /** Reads and populates all possible words that can be used in this game into listOfWords. */
  private void readFile() {
    String wordsFile = "words_database.txt";
    String tag = "HangmanWordGenerator";

    InputStream inputStream = null;
    Scanner scanner = null;

    try {
      inputStream = context.getAssets().open(wordsFile);
      scanner = new Scanner(inputStream);
      while (scanner.hasNext()) {
        listOfWords.add(scanner.next());
      }
    } catch (IOException e) {
      Log.e(tag, "Error encountered trying to open file for reading: " + wordsFile);
    } finally {
      try {
        if (inputStream != null) {
          inputStream.close();
        }
      } catch (IOException e) {
        Log.e(tag, "Error encountered trying to close inputStream");
      }

      if (scanner != null) {
        scanner.close();
      }
    }
  }

  /**
   * Returns the chosen word for this game.
   *
   * @return String the chosen word for this game.
   */
  String getChosenWord() {
    Collections.shuffle(listOfWords);
    String chosenWord = listOfWords.get(0);
    listOfWords.remove(0);
    return chosenWord;
  }

}
