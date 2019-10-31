package com.example.myapplication.Hangman;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class HangmanWordGenerator {

    private static final String WORDS_FILE = "words_database.txt";
    private static final String TAG = "Main Activity";

    private ArrayList<String> listOfWords = new ArrayList<String>();
    private Context context;

    HangmanWordGenerator(Context context) {
        this.context = context;
        readFile();
    }

    private void readFile() {
        InputStream inputStream = null;
        Scanner scanner = null;

        try {
            inputStream = context.getAssets().open(WORDS_FILE);
            scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                listOfWords.add(scanner.next());
            }
        } catch (IOException e) {
            Log.e(TAG, "Error encountered trying to open file for reading: " + WORDS_FILE);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error encountered trying to close inputStream");
            }

            if (scanner != null) {
                scanner.close();
            }
        }
    }

    String getChosenWord() {
        Collections.shuffle(listOfWords);
        String chosenWord = listOfWords.get(0);
        listOfWords.remove(0);
        return chosenWord;
    }
}
