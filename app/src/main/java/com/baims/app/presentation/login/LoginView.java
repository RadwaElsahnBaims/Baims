package com.baims.app.presentation.login;

public interface LoginView {
    void onLoginComplete();

    void showProgress(boolean show);

    void showError(String error);
}
