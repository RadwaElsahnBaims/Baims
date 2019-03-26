package com.baims.app.data.entities.response

import com.baims.app.data.entities.Course

class CourseDetailsResponse : BaseResponse() {
    var login: Int = 0
    var data: Course? = null
}