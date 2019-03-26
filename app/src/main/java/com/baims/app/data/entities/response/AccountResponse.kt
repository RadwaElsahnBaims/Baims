package com.baims.app.data.entities.response

import com.baims.app.data.entities.RegisterInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AccountResponse : BaseResponse() {

    var data: RegisterInfo? = null
}