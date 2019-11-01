package com.example.myapplication.Hangman;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.GameStatus;

import java.util.Objects;

public class HangmanGameStat extends GameStatus implements Parcelable {

    private String type;
    boolean played = false;
    private String secretWord = "";
    private char[] secretWordCharArray;
    private char[] maskedWordCharArray;
    private int score = 120;
    private int falseGuess = -1;
    private String gender = "MALE";
    private StringBuilder lettersGuessed = new StringBuilder();
    private StringBuilder displayedMaskedWord = new StringBuilder();

    HangmanGameStat(String name){
        super(name);
        this.type = "HangmanGameStat";
    }

    private HangmanGameStat(Parcel in) {
        super(in);
        played = in.readByte() != 0;
        secretWord = in.readString();
        secretWordCharArray = in.createCharArray();
        maskedWordCharArray = in.createCharArray();
        score = in.readInt();
        falseGuess = in.readInt();
        gender = in.readString();
        lettersGuessed = new StringBuilder(in.readString());
        displayedMaskedWord = new StringBuilder(in.readString());
        type = in.readString();
    }

    public static final Creator<HangmanGameStat> CREATOR = new Creator<HangmanGameStat>() {
        @Override
        public HangmanGameStat createFromParcel(Parcel in) {
            return new HangmanGameStat(in);
        }

        @Override
        public HangmanGameStat[] newArray(int size) {
            return new HangmanGameStat[size];
        }
    };

    char[] getMaskedWordCharArray() {
        return maskedWordCharArray;
    }

    StringBuilder getLettersGuessed() {
        return lettersGuessed;
    }

    StringBuilder getDisplayedMaskedWord(){
        return displayedMaskedWord;
    }

    int getScore() {
        return score;
    }

    void setFalseGuess(int falseGuess) {
        this.falseGuess = falseGuess;
    }

    int getFalseGuess() {
        return falseGuess;
    }

    void setGender(String gender) {
        this.gender = gender;
    }

    String getGender() {
        return gender;
    }

    String getSecretWord() {
        return secretWord;
    }

    void generateWord(String chosenWord) {
        played = true;
        secretWord = chosenWord;
        secretWordCharArray = new char[secretWord.length()];
        maskedWordCharArray = new char[secretWord.length()];
        for (int i = 0; i < secretWord.length(); i++) {
            maskedWordCharArray[i] = '_';
            secretWordCharArray[i] = secretWord.charAt(i);
        }
        displayedMaskedWord = generateDisplayedMaskedWord();
    }

    boolean checkLetterInGuessed(char guessedLetter) {
        if (!lettersGuessed.toString().contains(String.valueOf(guessedLetter))) {
            updateLettersGuessed(guessedLetter);
            return true;
        } else {
            return false;
        }
    }

    void checkLetter(char guessedLetter) {
        if (!secretWord.contains(String.valueOf(guessedLetter))) {
            decreaseScore();
        } else {
            revealLetter(guessedLetter);
        }
    }

    private void updateLettersGuessed(char guessedLetter) {
        String addLetter = guessedLetter + ", ";
        lettersGuessed.append(addLetter);
    }

    private void decreaseScore() {
        score -= 20;
    }

    private void revealLetter(char guessedLetter) {
        for (int i = 0; i < secretWordCharArray.length; i++) {
            if (secretWordCharArray[i] == guessedLetter) {
                maskedWordCharArray[i] = guessedLetter;
            }
        }
        displayedMaskedWord = generateDisplayedMaskedWord();
    }

    private StringBuilder generateDisplayedMaskedWord() {
        StringBuilder maskedWord = new StringBuilder();
        for (char letter : maskedWordCharArray) {
            maskedWord.append(letter);
            maskedWord.append(" ");
        }
        return maskedWord;
    }

    void resetGameStatus() {
        played = false;
        secretWord = "";
        secretWordCharArray = null;
        maskedWordCharArray = null;
        score = 120;
        falseGuess = -1;
        gender = "MALE";
        lettersGuessed = new StringBuilder();
        displayedMaskedWord = new StringBuilder();
        type = "HangmanGameStat";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeByte((byte) (played ? 1 : 0));
        parcel.writeString(secretWord);
        parcel.writeCharArray(secretWordCharArray);
        parcel.writeCharArray(maskedWordCharArray);
        parcel.writeInt(score);
        parcel.writeInt(falseGuess);
        parcel.writeString(gender);
        parcel.writeString(lettersGuessed.toString());
        parcel.writeString(displayedMaskedWord.toString());
        parcel.writeString(type);
    }
}