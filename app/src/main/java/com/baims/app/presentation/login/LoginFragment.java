package com.baims.app.presentation.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baims.app.R;
import com.baims.app.data.entities.Login;
import com.baims.app.data.repositories.BaimsRemoteDataSource;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.repository.BaimsCacheDataSource;
import com.baims.app.data.utils.schedulers.SchedulerProvider;
import com.baims.app.presentation.common.BaseFragment;
import com.baims.app.presentation.home.HomeActivity;
import com.baims.app.presentation.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Radwa Elsahn on 1/6/2019.
 */
public class LoginFragment extends BaseFragment implements LoginView {

    LoginPresenter presenter;

    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @BindView(R.id.edt_email)
    EditText mEdtEmail;

    @BindView(R.id.edt_password)
    EditText mEdtPassword;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    TelephonyManager telephonyManager;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaimsRepository repository = BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(getActivity()));
        presenter = new LoginPresenterImpl(this, SchedulerProvider.getInstance(), repository, getActivity());

        if (presenter.isLogin()) {
            onLoginComplete();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_login)
    public void onLoginClick() {

        //String imei = getIMEINumber();
        //getting unique id for device
        String id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i("deviceId", id);
        Login loginObj = new Login();
        loginObj.setPassword(mEdtPassword.getText().toString());
        loginObj.setEmail_user_name(mEdtEmail.getText().toString());
        loginObj.setDevice_id(id);

        presenter.ValidateControls(loginObj);

    }


//    @OnClick(R.id.btn_register)
//    public void onRegisterClick() {
//        ((HomeActivity)getActivity()).replaceFragment(RegisterFragment.newInstance());
////        ActivityUtil.replaceFragmentToActivity(getActivity().getSupportFragmentManager(),
////                new LoginPromptFragment(), R.id.content, getActivity());
//    }


    @Override
    public void showProgress(final boolean show) {
        showProgressBar(progressBar, show, getActivity());
    }

    @Override
    public void showError(String error) {
        showDialog(getActivity(), error);
    }

    @Override
    public void onLoginComplete() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);

    }

//    private void getDeviceId() {
//        telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
//            return;
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 101:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
//                        return;
//                    }
//                    String imeiNumber = telephonyManager.getDeviceId();
//                    Login loginObj = new Login();
//                    loginObj.setPassword(mEdtPassword.getText().toString());
//                    loginObj.setEmail_user_name(mEdtEmail.getText().toString());
//                    loginObj.setDevice_id(imeiNumber);
//                    presenter.ValidateControls(loginObj);
//
//                }
//                break;
//
//        }
//    }

//    public String getIMEINumber() {
//        String IMEINumber = "";
////        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
//            return "";
//        }
//        TelephonyManager telephonyMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            IMEINumber = telephonyMgr.getImei();
//        } else {
//            IMEINumber = telephonyMgr.getDeviceId();
//        }
//        return IMEINumber;
//    }
}
