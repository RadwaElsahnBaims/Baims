package com.baims.app.presentation.register.confirmaccount;

public interface RegisterView {
    void onRegisterComplete();

    void showProgress(boolean show);

    void showError(String error);
}
