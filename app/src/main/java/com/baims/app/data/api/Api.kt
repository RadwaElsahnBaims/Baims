package com.baims.app.data.api


import com.baims.app.data.entities.response.*
import retrofit2.http.*
import rx.Observable
import java.util.*

/**
 * Created by Radwa Elsahn on 10/14/2018.
 */
interface Api {

    @FormUrlEncoded
    @POST("/api/v1/login/email")
    fun login(@Field("email_user_name") username: String,
              @Field("password") password: String, @Field("device_id") deviceId: String, @Field("reg_site") reg_site: String): Observable<LoginResponse>

    @FormUrlEncoded
    @POST("/api/v1/register")
    fun register(@Field("email") email: String,
                 @Field("password") password: String,
                 @Field("display_name") display_name: String,
                 @Field("phone") phone: String,
                 @Field("device_id") deviceId: String,
                 @Field("reg_site") regSite: String): Observable<AccountResponse>


    @GET("/api/v1/profile")
    fun getProfile(@Header("access-token") access_token: String): Observable<ProfileResponse>

    @POST("/api/v1/courses")
    fun getCourses(@Header("access-token") access_token: String, @Header("page") page: String): Observable<CoursesResponse>

    @GET("/api/v1/categories")
    fun getCategories(): Observable<CategoriesResponse>

    @FormUrlEncoded
    @POST("/api/v1/category")
    fun getCategory(@Field("cat_link") email: String): Observable<CategoryResponse>

    @FormUrlEncoded
    @POST("/api/v1/featured")
    fun getFeatured(@Header("access-token") access_token: String, @Field("type") type: String): Observable<FeaturedResponse>

    //@Headers("Content-Type: application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded
    @POST("/api/v1/bundle")
    fun getBundleInfo(@Header("access-token") access_token: String, @Field("link") link: String): Observable<BundleInfoResponse>


    @FormUrlEncoded
    @POST("/api/v1/course")
    fun getCourseDetails(@Header("access-token") access_token: String, @Field("link") link: String): Observable<CourseDetailsResponse>

    @FormUrlEncoded
    @POST("/api/v1/comments")
    fun getCourseComments(@Header("access-token") access_token: String, @Field("cou_link") link: String, @Field("type") type: String, @Field("limit") limit: Int): Observable<CommentsResponse>

    @FormUrlEncoded
    @POST("/api/v1/course/chapters")
    fun getCourseChapters(@Header("access-token") access_token: String, @Field("link") link: String): Observable<ChaptersResponse>

    @FormUrlEncoded
    @POST("/api/v1/add_comment")
    fun addComment(@Header("access-token") access_token: String, @Field("cou_link") link: String
                   , @Field("content") content: String
                   , @Field("link_parent") linkParent: String
                   , @Field("state_hiden") stateHiden: Double
                   , @Field("display_name") displayName: String //You must be present when state_hiden = 1
                   , @Field("emoji") emoji: String
                   , @Field("image") image: String
                   , @Field("video") video: String
                   , @Field("audio") audio: String
                   , @Field("type") type: String
                   , @Field("user_email") userEmail: String //should find with type =comment
                   , @Field("user_name") userName: String
                   , @Field("time_task") time: String)//should find with type =comment
            : Observable<CommentResponse>


    @FormUrlEncoded
    @POST("/api/v1/buyPromocode")
    fun buyPromocode(@Header("access-token") access_token: String, @Field("cou_link") link: String, @Field("promocode") promocode: String): Observable<Objects>

    @FormUrlEncoded
    @POST("/api/v1/add_watch")
    fun addWatch(@Header("access-token") access_token: String, @Field("video_link") link: String): Observable<BaseResponse>

    @FormUrlEncoded
    @POST("/api/v1/add_del_like")
    fun addDelLike(@Header("access-token") access_token: String, @Field("link") link: String, @Field("type") type: String): Observable<BaseResponse>

    @FormUrlEncoded
    @POST("/api/v1/logout")
    fun logout(@Header("access-token") access_token: String): Observable<BaseResponse>

    @FormUrlEncoded
    @POST("/api/v1/validPromocode")
    fun validPromocode(@Header("access-token") access_token: String, @Field("cou_link") link: String, @Field("promocode") promocode: String): Observable<ValidPromocodeResponse>

    @FormUrlEncoded
    @POST("/api/v1/payment")
    fun payment(@Header("access-token") access_token: String, @Field("cou_link") link: String): Observable<PaymentResponse>

    @FormUrlEncoded
    @POST("/api/v1/paymentPromocode")
    fun paymentPromocode(@Header("access-token") access_token: String, @Field("cou_link") link: String, @Field("promocode") promocode: String): Observable<PaymentPromocodeResponse>

    @FormUrlEncoded
    @POST("/api/v1/confirmPayment")
    fun confirmPayment(@Header("access-token") access_token: String, @Field("cou_link") link: String, @Field("order_link") order_link: String, @Field("paymentId") paymentId: String): Observable<ConfirmPaymentResponse>

    @FormUrlEncoded
    @POST("/api/v1/confirmPromocode")
    fun confirmPromocode(@Header("access-token") access_token: String, @Field("cou_link") link: String, @Field("promocode") promocode: String, @Field("buy_link") buy_link: String): Observable<ConfirmPromocodeResponse>

    @FormUrlEncoded
    @POST("/api/v1/search")
    fun search(@Header("access-token") access_token: String, @Field("search") search: String): Observable<SearchResponse>

    @FormUrlEncoded
    @POST("/api/v1/uploadImage")
    fun uploadImage(@Header("access-token") access_token: String, @Field("image") image: String): Observable<UploadResponse>

    @FormUrlEncoded
    @POST("/api/v1/uploadAudio")
    fun uploadAudio(@Header("access-token") access_token: String, @Field("audio") file: String): Observable<UploadResponse>


    @FormUrlEncoded
    @POST("/api/v1/uploadVideo")
    fun uploadVideo(@Header("access-token") access_token: String, @Field("video") file: String): Observable<UploadResponse>

}
