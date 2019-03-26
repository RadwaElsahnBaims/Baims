package com.baims.app.presentation.login;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.baims.app.data.entities.Login;
import com.baims.app.data.entities.response.LoginResponse;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.utils.Utils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import rx.Subscriber;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView mView;

    Activity activity;
    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;


    public LoginPresenterImpl(LoginView view, BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository, Activity activity) {
        mView = view;
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;
        this.activity = activity;
    }

    @Override
    public void Login(Login loginObj) {
        mView.showProgress(true);
        baimsRepository.login(loginObj)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showProgress(false);

                        mView.showError("تأكد من الايميل او كلمة السر");
//                        mView.showError(e.getMessage());

                    }

                    @Override
                    public void onNext(LoginResponse accountResponse) {

                        if (accountResponse.getStatusCode() == 0) {
                            baimsRepository.saveUserInfo(accountResponse.getData());
                            mView.onLoginComplete();
                        } else if (accountResponse.getStatusCode() >= 1) {
                            mView.showError(accountResponse.getMessage());
                        }
                    }
                });
    }

    @Override
    public boolean isLogin() {

        return !baimsRepository.getUserInfo().getAccess_token().isEmpty();

    }

    @Override
    public void ValidateControls(Login loginObj) {

        baimsRepository.saveDeviceId(loginObj.device_id);
        if (loginObj.getEmail_user_name().isEmpty()) {
            mView.showError("من فضلك ادخل اسم المستخدم");
            return;
        }
        if (loginObj.getPassword().isEmpty()) {
            mView.showError("من فضلك ادخل الرمز السري");
            return;
        }
//        loginObj.device_id= Utils.getIMEINumber(activity);

        Login(loginObj);
    }
}
