package com.baims.app.domain.usecases

import com.baims.app.data.entities.response.ProfileResponse
import com.baims.app.domain.IBaimsDataSource

import rx.Observable

/**
 * Created by Radwa Elsahn on 10/14/2018.
 */

class GetProfileInfo(val baimsRepository: IBaimsDataSource) : UseCase<ProfileResponse>() {

    override fun createObservable(data: Map<String, Any>?): Observable<ProfileResponse> {
        val accessToken = data?.get(PARAM_ACCESS_TOKEN)
        return baimsRepository.getProfile(accessToken as String)
    }

    companion object {
        private const val PARAM_ACCESS_TOKEN = "param:access-token"
    }

    fun getById(accessToken: String): Observable<ProfileResponse> {
        val data = HashMap<String, String>()
        data[PARAM_ACCESS_TOKEN] = accessToken
        return observable(data)
    }


}