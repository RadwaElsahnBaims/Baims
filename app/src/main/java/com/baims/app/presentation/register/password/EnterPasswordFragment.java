package com.baims.app.presentation.register.password;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class EnterPasswordFragment extends BaseFragment {

//    @BindView(R.id.btn_next)
//    Button mBtnNext;

    @BindView(R.id.edt_password)
    EditText mEdtPassword;

    public static EnterPasswordFragment newInstance() {
        EnterPasswordFragment fragment = new EnterPasswordFragment();

        return fragment;
    }

    ListItemListener<Bundle> listener;


    public void setOnNextListener(ListItemListener<Bundle> bundleListItemListener) {
        listener = bundleListItemListener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_enter_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnTextChanged(R.id.edt_password)
    public void onNameTextChanged() {
//        if (!mEdtPassword.getText().toString().isEmpty()) {
//            mBtnNext.setEnabled(true);
//
//        } else {
//            mBtnNext.setEnabled(false);
//        }
    }

    @OnClick(R.id.btn_next)
    public void onNextClick() {

        if (Validation.isValidPassword(6, mEdtPassword, getActivity())) {
//            Intent intent = new Intent(getActivity(), ConfirmAccountActivity.class);
//
//            Bundle extras = getIntent().getExtras();
//            extras.putString(Constants.PASSWORD_KEY, mEdtPassword.getText().toString());
//
//            intent.putExtras(extras);
//
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString(Constants.PASSWORD_KEY, mEdtPassword.getText().toString());
            listener.itemListener(bundle);
        }
    }
}
