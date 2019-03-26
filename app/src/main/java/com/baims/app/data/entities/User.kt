package com.baims.app.data.entities

class User {

    var display_name: String? = ""
    var email: String? = ""
    var image: String? = ""
    var phone: String? = ""
    private var is_agreed: Int = 0
    var access_token: String = ""
    var old_fcm_token: String? = ""
    var num_watched: String? = ""

    var isIs_agreed: Boolean
        get() = if (is_agreed == 1) true else false
        set(is_agreed) {
            this.is_agreed = if (is_agreed) 1 else 0
        }
}
