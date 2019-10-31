package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText account;
    EditText password;
    Button login;
    Button backFront;
    String name;
    final int REQUEST_CODE = 5;
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
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            finish();
        }
    }

    private boolean verifyLogin(){
        name = account.getText().toString().trim();
        String passwordString = password.getText().toString().trim();
        UserManager userManager = UserManager.getInstance(this);
        return userManager.authenticate(name, passwordString);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                if (verifyLogin()){
                    Intent intent = new Intent(this,GameMain.class);
                    intent.putExtra("user", name);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                else {
                    Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.backfront:
                finish();
        }
    }
}

