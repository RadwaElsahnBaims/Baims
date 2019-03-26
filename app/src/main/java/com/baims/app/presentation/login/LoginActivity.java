//package com.baims.app.presentation.login;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.baims.app.R;
//import com.baims.app.data.entities.Login;
//import com.baims.app.data.repositories.BaimsRemoteDataSource;
//import com.baims.app.data.repositories.BaimsRepository;
//import com.baims.app.data.repository.BaimsCacheDataSource;
//import com.baims.app.data.utils.schedulers.SchedulerProvider;
//import com.baims.app.presentation.common.BaseActivity;
//import com.baims.app.presentation.home.HomeActivity;
//import com.baims.app.presentation.register.name.YourNameActivity;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//
//public class LoginActivity extends BaseActivity implements LoginView {
//
//    LoginPresenter presenter;
//
//    @BindView(R.id.btn_login)
//    Button mBtnLogin;
//
//    @BindView(R.id.edt_email)
//    EditText mEdtEmail;
//
//    @BindView(R.id.edt_password)
//    EditText mEdtPassword;
//
//    @BindView(R.id.progress_bar)
//    ProgressBar progressBar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        ButterKnife.bind(this);
//        getSupportActionBar().setTitle(R.string.my_page_title);
//
//        BaimsRepository repository = BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(this));
//        presenter = new LoginPresenterImpl(this, SchedulerProvider.getInstance(), repository);
//
//        if (presenter.isLogin()) {
//            onLoginComplete();
//        }
//    }
//
//    @OnClick(R.id.btn_login)
//    public void onLoginClick() {
//        Login loginObj = new Login();
//        loginObj.setPassword(mEdtPassword.getText().toString());
//        loginObj.setEmail_user_name(mEdtEmail.getText().toString());
//
//        presenter.Login(loginObj);
//    }
//
//
//    @OnClick(R.id.btn_register)
//    public void onRegisterClick() {
//        startActivity(new Intent(this, YourNameActivity.class));
//    }
//
//
//    @Override
//    public void showProgress(final boolean show) {
//        showProgressBar(progressBar, show, this);
//    }
//
//    @Override
//    public void showError(String error) {
//        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onLoginComplete() {
//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivity(intent);
//
//    }
//}
