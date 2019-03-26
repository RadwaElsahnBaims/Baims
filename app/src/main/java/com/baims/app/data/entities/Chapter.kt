package com.baims.app.data.entities

data class Chapter(
        var content: String,
        var id: Int,
        var image: String,
        var lectures: List<Lecture>,
        var name: String
)