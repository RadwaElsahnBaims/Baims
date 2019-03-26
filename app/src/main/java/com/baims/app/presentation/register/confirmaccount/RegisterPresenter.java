package com.baims.app.presentation.register.confirmaccount;


import com.baims.app.data.entities.Register;

public interface RegisterPresenter {
    void Register(Register registerObj);

    void setRegisterObj(Register registerObj);

    Register getRegisterObj();
}
