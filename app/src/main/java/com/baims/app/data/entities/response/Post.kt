package com.baims.app.data.entities.response

import com.baims.app.data.entities.ChapterTag

data class Post(
        var buy: Int,
        var chapter_tag: ChapterTag,
        var count_videos: Int,
        var discount: Int,
        var link: String,
        var name: String,
        var price: Int,
        var time_videos: String,
        var total_price: Int
)