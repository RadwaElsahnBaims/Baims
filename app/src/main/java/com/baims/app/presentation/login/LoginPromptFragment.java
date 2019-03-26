package com.baims.app.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.baims.app.presentation.register.RegisterFragment;
import com.baims.app.presentation.utils.ActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Radwa Elsahn on 1/6/2019.
 */
public class LoginPromptFragment extends BaseFragment  implements LoginView {

    LoginPresenter presenter;

//    @BindView(R.id.btn_login)
//    Button mBtnLogin;
//
//    @BindView(R.id.edt_email)
//    EditText mEdtEmail;
//
//    @BindView(R.id.edt_password)
//    EditText mEdtPassword;

//    @BindView(R.id.progress_bar)
//    ProgressBar progressBar;


    public static LoginPromptFragment newInstance() {
        LoginPromptFragment fragment = new LoginPromptFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaimsRepository repository = BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(getActivity()));
        presenter = new LoginPresenterImpl(this, SchedulerProvider.getInstance(), repository,getActivity());

        if (presenter.isLogin()) {
            onLoginComplete();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_prompt, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        ((HomeActivity)getActivity()).replaceFragment(LoginFragment.newInstance());
//        Login loginObj = new Login();
//        loginObj.setPassword(mEdtPassword.getText().toString());
//        loginObj.setEmail_user_name(mEdtEmail.getText().toString());
//
//        presenter.ValidateControls(loginObj);
    }


    @OnClick(R.id.btn_register)
    public void onRegisterClick() {
        ((HomeActivity)getActivity()).replaceFragment(RegisterFragment.newInstance());
//        ActivityUtil.replaceFragmentToActivity(getActivity().getSupportFragmentManager(),
//                new LoginPromptFragment(), R.id.content, getActivity());
    }


    @Override
    public void showProgress(final boolean show) {
       // showProgressBar(progressBar, show, getActivity());
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
}
