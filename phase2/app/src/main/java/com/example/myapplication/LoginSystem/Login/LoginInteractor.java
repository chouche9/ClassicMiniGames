package com.example.myapplication.LoginSystem.Login;

import com.example.myapplication.Domain.UserManager;

class LoginInteractor {

    interface onLoginInteractorListener {

        void onSuccess();

        void onFail();

    }

    public void verifyInteractor(String username, String password,
                                 LoginActivity loginActivity, onLoginInteractorListener listener) {

        UserManager userManager = UserManager.getInstance(loginActivity);
        if(userManager.authenticate(username, password)){
            listener.onSuccess();
        } else {
            listener.onFail();
        }

    }

}
