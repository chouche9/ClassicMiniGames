package com.example.myapplication.loginsystem.CreateAccount;

import com.example.myapplication.domain.UserManager;

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
