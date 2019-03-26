package com.baims.app.presentation.register.confirmaccount;

import android.support.annotation.NonNull;
import android.util.Log;

import com.baims.app.data.entities.Login;
import com.baims.app.data.entities.Register;
import com.baims.app.data.entities.response.AccountResponse;
import com.baims.app.data.entities.response.LoginResponse;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;
import com.baims.app.presentation.common.Constants;

import retrofit2.Response;
import rx.Subscriber;

public class RegisterPresenterImpl implements RegisterPresenter {
    private RegisterView mView;

    Register registerObj;

    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;


    public RegisterPresenterImpl(RegisterView view, BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository) {
        mView = view;
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void Register(Register registerObj1) {
        mView.showProgress(true);
        baimsRepository.register(registerObj1)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<AccountResponse>() {
                    @Override
                    public void onCompleted()
                    {
                    //    mView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //mView.showError("Something wrong happened");
                        Log.e("error", e.toString());
                        mView.showProgress(false);
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(AccountResponse accountResponse) {
//                        mView.showProgress(false);
                        Log.e("accountResponse", accountResponse.toString());
                        if (accountResponse.getStatusCode() == 0) {
                            getAccessToken();
                        } else {
                            Log.d("error", accountResponse.getMessage());
                            mView.showError(accountResponse.getMessage());
                        }
                    }
                });
    }

    private void getAccessToken() {

        mView.showProgress(true);

        Login loginObj = new Login();
        loginObj.setEmail_user_name(registerObj.getEmail());
        loginObj.setPassword(registerObj.getPassword());

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
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(LoginResponse accountResponse) {

                        if (accountResponse.getStatusCode() == 0) {
                            baimsRepository.saveUserInfo(accountResponse.getData());
                            mView.onRegisterComplete();
                        } else if (accountResponse.getStatusCode() >= 1) {
                            mView.showError(accountResponse.getMessage());
                        }
                    }
                });


    }

    @Override
    public void setRegisterObj(Register registerObj) {
        this.registerObj = registerObj;
    }

    @Override
    public Register getRegisterObj() {
        return registerObj;
    }

}
