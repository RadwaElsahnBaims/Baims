package com.baims.app.presentation.profile;

import android.support.annotation.NonNull;
import android.util.Log;

import com.baims.app.data.entities.User;
import com.baims.app.data.entities.response.ProfileResponse;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;
import com.baims.app.presentation.common.Constants;

import rx.Subscriber;

public class ProfilePresenterImpl implements ProfilePresenter {

    private ProfileView mView;

    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;


    public ProfilePresenterImpl(ProfileView view, BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository) {
        mView = view;
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;
    }


    @Override
    public void getUserProfile() {

        mView.showProgress(true);

        baimsRepository.getProfile(baimsRepository.getAccessToken())
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<ProfileResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                        mView.showProgress(false);
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ProfileResponse profileResponse) {
                        mView.showProgress(false);

                        if (profileResponse.getStatusCode() == 0) {
                            User user = profileResponse.getUser();
                            mView.showProfileInfo(user);

                            if (profileResponse.getMyCourses() == null || profileResponse.getMyCourses().size() == 0
                                    && profileResponse.getRegisteredCourses() == null || profileResponse.getRegisteredCourses().size() == 0) {
                                mView.showNoCourses();
                            } else
                                mView.showCourses(profileResponse.getMyCourses(), profileResponse.getRegisteredCourses(), "");

                        } else {
                            mView.showError(profileResponse.getMessage());
                        }
                    }
                });

    }

    @Override
    public void logout() {
        baimsRepository.clearCache();
    }


}
