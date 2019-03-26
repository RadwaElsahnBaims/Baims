package com.baims.app.data.entities


class Login {

    lateinit var email_user_name: String
    lateinit var password: String
    lateinit var device_id: String
    var reg_site: String = "android"

    override fun toString(): String {
        return "Login{" +
                "email='" + email_user_name + '\''.toString() +
                ", password='" + password + '\''.toString() +
                '}'.toString()
    }
}
