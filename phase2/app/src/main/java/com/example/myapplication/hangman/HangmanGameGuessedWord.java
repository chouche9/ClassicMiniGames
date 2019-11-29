package com.example.myapplication.hangman;

class HangmanGameGuessedWord {

    interface onHangmanGameGuessedWordListener {
        void onCorrectWord(String guessedWord);

        void onWrongWord(String guessedWord, HangmanGamePresenter hm);
    }


    void checkWord(String secretWord, String guessedWord, onHangmanGameGuessedWordListener listener, HangmanGamePresenter hm) {
        if (guessedWord.equalsIgnoreCase(secretWord)) {
            listener.onCorrectWord(guessedWord);
        } else {
            listener.onWrongWord(guessedWord, hm);
        }
    }

}
