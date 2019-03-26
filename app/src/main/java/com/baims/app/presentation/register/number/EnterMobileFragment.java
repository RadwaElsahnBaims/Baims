package com.baims.app.presentation.register.number;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.baims.app.R;
import com.baims.app.presentation.common.BaseFragment;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.common.ListItemListener;

import com.baims.app.presentation.utils.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class EnterMobileFragment extends BaseFragment {
    @BindView(R.id.btn_next)
    Button mBtnNext;

    @BindView(R.id.edt_country_code)
    EditText mEdtCountryCode;

    @BindView(R.id.edt_mobile)
    EditText mEdtMobile;

    ListItemListener<Bundle> listener;


    public void setOnNextListener(ListItemListener<Bundle> bundleListItemListener) {
        listener = bundleListItemListener;
    }

    public static EnterMobileFragment newInstance(String email, String name) {
        EnterMobileFragment fragment = new EnterMobileFragment();
        Bundle args = new Bundle();
        args.putString(Constants.NAME_KEY, name);
        args.putString(Constants.EMAIL_KEY, email);
        fragment.setArguments(args);
        return fragment;
    }


    public static EnterMobileFragment newInstance() {
        EnterMobileFragment fragment = new EnterMobileFragment();
//        Bundle args = new Bundle();
//        args.putString(Constants.NAME_KEY, name);
//        args.putString(Constants.EMAIL_KEY, email);
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_enter_mobile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnTextChanged({R.id.edt_mobile, R.id.edt_country_code})
    public void onNameTextChanged() {
//        if (!mEdtCountryCode.getText().toString().isEmpty() && !mEdtMobile.getText().toString().isEmpty()) {
//            mBtnNext.setEnabled(true);
//
//        } else {
//            mBtnNext.setEnabled(false);
//        }
    }

    @OnClick(R.id.btn_next)
    public void onNextClick() {
        if (Validation.isValidMobileNumber(mEdtMobile, getActivity())) {
//            Intent intent = new Intent(getActivity(), EnterEmailActivity.class);
//
//            Bundle extras = getIntent().getExtras();
//            extras.putString(Constants.PHONE_KEY, mEdtMobile.getText().toString());
//            extras.putString(Constants.COUNTRY_CODE_KEY, mEdtCountryCode.getText().toString());
//            Log.i("mobile" ,mEdtMobile.getText().toString());
//            Log.i("cc" ,mEdtCountryCode.getText().toString());
//
//            intent.putExtras(extras);
//
//            startActivity(intent);


            Bundle bundle = new Bundle();
            bundle.putString(Constants.PHONE_KEY, mEdtMobile.getText().toString());
            bundle.putString(Constants.COUNTRY_CODE_KEY, mEdtCountryCode.getText().toString());
            listener.itemListener(bundle);

        }
    }
}
