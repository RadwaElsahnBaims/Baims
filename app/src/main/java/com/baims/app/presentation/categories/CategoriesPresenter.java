package com.baims.app.presentation.categories;

import android.support.annotation.NonNull;
import android.util.Log;

import com.baims.app.data.entities.response.CategoriesResponse;
import com.baims.app.data.entities.response.ProfileResponse;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.profile.ProfileView;

import rx.Subscriber;

/**
 * Created by Radwa Elsahn on 12/4/2018.
 */
public class CategoriesPresenter implements ICategoriesPresenter {

    private CategoriesView mView;

    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;


    public CategoriesPresenter(CategoriesView view, BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository) {
        mView = view;
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;
    }


    @Override
    public void getCategories() {

        mView.showProgress(true);

        baimsRepository.getCategories()
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<CategoriesResponse>() {
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
                    public void onNext(CategoriesResponse categoriesResponse) {
                        mView.showProgress(false);
                        mView.showCategories(categoriesResponse.getData());
                    }
                });

    }


}
