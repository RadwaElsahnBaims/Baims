package com.baims.app.presentation.search;

import com.baims.app.data.entities.BundleCourse;
import com.baims.app.data.entities.Course;

import java.util.List;

/**
 * Created by Radwa Elsahn on 10/16/2018.
 */
interface SearchView {
    void showData(List<BundleCourse> courses);

    void showProgress(boolean show);

    void showError(String error);

    void showNoResult();
}
