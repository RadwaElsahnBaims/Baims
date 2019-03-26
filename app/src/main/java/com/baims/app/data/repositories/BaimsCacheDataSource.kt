package com.baims.app.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.baims.app.data.entities.Login
import com.baims.app.data.entities.Register
import com.baims.app.data.entities.RegisterInfo
import com.baims.app.data.entities.User
import com.baims.app.data.entities.response.*
import com.baims.app.domain.IBaimsDataSource
import com.google.gson.Gson
import retrofit2.Response
import rx.Observable

/**
 * Created by Radwa Elsahn on 10/14/2018.
 */


class BaimsCacheDataSource private constructor(private val context: Context) : IBaimsDataSource {
    override fun uploadVideo(access_token: String, video: String): Observable<UploadResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun uploadAudio(accessToken: String, image: String): Observable<UploadResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun uploadImage(access_token: String, image: String): Observable<UploadResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun search(accessToken: String, searchText: String): Observable<SearchResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addComment(access_token: String, link: String, content: String, linkParent: String, stateHidden: Double, displayName: String, emoji: String, image: String, video: String, audio: String, type: String, userEmail: String, userName: String, time: String): Observable<CommentResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun confirmPromocode(access_token: String, link: String, promocode: String, buy_link: String): Observable<ConfirmPromocodeResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun confirmPayment(accessToken: String, link: String, orderLink: String, paymentId: String): Observable<ConfirmPaymentResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun paymentPromocode(accessToken: String, link: String, promocode: String): Observable<PaymentPromocodeResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun payment(accessToken: String, link: String): Observable<PaymentResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun logout(accessToken: String): Observable<BaseResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun validPromocode(accessToken: String, link: String, promocode: String): Observable<ValidPromocodeResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addWatch(accessToken: String, videoLink: String): Observable<BaseResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addDelLike(access_token: String, link: String, type: String): Observable<BaseResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCourseChapters(access_token: String, link: String): Observable<ChaptersResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCourseComments(access_token: String, link: String, type: String, limit: Int): Observable<CommentsResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCourseDetails(access_token: String, link: String): Observable<CourseDetailsResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBundleInfo(access_token: String, link: String): Observable<BundleInfoResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun register(register: Register): Observable<AccountResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProfile(access_token: String): Observable<ProfileResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun login(login: Login): Observable<LoginResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCourses(access_token: String, page: String): Observable<CoursesResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCategories(): Observable<CategoriesResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCategory(cat_link: String): Observable<CategoryResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getFeatured(access_token: String, type: String): Observable<FeaturedResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val sharedPreferences: SharedPreferences

    private val registerInfo: RegisterInfo? = null

    val userInfo: User?
        get() {
            val userItemJson = sharedPreferences.getString(KEY_USER, "")

            if (!userItemJson!!.isEmpty()) {
                val gson = Gson()
                val user = gson.fromJson(userItemJson, User::class.java)
                return user
            }

            return User()
        }

    val deviceId: String?
    get(){
        return sharedPreferences.getString(KEY_DEVICEID, "")

    }

    val profileResponse: ProfileResponse?
        get() {
            val profileItemGson = sharedPreferences.getString(KEY_PROFILE, "")

            if (!profileItemGson!!.isEmpty()) {
                val gson = Gson()
                val user = gson.fromJson(profileItemGson, ProfileResponse::class.java)
                return user
            }

            return null
        }

//    var language: String?
//        get() = sharedPreferences.getString(KEY_SELECTED_LANGUAGE, "")
//        set(language) {
//            val editor = sharedPreferences.edit()
//            editor.putString(KEY_SELECTED_LANGUAGE, language).apply()
//        }

    init {

        sharedPreferences = this.context.getSharedPreferences(KEY_PREFS, Context.MODE_PRIVATE)

    }

    fun saveUserInfo(user: User) {
        val gson = Gson()

        val editor = sharedPreferences.edit()
        editor.putString(KEY_USER, gson.toJson(user)).apply()
        Log.i("user", gson.toJson(user))
    }

    fun saveDeviceId(id: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_DEVICEID, id).apply()

    }

    fun clearCache() {
        val editor = sharedPreferences.edit().clear().apply()

    }

    fun saveProfileInfo(profileResponse: ProfileResponse) {
        val gson = Gson()

        val editor = sharedPreferences.edit()
        editor.putString(KEY_PROFILE, gson.toJson(profileResponse)).apply()
        Log.i("user", gson.toJson(profileResponse))
    }


    companion object {

        private var INSTANCE: BaimsCacheDataSource? = null

        private val KEY_USER = "user"
        private val KEY_DEVICEID = "deviceId"
        private val KEY_PROFILE = "profile"
        private val KEY_PREFS = "baims.prefs"
        private val KEY_SELECTED_LANGUAGE = "language"

        fun getInstance(context: Context): BaimsCacheDataSource {
            if (INSTANCE == null)
                INSTANCE = BaimsCacheDataSource(context)

            return INSTANCE!!
        }
    }

}
