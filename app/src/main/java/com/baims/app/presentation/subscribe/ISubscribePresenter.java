package com.baims.app.presentation.subscribe;

/**
 * Created by Radwa Elsahn on 11/8/2018.
 */
public interface ISubscribePresenter {
    void payment();

    void validateCobone(String promocode);

    void setHasPromoCode(boolean hasPromocode);

    void subscribeClick(String promocode);

    void setPrice(String price);

    void setLink(String string);
}
