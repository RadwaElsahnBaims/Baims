package com.baims.app.data.repositories

import com.baims.app.data.api.RestClient
import com.baims.app.data.entities.Login
import com.baims.app.data.entities.Register
import com.baims.app.data.entities.response.*
import com.baims.app.domain.IBaimsDataSource
import rx.Observable

/**
 * Created by Radwa Elsahn on 10/14/2018.
 */
class BaimsRemoteDataSource private constructor() : IBaimsDataSource {
    override fun uploadAudio(accessToken: String, audio: String): Observable<UploadResponse> {
        return RestClient.get().uploadAudio(accessToken, audio)
    }

    override fun uploadVideo(access_token: String, video: String): Observable<UploadResponse> {
        return RestClient.get().uploadVideo(access_token, video)
    }

    override fun uploadImage(access_token: String, image: String): Observable<UploadResponse> {
        return RestClient.get().uploadImage(access_token, image)
    }

    override fun search(accessToken: String, searchText: String): Observable<SearchResponse> {
        return RestClient.get().search(accessToken, searchText)
    }

    override fun addComment(access_token: String, link: String, content: String, linkParent: String, stateHidden: Double, displayName: String, emoji: String, image: String, video: String, audio: String, type: String, userEmail: String, userName: String, time: String): Observable<CommentResponse> {
        return RestClient.get().addComment(access_token, link, content, linkParent, stateHidden, displayName, emoji, image, video, audio, type, userEmail, userName, time)
    }

    override fun confirmPromocode(accessToken: String, link: String, promocode: String, buyLink: String): Observable<ConfirmPromocodeResponse> {
        return RestClient.get().confirmPromocode(accessToken, link, promocode, buyLink)
    }

    override fun confirmPayment(accessToken: String, link: String, orderLink: String, paymentId: String): Observable<ConfirmPaymentResponse> {
        return RestClient.get().confirmPayment(accessToken, link, orderLink, paymentId)
    }

    override fun paymentPromocode(accessToken: String, link: String, promocode: String): Observable<PaymentPromocodeResponse> {
        return RestClient.get().paymentPromocode(accessToken, link, promocode)
    }

    override fun payment(accessToken: String, link: String): Observable<PaymentResponse> {
        return RestClient.get().payment(accessToken, link)
    }

    override fun logout(accessToken: String): Observable<BaseResponse> {
        return RestClient.get().logout(accessToken)
    }

    override fun validPromocode(accessToken: String, link: String, promocode: String): Observable<ValidPromocodeResponse> {
        return RestClient.get().validPromocode(accessToken, link, promocode)
    }

    override fun addWatch(accessToken: String, videoLink: String): Observable<BaseResponse> {
        return RestClient.get().addWatch(accessToken, videoLink)
    }

    override fun addDelLike(access_token: String, link: String, type: String): Observable<BaseResponse> {
        return RestClient.get().addDelLike(access_token, link, type)
    }

    override fun getCourseChapters(access_token: String, link: String): Observable<ChaptersResponse> {
        return RestClient.get().getCourseChapters(access_token, link)
    }

    override fun getCourseDetails(access_token: String, link: String): Observable<CourseDetailsResponse> {
        return RestClient.get().getCourseDetails(access_token, link)
    }

    override fun getBundleInfo(access_token: String, link: String): Observable<BundleInfoResponse> {
        return RestClient.get().getBundleInfo(access_token, link)
    }


    override fun login(login: Login): Observable<LoginResponse> {
        return RestClient.get().login(login.email_user_name, login.password, login.device_id, login.reg_site)
    }

    override fun register(register: Register): Observable<AccountResponse> {
        return RestClient.get().register(
                register.email,
                register.password,
                register.display_name,
                register.phone,
                register.device_id,
                register.reg_site)
    }

    override fun getProfile(access_token: String): Observable<ProfileResponse> {
        return RestClient.get().getProfile(access_token)
    }

    override fun getCourses(access_token: String, page: String): Observable<CoursesResponse> {
        return RestClient.get().getCourses(access_token, page)
    }

    override fun getCategories(): Observable<CategoriesResponse> {
        return RestClient.get().getCategories()
    }

    override fun getCategory(cat_link: String): Observable<CategoryResponse> {
        return RestClient.get().getCategory(cat_link)
    }

    override fun getFeatured(access_token: String, type: String): Observable<FeaturedResponse> {
        return RestClient.get().getFeatured(access_token, type)
    }

    override fun getCourseComments(access_token: String, link: String, type: String, limit: Int): Observable<CommentsResponse> {
        return RestClient.get().getCourseComments(access_token, link, type, limit)
    }

    companion object {

        private var INSTANCE: BaimsRemoteDataSource? = null

        val instance: BaimsRemoteDataSource
            get() {
                if (INSTANCE == null)
                    INSTANCE = BaimsRemoteDataSource()

                return INSTANCE!!
            }
    }


}
