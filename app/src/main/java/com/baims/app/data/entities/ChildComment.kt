package com.baims.app.data.entities

data class ChildComment(
        var audio: Any,
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
        var replay_id: Int,
        var replay_state_hiden: Int,
        var replay_user: Int,
        var replay_user_image: String,
        var replay_user_name: String,
        var video: Any
)