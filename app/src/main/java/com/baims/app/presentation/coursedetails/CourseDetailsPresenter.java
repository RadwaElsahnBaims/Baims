package com.baims.app.presentation.coursedetails;

import com.baims.app.data.entities.Course;
import com.baims.app.data.entities.Lecture;

/**
 * Created by Radwa Elsahn on 10/16/2018.
 */
interface CourseDetailsPresenter {
    void getCourseDetails(String link);

    void getCourseComments(String link, String type);

    void getCourseChapters(String link);

    void addWatch(String videoLink);

    boolean isFile();

    void watchVideo();

    void setSelectedLecture(Lecture lecture);

    void setCourse(Course course);

    Course getCourse();

    boolean isLogin();

    void addDelLike(String link, int index);

    Lecture getLecture();

    void openLecture();
}
