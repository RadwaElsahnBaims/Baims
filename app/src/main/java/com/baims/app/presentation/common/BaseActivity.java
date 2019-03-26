package com.baims.app.presentation.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.baims.app.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseActivity extends AppCompatActivity {

    protected void showProgressBar(ProgressBar progressBar, boolean show, Activity activity) {
        if (progressBar != null)
            if (show) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
    }

//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    protected void forceRTLIfSupported()
//    {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
//            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
//        }
//    }

    protected void initToolbar(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //    getSupportActionBar().setTitle(title);
    }


    protected void logout() {
    }


    protected void showDialog(Activity activity, String message) {

        SweetAlertDialog pDialog = new SweetAlertDialog(activity, SweetAlertDialog.NORMAL_TYPE);
        //pDialog.setTitleText("");
        pDialog.setContentText(message);
        pDialog.setConfirmText(getResources().getString(R.string.dialog_ok));
        pDialog.show();
        Button btn = (Button) pDialog.findViewById(R.id.confirm_button);
        btn.setBackgroundResource(R.drawable.bg_curved_primary);



//        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogStyle);
//// Add the buttons
//        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // User clicked OK button
//            }
//        });
//        builder.setMessage(message);
//
//// Create the AlertDialog
//        AlertDialog dialog = builder.create();
//        dialog.show();
    }

}
