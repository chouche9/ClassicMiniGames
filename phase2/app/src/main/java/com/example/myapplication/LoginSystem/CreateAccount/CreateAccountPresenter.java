package com.example.myapplication.LoginSystem.CreateAccount;

public class CreateAccountPresenter implements CreateAccountInteractor.onCreateAccountInteractorListener{

    private CreateAccountActivity createAccountActivity;

    private CreateAccountInteractor createAccountInteractor;

    public CreateAccountPresenter(CreateAccountActivity createAccountActivity) {
        this.createAccountActivity = createAccountActivity;
        this.createAccountInteractor = new CreateAccountInteractor();
    }

    public void verify(String username) {
        if (createAccountInteractor != null) {
            createAccountInteractor.verifyInteractor(username, createAccountActivity, this);
        }
    }

    @Override
    public void onSuccess() {
        if(createAccountActivity != null) {
            createAccountActivity.onSuccess();
        }
    }

    @Override
    public void onFail() {
        createAccountActivity.onFail();
    }
}
