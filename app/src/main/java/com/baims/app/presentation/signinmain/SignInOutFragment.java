//package com.baims.app.presentation.signinmain;
//
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.baims.app.R;
//import com.baims.app.data.repositories.BaimsRemoteDataSource;
//import com.baims.app.data.repositories.BaimsRepository;
//import com.baims.app.data.repository.BaimsCacheDataSource;
//import com.baims.app.data.utils.schedulers.SchedulerProvider;
//import com.baims.app.presentation.common.BaseFragment;
//import com.baims.app.presentation.home.FragmentHome;
//import com.baims.app.presentation.login.LoginPromptFragment;
//import com.baims.app.presentation.register.RegisterFragment;
//import com.baims.app.presentation.utils.ActivityUtil;
//
//import java.util.Locale;
//
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class SignInOutFragment extends BaseFragment {
//    SignInOutPresenter presenter;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_choose_login_or_register, container, false);
//        ButterKnife.bind(this, view);
//
//        return view;
//    }
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_choose_login_or_register);
////        ButterKnife.bind(this);
////        getSupportActionBar().setTitle(R.string.my_page_title);
////
////        BaimsRepository repository = BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(this));
////        presenter = new SignInOutPresenterImpl(SchedulerProvider.getInstance(), repository);
////
////        changeLocale();
////
////        if(presenter.isLogin())
////        {
//////            Intent intent = new Intent(this, HomeActivity.class);
//////            startActivity(intent);
////
////            ActivityUtil.replaceFragmentToActivity(getSupportFragmentManager(),
////                    new FragmentHome(), R.id.content, this);
////        }
////
////    }
//
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        BaimsRepository repository = BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(getActivity()));
//        presenter = new SignInOutPresenterImpl(SchedulerProvider.getInstance(), repository);
//
//        changeLocale();
//
//        if (presenter.isLogin()) {
////            Intent intent = new Intent(this, HomeActivity.class);
////            startActivity(intent);
//
//            ActivityUtil.replaceFragmentToActivity(getActivity().getSupportFragmentManager(),
//                    FragmentHome.newInstance(), R.id.content, getActivity());
//        }
//
//    }
//
//    private void changeLocale() {
//        Locale locale = new Locale("ar");
//        Resources resources = getResources();
//        Configuration config = resources.getConfiguration();
//        config.locale = locale;
//        if (Build.VERSION.SDK_INT >= 17) {
//            config.setLayoutDirection(locale);
//        }
//        resources.updateConfiguration(config, resources.getDisplayMetrics());
//    }
//
////    protected void initToolbar(Toolbar toolbar, String title) {
////        setSupportActionBar(toolbar);
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////        getSupportActionBar().setTitle(title);
////    }
//
//    @OnClick(R.id.btn_register)
//    public void onRegisterClick() {
//        ActivityUtil.replaceFragmentToActivity(getActivity().getSupportFragmentManager(),
//                new RegisterFragment(), R.id.content, getActivity());
//        //startActivity(new Intent(this, YourNameActivity.class));
//    }
//
//    @OnClick(R.id.btn_login)
//    public void onLoginClick() {
//        //  startActivity(new Intent(this, LoginActivity.class));
//        ActivityUtil.replaceFragmentToActivity(getActivity().getSupportFragmentManager(),
//                LoginPromptFragment.newInstance(), R.id.content, getActivity());
//    }
//
//}
