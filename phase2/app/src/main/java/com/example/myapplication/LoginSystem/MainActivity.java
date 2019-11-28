package com.example.myapplication.LoginSystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

/** Main page of this application. */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  /**
   * Initializes this MainActivity.
   *
   * @param savedInstanceState a bundle of the resources in this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button createAccount = findViewById(R.id.createAccount);
    Button login = findViewById(R.id.login);
    Button exit = findViewById(R.id.exit);
    createAccount.setOnClickListener(this);
    login.setOnClickListener(this);
    exit.setOnClickListener(this);
  }

  /**
   * Events that happen when each of the buttons in this activity is clicked.
   *
   * @param v the button clicked.
   */
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.createAccount:
        Intent intentCreate = new Intent(MainActivity.this, CreateAccount.class);
        startActivity(intentCreate);
        break;
      case R.id.login:
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
        break;

      case R.id.exit:
        finish();
        break;
    }
  }
}
