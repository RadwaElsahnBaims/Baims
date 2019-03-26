package com.baims.app.presentation.categories;

import com.baims.app.data.entities.Category;


import java.util.List;

/**
 * Created by Radwa Elsahn on 12/4/2018.
 */
public interface CategoriesView {
    void showCategories(List<Category> categories);

    void showProgress(boolean show);

    void showError(String error);

}
