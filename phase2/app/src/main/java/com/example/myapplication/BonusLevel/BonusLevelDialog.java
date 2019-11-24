package com.example.myapplication.BonusLevel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Hangman.HangmanStageEnded;
import com.example.myapplication.R;

public class BonusLevelDialog extends AppCompatDialogFragment implements BonusLevelView{

    public interface BonusLevelDialogListener {
        public void bonusLevelReview(boolean isWon, int bonusSore);
    }

    private EditText edtGuessNumber;

    private TextView txtTries;

    private Button btnDialogGuessNumber;

    private HangmanStageEnded hangmanStageEnded;

    private BonusLevelPresenter bonusLevelPresenter;

    /**
     * Initializes this dialog.
     *
     * @param savedInstanceState a bundle of the resources in this activity.
     * @return the dialog that is displayed on the screen.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_bonus_level, null);

        builder.setView(view);

        edtGuessNumber = view.findViewById(R.id.edtGuessNumber);
        txtTries = view.findViewById(R.id.txtTries);
        btnDialogGuessNumber = view.findViewById(R.id.btnDialogGuessWord);

        bonusLevelPresenter = new BonusLevelPresenter(this);

        setBtnDialogGuessNumber();

        return builder.create();
    }

    /** Event that happens after the btnDialogGuessWord button is clicked. */
    private void setBtnDialogGuessNumber() {
        btnDialogGuessNumber.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Editable input = edtGuessNumber.getText();

                        if (input.length() >0) {

                            String value = input.toString();
                            int guessedNumber = Integer.parseInt(value);

                            if ( 0 < guessedNumber && guessedNumber < 21 ) {
                                bonusLevelPresenter.validateGuessNumber(guessedNumber);
                            } else {
                                showOutOfBoundsError();
                            }
                        } else {
                            showEmptyError();
                        }
                    }
                });
    }

    /**
     * Called when the dialog fragment is first attached to the context.
     *
     * @param context the context of the activity.
     */
    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        hangmanStageEnded = (HangmanStageEnded) context;
    }

    @Override
    public void updateTries(int numTries) {
        txtTries.setText("X " + numTries + "Tries");
    }

    @Override
    public void showEmptyError() {
        edtGuessNumber.setError("Please input a number!");
    }

    @Override
    public void showOutOfBoundsError() {
        edtGuessNumber.setError("Your number is outside the limit. Please try again");
    }

    @Override
    public void showNumberToHighError() {
        edtGuessNumber.setError("Your number is too High! Try Again!");
    }

    @Override
    public void showNumberToLowError() {
        edtGuessNumber.setError("Your number is too Low! Try Again!");
    }

    @Override
    public void GameEnd(boolean isWon, int bonusSore) {
        hangmanStageEnded.bonusLevelReview(isWon, bonusSore);
    }
}
