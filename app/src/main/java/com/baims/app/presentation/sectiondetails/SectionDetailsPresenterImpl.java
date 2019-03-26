package com.baims.app.presentation.sectiondetails;

import android.support.annotation.NonNull;
import android.util.Log;

import com.baims.app.data.entities.response.BundleInfoResponse;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;
import com.baims.app.presentation.common.Constants;

import rx.Subscriber;

/**
 * Created by Radwa Elsahn on 10/22/2018.
 */
public class SectionDetailsPresenterImpl implements SectionDetailsPresenter {



    private SectionDetailsView mView;

    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;

    public SectionDetailsPresenterImpl(SectionDetailsView view, BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository) {
        mView = view;
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;
    }


    @Override
    public void getSectionDetails(String link) {
        mView.showProgress(true);

        baimsRepository.getBundleInfo(baimsRepository.getAccessToken(),link)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<BundleInfoResponse>() {
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
                    public void onNext(BundleInfoResponse bundleInfoResponse) {
                        mView.showProgress(false);

                        Log.e("bundleInfoResponse", bundleInfoResponse.toString());
                        if (bundleInfoResponse.getStatusCode() == 0) {
                            mView.showData(bundleInfoResponse.getData(),bundleInfoResponse.getColor());
                        } else {
                            mView.showError(bundleInfoResponse.getMessage());
                        }
                    }
                });

    }
}
