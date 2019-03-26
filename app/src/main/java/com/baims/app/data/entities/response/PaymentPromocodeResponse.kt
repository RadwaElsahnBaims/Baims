package com.baims.app.data.entities.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Radwa Elsahn on 11/11/2018.
 */
class PaymentPromocodeResponse : BaseResponse() {
    var response: String = "" // pay or free
    @SerializedName("type_cost")
    var typeCost: String = ""
    @SerializedName("complete_share")
    var completeShare: Boolean = false
    var discount: Double = 0.0
    @SerializedName("total_price")
    var totalPrice: Double = 0.0
    var successURL: String = ""
    var errorURL: String = ""
    @SerializedName("url_payment")
    var urlPayment: String = ""
}
