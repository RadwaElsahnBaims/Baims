package com.baims.app.presentation.coursedetails;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.constraint.Group;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.baims.app.App;
import com.baims.app.R;
import com.baims.app.data.entities.Chapter;
import com.baims.app.data.entities.ChapterTag;
import com.baims.app.data.entities.Course;
import com.baims.app.data.entities.Lecture;
import com.baims.app.data.entities.Question;
import com.baims.app.data.repositories.BaimsRemoteDataSource;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.repository.BaimsCacheDataSource;
import com.baims.app.data.utils.schedulers.BaseSchedulerProvider;
import com.baims.app.data.utils.schedulers.SchedulerProvider;
import com.baims.app.presentation.common.BaseActivity;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.common.ListItemListener;
import com.baims.app.presentation.common.ListItemListener2;
import com.baims.app.presentation.common.SubscriptionNotifier;
import com.baims.app.presentation.question.AddQuestionActivity;
import com.baims.app.presentation.question.QuestionClickListener;
import com.baims.app.presentation.sectiondetails.TagsAdapter;
import com.baims.app.presentation.subscribe.SubscribeDialog;
import com.baims.app.presentation.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

public class CourseDetailsActivity extends BaseActivity implements CourseDetailsView, QuestionClickListener {
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.iv_image_course)
    ImageView mIvImage;
    @BindView(R.id.tv_lecture_name)
    TextView mTvLectureName;
    @BindView(R.id.tv_section_name)
    TextView mTvSectionName;
    //    @BindView(R.id.tv_course_name)
//    TextView mTvCourseName;
    @BindView(R.id.tv_chapter_name_questions)
    TextView mTvChapterName;
    @BindView(R.id.tv_cost)
    TextView mTvCost;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_file)
    TextView mTvFile;
    @BindView(R.id.tv_videos)
    TextView mTvVideos;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_instructor_name)
    TextView mTvInstructorName;
    @BindView(R.id.tv_details)
    TextView mTvDetails;
    @BindView(R.id.tv_questions)
    TextView mTvQuestions;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_total_time)
    TextView mTvTotalTime;
    @BindView(R.id.tv_lecture_name_ques)
    TextView mTvLectureNameQues;

    @BindView(R.id.btn_subscribe)
    Button mBtnSubscribe;

    @BindView(R.id.tv_no_questions)
    TextView mTvNoQuestions;

    @BindView(R.id.view_details)
    View mViewDetails;
    @BindView(R.id.view_questions)
    View mViewQuestions;
    @BindView(R.id.view_subscribe)
    View mViewSubscribe;

    @BindView(R.id.group1)
    Group group1;
    @BindView(R.id.group_desc)
    Group groupDesc;

    @BindView(R.id.iv_instructor)
    ImageView mIvImageInstructor;

    @BindView(R.id.rv_questions)
    RecyclerView mRcQuestions;
    @BindView(R.id.rc_chapters)
    RecyclerView mRcChapters;
    @BindView(R.id.rc_tags)
    RecyclerView mRcTags;

    @BindView(R.id.video_view)
    VideoView mVideoView;

    @BindView(R.id.tv_show_more_details)
    ImageView mTvShowMore;

    @BindView(R.id.group_header_image)
    Group groupHeaderImage;

    CourseDetailsPresenter presenter;
    private String color;
    private String sectionName;
    private String link;
    private String commentsLink;
    private List<ChapterTag> tags;
    private static final String PLAYBACK_TIME = "play_time";
    int duration = 0;

    @BindView(R.id.view_intro)
    ConstraintLayout main_layout;
    @BindView(R.id.iv_question)
    ImageView iv_question;

    private int mCurrentPosition = 0;
    MediaController controller;

    boolean fromBack = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_course_details);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        color = getIntent().getStringExtra(Constants.KEY_COLOR);
        sectionName = getIntent().getStringExtra(Constants.KEY_SECTION_NAME);
        link = getIntent().getStringExtra(Constants.KEY_EXTRA_COURSE_LINK);
        commentsLink = link;
        if (color == null || color.isEmpty())
            color = "#000000";
        presenter = new CourseDetailsPresenterImpl(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(this)), this);
        presenter.getCourseChapters(link);

        Drawable drawable = getResources().getDrawable(R.drawable.bottom_line);
        drawable.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);
        mTvDetails.setBackground(drawable);

        initPlayerNormalVideo();

