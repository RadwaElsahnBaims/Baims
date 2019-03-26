package com.baims.app.presentation.subscribe;

/**
 * Created by Radwa Elsahn on 11/8/2018.
 */
interface SubscribeView {

    void showPromoCodeFeilds();

    void showProgress(boolean show);

    void showError(String error);

    void showInValidPromocode();

    void openConfirmPage(String urlPayment, double discount,double originaPrice, double finalPrice, String typeCost);

    void showLogin();

    void openSuccessPage();
}
