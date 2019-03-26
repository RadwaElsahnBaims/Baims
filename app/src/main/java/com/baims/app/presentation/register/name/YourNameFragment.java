package com.baims.app.presentation.register.name;

import android.app.Activity;
import android.content.Intent;
import android.os.Binder;
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
import butterknife.Unbinder;

public class YourNameFragment extends BaseFragment {

    @BindView(R.id.edt_name)
    EditText mEdtName;
    //
    @BindView(R.id.btn_next)
    Button mBtnNext;

    ListItemListener<Bundle> listener;


    public void setOnNextListener(ListItemListener<Bundle> bundleListItemListener) {
        listener = bundleListItemListener;
    }

    public static YourNameFragment newInstance() {
        YourNameFragment fragment = new YourNameFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_your_name, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // unbinder.unbind();
    }

    @OnTextChanged(R.id.edt_name)
    public void onNameTextChanged() {
//        if (!mEdtName.getText().toString().isEmpty()) {
//            mBtnNext.setEnabled(true);
//
//        } else {
//            mBtnNext.setEnabled(false);
//        }
    }

    @OnClick(R.id.btn_next)
    public void onNextClick() {
        if (Validation.isValidString(mEdtName, getString(R.string.require_field))) {
//            Intent intent = new Intent(getActivity(), EnterMobileActivity.class);
//            intent.putExtra(Constants.NAME_KEY, mEdtName.getText().toString());
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString(Constants.NAME_KEY, mEdtName.getText().toString());
            listener.itemListener(bundle);
        }
    }
}
