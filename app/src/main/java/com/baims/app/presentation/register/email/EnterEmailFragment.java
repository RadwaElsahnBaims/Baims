package com.baims.app.presentation.register.email;

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

public class EnterEmailFragment extends BaseFragment {

//    @BindView(R.id.btn_next)
//    Button mBtnNext;

    @BindView(R.id.edt_email)
    EditText mEdtEmail;

    ListItemListener<Bundle> listener;


    public void setOnNextListener(ListItemListener<Bundle> bundleListItemListener) {
        listener = bundleListItemListener;
    }


    public static EnterEmailFragment newInstance() {
        EnterEmailFragment fragment = new EnterEmailFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_enter_email, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnTextChanged(R.id.edt_email)
    public void onNameTextChanged() {
//        if (!mEdtEmail.getText().toString().isEmpty()) {
//            mBtnNext.setEnabled(true);
//
//        } else {
//            mBtnNext.setEnabled(false);
//        }
    }

    @OnClick(R.id.btn_next)
    public void onNextClick() {

        if (Validation.isValidEmail(mEdtEmail, getActivity())) {
//            Intent intent = new Intent(getActivity(), EnterPasswordActivity.class);
//
//            Bundle extras = getArguments();
//            extras.putString(Constants.EMAIL_KEY, mEdtEmail.getText().toString());
//
//            intent.putExtras(extras);
//
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString(Constants.EMAIL_KEY, mEdtEmail.getText().toString());
            listener.itemListener(bundle);
        }
    }
}
