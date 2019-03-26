//package com.baims.app.domain
//
//import com.baims.app.data.entities.Login
//import com.baims.app.data.entities.Register
//import com.baims.app.data.entities.User
//import com.baims.app.data.entities.response.*
//import rx.Observable
//
///**
// * Created by Radwa Elsahn on 10/15/2018.
// */
//interface BaimsRepository{
//    fun login(login: Login): Observable<LoginResponse>
//    fun register(register: Register): Observable<AccountResponse>
//    fun getProfile(access_token: String): Observable<ProfileResponse>
//    fun getCourses(access_token: String, page: String): Observable<CoursesResponse>
//    fun getCategories(access_token: String): Observable<CategoriesResponse>
//    fun getCategory(cat_link: String): Observable<CategoryResponse>
//    fun getFeatured(access_token: String, type: String): Observable<FeaturedResponse>
//    fun getBundleInfo(access_token: String, link: String): Observable<BundleInfoResponse>
//    fun getCourseDetails(access_token: String, link: String): Observable<CourseDetailsResponse>
//    fun getCourseComments(access_token: String, link: String, type: String, i: Int): Observable<CommentsResponse>
//    fun getCourseChapters(access_token: String, link: String): Observable<ChaptersResponse>
//    fun addWatch(accessToken: String, videoLink: String): Observable<BaseResponse>
//    fun logout(accessToken: String): Observable<BaseResponse>
//    fun validPromocode(accessToken: String, link: String, promocode: String): Observable<ValidPromocodeResponse>
//    fun payment(accessToken: String, link: String): Observable<BaseResponse>
//    fun paymentPromocode(accessToken: String, link: String, promocode: String): Observable<BaseResponse>
//    fun confirmPayment( access_token: String, link: String,  buy_link: String): Observable<BaseResponse>
//    fun confirmPromocode( access_token: String, link: String,  buy_link: String): Observable<BaseResponse>
//
//
//    /////internal
//    fun saveUserInfo(user: User)
//
//    fun getUserInfo(): User?
//    fun getAccessToken(): String
//    fun isLogin(): Boolean
//
//}
