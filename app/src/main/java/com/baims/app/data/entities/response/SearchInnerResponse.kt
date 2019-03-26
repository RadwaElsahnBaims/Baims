package com.baims.app.data.entities.response

import com.baims.app.data.entities.BundleCourse
import com.google.gson.annotations.SerializedName

data class SearchInnerResponse(
        var bundleCourse: List<BundleCourse>,
        @SerializedName("count_search")
        var countSearch: String,
        var search: String
)