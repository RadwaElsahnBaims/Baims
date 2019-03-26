package com.baims.app.presentation.sectiondetails;

import com.baims.app.data.entities.BundleCourse;

import java.util.List;

/**
 * Created by Radwa Elsahn on 10/22/2018.
 */
interface SectionDetailsView {
    void showData(List<BundleCourse> courses,String color);

    void showProgress(boolean show);

    void showError(String error);
}
