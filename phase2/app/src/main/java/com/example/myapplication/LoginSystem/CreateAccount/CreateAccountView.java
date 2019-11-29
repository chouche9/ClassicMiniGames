package com.example.myapplication.LoginSystem.CreateAccount;

interface CreateAccountView {

    void onUsernameEmptyError();

    void onPasswordEmptyError();

    void onPasswordMismatchError();

    void onFail();

    void onSuccess();
}
