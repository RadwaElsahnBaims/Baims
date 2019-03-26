package com.baims.app.data.repositories

import android.util.Log

import com.baims.app.data.entities.Login
import com.baims.app.data.entities.Register
import com.baims.app.data.entities.User
import com.baims.app.data.entities.response.*
import com.baims.app.data.repository.BaimsCacheDataSource
import com.baims.app.domain.IBaimsDataSource

import rx.Observable

/**
 * Created by Radwa Elsahn on 10/14/2018.
 */
class BaimsRepository(private val remoteDataSource: BaimsRemoteDataSource, private val localDataSource: BaimsCacheDataSource) : IBaimsDataSource {
    override fun uploadVideo(access_token: String, video: String): Observable<UploadResponse> {
        return remoteDataSource.uploadVideo(access_token, video)
    }

    override fun uploadAudio(accessToken: String, file: String): Observable<UploadResponse> {
        return remoteDataSource.uploadAudio(accessToken, file)
    }

    override fun uploadImage(access_token: String, image: String): Observable<UploadResponse> {
        return remoteDataSource.uploadImage(access_token, image)
    }

    override fun search(accessToken: String, searchText: String): Observable<SearchResponse> {
        return remoteDataSource.search(accessToken, searchText)
    }

    override fun addComment(access_token: String, link: String, content: String, linkParent: String, stateHidden: Double, displayName: String, emoji: String, image: String, video: String, audio: String, type: String, userEmail: String, userName: String, time: String): Observable<CommentResponse> {
        return remoteDataSource.addComment(access_token, link, content, linkParent, stateHidden, displayName, emoji, image, video, audio, type, userEmail, userName, time)
    }

    override fun confirmPromocode(access_token: String, link: String, promocode: String, buy_link: String): Observable<ConfirmPromocodeResponse> {
        return remoteDataSource.confirmPromocode(access_token, link, promocode, buy_link)
    }

    override fun confirmPayment(access_token: String, link: String, orderLink: String, paymentId: String): Observable<ConfirmPaymentResponse> {
        return remoteDataSource.confirmPayment(access_token, link, orderLink, paymentId)
    }

    override fun paymentPromocode(accessToken: String, link: String, promocode: String): Observable<PaymentPromocodeResponse> {
        return remoteDataSource.paymentPromocode(accessToken, link, promocode)
    }

    override fun payment(accessToken: String, link: String): Observable<PaymentResponse> {
        return remoteDataSource.payment(accessToken, link)
    }

    override fun validPromocode(accessToken: String, link: String, promocode: String): Observable<ValidPromocodeResponse> {
        return remoteDataSource.validPromocode(accessToken, link, promocode)
    }

    override fun logout(accessToken: String): Observable<BaseResponse> {
        return remoteDataSource.logout(accessToken)
    }

    override fun addWatch(accessToken: String, videoLink: String): Observable<BaseResponse> {
        return remoteDataSource.addWatch(accessToken, videoLink)
    }

    override fun addDelLike(access_token: String, link: String, type: String): Observable<BaseResponse> {
        return remoteDataSource.addDelLike(access_token, link, type)
    }

    override fun getCourseChapters(access_token: String, link: String): Observable<ChaptersResponse> {
        return remoteDataSource.getCourseChapters(access_token, link)
    }

    override fun getCourseComments(access_token: String, link: String, type: String, limit: Int): Observable<CommentsResponse> {
        return remoteDataSource.getCourseComments(access_token, link, type, limit)
    }

    override fun getCourseDetails(access_token: String, link: String): Observable<CourseDetailsResponse> {
        return remoteDataSource.getCourseDetails(access_token, link)
    }

    override fun getBundleInfo(access_token: String, link: String): Observable<BundleInfoResponse> {
        return remoteDataSource.getBundleInfo(access_token, link)
    }


    fun getUserInfo(): User? {
        if (localDataSource != null)
            return localDataSource.userInfo
        else
            return User()
    }

    fun getDeviceId(): String? {
        if (localDataSource != null)
            return localDataSource.deviceId
        else
            return null
    }

    fun getAccessToken(): String {
        if (localDataSource.userInfo != null)
            return localDataSource.userInfo!!.access_token
        else
            return ""
    }

    fun isLogin(): Boolean {
        //return localDataSource.userInfo != null && localDataSource.userInfo!!.access_token!= ""
        return !getAccessToken().isEmpty()
    }

    fun saveUserInfo(user: User) {
        localDataSource.saveUserInfo(user)

    }

    fun saveDeviceId(id: String) {
        localDataSource.saveDeviceId(id)

    }

    fun clearCache() {
        localDataSource.clearCache()
    }


    override fun login(login: Login): Observable<LoginResponse> {
        return remoteDataSource.login(login)
                .doOnNext { accountResponse ->

                }
                .doOnError { throwable ->
                    reportNetworkError("login", throwable)
                }
    }

    override fun register(register: Register): Observable<AccountResponse> {
        return remoteDataSource.register(register)
                .doOnNext { accountResponse -> Log.i("register", "onSuccess") }
                .doOnError { throwable -> reportNetworkError("register", throwable) }
    }

    override fun getProfile(access_token: String): Observable<ProfileResponse> {
        // check if in local cache
        //else
        return remoteDataSource.getProfile(access_token)
                .doOnNext { profileResponse -> Log.i("getProfile", "onSuccess") }
                .doOnError { throwable -> reportNetworkError("getProfile", throwable) }

    }

    override fun getCourses(access_token: String, page: String): Observable<CoursesResponse> {
        // check if in local cache
        //else
        return remoteDataSource.getCourses(access_token, page)
                .doOnNext { profileResponse -> Log.i("getCourses", "onSuccess") }
                .doOnError { throwable -> reportNetworkError("getCourses", throwable) }

    }

    override fun getCategories(): Observable<CategoriesResponse> {
        // check if in local cache
        //else
        return remoteDataSource.getCategories()
                .doOnNext { profileResponse -> Log.i("getCategories", "onSuccess") }
                .doOnError { throwable -> reportNetworkError("getCategories", throwable) }

    }


    override fun getCategory(cat_link: String): Observable<CategoryResponse> {
        // check if in local cache
        //else
        return remoteDataSource.getCategory(cat_link)
                .doOnNext { CategoryResponse -> Log.i("getCategory", "onSuccess") }
                .doOnError { throwable -> reportNetworkError("getCategory", throwable) }

    }

    private fun reportNetworkError(tag: String, throwable: Throwable) {
        Log.e("Network Error - ", throwable.toString())
    }


    override fun getFeatured(access_token: String, type: String): Observable<FeaturedResponse> {
        return remoteDataSource.getFeatured(access_token, type).doOnNext { featuredResponse ->
            Log.i("getFeatured", "onSuccess")

        }.doOnError { throwable -> reportNetworkError("getFeatured", throwable) }
    }


    companion object {

        private val TAG = "BaimsRepository"
        var INSTANCE: BaimsRepository? = null

        fun getInstance(
                remoteDataSource: BaimsRemoteDataSource, localDataSource: BaimsCacheDataSource): BaimsRepository {
            if (INSTANCE == null)
                INSTANCE = BaimsRepository(remoteDataSource, localDataSource)

            return INSTANCE as BaimsRepository
        }
    }
}