package com.baims.app.data.entities.response

import com.baims.app.data.entities.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse : BaseResponse() {

    var data: User? = null
}
