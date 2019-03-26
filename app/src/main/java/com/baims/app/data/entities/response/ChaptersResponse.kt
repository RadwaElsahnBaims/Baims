package com.baims.app.data.entities.response

import com.baims.app.data.entities.Chapter

class ChaptersResponse : BaseResponse() {
    var data: List<Chapter>? = null
    var login: Int = 0
}