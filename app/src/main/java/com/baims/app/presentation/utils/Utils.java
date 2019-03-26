package com.baims.app.presentation.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.baims.app.R;

/**
 * Created by Radwa Elsahn on 10/21/2018.
 */
public class Utils {
    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }

    public static LayerDrawable createRoundedBg(Context mContext, String color) {
        // Initialize two float arrays
//        float[] outerRadii = new float[]{75, 75, 75, 75, 75, 75, 75, 75};
//        float[] innerRadii = new float[]{75, 75, 75, 75, 75, 75, 75, 75};
        float[] outerRadii = new float[]{15, 15, 15, 15, 15, 15, 15, 15};
        float[] innerRadii = new float[]{15, 15, 15, 15, 15, 15, 15, 15};

        // Set the shape background
        ShapeDrawable backgroundShape = new ShapeDrawable(new RoundRectShape(
                outerRadii,
                null,
                innerRadii
        ));

        if (color.isEmpty())
            color = "#000000";

        backgroundShape.getPaint().setColor(Color.parseColor(color)); // background color
        backgroundShape.getPaint().setStyle(Paint.Style.FILL); // Define background
        backgroundShape.getPaint().setAntiAlias(true);

        // Initialize an array of drawables
        Drawable[] drawables = new Drawable[]{
                backgroundShape
        };

        int dimen_16 = (int) mContext.getResources().getDimension(R.dimen._16sdp);
        int dimen_4 = (int) mContext.getResources().getDimension(R.dimen._4sdp);
        // Set shape padding
        backgroundShape.setPadding(dimen_16, dimen_4, dimen_16, dimen_4);

        // Initialize a layer drawable object
        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        return layerDrawable;

    }

    public static LayerDrawable createRoundedBg(Context mContext, String color, int curve) {
        // Initialize two float arrays
        float[] outerRadii = new float[]{curve, curve, curve, curve, curve, curve, curve, curve};
        float[] innerRadii = new float[]{curve, curve, curve, curve, curve, curve, curve, curve};

        // Set the shape background
        ShapeDrawable backgroundShape = new ShapeDrawable(new RoundRectShape(
                outerRadii,
                null,
                innerRadii
        ));
        if (color.isEmpty())
            color = "#000000";

        backgroundShape.getPaint().setColor(Color.parseColor(color)); // background color
        backgroundShape.getPaint().setStyle(Paint.Style.FILL); // Define background
        backgroundShape.getPaint().setAntiAlias(true);

        // Initialize an array of drawables
        Drawable[] drawables = new Drawable[]{
                backgroundShape
        };

        int dimen_16 = (int) mContext.getResources().getDimension(R.dimen._16sdp);
        int dimen_4 = (int) mContext.getResources().getDimension(R.dimen._4sdp);
        // Set shape padding
        backgroundShape.setPadding(dimen_16, dimen_4, dimen_16, dimen_4);

        // Initialize a layer drawable object
        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        return layerDrawable;

    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected();
        }
        return false;
    }

    public static String getDeviceId(Activity context) {
        TelephonyManager tm = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            }
            imei = (String) tm.getImei();
        } else
            imei = tm.getDeviceId();
        return imei;
    }

//    public static String getIMEINumber(Activity activity) {
//        String IMEINumber = "";
////        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
//        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
//            return "";
//        }
//        TelephonyManager telephonyMgr = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            IMEINumber = telephonyMgr.getImei();
//        } else {
//            IMEINumber = telephonyMgr.getDeviceId();
//        }
//        return IMEINumber;
//    }

}
