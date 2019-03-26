package com.baims.app.presentation.profile;

import com.baims.app.data.entities.Course;
import com.baims.app.data.entities.User;

import java.util.List;

public interface ProfileView {
    void showProfileInfo(User user);

    void showProgress(boolean show);

    void showError(String error);

    void showNoCourses();

    void showCourses(List<Course> my_courses, List<Course> registered_courses, String sectionName);
}
