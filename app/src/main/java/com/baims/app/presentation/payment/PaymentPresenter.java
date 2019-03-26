package com.baims.app.presentation.payment;

import android.support.annotation.NonNull;
import android.util.Log;

import com.baims.app.data.entities.response.ConfirmPaymentResponse;
import com.baims.app.data.entities.response.ConfirmPromocodeResponse;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;
import com.baims.app.presentation.common.Constants;
import com.google.gson.Gson;

import rx.Subscriber;

/**
 * Created by Radwa Elsahn on 11/13/2018.
 */
public class PaymentPresenter implements IPaymentPresenter {

    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;

    PaymentView mView;

    public PaymentPresenter(PaymentView view, BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository) {
        mView = view;
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;

    }

    public void confirmPromocode(String link, String promocode, String buyLink) {
        mView.showProgress(true);

        baimsRepository.confirmPromocode(baimsRepository.getAccessToken(), link, promocode, buyLink)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<ConfirmPromocodeResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                        mView.showProgress(false);
                        mView.onPaymentError(e.getMessage());
                    }

                    @Override
                    public void onNext(ConfirmPromocodeResponse response) {
                        mView.showProgress(false);
                        if (response.getStatusCode() == 0)// success
                        {
                            mView.onPaymentSuccess();
                        } else {
                            mView.onPaymentError(response.getMessage());
                        }


                    }
                });

    }

    void confirmPayment(String link, String orderLink, String paymentId) {
        mView.showProgress(true);
        baimsRepository.confirmPayment(baimsRepository.getAccessToken(), link, orderLink, paymentId)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<ConfirmPaymentResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                        mView.showProgress(false);
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ConfirmPaymentResponse response) {
                        Log.i("confirm","response: " + new Gson().toJson(response));
                        mView.showProgress(false);
                        if (response.getStatusCode() == 0)// success
                        {
                            mView.onPaymentSuccess();
                        } else {
                            mView.showError(response.getMessage());
                        }


                    }
                });

    }
}
