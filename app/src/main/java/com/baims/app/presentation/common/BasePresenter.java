package com.baims.app.presentation.common;

import com.baims.app.data.repositories.BaimsRepository;

/**
 * Created by Radwa Elsahn on 11/28/2018.
 */
public class BasePresenter {


    public boolean isLogin(BaimsRepository baimsRepository) {

        return baimsRepository.isLogin();
    }
}