//        initializePlayer();
//        initExoPlayer("");

    }


    private void initPlayerNormalVideo() {
        controller = new MediaController(CourseDetailsActivity.this);

        controller.setMediaPlayer(mVideoView);
        controller.setAnchorView(mVideoView);
        mVideoView.setMediaController(controller);
        mVideoView.setVisibility(View.VISIBLE);

        presenter.getCourseDetails(link);

    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.getCourseComments(commentsLink, Constants.COMMENTS_KEY);

//        if(presenter.getCourse().isSubscribed())
//            showSubscribe(false);
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        fromBack = true;
    }

    @Override
    public void showCommonDetails(Course course) {

        DisplayMetrics display = getResources().getDisplayMetrics();

        Glide.with(this)
                .load(course.getImage())
//                .apply(new RequestOptions().override(display.widthPixels, Utils.convertDpToPixel(150,this)))

                //.placeholder(R.drawable.default_placeholder)
                .into(mIvImage);

        if (sectionName == null || sectionName.isEmpty()) {
            mTvSectionName.setText(course.getName());
        } else
            mTvSectionName.setText(sectionName);

        mTvLectureNameQues.setText(course.getName());
        mTvChapterName.setText(sectionName);


        if (color.isEmpty())
            color = "#000000";

        mTvChapterName.setBackgroundColor(Color.parseColor(color));

        if (TextUtils.isEmpty(course.getContent()) && TextUtils.isEmpty(course.getDescription()))
            groupDesc.setVisibility(View.GONE);
        else
            mTvDesc.setText(course.getContent().isEmpty() ? course.getDescription() : course.getContent());


//        mBtnSubscribe.setBackgroundColor(Color.parseColor(color));
        mBtnSubscribe.setBackground(Utils.createRoundedBg(getApplicationContext(), color));

        Log.i("cost", String.format(getString(R.string.currency), course.getTotalPrice()));
        mTvCost.setText(course.getTotalPrice().equals("0") ? getString(R.string.free) : String.format(getString(R.string.currency), course.getTotalPrice()));

        mTvTime.setText(course.getTimeVideos());// radwa to put the video intro time when eman add it
        mTvTitle.setText(getString(R.string.course_intro));

        main_layout.setBackgroundColor(Color.parseColor("#33" + color.substring(1, color.length())));
//        main_layout.setBackgroundColor(getResources().getColor(R.color.colorHint));

        // mTvPrice.setText(getString(R.string.free));
        mTvPrice.setVisibility(View.GONE);

        tags = getIntent().getParcelableArrayListExtra(Constants.KEY_TAGS);
        TagsAdapter tagsAdapter = new TagsAdapter(this, color);
        tagsAdapter.setList(tags);
        mRcTags.setAdapter(tagsAdapter);

//        if (fromBack && presenter.isFile())
//            onIntroClick();
//        onPlayClick();
    }


    @Override
    public void showFreeData(Course course) {

//        if (course.getTotalFile() == 0)
//            mTvFile.setVisibility(View.GONE);
//        else
        mTvFile.setText(String.format(getString(R.string.file), course.getTotalFile()));


//        if (course.getTotalVideo() == 0)
//            mTvVideos.setVisibility(View.GONE);
//        else
        mTvVideos.setText(String.format(getString(R.string.videos), course.getTotalVideo()));

        if (course.getInstructor().isEmpty())
            mTvInstructorName.setVisibility(View.GONE);
        else
            mTvInstructorName.setText(course.getInstructor());

        if (course.getTimeVideos().isEmpty())
            mTvTotalTime.setVisibility(View.GONE);
        else
            mTvTotalTime.setText(course.getTimeVideos());

        if (!course.getInstructorImage().isEmpty())
            Glide.with(this)
                    .load(course.getInstructorImage())
                    .apply(RequestOptions.circleCropTransform())
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(Constants.roundCurve))
                            .placeholder(R.drawable.instructor_default)
                    )
                    //  .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(50)))
                    .into(mIvImageInstructor);
        else
            mIvImageInstructor.setVisibility(View.GONE);
    }


    @Override
    public void showSubscribe(boolean show) {
        mViewSubscribe.setVisibility(show ? View.VISIBLE : View.GONE);
        if (!show)
            presenter.getCourse().setSubscribed(true);
    }

    @Override
    public void showFreeCost() {
//        mTvCost.setText(getString(R.string.free));
//        mTvCost.setBackground(getResources().getDrawable(R.drawable.bg_curved_white_primary_border));

    }

    @Override
    public void showVideo(String videoUrl, String link) {
//1
        //        Intent intent = new Intent(CourseDetailsActivity.this, VideoActivity.class);
//        intent.putExtra(Constants.KEY_VIDEO, videoUrl);
//        intent.putExtra(Constants.KEY_LECTURE_LINK, link);
//        //intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
//
//        startActivity(intent);


        //3
//        Intent intent = new Intent(CourseDetailsActivity.this, Browser.class);
//        intent.putExtra(Browser.URL_TO_LOAD, videoUrl);
//        startActivity(intent);

//2
        mIvImage.setVisibility(View.GONE);
        iv_question.setVisibility(View.VISIBLE);
        initializePlayer(videoUrl);
        //4

        groupHeaderImage.setVisibility(View.GONE);
        Log.i("videoUrl", videoUrl);

//        initExoPlayer(videoUrl);
//        fullscreenVideoView.videoUrl(videoUrl);
//        fullscreenVideoView.enableAutoStart();

    }


    @Override
    public void showNoQuestions() {
        mRcQuestions.setVisibility(View.GONE);
        mTvNoQuestions.setVisibility(View.VISIBLE);
    }

    @Override
    public void openPdf(String file) {
        Intent intent = new Intent(CourseDetailsActivity.this, Browser.class);
        intent.putExtra("URL_TO_LOAD", file);
        startActivity(intent);
//        startActivity(new Intent(CourseDetailsActivity.this,PDFActivity.class));
    }

    @OnClick(R.id.iv_question)
    public void onAddQuestionClick() {
        if (presenter.getCourse().isSubscribed()) {
            String time = "0";

            if (mVideoView.isPlaying() && duration > 0)
                time = (mVideoView.getCurrentPosition() * 100 / duration) + "";

            Log.i("time",(mVideoView.getCurrentPosition() / 1000 ) +"");
            Log.i("time",(mVideoView.getCurrentPosition() ) +"");
            Log.i("duration",(duration ) +"");

            Intent intent = new Intent(CourseDetailsActivity.this, AddQuestionActivity.class);
            intent.putExtra(Constants.KEY_EXTRA_COURSE, presenter.getCourse());
            intent.putExtra(Constants.KEY_EXTRA_LECTURE_LINK, presenter.getLecture().getLec_link());
//            intent.putExtra(ConstantsKEY_EXTRA_LECTURE_LINK.KEY_QUESTION_TIME, time);
            intent.putExtra(Constants.KEY_QUESTION_TIME, (mVideoView.getCurrentPosition() / 1000 ) + "");
            startActivity(intent);
        } else {
            showDialog(this, "برجاء الاشتراك");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        // player.release();
        //fullscreenVideoView = null;
    }

    @Override
    public void showQuestions(List<Question> questionList) {
        if (presenter.getCourse().isSubscribed()) {
            mRcQuestions.setVisibility(View.VISIBLE);
            mTvNoQuestions.setVisibility(View.GONE);
            QuestionsAdapter adapter = new QuestionsAdapter(this, color);
            adapter.setList(questionList);
            adapter.setOnClickListener(this);
            mRcQuestions.setAdapter(adapter);

            Log.i("childComments", new Gson().toJson(questionList.get(0).getChildComments()));
        } else {
            showDialog(this, "برجاء الاشتراك");
        }
    }

    @Override
    public void showChapters(List<Chapter> chapters) {
        if (chapters != null && chapters.size() > 0) {
            ChaptersAdapter adapter = new ChaptersAdapter(this, color);
            adapter.setList(chapters);
//            mTvLectureNameQues.setText(chapters.get(0).getName());

            adapter.setOnClickListener(new ListItemListener2<Lecture>() {

                @Override
                public void itemListener(Lecture lecture, String chapterName) {
                    mVideoView.stopPlayback();

                    mTvLectureNameQues.setText(lecture.getName());
                    mTvChapterName.setText(chapterName);

                    mTvLectureName.setText(lecture.getName());
                    commentsLink = lecture.getLec_link();
                    presenter.setSelectedLecture(lecture);
                    presenter.openLecture();
                    main_layout.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
            });

            mRcChapters.setAdapter(adapter);
        } else
            mRcChapters.setVisibility(View.GONE);

//
    }

    @Override
    public void showProgress(boolean show) {
        showProgressBar(progressBar, show, this);
    }

    @Override
    public void showError(String error) {
        showDialog(this, error);
    }


    @OnClick(R.id.tv_details)
    public void onDetailsClick() {
        mViewQuestions.setVisibility(View.GONE);
        mViewDetails.setVisibility(View.VISIBLE);

        mTvQuestions.setBackground(null);
//        mTvDetails.setBackground(getResources().getDrawable(R.drawable.bottom_line));
        Drawable drawable = getResources().getDrawable(R.drawable.bottom_line);
        drawable.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);
        mTvDetails.setBackground(drawable);

    }

    @OnClick(R.id.tv_questions)
    public void onQuestionsClick() {
        presenter.getCourseComments(commentsLink, Constants.COMMENTS_KEY);
        mViewDetails.setVisibility(View.GONE);
        mViewQuestions.setVisibility(View.VISIBLE);

        mTvDetails.setBackground(null);
//        mTvQuestions.setBackground(getResources().getDrawable(R.drawable.bottom_line));
        Drawable drawable = getResources().getDrawable(R.drawable.bottom_line);
        drawable.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);
        mTvQuestions.setBackground(drawable);
    }


    private BaseSchedulerProvider schedulerProvider;
    @NonNull
    private CompositeSubscription subscriptions;


    @OnClick(R.id.btn_subscribe)
    public void onSubscribeClick() {
        if (presenter.isLogin()) {

            SubscriptionNotifier.getInstance().subscribe(show -> showSubscribe(show));
            /////
            mVideoView.pause();

            Course course = presenter.getCourse();
            // if (course.hasPromoCode()) {
            FragmentManager fm = getSupportFragmentManager();
            SubscribeDialog subscribeDialog = SubscribeDialog.newInstance("Baims");

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(Constants.KEY_TAGS, (ArrayList<? extends Parcelable>) tags);
            bundle.putString(Constants.KEY_COLOR, color);
            bundle.putString(Constants.KEY_EXTRA_COURSE_NAME, course.getName());
            bundle.putString(Constants.KEY_EXTRA_COURSE_PRICE, course.getPrice());
            bundle.putBoolean(Constants.KEY_EXTRA_COURSE_HAS_PROMOCODE, course.hasPromoCode());
            bundle.putString(Constants.KEY_EXTRA_COURSE_LINK, course.getLink());
            subscribeDialog.setArguments(bundle);

            subscribeDialog.show(fm, "Baims");

        } else {
            showDialog(this, "برجاء تسجيل الدخول");
        }


        //   }ه
    }

    @OnClick({R.id.iv_play, R.id.tv_lecture_name})
    public void onPlayClick() {
        mIvImage.setVisibility(View.GONE);
        presenter.watchVideo();
    }

    @OnClick({R.id.view_intro})
    public void onIntroClick() {
        main_layout.setBackgroundColor(Color.parseColor("#33" + color.substring(1, color.length())));
        presenter.setSelectedLecture(null);

        presenter.openLecture();
    }

    @OnClick(R.id.tv_show_more_details)
    public void onShowMoreDetails() {
        if (group1.getVisibility() == View.VISIBLE) {
            mTvShowMore.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_arrow));
            group1.setVisibility(View.GONE);
        } else {
            mTvShowMore.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow));
            group1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onReplyClick(Question question) {
        Intent intent = new Intent(CourseDetailsActivity.this, AddQuestionActivity.class);
        Log.i("courseRad",new Gson().toJson(presenter.getCourse()));
        intent.putExtra(Constants.KEY_EXTRA_COURSE, presenter.getCourse());
        intent.putExtra(Constants.KEY_EXTRA_LECTURE_LINK, presenter.getLecture().getLec_link());
        intent.putExtra(Constants.KEY_EXTRA_QUESTION, question);

        Log.i("time",(mVideoView.getCurrentPosition() / 1000 ) +"");
        Log.i("time",(mVideoView.getCurrentPosition() ) +"");
        Log.i("duration",(duration ) +"");
        if (duration > 0)
            intent.putExtra(Constants.KEY_QUESTION_TIME, (mVideoView.getCurrentPosition() / 1000 ) + "");
        else
            intent.putExtra(Constants.KEY_QUESTION_TIME, "0");
        startActivity(intent);
    }

    @Override
    public void onLikeClick(Question question, int index) {

        presenter.addDelLike(question.getLink(), index);

    }

    @Override
    public void onImageClick(String imageUrl) {

        FragmentManager fm = getSupportFragmentManager();
        BigImageDialog bigImageDialog = BigImageDialog.newInstance("Baims", imageUrl);
        bigImageDialog.show(fm, "Baims");
    }


    private void initializePlayer(String videoUrl) {
        Log.i("video", videoUrl);
        //mBufferingTextView.setVisibility(VideoView.VISIBLE);
        mVideoView.setVisibility(View.VISIBLE);
        groupHeaderImage.setVisibility(View.GONE);

        // Buffer and decode the video sample.
        Uri videoUri = getMedia(videoUrl);
        mVideoView.setVideoURI(videoUri);

        // Listener for onPrepared() event (runs after the media is prepared).
        mVideoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        duration = mVideoView.getDuration();
                        // Hide buffering message.
//                        mBufferingTextView.setVisibility(VideoView.INVISIBLE);

                        // Restore saved position, if available.
                        if (mCurrentPosition > 0) {
                            mVideoView.seekTo(mCurrentPosition);
                        } else {
                            // Skipping to 1 shows the first frame of the video.
                            mVideoView.seekTo(1);
                        }

                        // Start playing!
                        mVideoView.start();
//
//                        int height2 = controller.getHeight();
//                        Log.i("height controller2", height2 + "");
//                        Constraints.LayoutParams params = new Constraints.LayoutParams(
//                                Constraints.LayoutParams.WRAP_CONTENT,
//                                Constraints.LayoutParams.WRAP_CONTENT
//                        );
//                        params.setMargins(0, height2, 0, 0);
//                        mVideoView.setLayoutParams(params);

                    }
                });

        // Listener for onCompletion() event (runs after media has finished
        // playing).
        mVideoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(CourseDetailsActivity.this,
                                "done",
                                Toast.LENGTH_SHORT).show();

                        // Return the video position to the start.
                        mVideoView.seekTo(0);
                    }
                });
