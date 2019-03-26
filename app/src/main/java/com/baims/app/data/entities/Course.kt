package com.baims.app.data.entities

import android.os.Parcel
import android.os.Parcelable
import com.baims.app.presentation.common.Constants
import com.google.gson.annotations.SerializedName

/**
 * Created by Radwa Elsahn on 10/17/2018.
 */
class Course() : Parcelable {
    @SerializedName("section_name")
    var sectionName: String? = null
        private set
    var id: Int = 0
    var image: String? = null
    var name: String? = null
    @SerializedName("type_feat")
    var typeFeat: String? = null
    var link: String? = null
    @SerializedName("is_active")
    var isActive: Int = 0
    var buy: Int = 0
    var instructor: String? = null
    @SerializedName("instructor_image")
    var instructorImage: String? = null
    @SerializedName("type_cost")
    private var typeCost: String? = null
    var level: String? = null
    @SerializedName("view_count")
    var viewCount: Int = 0
    var content: String? = null
    var description: String? = null
    @SerializedName("count_chapter")
    var countChapter: Int = 0
    @SerializedName("count_lecture")
    var countLecture: Int = 0
    @SerializedName("total_file")
    var totalFile: Int = 0
    @SerializedName("total_video")
    var totalVideo: Int = 0
        private set
    @SerializedName("total_quiz")
    var totalQuiz: Int = 0
    var like: Int = 0
    @SerializedName("subscribed")
    var isSubscribed: Boolean = false
    var promocode: Int = 0
    var video: String? = null
    @SerializedName("video_sd")
    var videoSd: String? = null
    @SerializedName("video_mob")
    var videoMob: String? = null
    var price: String? = null
    var discount: String? = null
    @SerializedName("total_price")
    var totalPrice: String? = null
    @SerializedName("count_course")
    var countCourse: Int? = null
    @SerializedName("time_videos")
    var timeVideos: String? = null
    @SerializedName("video_link")
    var videoLink: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        image = parcel.readString()
        name = parcel.readString()
        typeFeat = parcel.readString()
        link = parcel.readString()
        isActive = parcel.readInt()
        buy = parcel.readInt()
        instructor = parcel.readString()
        instructorImage = parcel.readString()
        typeCost = parcel.readString()
        level = parcel.readString()
        viewCount = parcel.readInt()
        content = parcel.readString()
        description = parcel.readString()
        countChapter = parcel.readInt()
        countLecture = parcel.readInt()
        totalFile = parcel.readInt()
        totalQuiz = parcel.readInt()
        like = parcel.readInt()
        isSubscribed = parcel.readByte() != 0.toByte()
        promocode = parcel.readInt()
        video = parcel.readString()
        videoSd = parcel.readString()
        videoMob = parcel.readString()
        price = parcel.readString()
        discount = parcel.readString()
        totalPrice = parcel.readString()
        countCourse = parcel.readValue(Int::class.java.classLoader) as? Int
        timeVideos = parcel.readString()
        videoLink = parcel.readString()
    }

    fun isBuy(): Boolean {

        return (buy == 1)
    }

    fun isFree(): Boolean {
        return typeCost.equals(Constants.FREE)//this.price!!.toDouble() == 0.0
    }

    fun hasPromoCode(): Boolean {
        return this.promocode > 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeString(typeFeat)
        parcel.writeString(link)
        parcel.writeInt(isActive)
        parcel.writeInt(buy)
        parcel.writeString(instructor)
        parcel.writeString(instructorImage)
        parcel.writeString(typeCost)
        parcel.writeString(level)
        parcel.writeInt(viewCount)
        parcel.writeString(content)
        parcel.writeString(description)
        parcel.writeInt(countChapter)
        parcel.writeInt(countLecture)
        parcel.writeInt(totalFile)
        parcel.writeInt(totalQuiz)
        parcel.writeInt(like)
        parcel.writeByte(if (isSubscribed) 1 else 0)
        parcel.writeInt(promocode)
        parcel.writeString(video)
        parcel.writeString(videoSd)
        parcel.writeString(videoMob)
        parcel.writeString(price)
        parcel.writeString(discount)
        parcel.writeString(totalPrice)
        parcel.writeValue(countCourse)
        parcel.writeString(timeVideos)
        parcel.writeString(videoLink)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Course> {
        override fun createFromParcel(parcel: Parcel): Course {
            return Course(parcel)
        }

        override fun newArray(size: Int): Array<Course?> {
            return arrayOfNulls(size)
        }
    }
}
