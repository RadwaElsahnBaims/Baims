package com.baims.app.presentation.common;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.baims.app.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Radwa Elsahn on 12/17/2018.
 */
public class BaseFragment extends Fragment {
    protected void showProgressBar(ProgressBar progressBar, boolean show, Activity activity) {
        if (progressBar != null)
            if (show) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
    }

    protected void showDialog(Activity activity, String message) {

        SweetAlertDialog pDialog = new SweetAlertDialog(activity, SweetAlertDialog.NORMAL_TYPE);
        //pDialog.setTitleText("");
        pDialog.setContentText(message);
        pDialog.setConfirmText(getResources().getString(R.string.dialog_ok));
        pDialog.show();
        Button btn = (Button) pDialog.findViewById(R.id.confirm_button);
        btn.setBackgroundResource(R.drawable.bg_curved_primary);
    }


    public static void showDialog2(Context context, String message) {

        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        //pDialog.setTitleText("");
        pDialog.setContentText(message);
        pDialog.setConfirmText(context.getResources().getString(R.string.dialog_ok));
        pDialog.show();
        Button btn = (Button) pDialog.findViewById(R.id.confirm_button);
        btn.setBackgroundResource(R.drawable.bg_curved_primary);
    }
}
