package com.baims.app.presentation.categorycourses;

import com.baims.app.data.entities.Category;

/**
 * Created by Radwa Elsahn on 12/17/2018.
 */
public interface CategoryCoursesView {
    void showCategoryCourses(Category category);

    void showProgress(boolean show);

    void showError(String error);
    void showNoResult();

}
