package com.baims.app.presentation.video;

import android.support.annotation.NonNull;
import android.util.Log;

import com.baims.app.data.entities.response.BaseResponse;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;
import com.baims.app.presentation.common.Constants;

import rx.Subscriber;

/**
 * Created by Radwa Elsahn on 11/11/2018.
 */
public class VideoPresenter implements IVideoPresenter {
    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;



    public VideoPresenter(BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository) {
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void addWatch(String link) {

        baimsRepository.addWatch(baimsRepository.getAccessToken(), link)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());

                    }

                    @Override
                    public void onNext(BaseResponse response) {


                    }
                });

    }
}
