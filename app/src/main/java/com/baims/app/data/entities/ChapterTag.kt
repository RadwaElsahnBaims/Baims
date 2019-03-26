package com.baims.app.data.entities

import android.os.Parcel
import android.os.Parcelable

class ChapterTag() : Parcelable {
    var name: String? = ""

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChapterTag> {
        override fun createFromParcel(parcel: Parcel): ChapterTag {
            return ChapterTag(parcel)
        }

        override fun newArray(size: Int): Array<ChapterTag?> {
            return arrayOfNulls(size)
        }
    }


}