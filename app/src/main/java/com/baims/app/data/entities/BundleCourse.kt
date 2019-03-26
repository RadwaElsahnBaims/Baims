package com.baims.app.data.entities

import com.google.gson.annotations.SerializedName

class BundleCourse {
    var sectionName: String = ""
    @SerializedName("time_videos")
    var timeVideos: String = ""
    @SerializedName("count_videos")
    var countVideos: Int = 0
    @SerializedName("chapter_tag")
    lateinit var chapterTag: List<ChapterTag>
    var buy: Int = 0
    var name: String = ""
    var link: String = ""
    var price: String = ""
    var discount: String = ""
    @SerializedName("total_price")
    var totalPrice: String = ""
    var instructor: String = ""
    var image: String = ""
    @SerializedName("count_course")
    var countCourse: Int = 0
    @SerializedName("type_feat")
    var typeFeat: String = ""
    var color: String = ""

    fun isBuy():Boolean
    {
        return buy == 1
    }


}
