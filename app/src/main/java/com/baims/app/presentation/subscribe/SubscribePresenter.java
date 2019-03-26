package com.baims.app.presentation.subscribe;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.baims.app.data.entities.response.PaymentPromocodeResponse;
import com.baims.app.data.entities.response.PaymentResponse;
import com.baims.app.data.entities.response.ValidPromocodeResponse;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;
import com.baims.app.presentation.common.Constants;

import rx.Subscriber;

/**
 * Created by Radwa Elsahn on 11/8/2018.
 */
public class SubscribePresenter implements ISubscribePresenter {

    boolean hasPromocode;
    String link;

    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;

    SubscribeView mView;
    private String price;

    public SubscribePresenter(SubscribeView view, BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository) {
        mView = view;
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;
    }

    public void setHasPromoCode(boolean hasPromocode) {
        this.hasPromocode = hasPromocode;
        if (hasPromocode)
            mView.showPromoCodeFeilds();
    }


    @Override
    public void subscribeClick(String promocode) {
        Log.e("radwaaa", price);
        if (baimsRepository.isLogin()) {
            if (hasPromocode && !promocode.isEmpty()) {
                validateCobone(promocode);
            }
// else if (price.equals(0) || price.equals("0.0")) {
//                paymentPromocode(promocode);
//            }
            else {
                payment();
            }
        } else {
            mView.showError("please login");
            mView.showLogin();
            // please login
        }
    }

    @Override
    public void setLink(String link) {
        this.link = link;
    }


    @Override
    public void payment() {
        if (baimsRepository.isLogin()) {
            mView.showProgress(true);

            baimsRepository.payment(baimsRepository.getAccessToken(), link)
                    .observeOn(schedulerProvider.ui())
                    .subscribeOn(schedulerProvider.computation())
                    .retry(Constants.RETRY_COUNT)
                    .subscribe(new Subscriber<PaymentResponse>() {
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
                        public void onNext(PaymentResponse response) {
                            mView.showProgress(false);
                            if (response.getUrlPayment().startsWith("http"))
                                mView.openConfirmPage(response.getUrlPayment(), 0, Double.valueOf(price), Double.valueOf(price), "pay");
                            else if (response.getResponse().equals("free")) {
                                mView.openSuccessPage();
                            } else
                                mView.showError("برجاء المحاولة مرة اخرى");
                        }
                    });
        } else
            mView.showLogin();
    }

    public void paymentPromocode(String promocode) {
        mView.showProgress(true);

        baimsRepository.paymentPromocode(baimsRepository.getAccessToken(), link, promocode)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<PaymentPromocodeResponse>() {
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
                    public void onNext(PaymentPromocodeResponse response) {
                        mView.showProgress(false);
                        mView.openConfirmPage(response.getUrlPayment(), response.getDiscount(), Double.valueOf(price), response.getTotalPrice(), response.getTypeCost());
                    }
                });

    }


    @Override
    public void validateCobone(String promocode) {
        mView.showProgress(true);

        baimsRepository.validPromocode(baimsRepository.getAccessToken(), link, promocode)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<ValidPromocodeResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                        mView.showProgress(false);
                        mView.showError("Invalid PromoCode");
                    }

                    @Override
                    public void onNext(ValidPromocodeResponse response) {
                        mView.showProgress(false);
                        if (response.isValidPromocode()) { //isvalidCobone
                            paymentPromocode(promocode);
                        } else
                            mView.showInValidPromocode();

                    }
                });
    }

    @Override
    public void setPrice(String price) {
        this.price = price;
    }
}
