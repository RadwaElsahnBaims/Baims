package com.baims.app.data.entities

import android.os.Parcel
import android.os.Parcelable
import com.baims.app.presentation.common.Constants
import com.google.gson.annotations.SerializedName

class Lecture() : Parcelable {
    override fun describeContents(): Int {
        return 0
    }


    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(chapterId)
        dest.writeString(content)
        dest.writeString(file)
        dest.writeString(fileLink)
        dest.writeString(fileName)
        dest.writeInt(id)
        dest.writeString(image)
        dest.writeString(name)
        dest.writeString(videoTime)
        dest.writeString(typeCost)
        dest.writeString(video)
        dest.writeString(videoLink)
        dest.writeString(videoMob)
        dest.writeString(videoName)
        dest.writeString(videoSd)
        dest.writeByte(if (videoWatch) 1 else 0)
        dest.writeString(lec_link)
    }

    @SerializedName("chapter_id")
    var chapterId: Int = 0
    var content: String = ""
    var file: String = ""
    @SerializedName("file_link")
    var fileLink: String = ""
    @SerializedName("file_name")
    var fileName: String = ""
    var id: Int = 0
    var image: String = ""
    var name: String = ""
    @SerializedName("video_time")
    var videoTime: String = ""
    @SerializedName("type_cost")
    var typeCost: String = ""
    var video: String = ""
    @SerializedName("video_link")
    var videoLink: String = ""
    @SerializedName("video_mob")
    var videoMob: String = ""
    @SerializedName("video_name")
    var videoName: String = ""
    @SerializedName("video_sd")
    var videoSd: String = ""
    @SerializedName("video_watch")
    var videoWatch: Boolean = false

    var playing: Boolean = false
    var lec_link:String=""

    constructor(parcel: Parcel) : this() {
        chapterId = parcel.readInt()
        content = parcel.readString()
        file = parcel.readString()
        fileLink = parcel.readString()
        fileName = parcel.readString()
        id = parcel.readInt()
        image = parcel.readString()
        name = parcel.readString()
        videoTime = parcel.readString()
        typeCost = parcel.readString()
        video = parcel.readString()
        videoLink = parcel.readString()
        videoMob = parcel.readString()
        videoName = parcel.readString()
        videoSd = parcel.readString()
        videoWatch = parcel.readByte() != 0.toByte()
        lec_link = parcel.readString()
    }

    fun hasCost(): Boolean {
        try {

            return this.typeCost!!.toDouble() != 0.0
        } catch (e:Exception) {
            return false
        }
    }

    companion object CREATOR : Parcelable.Creator<Lecture> {
        override fun createFromParcel(parcel: Parcel): Lecture {
            return Lecture(parcel)
        }

        override fun newArray(size: Int): Array<Lecture?> {
            return arrayOfNulls(size)
        }
    }

    fun isFree(): Boolean {
        return typeCost.equals(Constants.FREE)//this.price!!.toDouble() == 0.0
    }

}