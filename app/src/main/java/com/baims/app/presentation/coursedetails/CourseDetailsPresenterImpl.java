package com.baims.app.presentation.coursedetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.baims.app.R;
import com.baims.app.data.entities.Course;
import com.baims.app.data.entities.Lecture;
import com.baims.app.data.entities.response.BaseResponse;
import com.baims.app.data.entities.response.ChaptersResponse;
import com.baims.app.data.entities.response.CommentsResponse;
import com.baims.app.data.entities.response.CourseDetailsResponse;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.common.LikedNotifier;
import com.baims.app.presentation.common.SubscriptionNotifier;
import com.google.gson.Gson;

import rx.Subscriber;

/**
 * Created by Radwa Elsahn on 10/16/2018.
 */
public class CourseDetailsPresenterImpl implements CourseDetailsPresenter {
    private CourseDetailsView mView;

    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private BaimsRepository baimsRepository;

    private int pageNumber;
    Lecture lectureIntro;
    private Course course;
    boolean isIntro = true;

    private Lecture lecture;

    Context mContext;

    public CourseDetailsPresenterImpl(CourseDetailsView view, BaseSchedulerProvider schedulerProvider, BaimsRepository baimsRepository, Context mcontext) {
        mView = view;
        this.baimsRepository = baimsRepository;
        this.schedulerProvider = schedulerProvider;
        mContext = mcontext;
        this.setPageNumber(0);
    }

    @Override
    public void getCourseDetails(String link) {
        mView.showProgress(true);

        baimsRepository.getCourseDetails(baimsRepository.getAccessToken(), link)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<CourseDetailsResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                        mView.showProgress(false);
                        mView.showError(mContext.getString(R.string.try_again));
                    }

