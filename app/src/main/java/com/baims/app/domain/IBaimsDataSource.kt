package com.baims.app.domain

import com.baims.app.data.entities.Login
import com.baims.app.data.entities.Register
import com.baims.app.data.entities.response.*
import retrofit2.Response
import rx.Observable


/**
 * Created by Radwa Elsahn on 10/14/2018.
 */
interface IBaimsDataSource {
    fun login(login: Login): Observable<LoginResponse>
    fun register(register: Register): Observable<AccountResponse>
    fun getProfile(access_token: String): Observable<ProfileResponse>
    fun getCourses(access_token: String, page: String): Observable<CoursesResponse>
    fun getCategories(): Observable<CategoriesResponse>
    fun getCategory(cat_link: String): Observable<CategoryResponse>
    fun getFeatured(access_token: String, type: String): Observable<FeaturedResponse>
    fun getBundleInfo(access_token: String, link: String): Observable<BundleInfoResponse>
    fun getCourseDetails(access_token: String, link: String): Observable<CourseDetailsResponse>
    fun getCourseComments(access_token: String, link: String, type: String, limit: Int): Observable<CommentsResponse>
    fun getCourseChapters(access_token: String, link: String): Observable<ChaptersResponse>
    fun addWatch(accessToken: String, videoLink: String): Observable<BaseResponse>
    fun addDelLike(access_token: String, link: String, type: String): Observable<BaseResponse>
    fun logout(accessToken: String): Observable<BaseResponse>
    fun validPromocode(accessToken: String, link: String, promocode: String): Observable<ValidPromocodeResponse>
    fun payment(accessToken: String, link: String): Observable<PaymentResponse>
    fun paymentPromocode(accessToken: String, link: String, promocode: String): Observable<PaymentPromocodeResponse>
    fun confirmPayment(accessToken: String, link: String, orderLink: String, paymentId: String): Observable<ConfirmPaymentResponse>
    fun confirmPromocode(access_token: String, link: String, promocode: String, buy_link: String): Observable<ConfirmPromocodeResponse>
    fun addComment(access_token: String, link: String, content: String, linkParent: String, stateHidden: Double, displayName: String, emoji: String, image: String, video: String, audio: String, type: String, userEmail: String, userName: String,time:String): Observable<CommentResponse>
    fun search(accessToken: String, searchText: String): Observable<SearchResponse>
    fun uploadImage(access_token: String, image: String): Observable<UploadResponse>
    fun uploadAudio(accessToken: String, image: String): Observable<UploadResponse>
    fun uploadVideo(access_token: String, video: String): Observable<UploadResponse>


}
