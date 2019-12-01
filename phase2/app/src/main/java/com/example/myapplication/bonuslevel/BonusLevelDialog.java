package com.example.myapplication.bonuslevel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

/** Class BonusLevelDialog */
public class BonusLevelDialog extends AppCompatDialogFragment implements BonusLevelView {

  /** Interface BonusLevelDialogListener */
  public interface BonusLevelDialogListener {

    /**
     * Abstract Method for doing something upon game ended to show the result of the bonus level
     *
     * @param isWon: boolean to show whether game isWon or not
     * @param bonusSore: the score of the game
     */
    void bonusLevelResult(boolean isWon, int bonusSore);

    /** Abstract Method for doing something upon cancelling the bonus game. */
    void onCancel();
  }

  /** EditText Guess number attribute */
  private EditText edtGuessNumber;

  /** TextView attribute that linked to the text field for number of tries */
  private TextView txtTries;

  /** Button that linked to the button to guess the number */
  private Button btnDialogGuessNumber;

  /** attribute that saves the bonus level dialog listener instance */
  private BonusLevelDialogListener listener;

  /** attribute that saves the bonus level presenter instance */
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

    builder
        .setView(view)
        .setNegativeButton(
            "cancel",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                listener.onCancel();
              }
            });

    edtGuessNumber = view.findViewById(R.id.edtGuessNumber);
    txtTries = view.findViewById(R.id.txtTries);
    btnDialogGuessNumber = view.findViewById(R.id.btnDialogGuessNumber);

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

            if (input.length() > 0) {

              String value = input.toString();
              int guessedNumber = Integer.parseInt(value);

              if (0 < guessedNumber && guessedNumber < 21) {
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

    //        hangmanStageEnded = (HangmanStageEnded) context;

    try {
      listener = (BonusLevelDialogListener) context;
    } catch (ClassCastException e) {
      throw new ClassCastException(context.toString() + "must implement BonusLevelDialogListener");
    }
  }

  /**
   * Show the number of Tries in View
   *
   * @param numTries: number of tries
   */
  @Override
  public void updateTries(int numTries) {
    String tries = "X " + numTries + "Tries";
    txtTries.setText(tries);
  }

  /** Show an error when the input is empty */
  @Override
  public void showEmptyError() {
    edtGuessNumber.setError("Please input a number!");
  }

  /** Show an error when the number is out of bounds */
  @Override
  public void showOutOfBoundsError() {
    edtGuessNumber.setError("Your number is outside the limit. Please try again");
  }

  /** Show an error when number is too high */
  @Override
  public void showNumberToHighError() {
    edtGuessNumber.setError("Your number is too High! Try Again!");
  }

  /** Show an error when number is too low */
  @Override
  public void showNumberToLowError() {
    edtGuessNumber.setError("Your number is too Low! Try Again!");
  }

  /**
   * Abstract Method to do something when game has ended
   *
   * @param isWon boolean indicating if the game was won or not
   * @param bonusScore the bonus score earned during bonus game
   */
  @Override
  public void gameEnd(boolean isWon, int bonusScore) {
    listener.bonusLevelResult(isWon, bonusScore);
  }
}
