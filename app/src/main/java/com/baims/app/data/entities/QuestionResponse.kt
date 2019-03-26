package com.baims.app.data.entities

import com.google.gson.annotations.SerializedName

data class QuestionResponse(
        var audio: Any,
        var child_comments: List<ChildComment>,
        var content: String,
        var created_at: CreatedAt,
        var image: Any,
        var like: Boolean,
        var link: String,
        var num_like: String,
        var owner_question: String,
        var parent_id: Int,
        var parent_state_hiden: Int,
        var parent_user_image: String,
        var parent_user_name: String,
        var time_task: String,
        var video: Any,

        var replay_id: Int,
        var replay_state_hiden: Int,
        var replay_user: Int,
        var replay_user_image: String,
        var replay_user_name: String

)