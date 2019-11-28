package com.example.myapplication.LoginSystem.Login;

interface LoginView {

    void onUsernameEmptyError();

    void onPasswordEmptyError();

    void onFail();

    void onSuccess();

}
