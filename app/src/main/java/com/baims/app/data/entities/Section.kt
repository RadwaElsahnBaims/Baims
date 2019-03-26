package com.baims.app.data.entities


data class Section(
        var id: Int,
        var name: String,
        var link: String,
        var bundleCourse: List<BundleCourse>
)