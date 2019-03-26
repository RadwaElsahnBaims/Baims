package com.baims.app.data.entities.response

import com.baims.app.data.entities.Course
import com.baims.app.data.entities.User
import com.google.gson.annotations.SerializedName

class ProfileResponse : BaseResponse() {

    var user: User? = null
    @SerializedName("my_courses")
    var myCourses: List<Course>? = null
    @SerializedName("registered_courses")
    var registeredCourses: List<Course>? = null
}
