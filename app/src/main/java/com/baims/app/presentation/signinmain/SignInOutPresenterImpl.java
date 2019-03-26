package com.baims.app.presentation.signinmain;

import android.support.annotation.NonNull;

import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;


/**
 * Created by Radwa Elsahn on 10/18/2018.
 */
public class SignInOutPresenterImpl implements SignInOutPresenter {

    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;


    public SignInOutPresenterImpl(BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository) {
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public boolean isLogin() {

        return baimsRepository.isLogin();
    }
}