//        mVideoView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                togglePlaying();
//                return true;
//            }
//        });
    }


    public void togglePlaying() {
        if (mVideoView.isPlaying())
            mVideoView.stopPlayback();
        else
            mVideoView.start();

    }

    // Release all media-related resources. In a more complicated app this
    // might involve unregistering listeners or releasing audio focus.
    private void releasePlayer() {
        mVideoView.stopPlayback();
    }

    // Get a Uri for the media sample regardless of whether that sample is
    // embedded in the app resources or available on the internet.
    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // Media name is an external URL.
            return Uri.parse(mediaName);
        } else {
            // Media name is a raw resource embedded in the app.
            return Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + mediaName);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
//        releasePlayer();
        // In Android versions less than N (7.0, API 24), onPause() is the
        // end of the visual lifecycle of the app.  Pausing the video here
        // prevents the sound from continuing to play even after the app
        // disappears.
        //
        // This is not a problem for more recent versions of Android because
        // onStop() is now the end of the visual lifecycle, and that is where
        // most of the app teardown should take place.
//        ii
//        if (Util.SDK_INT <= 23) {
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mVideoView.pause();
        // Media playback takes a lot of resources, so everything should be
        // stopped and released at this time.
//        if (Util.SDK_INT <= 23) {
//            releasePlayer();
//        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }


