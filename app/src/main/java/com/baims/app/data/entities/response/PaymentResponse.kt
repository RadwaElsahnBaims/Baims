package com.baims.app.data.entities.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Radwa Elsahn on 11/11/2018.
 */
class PaymentResponse : BaseResponse() {
    var response: String = ""// "pay",
    @SerializedName("total_price")
    var typePrice: String = ""// "premium",
    @SerializedName("url_payment")
    var urlPayment: String = ""//"https://www.myfatoorah.com/pg/11112018222233933286510898"
    @SerializedName("order_link")
    var orderLink: String = ""
    var discount: Double = 0.0
}