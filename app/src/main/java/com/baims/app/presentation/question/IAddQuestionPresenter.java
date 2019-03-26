package com.baims.app.presentation.question;

import com.baims.app.data.entities.Course;
import com.baims.app.data.entities.Question;

/**
 * Created by Radwa Elsahn on 11/19/2018.
 */
public interface IAddQuestionPresenter {

    void addComment(String content, Double stateHiden, String displayName, String emoji, String image, String video, String audio, String type, String userEmail, String userName,String time);

    void setCourse(Course course);

    void setIsReply(boolean booleanExtra);

    void setQuestion(Question question);
    void setLectureLink(String link);
    void uploadImage(String link, String content, String linkParent, Double stateHidden, String displayName, String emoji, String image, String video, String audio, String type, String userEmail, String userName,String time) ;
    void setTime(String time);

    String getTime();
}
