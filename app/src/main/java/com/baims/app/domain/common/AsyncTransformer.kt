//package com.baims.app.domain.common
//
//import io.reactivex.Observable
//import io.reactivex.ObservableSource
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.schedulers.Schedulers
//
///**
// * Created by Radwa Elsahn on 10/15/2018.
// */
//class ASyncTransformer<T> : Transformer<T>() {
//    override fun apply(upstream: Observable<T>): ObservableSource<T> {
//        return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//    }
//}