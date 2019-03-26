package com.baims.app.data.entities.response

import com.baims.app.data.entities.Course
import com.baims.app.data.entities.User

/**
 * Created by Radwa Elsahn on 10/17/2018.
 */
class CoursesResponse : BaseResponse() {

    var user: User? = null
    var login: Int = 0
    var data: List<Course>? = null
}
