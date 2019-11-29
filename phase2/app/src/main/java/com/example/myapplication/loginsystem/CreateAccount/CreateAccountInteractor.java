package com.example.myapplication.LoginSystem.CreateAccount;

import com.example.myapplication.Domain.UserManager;

public class CreateAccountInteractor {

    interface onCreateAccountInteractorListener {

        void onSuccess();

        void onFail();
    }

    public void verifyInteractor(String username, CreateAccountActivity createAccountActivity,
                                 onCreateAccountInteractorListener listener) {
        UserManager userManager = UserManager.getInstance(createAccountActivity);
        if(userManager.createAuthenticate(username)) {
            listener.onSuccess();
        } else {
            listener.onFail();
        }
    }
}
