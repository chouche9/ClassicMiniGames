package com.example.myapplication.hangman;

class HangmanGameGuessedLetter {
    interface onHangmanGameGuessedLetterListener {
        void onCorrectLetter(char guessedLetter);

        void onWrongLetter(char guessedLetter);
    }


    void checkLetter(String word, char guessedLetter, onHangmanGameGuessedLetterListener listener) {
        if (!word.contains(String.valueOf(guessedLetter))) {
            listener.onCorrectLetter(guessedLetter);
        } else {
            listener.onWrongLetter(guessedLetter);
        }
    }

}
