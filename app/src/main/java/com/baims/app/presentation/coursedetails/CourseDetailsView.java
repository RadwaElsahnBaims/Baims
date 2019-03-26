package com.baims.app.presentation.coursedetails;

import com.baims.app.data.entities.Chapter;
import com.baims.app.data.entities.Course;
import com.baims.app.data.entities.Question;

import java.util.List;

/**
 * Created by Radwa Elsahn on 10/16/2018.
 */
public interface CourseDetailsView {
    void showCommonDetails(Course course);

    void showProgress(boolean show);

    void showError(String error);

    void showQuestions(List<Question> questionList);

    void showChapters(List<Chapter> chapters);

    void showFreeData(Course course);

    void showSubscribe(boolean show);

    void showFreeCost();

    void showVideo(String videoUrl,String link);

    void showNoQuestions();

    void openPdf(String file);
}
