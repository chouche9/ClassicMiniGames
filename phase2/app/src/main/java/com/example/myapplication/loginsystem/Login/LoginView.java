package com.example.myapplication.loginsystem.Login;

interface LoginView {

    void onUsernameEmptyError();

    void onPasswordEmptyError();

    void onFail();

    void onSuccess();

}