//    @Override
//    public void onVideoEnabled(DecoderCounters counters) {
//
//    }
//
//    @Override
//    public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs,
//                                          long initializationDurationMs) {
//
//    }
//
//    @Override
//    public void onVideoInputFormatChanged(Format format) {
//
//    }
//
//    @Override
//    public void onDroppedFrames(int count, long elapsedMs) {
//
//    }
//
//    @Override
//    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees,
//                                   float pixelWidthHeightRatio) {
//
//    }
//
//    @Override
//    public void onRenderedFirstFrame(@Nullable Surface surface) {
//
//    }
//
//    @Override
//    public void onVideoDisabled(DecoderCounters counters) {
//
//    }

    @OnClick(R.id.iv_fullscreen)
    public void toggleFullscreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        View v = findViewById(R.id.view_other);
        // Checking the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("configuratino", "landscape");
            v.setVisibility(View.GONE);

            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) mVideoView.getLayoutParams();
            params.width = width;//params.MATCH_PARENT;
            params.height = height;//params.MATCH_PARENT;
            Log.i("height", params.height + "");

            mVideoView.setLayoutParams(params);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //unhide your objects here.
            v.setVisibility(View.VISIBLE);
            Log.i("configuratino", "portrait");
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) mVideoView.getLayoutParams();
            params.width = width;//params.MATCH_PARENT;
            params.height = Utils.convertDpToPixel(getResources().getDimension(R.dimen._150sdp), this);
            mVideoView.setLayoutParams(params);
        }
    }

}
