package com.example.myapplication.loginsystem.CreateAccount;

interface CreateAccountView {

    void onUsernameEmptyError();

    void onPasswordEmptyError();

    void onPasswordMismatchError();

    void onFail();

    void onSuccess();
}
