package com.baims.app.presentation.question;

import com.baims.app.data.entities.Question;

/**
 * Created by Radwa Elsahn on 11/19/2018.
 */
public interface QuestionClickListener {
    void onReplyClick(Question question);

    void onLikeClick(Question question,int index);

    void onImageClick(String imageUrl);
}
