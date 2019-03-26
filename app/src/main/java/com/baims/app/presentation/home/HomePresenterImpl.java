package com.baims.app.presentation.home;

import android.support.annotation.NonNull;
import android.util.Log;

import com.baims.app.data.entities.response.FeaturedResponse;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;
import com.baims.app.presentation.common.Constants;
import com.google.gson.Gson;

import rx.Subscriber;

/**
 * Created by Radwa Elsahn on 10/18/2018.
 */
public class HomePresenterImpl implements HomePresenter {
    private HomeView mView;

    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;

    int pageNumber;

    public HomePresenterImpl(HomeView view, BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository) {
        mView = view;
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;
        this.pageNumber = 0;
    }

    @Override
    public void getFeatured(String type) {
        pageNumber =0;
        mView.showProgress(true);

        baimsRepository.getFeatured(baimsRepository.getAccessToken(), type)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<FeaturedResponse>() {
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
                    public void onNext(FeaturedResponse featuredResponse) {
                        mView.showProgress(false);
                        pageNumber++;
                        Log.e("featuredResponse", new Gson().toJson(featuredResponse));
                        if (featuredResponse.getStatusCode() == 0) {
                            mView.showData(featuredResponse.getData());
                        } else {
                            mView.showError(featuredResponse.getMessage());
                        }
                    }
                });
    }

    @Override
    public boolean isLogin() {

        return baimsRepository.isLogin();
    }
}
