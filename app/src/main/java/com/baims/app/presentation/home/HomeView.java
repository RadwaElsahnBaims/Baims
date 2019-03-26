package com.baims.app.presentation.home;

import com.baims.app.data.entities.Section;

import java.util.List;

/**
 * Created by Radwa Elsahn on 10/18/2018.
 */
interface HomeView {
    void showData(List<Section> sections);

    void showProgress(boolean show);

    void showError(String error);
}
