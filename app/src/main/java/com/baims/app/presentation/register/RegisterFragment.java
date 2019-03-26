package com.baims.app.presentation.register;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baims.app.R;
import com.baims.app.presentation.common.BaseFragment;
import com.baims.app.presentation.common.ListItemListener;
import com.baims.app.presentation.home.FragmentHome;
import com.baims.app.presentation.home.HomeActivity;
import com.baims.app.presentation.register.confirmaccount.ConfirmAccountFragment;
import com.baims.app.presentation.register.email.EnterEmailFragment;
import com.baims.app.presentation.register.name.YourNameFragment;
import com.baims.app.presentation.register.number.EnterMobileFragment;
import com.baims.app.presentation.register.password.EnterPasswordFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterFragment extends BaseFragment implements ListItemListener<Bundle> {
    @BindView(R.id.viewpager_intro)
    ViewPager introViewPager;
    @BindView(R.id.tabs_intro)
    TabLayout introTabs;

    @BindView(R.id.tv_page)
    TextView tvPage;
    int setPos = 0;
    private boolean changePage = false;
    private RegisterTabsAdapter tabsAdapter;

    Bundle bundle = new Bundle();

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_register, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupView();
    }

    private void setupView() {

        tabsAdapter = new RegisterTabsAdapter(((HomeActivity) getActivity()).getSupportFM());

        YourNameFragment yourNameFragment = YourNameFragment.newInstance();
        yourNameFragment.setOnNextListener(this);

        EnterEmailFragment enterEmailFragment = EnterEmailFragment.newInstance();
        enterEmailFragment.setOnNextListener(this);

        EnterMobileFragment enterMobileFragment = EnterMobileFragment.newInstance();
        enterMobileFragment.setOnNextListener(this);

        EnterPasswordFragment enterPasswordFragment = EnterPasswordFragment.newInstance();
        enterPasswordFragment.setOnNextListener(this);


        tabsAdapter.addFragment(yourNameFragment);
        tabsAdapter.addFragment(enterEmailFragment);
        tabsAdapter.addFragment(enterMobileFragment);
        tabsAdapter.addFragment(enterPasswordFragment);

        introViewPager.setAdapter(tabsAdapter);
        introTabs.setupWithViewPager(introViewPager);
        introViewPager.setCurrentItem(0);

        introViewPager.beginFakeDrag();
//        setupCircularViewPager();
//        hideTabs();
    }

    // hide first and last tabs which was added in setupView method
    private void hideTabs() {
//        ((ViewGroup) introTabs.getChildAt(0)).getChildAt(0).setVisibility(View.GONE);
//        ((ViewGroup) introTabs.getChildAt(0)).getChildAt(4).setVisibility(View.GONE);

    }

    private void setupCircularViewPager() {
        introViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == tabsAdapter.getCount() - 1) {
                    changePage = true;
                    setPos = 0;
                } else if (position == 0) {
                    changePage = true;
                    setPos = tabsAdapter.getCount() - 1;
                } else {
                    changePage = false;
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE && changePage) {
                    introViewPager.setCurrentItem(setPos, false);

                }
            }
        });
    }

    //@OnClick(R.id.btn_next)
    public void nextClick() {
        if (setPos < tabsAdapter.getCount() - 1) {
            setPos++;
            introViewPager.setCurrentItem(setPos);
        } else if (setPos == tabsAdapter.getCount() - 1) // confirm
        {
            ConfirmAccountFragment confirmAccountFragment = ConfirmAccountFragment.newInstance(bundle);
            confirmAccountFragment.setOnNextListener(this);

            tabsAdapter.addFragment(confirmAccountFragment);
            setPos++;
            tabsAdapter.notifyDataSetChanged();
            introViewPager.setCurrentItem(setPos);

            // ((HomeActivity) getActivity()).replaceFragment(ConfirmAccountFragment.newInstance(bundle));
        } else {
            Toast.makeText(getContext(), "confirm", Toast.LENGTH_SHORT).show();
            ///openConfirm;
        }

        if (setPos + 1 <= 4)
            tvPage.setText((setPos + 1 + "/4"));
        else
        {
            tvPage.setVisibility(View.GONE);
        }
    }


    @Override
    public void itemListener(Bundle bundle1) {
        if (bundle1 == null) {
            // successfully added
            ((HomeActivity) getActivity()).replaceFragment(FragmentHome.newInstance());
        } else {
            bundle.putAll(bundle1);
            nextClick();
        }
    }
}
