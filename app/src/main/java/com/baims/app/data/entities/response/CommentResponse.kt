package com.baims.app.data.entities.response

import com.google.gson.annotations.SerializedName

class CommentResponse : BaseResponse() {
    var subscribed: Boolean = false
    @SerializedName("state_add")
    var added: Int = 0
}
