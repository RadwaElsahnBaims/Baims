package com.baims.app.presentation.payment;

/**
 * Created by Radwa Elsahn on 11/8/2018.
 */
interface PaymentView {

    void showProgress(boolean show);

    void showError(String error);

    void onPaymentSuccess();

    void onPaymentError(String error);

}
