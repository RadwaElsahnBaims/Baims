package com.baims.app.domain.usecases

import rx.Observable


/**
 * Created by Radwa Elsahn on 10/14/2018.
 */
abstract class UseCase<T>() {

    abstract fun createObservable(data: Map<String, Any>? = null): Observable<T>

    fun observable(withData: Map<String, Any>? = null): Observable<T> {
        return createObservable(withData)
    }

}