                    @Override
                    public void onNext(CourseDetailsResponse courseDetailsResponse) {
                        mView.showProgress(false);
                        setPageNumber(getPageNumber() + 1);
                        if (courseDetailsResponse.getStatusCode() == 0) {
                            setCourse(courseDetailsResponse.getData());
                            lectureIntro = getLectureIntro();
                            lecture = lectureIntro;
                            mView.showCommonDetails(getCourse());

                            if (course.isBuy())
                                mView.showSubscribe(false);
                            else
                                mView.showSubscribe(true);
                            mView.showFreeData(getCourse());
                            handleCourseSubscription(getCourse());

                        } else {
                          //  mView.showError(mContext.getString(R.string.try_again));
//                            mView.showError(courseDetailsResponse.getMessage());
                        }
                    }
                });
    }

    public Lecture getLectureIntro() {
        if (getCourse() != null) {
            isIntro = true;
            lectureIntro = new Lecture();
            lectureIntro.setVideo(getCourse().getVideo());
            lectureIntro.setVideoMob(getCourse().getVideoMob());
            lectureIntro.setVideoLink(getCourse().getVideoLink());
            return lectureIntro;
        }
        return null;
    }

    @Override
    public void addWatch(String videoLink) {
        if (!videoLink.isEmpty())
            if (baimsRepository.isLogin()) {
                // mView.showProgress(true);

                baimsRepository.addWatch(baimsRepository.getAccessToken(), videoLink)
                        .observeOn(schedulerProvider.ui())
                        .subscribeOn(schedulerProvider.computation())
                        .retry(Constants.RETRY_COUNT)
                        .subscribe(new Subscriber<BaseResponse>() {
                            @Override
                            public void onCompleted() {
                                mView.showProgress(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("error", e.toString());
                                mView.showProgress(false);
                               // mView.showError(mContext.getString(R.string.try_again));
                            }

                            @Override
                            public void onNext(BaseResponse response) {
                                mView.showProgress(false);
                            }
                        });
            }
//        else
//            mView.showLogin();
    }

    @Override
    public void addDelLike(String link, int index) {
        if (baimsRepository.isLogin()) {
            // mView.showProgress(true);

            baimsRepository.addDelLike(baimsRepository.getAccessToken(), link, "comment")
                    .observeOn(schedulerProvider.ui())
                    .subscribeOn(schedulerProvider.computation())
                    .retry(Constants.RETRY_COUNT)
                    .subscribe(new Subscriber<BaseResponse>() {
                        @Override
                        public void onCompleted() {
                            mView.showProgress(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", e.toString());
                            mView.showProgress(false);
                            mView.showError(mContext.getString(R.string.try_again));//e.getMessage());
                        }

                        @Override
                        public void onNext(BaseResponse response) {
                            mView.showProgress(false);
                            //  LikedNotifier.getInstance().post(index);// show subsription
                        }
                    });
        }
//        else
//            mView.showLogin();
    }

    @Override
    public boolean isFile() {
        return !lecture.getFile().isEmpty();
    }

    @Override
    public void watchVideo() {

        if (lecture == null)// intro
        {
            lecture = lectureIntro;
            isIntro = true;
        } else
            isIntro = false;

        String link = lecture.getVideoLink();
        if (isIntro || course.isBuy() || lecture.isFree() || course.isSubscribed()) {
//            if (!lecture.getFile().isEmpty()) {
//                mView.openPdf(lecture.getFile());
//            } else {
                //    course.isFree() || lecture.getTypeCost().equals(Constants.FREE)
                if (lecture != null && !lecture.getVideo().isEmpty()) {
                    mView.showVideo(lecture.getVideo(), link);
                    addWatch(lecture.getVideoLink());
                }
//            }
        } else
            mView.showError("برجاء الاشتراك");
    }

    @Override
    public void openLecture()
    {
        if (lecture == null)// intro
        {
            lecture = lectureIntro;
            isIntro = true;
        } else
            isIntro = false;

        String link = lecture.getVideoLink();
        if (isIntro || course.isBuy() || lecture.isFree() || course.isSubscribed()) {
            if (!lecture.getFile().isEmpty()) {
                mView.openPdf(lecture.getFile());
            } else {
                //    course.isFree() || lecture.getTypeCost().equals(Constants.FREE)
                if (lecture != null && !lecture.getVideo().isEmpty()) {
                    mView.showVideo(lecture.getVideo(), link);
                    addWatch(lecture.getVideoLink());
                }
            }
        } else
            mView.showError("برجاء الاشتراك");
    }

    @Override
    public void setSelectedLecture(Lecture lecture) {
        this.lecture = lecture;
    }


    private void handleCourseSubscription(Course course) {


        if (course.isFree())
            mView.showFreeCost();


//        if(course.isFree() || course.isBuy())
//        {
//
//        }
//
//        if (course.isFree() || course.isBuy() )
//            mView.hideMoreDetails();
    }

    @Override
    public void getCourseComments(String link, String type) {
//        mView.showProgress(true);

        baimsRepository.getCourseComments(baimsRepository.getAccessToken(), link, type, 20)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<CommentsResponse>() {
                    @Override
                    public void onCompleted() {
//                        mView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                     //   mView.showProgress(false);
                       // mView.showError(mContext.getString(R.string.try_again));
                    }

                    @Override
                    public void onNext(CommentsResponse commentsResponse) {
                        mView.showProgress(false);
                        setPageNumber(getPageNumber() + 1);
                        if (commentsResponse.getStatusCode() == 0) {

                            if (Integer.parseInt(commentsResponse.getCountQuestions()) > 0)
                                mView.showQuestions(commentsResponse.getQuestions());
                            else
                                mView.showNoQuestions();

                        } else {
                            mView.showError(commentsResponse.getMessage());
                            mView.showNoQuestions();
                        }
                    }
                });

    }

    @Override
    public void getCourseChapters(String link) {
        mView.showProgress(true);

        baimsRepository.getCourseChapters(baimsRepository.getAccessToken(), link)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.computation())
                .retry(Constants.RETRY_COUNT)
                .subscribe(new Subscriber<ChaptersResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.showProgress(false);
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                        mView.showProgress(false);
                      //  mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ChaptersResponse chaptersResponse) {
                        Log.e("chaptersResponse", new Gson().toJson(chaptersResponse.getData()));
                        mView.showProgress(false);

                        if (chaptersResponse.getStatusCode() == 0) {
                            mView.showChapters(chaptersResponse.getData());
                        } else {
//                            mView.showError(chaptersResponse.getMessage());
                            mView.showError(mContext.getString(R.string.try_again));
                        }
                    }
                });

    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public boolean isLogin() {
        return baimsRepository.isLogin();
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    public Lecture getLecture() {
        return this.lecture;
    }

//    public void setLecture(Lecture lecture) {
//        this.lecture = lecture;
//    }
}
