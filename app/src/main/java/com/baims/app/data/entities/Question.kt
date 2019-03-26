package com.baims.app.data.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Question() : Parcelable {

    @SerializedName("parent_id")
    var parentId: Int = 0
    @SerializedName("parent_state_hiden")
    var parentStateHiden: Int = 0
    @SerializedName("parent_user_name")
    var parentUserName: String = ""
    @SerializedName("parent_user_image")
    var parentUserImage: String = ""
    @SerializedName("time_task")
    var timeTask: String = ""
    var content: String = ""
    var image: String? = null
    var video: String? = null
    var audio: String? = null
    @SerializedName("owner_question")
    var ownerQuestion: String = ""
    var like: Boolean = false
    @SerializedName("num_like")
    var numLike: String = ""
    var link: String = ""

    @SerializedName("replay_id")
    var replyId: String = ""
    @SerializedName("replay_user_name")
    var replyUserName: String = ""
    @SerializedName("replay_user_image")
    var replyUserImage: String = ""
    @SerializedName("child_comments")
    var childComments: List<Question>? = null


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(parentId)
        parcel.writeInt(parentStateHiden)
        parcel.writeString(parentUserName)
        parcel.writeString(parentUserImage)
        parcel.writeString(timeTask)
        parcel.writeString(content)
        parcel.writeString(image)
        parcel.writeString(video)
        parcel.writeString(audio)
        parcel.writeString(ownerQuestion)
        parcel.writeByte(if (like) 1 else 0)
        parcel.writeString(numLike)
        parcel.writeString(link)
        parcel.writeString(replyId)
        parcel.writeString(replyUserName)
        parcel.writeString(replyUserImage)
        parcel.writeList(childComments)
    }


    constructor(parcel: Parcel) : this() {
        parentId = parcel.readInt()
        parentStateHiden = parcel.readInt()
        parentUserName = parcel.readString()
        parentUserImage = parcel.readString()
        timeTask = parcel.readString()
        content = parcel.readString()
        image = parcel.readString()
        video = parcel.readString()
        audio = parcel.readString()
        ownerQuestion = parcel.readString()
        like = parcel.readByte() != 0.toByte()
        numLike = parcel.readString()
        link = parcel.readString()
        replyId = parcel.readString()
        replyUserName = parcel.readString()
        replyUserImage = parcel.readString()
        parcel.readList(childComments, Question::class.java!!.getClassLoader())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}