package com.baims.app.data.entities.response

import com.baims.app.data.entities.Question
import com.google.gson.annotations.SerializedName

class CommentsResponse : BaseResponse() {
    @SerializedName("count_data")
    var countQuestions: String = ""
    @SerializedName("data")
    var questions: List<Question>? = null
}


