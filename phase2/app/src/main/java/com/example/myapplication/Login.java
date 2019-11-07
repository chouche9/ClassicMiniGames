package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/** A login activity. */
public class Login extends AppCompatActivity implements View.OnClickListener {

  /** The input field used to get the username. */
  EditText account;

  /** The input field used to get password. */
  EditText password;

  /** Button used to log in. */
  Button login;

  /** Button used to go back to the main page of the application. */
  Button backFront;

  /** Username that was input into EditText account. */
  String name;

  /** The code used to match the correct activity launch. */
  final int REQUEST_CODE = 5;

  /**
   * Initializes this login activity.
   *
   * @param savedInstanceState a bundle of the resources in this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    account = findViewById(R.id.nameEntry);
    password = findViewById(R.id.passwordEntry);
    login = findViewById(R.id.login);
    login.setOnClickListener(this);
    backFront = findViewById(R.id.backfront);
    backFront.setOnClickListener(this);
  }

  /**
   * Dispatch incoming result to the correct fragment.
   *
   * @param requestCode The code used to match the correct activity launch.
   * @param resultCode Represents whether the result is okay or not.
   * @param data The intent that is used to pass info.
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE) {
      finish();
    }
  }

  /**
   * Return whether if the login is verified.
   *
   * @return true if the login is verified.
   */
  private boolean verifyLogin() {
    name = account.getText().toString().trim();
    String passwordString = password.getText().toString().trim();
    UserManager userManager = UserManager.getInstance(this);
    return userManager.authenticate(name, passwordString);
  }

  /**
   * Events that happen when each of the buttons in this activity is clicked.
   *
   * @param view view responsible for event handling.
   */
  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.login:
        if (verifyLogin()) {
          Intent intent = new Intent(this, GameMain.class);
          intent.putExtra("user", name);
          startActivityForResult(intent, REQUEST_CODE);
        } else {
          Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
        }
        break;
      case R.id.backfront:
        finish();
        break;
    }
  }
}
