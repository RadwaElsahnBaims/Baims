package com.baims.app.presentation.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.baims.app.R;
import com.baims.app.data.entities.Section;
import com.baims.app.data.repositories.BaimsRemoteDataSource;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.repository.BaimsCacheDataSource;
import com.baims.app.data.utils.schedulers.SchedulerProvider;
import com.baims.app.presentation.categories.CategoriesFragment;
import com.baims.app.presentation.common.BaseActivity;
import com.baims.app.presentation.common.BaseFragment;
import com.baims.app.presentation.login.LoginPromptFragment;
import com.baims.app.presentation.profile.ProfileFragment;
import com.baims.app.presentation.search.SearchFramgment;
import com.baims.app.presentation.utils.ActivityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeView {

    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.iv_profile)
    ImageView mIvProfile;
    @BindView(R.id.iv_timeline)
    ImageView mIvTimeline;
    @BindView(R.id.iv_settings)
    ImageView mIvSettings;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    HomePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        presenter = new HomePresenterImpl(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(this)));

        ActivityUtil.addFirstFragmentToActivity(getSupportFragmentManager(),
                FragmentHome.newInstance(), R.id.content, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //closeButtonsExcept(R.id.iv_timeline);

    }

    @OnClick({R.id.iv_profile, R.id.iv_search, R.id.iv_timeline, R.id.iv_settings})
    public void onfooterClick(View view) {
        closeButtonsExcept(view.getId());
        switch (view.getId()) {
            case R.id.iv_profile:
                if (presenter.isLogin()) {
                    ActivityUtil.replaceFragmentToActivity(getSupportFragmentManager(),
                            new ProfileFragment(), R.id.content, this);
                } else {
                    //showDialog(this,"برجاء تسجيل الدخول");
                    ActivityUtil.replaceFragmentToActivity(getSupportFragmentManager(),
                            new LoginPromptFragment(), R.id.content, this);
//                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
//                    startActivity(intent);
                }
                break;
            case R.id.iv_search: {
                ActivityUtil.replaceFragmentToActivity(getSupportFragmentManager(),
                        new SearchFramgment(), R.id.content, this);
            }
            break;
            case R.id.iv_timeline: {
                ActivityUtil.replaceFragmentToActivity(getSupportFragmentManager(),
                        new FragmentHome(), R.id.content, this);
            }
            break;
            case R.id.iv_settings:
                ActivityUtil.replaceFragmentToActivity(getSupportFragmentManager(),
                        new CategoriesFragment(), R.id.content, this);
                break;
        }
    }

    private void closeButtonsExcept(int viewId) {

        mIvProfile.setImageDrawable(viewId != R.id.iv_profile ? getResources().getDrawable(R.drawable.ic_profile) : getResources().getDrawable(R.drawable.ic_profile_selected));
        mIvTimeline.setImageDrawable(viewId != R.id.iv_timeline ? getResources().getDrawable(R.drawable.ic_home) : getResources().getDrawable(R.drawable.ic_home_selected));
        mIvSettings.setImageDrawable(viewId != R.id.iv_settings ? getResources().getDrawable(R.drawable.ic_menu) : getResources().getDrawable(R.drawable.ic_menu_selected));
        mIvSearch.setImageDrawable(viewId != R.id.iv_search ? getResources().getDrawable(R.drawable.ic_search) : getResources().getDrawable(R.drawable.ic_search_selected));

    }

    @Override
    public void showData(List<Section> sections) {

    }

    @Override
    public void showProgress(boolean show) {
    }

    public void replaceFragment(BaseFragment fragment) {

        ActivityUtil.replaceFragmentToActivity(getSupportFragmentManager(),
                fragment, R.id.content, this);

//        ActivityUtil.addFirstFragmentToActivity(getSupportFragmentManager(),
//                FragmentHome.newInstance(), R.id.content, this);
    }

    @Override
    public void showError(String error) {
        showDialog(this, error);
    }

    public FragmentManager getSupportFM()
    {
        return getSupportFragmentManager();
    }
}
