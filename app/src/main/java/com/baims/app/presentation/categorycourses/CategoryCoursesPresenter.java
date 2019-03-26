package com.baims.app.presentation.categorycourses;

import android.support.annotation.NonNull;
import android.util.Log;

import com.baims.app.data.entities.response.CategoriesResponse;
import com.baims.app.data.entities.response.CategoryResponse;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;
import com.baims.app.presentation.common.Constants;

import rx.Subscriber;

/**
 * Created by Radwa Elsahn on 12/17/2018.
 */
public class CategoryCoursesPresenter implements ICategoryCoursesPresenter {
    private CategoryCoursesView mView;

    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;


    public CategoryCoursesPresenter(CategoryCoursesView view, BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository) {
        mView = view;
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;
    }


    @Override
    public void getCategory(String link) {

        mView.showProgress(true);

        baimsRepository.getCategory(link)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<CategoryResponse>() {
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
                    public void onNext(CategoryResponse categoryResponse) {

                        mView.showProgress(false);
                        if (categoryResponse.getData().getBundleCourses().size() > 0)
                            mView.showCategoryCourses(categoryResponse.getData());
                        else
                            mView.showNoResult();

                    }
                });
    }
}
