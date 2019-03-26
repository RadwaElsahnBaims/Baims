package com.baims.app.presentation.question;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.baims.app.data.entities.Course;
import com.baims.app.data.entities.Question;
import com.baims.app.data.entities.response.CommentResponse;
import com.baims.app.data.entities.response.UploadResponse;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;
import com.baims.app.presentation.common.Constants;

import java.time.Duration;
import java.time.Period;

import rx.Subscriber;

/**
 * Created by Radwa Elsahn on 11/19/2018.
 */
public class AddQuestionPresenter implements IAddQuestionPresenter {
    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;

    AddQuestionView mView;
    Course course;
    Boolean isReply;
    Question question;
    String time = "";
    String lectureLink;

    public AddQuestionPresenter(AddQuestionView view, BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository) {
        mView = view;
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void setTime(String time1) {
        this.time = time1;
    }

    @Override
    public String getTime() {
        return time;
    }


    @Override
    public void addComment(String content, Double stateHidden, String displayName, String emoji, String image, String video, String audio, String type, String userEmail, String userName, String time) {
        String linkParent = "";
        String link; //course.getLink();
        if (type.equals(Constants.TYPE_QUESTION) && course != null && !course.isSubscribed())// must subscribe to add question
        {
            mView.showError("برجاء الاشتراك");

        } else {
            if (type.equals(Constants.TYPE_COMMENT) && !baimsRepository.isLogin() && userEmail.isEmpty()) {
                mView.showEmailFeilds();
            }

            if (question != null) {// it's a reply
                linkParent = question.getLink();
                link = course.getLink();
            } else
                link = lectureLink;

            if (image != null && !image.isEmpty()) {
                uploadImage(link, content, linkParent, stateHidden, displayName, "emoji", image, video, audio, "comment", userEmail, userName, time);
            } else if (audio != null && !audio.isEmpty()) {
                uploadAudio(link, content, linkParent, stateHidden, displayName, "emoji", image, video, audio, "comment", userEmail, userName, time);
            } else
                callAddComment(link, content, linkParent, stateHidden, displayName, "emoji", image, video, audio, "comment", userEmail, userName, time);// (note,comment,question)
        }

    }

    private void uploadVideo(String link, String content, String linkParent, Double stateHidden, String displayName, String emoji, String image, String video, String audio, String comment, String userEmail, String userName, String tmime) {
        mView.showProgress(true);

        baimsRepository.uploadVideo(baimsRepository.getAccessToken(), video)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<UploadResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                        mView.showProgress(false);
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(UploadResponse commentResponse) {
                        mView.showProgress(false);
                        Log.i("success", "success");
                        if (commentResponse.getStatusCode() == 0) {
                            //uploads/32fb8571df1442fd7ec080685623248b.png

                            callAddComment(link, content, linkParent, stateHidden, displayName, "emoji", image, commentResponse.getData(), audio, "comment", userEmail, userName, time);
                        }
//                        if (commentResponse.getAdded() == 1) {
//                            mView.showSuccess("");
//                        }

                    }
                });

    }

    private void uploadAudio(String link, String content, String linkParent, Double stateHidden, String displayName, String emoji, String image, String video, String audio, String comment, String userEmail, String userName, String time) {
        mView.showProgress(true);

        baimsRepository.uploadAudio(baimsRepository.getAccessToken(), audio)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<UploadResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                        mView.showProgress(false);
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(UploadResponse commentResponse) {
                        mView.showProgress(false);
                        Log.i("success", "success");
                        if (commentResponse.getStatusCode() == 0) {
                            //uploads/32fb8571df1442fd7ec080685623248b.png

                            callAddComment(link, content, linkParent, stateHidden, displayName, "emoji", image, video, commentResponse.getData(), "comment", userEmail, userName, time);
                        }
//                        if (commentResponse.getAdded() == 1) {
//                            mView.showSuccess("");
//                        }

                    }
                });

    }


    void callAddComment(String link, String content, String linkParent, Double stateHidden, String displayName, String emoji, String image, String video, String audio, String type, String userEmail, String userName, String time) {

        mView.showProgress(true);

        time = formatSecs(time);
        Log.i("time", time);
        baimsRepository.addComment(baimsRepository.getAccessToken()
                , link, content, linkParent, stateHidden, displayName, emoji, image, video, audio, type, userEmail, userName, time)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<CommentResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                        mView.showProgress(false);
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(CommentResponse commentResponse) {
                        mView.showProgress(false);
                        if (commentResponse.getAdded() == 1) {
                            mView.showSuccess("");
                        }

                    }
                });

    }

    public String formatSecs(String seconsd) {
        int all = Integer.valueOf(seconsd);

        int mins = all / 60;
        int remainder = all - mins * 60;
        int secs = remainder;

        String formated = (mins < 10 ? "0" + mins : mins) + ":" + (secs < 10 ? "0" + secs : secs);
        return formated;
    }

    @Override
    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public void setIsReply(boolean isReply) {
        this.isReply = isReply;
    }

    @Override
    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public void uploadImage(String link, String content, String linkParent, Double stateHidden, String displayName, String emoji, String image, String video, String audio, String type, String userEmail, String userName, String time) {
        mView.showProgress(true);

        baimsRepository.uploadImage(baimsRepository.getAccessToken(), image)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<UploadResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                        mView.showProgress(false);
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(UploadResponse commentResponse) {
                        mView.showProgress(false);
                        Log.i("success", "success");
                        if (commentResponse.getStatusCode() == 0) {
                            //uploads/32fb8571df1442fd7ec080685623248b.png

                            callAddComment(link, content, linkParent, stateHidden, displayName, "emoji", commentResponse.getData(), video, audio, "comment", userEmail, userName, time);
                        }
//                        if (commentResponse.getAdded() == 1) {
//                            mView.showSuccess("");
//                        }

                    }
                });

    }

    public void setLectureLink(String link) {
        if (!link.isEmpty())
            lectureLink = link;
        else
            lectureLink = course.getLink();
    }
}
