package com.baims.app.presentation.question;

/**
 * Created by Radwa Elsahn on 11/19/2018.
 */
interface AddQuestionView {

    void showProgress(boolean show);

    void showError(String error);

    void showSuccess(String message);

    void showEmailFeilds();
}
