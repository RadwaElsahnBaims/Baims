package com.baims.app.data.entities

data class Post(
        var timeVideos: String,
        var countVideos: Int,
        var chapterTag: ChapterTag,
        var buy: Int,
        var name: String,
        var link: String,
        var price: String,
        var discount: String,
        var totalPrice: String
)