package com.baims.app.data.entities.response

import com.baims.app.data.entities.BundleCourse
import com.baims.app.data.entities.User

/**
 * Created by Radwa Elsahn on 10/22/2018.
 */
class BundleInfoResponse : BaseResponse() {
    var color: String? = ""
    var user: User? = null
    var login: Int? = null
    var data: List<BundleCourse>? = null

}

