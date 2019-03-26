package com.baims.app.data.entities


class Register {

    lateinit var email: String
    lateinit var display_name: String
    lateinit var phone: String
    lateinit var password: String
    lateinit var device_id: String
    var reg_site: String = "android"

    override fun toString(): String {
        return "Register{" +
                "email='" + email + '\''.toString() +
                ", firstname='" + display_name + '\''.toString() +
                ", mobile='" + phone + '\''.toString() +
                ", password='" + password + '\''.toString() +
                '}'.toString()
    }
}
