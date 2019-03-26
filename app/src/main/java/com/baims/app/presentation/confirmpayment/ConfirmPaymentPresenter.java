package com.baims.app.presentation.confirmpayment;

import android.support.annotation.NonNull;

import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;


/**
 * Created by Radwa Elsahn on 11/13/2018.
 */
public class ConfirmPaymentPresenter implements IConfirmPaymentPresenter {

    String urlPayment;

    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;

    ConfirmPaymentView mView;

    public ConfirmPaymentPresenter(ConfirmPaymentView view, BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository) {
        mView = view;
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;
    }

    public void setUrlPayment(String urlPayment) {
        this.urlPayment = urlPayment;
    }

    @Override
    public void onSubscribeClick(String type) {
        if (type.equals("free"))
            mView.openSuccessPage();
        else
            mView.openPaymentPage(urlPayment);
//        mView.openSuccessPage();

    }
}
