package com.baims.app.presentation.register.confirmaccount;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baims.app.R;
import com.baims.app.data.entities.Register;
import com.baims.app.data.repositories.BaimsRemoteDataSource;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.repository.BaimsCacheDataSource;
import com.baims.app.data.utils.schedulers.SchedulerProvider;
import com.baims.app.presentation.common.BaseActivity;
import com.baims.app.presentation.common.BaseFragment;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.common.ListItemListener;
import com.baims.app.presentation.home.HomeActivity;
import com.baims.app.presentation.register.number.EnterMobileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmAccountFragment extends BaseFragment implements RegisterView {

    @BindView(R.id.tv_name)
    TextView mTvName;

    @BindView(R.id.tv_mail)
    TextView mTvMail;

    @BindView(R.id.tv_number)
    TextView mTvPhone;

    @BindView(R.id.tv_password)
    TextView mTvPassword;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    RegisterPresenter registerPresenter;
    ListItemListener<Bundle> listener;

    public void setOnNextListener(ListItemListener<Bundle> bundleListItemListener) {
        listener = bundleListItemListener;
    }

    public static ConfirmAccountFragment newInstance(Bundle bundle) {
        ConfirmAccountFragment fragment = new ConfirmAccountFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_confirm_account, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerPresenter = new RegisterPresenterImpl(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(getActivity())));

        Register registerObj = new Register();
        registerObj.setPassword("******");//(getArguments().getString(Constants.PASSWORD_KEY));
        registerObj.setPhone(getArguments().getString(Constants.PHONE_KEY));
        registerObj.setEmail(getArguments().getString(Constants.EMAIL_KEY));
        registerObj.setDisplay_name(getArguments().getString(Constants.NAME_KEY));

        String id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i("deviceId", id);
        registerObj.setDevice_id(id);
        registerPresenter.setRegisterObj(registerObj);

        mTvMail.setText(registerObj.getEmail());
        mTvName.setText(registerObj.getDisplay_name());
        mTvPassword.setText(registerObj.getPassword());
        mTvPhone.setText(registerObj.getPhone());
    }


    @OnClick(R.id.btn_confirm)
    public void onConfirmClick() {
        registerPresenter.Register(registerPresenter.getRegisterObj());
    }

    @Override
    public void onRegisterComplete() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        listener.itemListener(null);
    }

    @Override
    public void showProgress(final boolean show) {
        showProgressBar(progressBar, show, getActivity());
    }

    @Override
    public void showError(String error) {
        showDialog(getActivity(),error);
    }


}

