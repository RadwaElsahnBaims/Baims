package com.baims.app.presentation.common;

/**
 * Created by Radwa Elsahn on 2/5/2019.
 */
public class LikedNotifier extends Notifier<Integer> {
    private static LikedNotifier instance;

    public static LikedNotifier getInstance() {
        if (instance == null) {
            instance = new LikedNotifier();
        }
        return instance;
    }
}
