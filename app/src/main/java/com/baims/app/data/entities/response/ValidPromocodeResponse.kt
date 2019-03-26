package com.baims.app.data.entities.response

import com.google.gson.annotations.SerializedName

class ValidPromocodeResponse : BaseResponse() {
    @SerializedName("valid_promocode")
    var isValidPromocode: Boolean = false
    @SerializedName("course_discount")
    var courseDiscount: String = "0.0"
    @SerializedName("course_price")
    var coursePrice: String = ""
    var totalPrice: String = ""
    
}