package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener {
    EditText accountEntry;
    EditText password1;
    EditText password2;
    Button createAccount;
    Intent intent1;
    UserManager userManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = UserManager.getInstance(this);
        setContentView(R.layout.activity_create_account);
        intent1 = getIntent();
        accountEntry = findViewById(R.id.accountEntry);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        createAccount = findViewById(R.id.enterButton);
        createAccount.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        String name = accountEntry.getText().toString().trim();
        String pass1 = password1.getText().toString().trim();
        String pass2 = password2.getText().toString().trim();
        if (!pass1.equals(pass2)){
            Toast.makeText(this, "passwords do not match!!", Toast.LENGTH_SHORT).show();
            return ;
        }
        if (userManager.createAuthenticate(name)){
            User newUser = new User(name, pass2);
            Toast.makeText(this, "Create Successfully!", Toast.LENGTH_SHORT).show();
            userManager.saveUsers(newUser);
            finish();
        }
        else{
            Toast.makeText(this, "User name Already exists!!!", Toast.LENGTH_SHORT).show();
        }
    }
}
