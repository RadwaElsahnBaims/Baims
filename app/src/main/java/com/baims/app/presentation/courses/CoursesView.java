package com.baims.app.presentation.courses;

import com.baims.app.data.entities.Course;

import java.util.List;

/**
 * Created by Radwa Elsahn on 10/16/2018.
 */
interface  CoursesView {
    void showData(List<Course> courses);

    void showProgress(boolean show);

    void showError(String error);
}
