package com.example.myapplication.Hangman;

import com.example.myapplication.Domain.GameStatus;

public class HangmanGamePresenter implements HangmanGameStatFacade.OnValidateCharListener{

    private HangmanGameActivity hangmanGameActivity;
    private HangmanGameStatFacade hangmanGameStat;
    private HangmanWordGenerator hangmanWordGenerator;

    HangmanGamePresenter(HangmanGameActivity view, HangmanGameStatFacade interactor) {
        this.hangmanGameActivity = view;
        this.hangmanGameStat = interactor;
        this.hangmanWordGenerator = new HangmanWordGenerator(view);
    }

    public void validateChar(char c) {
        hangmanGameStat.validateCharInteractor(c, this);
    }

    public void validateWord(String guessedWord) {
        hangmanGameStat.validateWordInteractor(guessedWord, this);
    }

    public GameStatus getHangmanGameStat() {
        return this.hangmanGameStat;
    }

    public boolean getPlayed() {
        return hangmanGameStat.getPlayed();
    }

    public void getNewWord() {
        String chosenWord = hangmanWordGenerator.getChosenWord();
        hangmanGameStat.generateWord(chosenWord);
    }

    public boolean onCheckIfGameEnded() {
        return hangmanGameStat.gameEndedInteractor();
    }

    public void onOpenGameEndedActivity() {
        onGameEnd(hangmanGameStat);
    }

    public void onResuming() {
        hangmanGameActivity.showTxtStageNum(hangmanGameStat.getStageNum());
        hangmanGameActivity.showTxtMaskedWord(hangmanGameStat.getDisplayedMaskedWord().toString());
        hangmanGameActivity.showTxtScore(hangmanGameStat.getCurrentScore());
        hangmanGameActivity.showLettersGuessed(hangmanGameStat.getLettersGuessed().toString());
        hangmanGameActivity.setPictureIndex(hangmanGameStat.getFalseGuess());
        hangmanGameActivity.showImage();
    }

    @Override
    public void onEmptyError() {
        if (hangmanGameActivity != null) {
            hangmanGameActivity.showEmptyError();
        }
    }

    @Override
    public void onLetterUsedError(char c) {
        if (hangmanGameActivity != null) {
            hangmanGameActivity.showLetterUsedError(c);
            hangmanGameActivity.clearEdtLetterGuess();
        }
    }

    @Override
    public void onDisplayViews() {
        if (hangmanGameActivity != null) {
            hangmanGameActivity.showTxtStageNum(hangmanGameStat.getStageNum());
            hangmanGameActivity.setPictureIndex(hangmanGameStat.getFalseGuess());
            hangmanGameActivity.showTxtMaskedWord(hangmanGameStat.getDisplayedMaskedWord().toString());
            hangmanGameActivity.showLettersGuessed(hangmanGameStat.getLettersGuessed().toString());
            hangmanGameActivity.showTxtScore(hangmanGameStat.getCurrentScore());
            hangmanGameActivity.showImage();
            hangmanGameActivity.clearEdtLetterGuess();
        }
    }

    @Override
    public void onGameEnd(HangmanGameStatFacade hm) {
        if (hangmanGameActivity != null) {
            hangmanGameActivity.gameEnded(hm);
        }
    }

    @Override
    public void onGuessWordFailed() {
        if (hangmanGameActivity != null) {
            hangmanGameActivity.showGuessWordFailed();
            onDisplayViews();
        }
    }
}

