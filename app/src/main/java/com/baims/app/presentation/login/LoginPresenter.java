package com.baims.app.presentation.login;

import com.baims.app.data.entities.Login;

public interface LoginPresenter {
    void Login(Login loginObj);

    boolean isLogin();

    void ValidateControls(Login loginObj);
}
