package com.baims.app.presentation.common;

/**
 * Created by Radwa Elsahn on 2/5/2019.
 */
public class SubscriptionNotifier extends Notifier<Boolean> {
    private static SubscriptionNotifier instance;

    public static SubscriptionNotifier getInstance() {
        if (instance == null) {
            instance = new SubscriptionNotifier();
        }
        return instance;
    }
}
