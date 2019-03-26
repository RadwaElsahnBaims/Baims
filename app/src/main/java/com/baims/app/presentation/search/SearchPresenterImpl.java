package com.baims.app.presentation.search;

import android.support.annotation.NonNull;
import android.util.Log;

import com.baims.app.R;
import com.baims.app.data.entities.response.CoursesResponse;
import com.baims.app.data.entities.response.SearchResponse;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;
import com.baims.app.presentation.common.Constants;

import rx.Subscriber;

/**
 * Created by Radwa Elsahn on 10/16/2018.
 */
public class SearchPresenterImpl implements SearchPresenter {
    private SearchView mView;

    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;

    int pageNumber;

    public SearchPresenterImpl(SearchView view, BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository) {
        mView = view;
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;
        this.pageNumber = 0;
    }


    @Override
    public void getSearchResults(String searchText) {
        mView.showProgress(true);

        baimsRepository.search(baimsRepository.getAccessToken(), searchText)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<SearchResponse>() {
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
                    public void onNext(SearchResponse searchResponse) {
                        mView.showProgress(false);
                        pageNumber++;
                        Log.e("coursesResponse", searchResponse.toString());
                        if (searchResponse.getStatusCode() == 0) {
                            if(searchResponse.getData().getBundleCourse().size() == 0)
                                mView.showNoResult();
                            else
                            mView.showData(searchResponse.getData().getBundleCourse());
                        } else {
                            mView.showError(searchResponse.getMessage());
                        }
                    }
                });

    }
}
