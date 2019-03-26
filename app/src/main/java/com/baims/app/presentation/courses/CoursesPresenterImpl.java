package com.baims.app.presentation.courses;

import android.support.annotation.NonNull;
import android.util.Log;

import com.baims.app.data.entities.response.CoursesResponse;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;
import com.baims.app.presentation.common.Constants;

import rx.Subscriber;

/**
 * Created by Radwa Elsahn on 10/16/2018.
 */
public class CoursesPresenterImpl implements CoursesPresenter {
    private CoursesView mView;

    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;

    int pageNumber;

    public CoursesPresenterImpl(CoursesView view, BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository) {
        mView = view;
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;
        this.pageNumber = 0;
    }

    @Override
    public void getCourses() {
        mView.showProgress(true);

        baimsRepository.getCourses(baimsRepository.getAccessToken(), String.valueOf(pageNumber))
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<CoursesResponse>() {
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
                    public void onNext(CoursesResponse coursesResponse) {
                        mView.showProgress(false);
                        pageNumber++;
                        Log.e("coursesResponse", coursesResponse.toString());
                        if (coursesResponse.getStatusCode() == 0) {
                            mView.showData(coursesResponse.getData());
                        } else {
                            mView.showError(coursesResponse.getMessage());
                        }
                    }
                });

    }
}
