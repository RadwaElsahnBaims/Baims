package com.baims.app.presentation.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.baims.app.R;


/**
 * Created by vezikon on 10/24/16.
 */

public class ActivityUtil {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";


    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId, Activity
                                                     activity) {
        //checkNotNull(fragmentManager);
        //checkNotNull(fragment);
        fragmentManager.beginTransaction()
                .add(frameId, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();

        closeKeyboard(activity);
    }

    public static void replaceFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, int frameId, Activity
                                                         activity) {
        //checkNotNull(fragmentManager);
        //checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();

        closeKeyboard(activity);
    }

    public static void addFirstFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                                  @NonNull Fragment fragment, int frameId,
                                                  Activity activity) {
        //checkNotNull(fragmentManager);
        //checkNotNull(fragment);
        fragmentManager.beginTransaction()
                .replace(frameId, fragment)
                .commitAllowingStateLoss();

        closeKeyboard(activity);
    }


    public static void setRootView(Fragment currentView,
                                   FragmentManager fragmentManager,
                                   int frameId,
                                   String tag, Activity activity) {
        // Pop off everything up to and including the current tab
        try {
            fragmentManager.popBackStackImmediate(BACK_STACK_ROOT_TAG, FragmentManager
                    .POP_BACK_STACK_INCLUSIVE);
        } catch (IllegalStateException e) {
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager
                    .POP_BACK_STACK_INCLUSIVE);
        }
        fragmentManager.beginTransaction()
                .replace(frameId, currentView, tag)
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commitAllowingStateLoss();

        closeKeyboard(activity);

    }

    //{activity which hold the current fragment close it's keyboard if it's opened in fragment transition
    private static void closeKeyboard(Activity activity) {
        View v = activity.getCurrentFocus();
        if (v != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (inputManager != null)
                inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


    public static void setFullScreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
