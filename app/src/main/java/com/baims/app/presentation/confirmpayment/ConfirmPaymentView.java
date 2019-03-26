package com.baims.app.presentation.confirmpayment;

/**
 * Created by Radwa Elsahn on 11/13/2018.
 */
interface ConfirmPaymentView {
    void showProgress(boolean show);

    void showError(String error);

    void openPaymentPage(String urlPayment);

    void openSuccessPage();
}
